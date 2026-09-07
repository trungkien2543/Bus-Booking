-- Bat buoc: PostgreSQL 13+ da co san gen_random_uuid() built-in, khong can extension

-- ==========================================
-- DROP TABLES (theo thu tu nguoc de tranh loi FK)
-- ==========================================
DROP TABLE IF EXISTS payment CASCADE;
DROP TABLE IF EXISTS booking_seat CASCADE;
DROP TABLE IF EXISTS booking CASCADE;
DROP TABLE IF EXISTS trip_stop CASCADE;
DROP TABLE IF EXISTS route_stop_template CASCADE;
DROP TABLE IF EXISTS app_user CASCADE;
DROP TABLE IF EXISTS location CASCADE;
DROP TABLE IF EXISTS trip CASCADE;
DROP TABLE IF EXISTS route CASCADE;
DROP TABLE IF EXISTS bus_seat CASCADE;
DROP TABLE IF EXISTS bus CASCADE;
DROP TABLE IF EXISTS operator CASCADE;
DROP TABLE IF EXISTS city CASCADE;

-- ==========================================
-- CITY
-- ==========================================
CREATE TABLE city (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code        VARCHAR(20) NOT NULL UNIQUE,
    name        VARCHAR(100) NOT NULL,
    status      VARCHAR(20)
);

-- ==========================================
-- OPERATOR
-- ==========================================
CREATE TABLE operator (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(150) NOT NULL,
    status      VARCHAR(20),
    phone       VARCHAR(20),
    description TEXT,
    address     VARCHAR(255),
    email       VARCHAR(100)
);

-- ==========================================
-- BUS
-- ==========================================
CREATE TABLE bus (
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    bus_type       VARCHAR(50),
    seat_count     INT,
    license_plate  VARCHAR(20) NOT NULL UNIQUE,
    status         VARCHAR(20),
    brand          VARCHAR(50),
    year           INT,
    operator_id    UUID NOT NULL REFERENCES operator(id) ON DELETE CASCADE
);

-- ==========================================
-- BUS_SEAT
-- ==========================================
CREATE TABLE bus_seat (
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    seat_type      VARCHAR(30),
    seat_number    VARCHAR(10),
    seat_side      VARCHAR(10),
    position       VARCHAR(20),
    row_number     INT,
    column_number  INT,
    bus_id         UUID NOT NULL REFERENCES bus(id) ON DELETE CASCADE
);

-- ==========================================
-- ROUTE
-- ==========================================
CREATE TABLE route (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    estimate_distance   NUMERIC(10,2),
    status              VARCHAR(20),
    origin_city_id      UUID NOT NULL REFERENCES city(id),
    destination_city_id UUID NOT NULL REFERENCES city(id)
);

-- ==========================================
-- TRIP
-- ==========================================
CREATE TABLE trip (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    status          VARCHAR(20),
    price           NUMERIC(12,2),
    departure_time  TIMESTAMP NOT NULL,
    arrival_time    TIMESTAMP NOT NULL,
    route_id        UUID NOT NULL REFERENCES route(id),
    bus_id          UUID NOT NULL REFERENCES bus(id)
);

-- ==========================================
-- LOCATION (du lieu goc - diem don/tra vat ly, doc lap, dung lai duoc
-- cho nhieu route/trip khac nhau. KHONG gan voi route hay trip nao ca)
-- ==========================================
CREATE TABLE location (
    id       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    city_id  UUID NOT NULL REFERENCES city(id),
    name     VARCHAR(150) NOT NULL,
    address  VARCHAR(255),
    map_url  VARCHAR(255),
    status   VARCHAR(20)
);

-- ==========================================
-- ROUTE_STOP_TEMPLATE (mau lich trinh diem dung cho 1 route -
-- de admin khong phai nhap tay tung diem cho tung trip.
-- offset_minutes = do lech thoi gian so voi departure_time cua trip,
-- KHONG luu gio cung)
-- ==========================================
CREATE TABLE route_stop_template (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    route_id        UUID NOT NULL REFERENCES route(id) ON DELETE CASCADE,
    location_id     UUID NOT NULL REFERENCES location(id),
    stop_type       VARCHAR(10) NOT NULL,  -- PICKUP hoac DROPOFF
    offset_minutes  INT NOT NULL,
    sequence_order  INT NOT NULL
);

-- ==========================================
-- TRIP_STOP (du lieu THAT, thuoc ve 1 trip cu the - duoc he thong
-- tu sinh tu route_stop_template khi tao trip moi. Booking se
-- tham chieu truc tiep vao day, KHONG tham chieu vao location)
-- ==========================================
CREATE TABLE trip_stop (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    trip_id         UUID NOT NULL REFERENCES trip(id) ON DELETE CASCADE,
    location_id     UUID NOT NULL REFERENCES location(id),
    stop_type       VARCHAR(10) NOT NULL,  -- PICKUP hoac DROPOFF
    stop_time       TIMESTAMP NOT NULL,
    sequence_order  INT NOT NULL,
    status          VARCHAR(20)
);

-- ==========================================
-- APP_USER
-- ==========================================
CREATE TABLE app_user (
    id       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email    VARCHAR(100) NOT NULL UNIQUE,
    name     VARCHAR(100),
    password VARCHAR(255) NOT NULL,
    status   VARCHAR(20)
);

-- ==========================================
-- BOOKING (pick_up_point_id / drop_off_point_id doi ten thanh
-- pick_up_stop_id / drop_off_stop_id, tro toi TRIP_STOP thay vi Location,
-- vi booking can biet chinh xac GIO don, khong chi ten dia diem)
-- ==========================================
CREATE TABLE booking (
    id                UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    status            VARCHAR(20),
    expires_at        TIMESTAMP,
    trip_id           UUID NOT NULL REFERENCES trip(id),
    user_id           UUID NOT NULL REFERENCES app_user(id),
    pick_up_stop_id   UUID REFERENCES trip_stop(id),
    drop_off_stop_id  UUID REFERENCES trip_stop(id)
);

-- ==========================================
-- BOOKING_SEAT
-- ==========================================
CREATE TABLE booking_seat (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    price       NUMERIC(12,2),
    booking_id  UUID NOT NULL REFERENCES booking(id) ON DELETE CASCADE,
    bus_seat_id UUID NOT NULL REFERENCES bus_seat(id)
);

-- ==========================================
-- PAYMENT
-- ==========================================
CREATE TABLE payment (
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    amount         NUMERIC(12,2),
    method         VARCHAR(30),
    status         VARCHAR(20),
    transaction_id VARCHAR(100),
    paid_at        TIMESTAMP,
    booking_id     UUID NOT NULL UNIQUE REFERENCES booking(id) ON DELETE CASCADE
);
