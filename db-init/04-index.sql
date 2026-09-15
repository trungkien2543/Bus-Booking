-- ==========================================
-- INDEX - dua tren do bang EXPLAIN ANALYZE tren query tim kiem trip
-- (xem chi tiet quy trinh trong huong-dan-danh-index.md)
-- Chay SAU 03-bulk-data.sql: tao index sau khi da co du lieu se nhanh
-- hon tao truoc roi moi insert (Postgres chi quet 1 lan de xay index,
-- thay vi cap nhat index cho tung dong insert rieng le)
-- ==========================================

-- Loc nhanh dung 1 route theo cap thanh pho di/den (dung trong moi
-- lan tim kiem chuyen di)
CREATE INDEX idx_route_origin_destination
    ON route (origin_city_id, destination_city_id);

-- Composite index cho trip: route_id (FK, bang nhau) truoc,
-- status (bang nhau) giua, departure_time (khoang) sau cung.
-- KHONG can them index rieng cho tung cot (route_id mot minh,
-- status mot minh...) vi composite nay da phuc vu duoc ca truong hop
-- chi loc theo route_id mot minh (nguyen tac "leftmost prefix").
CREATE INDEX idx_trip_route_status_departure
    ON trip (route_id, status, departure_time);

-- Danh index cho phan tim trip stop theo danh sach trip
CREATE INDEX idx_trip_stop_trip_id
    ON trip_stop (trip_id);
