# TODO / Backlog - Bus Booking System

Ghi chú các việc cần làm/tối ưu sau này, chưa xử lý ngay vì chưa cấp thiết cho Phase 1 (tính năng tìm kiếm chuyến đi).

---

## 1. Tối ưu quan hệ PickupTrip <-> Route (many-to-many)

**Hiện trạng:**
`pick_up_trip.route_id` là NOT NULL → 1 điểm đón chỉ thuộc về đúng 1 route.
Hệ quả: cùng 1 địa điểm thật ngoài đời (VD "Bến xe Miền Đông") nếu được nhiều route
khác nhau ghé qua, phải tạo **nhiều dòng trùng lặp** trong bảng `pick_up_trip`
(khác `id`, khác `route_id`, nhưng giống `name`/`address`).

**Vấn đề:**
- Trùng lặp dữ liệu (data duplication) khi 1 địa điểm được nhiều route dùng chung.
- Sửa thông tin 1 địa điểm (VD đổi địa chỉ bến xe) phải sửa ở NHIỀU dòng thay vì 1.

**Hướng tối ưu (chưa làm):**
Tách thành quan hệ many-to-many bằng bảng trung gian:

```
pick_up_trip (doc lap, KHONG con route_id)
├── id
├── city_id
├── name
├── address
├── status
└── map_url

route_pickup_point (bang trung gian)
├── route_id       (FK -> route.id)
└── pickup_trip_id (FK -> pick_up_trip.id)
```

**Anh huong khi lam:**
- Sua entity `PickupTrip` (bo `route_id`, bo quan he ManyToOne toi Route).
- Tao entity/bang moi `RoutePickupPoint`.
- Sua lai `booking.pick_up_point_id` / `drop_off_point_id` - can xem xet co
  tro thang vao `pick_up_trip.id` nhu hien tai hay phai tro qua bang trung gian.
- Sua lai toan bo data mau (02-data.sql) va bulk data (03-bulk-data.sql) dang
  insert theo cau truc cu.

**Khi nao lam:** Phase toi uu database, sau khi Phase 1 (search) va cac tinh nang
core (booking, payment) da on dinh. Chi lam khi thay ro van de trung lap du lieu
anh huong thuc te (VD so luong dong pick_up_trip qua lon do trung lap).

---

## 2. (Cho tinh nang khac se ghi tiep o day)
