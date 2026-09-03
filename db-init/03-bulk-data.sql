-- ==========================================
-- BULK DATA GENERATOR - de benchmark search
-- Chay SAU 01-schema.sql va 02-data.sql
-- Tao them: toan bo cap tinh/thanh lam route,
-- moi route co 2 diem don/tra, moi route co nhieu trip
-- ==========================================

-- ------------------------------------------
-- 1) ROUTE: sinh toan bo cap thanh pho (34 x 33 = 1122 cap co the)
--    Bo qua cap da ton tai san (6 route curated truoc do)
-- ------------------------------------------
INSERT INTO route (estimate_distance, status, origin_city_id, destination_city_id)
SELECT
    round((random() * 1900 + 50)::numeric, 2),   -- khoang cach gia lap 50km - 1950km
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
-- 2) PICK_UP_TRIP: moi route co 1 diem o thanh pho di, 1 diem o thanh pho den
--    (bo qua route da co san diem don/tra tu du lieu curated)
-- ------------------------------------------
INSERT INTO pick_up_trip (name, address, status, map_url, city_id, route_id)
SELECT
    'Ben xe trung tam ' || c.name,
    'Trung tam thanh pho ' || c.name,
    'ACTIVE',
    'https://maps.google.com/?q=' || replace(lower(c.name), ' ', '-'),
    c.id,
    r.id
FROM route r
JOIN city c ON c.id = r.origin_city_id
WHERE NOT EXISTS (
    SELECT 1 FROM pick_up_trip p WHERE p.route_id = r.id AND p.city_id = c.id
);

INSERT INTO pick_up_trip (name, address, status, map_url, city_id, route_id)
SELECT
    'Ben xe trung tam ' || c.name,
    'Trung tam thanh pho ' || c.name,
    'ACTIVE',
    'https://maps.google.com/?q=' || replace(lower(c.name), ' ', '-'),
    c.id,
    r.id
FROM route r
JOIN city c ON c.id = r.destination_city_id
WHERE NOT EXISTS (
    SELECT 1 FROM pick_up_trip p WHERE p.route_id = r.id AND p.city_id = c.id
);

-- ------------------------------------------
-- 3) TRIP: moi route sinh 5 chuyen voi bus/gia/trang thai/gio ngau nhien
--    => ~1122 route x 5 = ~5610 trip
--    Ngay khoi hanh: rai deu trong 60 ngay toi, gio khoi hanh ngau nhien trong ngay
--    Thoi gian di chuyen: uoc luong theo khoang cach (toc do trung binh ~50km/h)
-- ------------------------------------------
INSERT INTO trip (status, price, departure_time, arrival_time, route_id, bus_id)
SELECT
    CASE
        WHEN random() < 0.7 THEN 'SCHEDULED'
        WHEN random() < 0.9 THEN 'COMPLETED'
        ELSE 'CANCELLED'
    END,
    round((random() * 900000 + 100000)::numeric, -3),  -- gia 100k - 1tr, lam tron nghin
    departure,
    departure + make_interval(hours => greatest(2, round(r.estimate_distance / 50))::int),
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
-- Thong ke nhanh sau khi sinh du lieu
-- ------------------------------------------
DO $$
DECLARE
    v_route_count   INT;
    v_pickup_count  INT;
    v_trip_count    INT;
BEGIN
    SELECT COUNT(*) INTO v_route_count FROM route;
    SELECT COUNT(*) INTO v_pickup_count FROM pick_up_trip;
    SELECT COUNT(*) INTO v_trip_count FROM trip;

    RAISE NOTICE 'Tong so route: %', v_route_count;
    RAISE NOTICE 'Tong so pick_up_trip: %', v_pickup_count;
    RAISE NOTICE 'Tong so trip: %', v_trip_count;
END $$;
