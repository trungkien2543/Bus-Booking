# Quy trình đánh Index cho PostgreSQL (áp dụng cho mọi query)

> **Ghi nhớ ngắn gọn:** Đo trước — gạch cột trong WHERE/JOIN — gộp composite (bằng nhau trước, khoảng sau) — đo lại xác nhận.

---

## Bước 0 — Hỏi "query này có đáng để tối ưu không"

Trước khi nghĩ tới index, tự hỏi: query này có chạy **thường xuyên** không (VD mỗi lần user bấm tìm kiếm), hay chỉ chạy 1-2 lần/tháng (VD báo cáo admin)?

Chỉ query **thường xuyên** + trên bảng **lớn** mới đáng đầu tư thời gian đánh index. Nếu chỉ là query hiếm khi chạy, bỏ qua, không cần làm gì cả.

---

## Bước 1 — Đo trước, đừng đoán

Chạy `EXPLAIN ANALYZE` trên **chính query thật** đang chậm (không phải đoán).

Đọc 2 thứ:
1. Có dòng `Seq Scan` trên bảng lớn không?
2. `Execution Time` là bao nhiêu mili-giây?

Đây là bước **bắt buộc** trước khi tạo bất kỳ index nào — không có số liệu thì không biết có thật sự cần tối ưu hay không.

```sql
EXPLAIN ANALYZE
SELECT ...
FROM ...
WHERE ...
```

---

## Bước 2 — Gạch chân cột trong WHERE / JOIN / ORDER BY / GROUP BY

Đọc lại chính câu SQL, gạch chân **mọi cột** xuất hiện sau:
- `WHERE`
- `JOIN ... ON`
- `ORDER BY`
- `GROUP BY`

Danh sách cột gạch chân đó là **toàn bộ ứng viên** cần index — không nhiều hơn, không ít hơn.

⚠️ **Lưu ý quan trọng:** cột là khóa ngoại (có `REFERENCES`) dùng trong `JOIN` thì **ưu tiên cao nhất**, vì **PostgreSQL không tự động index khóa ngoại** (khác với MySQL) — chỉ có `PRIMARY KEY` mới tự động có index.

---

## Bước 3 — Gộp thành composite index nếu có nhiều cột, đúng thứ tự

- Nếu chỉ có **1 cột** được gạch chân → tạo index 1 cột bình thường:
```sql
CREATE INDEX idx_ten_bang_ten_cot ON ten_bang (ten_cot);
```

- Nếu có **nhiều cột** cùng xuất hiện trong 1 query → gộp thành **1 composite index**, xếp theo đúng thứ tự:
  1. Cột lọc **bằng nhau** (`=`) đặt **trước**
  2. Cột lọc **khoảng** (`>=`, `<`, `BETWEEN`) hoặc dùng để `ORDER BY` đặt **sau cùng**

```sql
CREATE INDEX idx_ten_bang_composite
    ON ten_bang (cot_bang_nhau_1, cot_bang_nhau_2, cot_khoang);
```

**Đây là quy tắc thứ tự quan trọng nhất, hay bị làm sai.**

---

## Bước 4 — Đo lại, xác nhận bằng số liệu, không phỏng đoán

Chạy lại **đúng** câu `EXPLAIN ANALYZE` ở Bước 1.

- `Seq Scan` biến mất (hoặc `Execution Time` giảm rõ rệt) → index đúng, giữ lại.
- Không thay đổi gì → có thể chưa đánh đúng cột, hoặc bảng quá nhỏ nên Postgres vẫn chọn `Seq Scan` (không sao, không phải lỗi — với bảng nhỏ, quét thẳng đôi khi vẫn nhanh hơn tra index).
- Chỉ cải thiện rất ít → kiểm tra xem nút thắt cổ chai có thật sự nằm ở đây không, hay ở chỗ khác trong query.

---

## 2 cái bẫy hay gặp nhất

1. **Quên FK không tự có index** (PostgreSQL, khác MySQL) — mọi cột `REFERENCES` dùng trong `JOIN` đều là ứng viên mặc định, không tự động có sẵn.

2. **Tạo nhiều index 1-cột riêng lẻ thay vì 1 composite index** — VD tạo 3 index riêng (`idx_a`, `idx_b`, `idx_c`) cho 3 cột cùng xuất hiện trong 1 query, thay vì gộp thành `idx_a_b_c` — composite index luôn hiệu quả hơn vì Postgres không phải tự "giao" (intersect) nhiều kết quả riêng lẻ lại.

---

## Nguyên tắc đánh đổi cần nhớ

Mỗi index thêm vào đều có **chi phí**:
- `SELECT` (đọc) → nhanh hơn
- `INSERT`/`UPDATE`/`DELETE` (ghi) → chậm hơn 1 chút (phải cập nhật thêm index), tốn thêm dung lượng ổ đĩa

**Quy tắc chọn bảng/cột đáng đánh index:**

| Tần suất đọc | Tần suất ghi | Nên đánh index? |
|---|---|---|
| Nhiều | Ít | ✅ Đánh thoải mái |
| Nhiều | Nhiều | ⚠️ Cân nhắc, ưu tiên cột thực sự cần thiết |
| Ít | Bất kỳ | ❌ Không cần, chỉ tốn dung lượng vô ích |

---

## Ví dụ thực tế đã áp dụng (bus-booking project)

Query tìm kiếm trip theo route + ngày + trạng thái:

```sql
SELECT t.id FROM trip t
JOIN route r ON r.id = t.route_id
WHERE r.origin_city_id = :originCityId
  AND r.destination_city_id = :destinationCityId
  AND t.departure_time >= :startOfDay
  AND t.departure_time < :endOfDay
  AND t.status = 'SCHEDULED';
```

Index đã tạo:

```sql
-- Loc nhanh dung 1 route theo cap thanh pho di/den
CREATE INDEX idx_route_origin_destination
    ON route (origin_city_id, destination_city_id);

-- FK khong tu co index, bat buoc phai co de JOIN nhanh
CREATE INDEX idx_trip_route_id
    ON trip (route_id);

-- Composite: bang nhau truoc (route_id, status), khoang sau cung (departure_time)
CREATE INDEX idx_trip_route_status_departure
    ON trip (route_id, status, departure_time);
```

**Kết quả đo được:**

| | Execution Time |
|---|---|
| Trước khi đánh index | 1.014 ms |
| Sau khi đánh index cho `route` | 0.801 ms |
| Sau khi đánh index đủ cho `route` + `trip` | **0.107 ms** |

→ Cải thiện ~9.5 lần, `Seq Scan` biến mất hoàn toàn, thay bằng `Index Scan` ở cả 2 bảng.
