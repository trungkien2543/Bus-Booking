-- ==========================================
-- BULK DATA GENERATOR - de benchmark search
-- Chay SAU 01-schema.sql va 02-data.sql
-- Cau truc: Location (1 dong/thanh pho, khong lap) -> RouteStopTemplate
-- (mau lich trinh cho tung route) -> TripStop (sinh tu template, ap dung
-- cho tung trip that)
-- ==========================================

-- ------------------------------------------
-- 1) ROUTE: sinh toan bo cap thanh pho (34 x 33 = 1122 cap co the)
--    Bo qua cap da ton tai san (6 route curated truoc do)
-- ------------------------------------------
INSERT INTO route (estimate_distance, status, origin_city_id, destination_city_id)
SELECT
    round((random() * 1900 + 50)::numeric, 2),
    'ACTIVE',
    c1.id,
    c2.id
FROM city c1
CROSS JOIN city c2
WHERE c1.id <> c2.id
  AND NOT EXISTS (
      SELECT 1 FROM route r
      WHERE r.origin_city_id = c1.id AND r.destination_city_id = c2.id
  );

-- ------------------------------------------
-- 2) TRIP: moi route sinh 5 chuyen voi bus/gia/trang thai/gio ngau nhien
--    => ~1122 route x 5 = ~5610 trip
-- ------------------------------------------
INSERT INTO trip (status, price, departure_time, arrival_time, route_id, bus_id)
SELECT
    CASE
        WHEN random() < 0.7 THEN 'SCHEDULED'
        WHEN random() < 0.9 THEN 'COMPLETED'
        ELSE 'CANCELLED'
    END,
    round((random() * 900000 + 100000)::numeric, -3),
    d.departure,
    d.departure + make_interval(hours => greatest(2, round(r.estimate_distance / 50))::int),
    r.id,
    b.id
FROM route r
CROSS JOIN generate_series(1, 5) AS trip_seq
CROSS JOIN LATERAL (SELECT id FROM bus ORDER BY random() LIMIT 1) AS b
CROSS JOIN LATERAL (
    SELECT (CURRENT_DATE + (floor(random() * 60))::int * INTERVAL '1 day'
            + (floor(random() * 24))::int * INTERVAL '1 hour') AS departure
) AS d;

-- ------------------------------------------
-- 3) LOCATION: sinh DUNG 1 diem/thanh pho (34 dia diem, khong lap lai)
--    Day la diem khac biet lon so voi thiet ke cu: du co hang nghin
--    route/trip dung chung 1 thanh pho, Location van chi co 1 dong duy nhat.
-- ------------------------------------------
INSERT INTO location (city_id, name, address, map_url, status)
SELECT
    c.id,
    'Ben xe trung tam ' || c.name,
    'Trung tam thanh pho ' || c.name,
    'https://maps.google.com/?q=' || replace(lower(c.name), ' ', '-'),
    'ACTIVE'
FROM city c
WHERE NOT EXISTS (
    SELECT 1 FROM location l WHERE l.city_id = c.id
);

-- ------------------------------------------
-- 4) ROUTE_STOP_TEMPLATE: moi route sinh 2 dong (PICKUP o thanh pho di,
--    DROPOFF o thanh pho den), offset_minutes uoc luong theo khoang cach
--    (toc do trung binh ~50km/h, giong cong thuc tinh arrival_time o buoc 2)
-- ------------------------------------------
INSERT INTO route_stop_template (route_id, location_id, stop_type, offset_minutes, sequence_order)
SELECT
    r.id,
    lo.id,
    'PICKUP',
    0,
    1
FROM route r
JOIN location lo ON lo.city_id = r.origin_city_id
WHERE NOT EXISTS (
    SELECT 1 FROM route_stop_template t WHERE t.route_id = r.id AND t.stop_type = 'PICKUP'
);

INSERT INTO route_stop_template (route_id, location_id, stop_type, offset_minutes, sequence_order)
SELECT
    r.id,
    ld.id,
    'DROPOFF',
    greatest(2, round(r.estimate_distance / 50)) * 60,
    2
FROM route r
JOIN location ld ON ld.city_id = r.destination_city_id
WHERE NOT EXISTS (
    SELECT 1 FROM route_stop_template t WHERE t.route_id = r.id AND t.stop_type = 'DROPOFF'
);

-- ------------------------------------------
-- 5) TRIP_STOP: sinh tu ROUTE_STOP_TEMPLATE, ap dung cho tung trip that
--    (stop_time = trip.departure_time + offset_minutes cua template)
-- ------------------------------------------
INSERT INTO trip_stop (trip_id, location_id, stop_type, stop_time, sequence_order, status)
SELECT
    t.id,
    rst.location_id,
    rst.stop_type,
    t.departure_time + make_interval(mins => rst.offset_minutes),
    rst.sequence_order,
    'ACTIVE'
FROM trip t
JOIN route_stop_template rst ON rst.route_id = t.route_id
WHERE NOT EXISTS (
    SELECT 1 FROM trip_stop ts WHERE ts.trip_id = t.id AND ts.stop_type = rst.stop_type
);

-- ------------------------------------------
-- Thong ke nhanh sau khi sinh du lieu
-- ------------------------------------------
DO $$
DECLARE
    v_route_count    INT;
    v_trip_count     INT;
    v_location_count INT;
    v_template_count INT;
    v_stop_count     INT;
BEGIN
    SELECT COUNT(*) INTO v_route_count FROM route;
    SELECT COUNT(*) INTO v_trip_count FROM trip;
    SELECT COUNT(*) INTO v_location_count FROM location;
    SELECT COUNT(*) INTO v_template_count FROM route_stop_template;
    SELECT COUNT(*) INTO v_stop_count FROM trip_stop;

    RAISE NOTICE 'Tong so route: %', v_route_count;
    RAISE NOTICE 'Tong so trip: %', v_trip_count;
    RAISE NOTICE 'Tong so location: % (chi ~34, khong lap theo route/trip)', v_location_count;
    RAISE NOTICE 'Tong so route_stop_template: %', v_template_count;
    RAISE NOTICE 'Tong so trip_stop: %', v_stop_count;
END $$;
