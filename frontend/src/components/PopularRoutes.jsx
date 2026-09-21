import { useEffect, useRef, useState } from "react";
import { getPopularRoutes } from "../api/tripApi";
import { formatPrice } from "../utils/format";
import { getCityImage } from "../utils/cityImages";

const ITEMS_PER_VIEW = 4;
const AUTO_SLIDE_INTERVAL_MS = 50000; // 50 giay

export default function PopularRoutes() {
  const [routes, setRoutes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [current, setCurrent] = useState(0);
  const timerRef = useRef(null);

  useEffect(() => {
    getPopularRoutes(8)
      .then(setRoutes)
      .catch((err) => console.error("Lỗi khi tải tuyến phổ biến:", err))
      .finally(() => setLoading(false));
  }, []);

  const maxIndex = Math.max(0, routes.length - ITEMS_PER_VIEW);

  function goNext() {
    setCurrent((prev) => (prev >= maxIndex ? 0 : prev + 1));
  }

  function goPrev() {
    setCurrent((prev) => (prev <= 0 ? maxIndex : prev - 1));
  }

  useEffect(() => {
    if (routes.length <= ITEMS_PER_VIEW) return; // khong du item de truot thi khong can timer
    clearInterval(timerRef.current);
    timerRef.current = setInterval(goNext, AUTO_SLIDE_INTERVAL_MS);
    return () => clearInterval(timerRef.current);
  }, [current, routes.length]);

  if (loading) {
    return (
      <div className="max-w-6xl mx-auto px-4 mt-10 text-gray-400 text-sm">
        Đang tải tuyến đường phổ biến...
      </div>
    );
  }

  if (routes.length === 0) return null;

  return (
    <div className="max-w-6xl mx-auto px-4 mt-10">
      <h2 className="text-lg font-semibold text-gray-800 mb-4">
        Tuyến đường phổ biến
      </h2>

      <div className="relative">
        <div className="overflow-hidden rounded-2xl">
          <div
            className="flex transition-transform duration-500 ease-in-out"
            style={{ transform: `translateX(-${current * (100 / ITEMS_PER_VIEW)}%)` }}
          >
            {routes.map((route) => (
              <div key={route.routeId} className="w-1/4 shrink-0 px-2">
                <div className="rounded-xl overflow-hidden shadow-sm hover:shadow-md transition-shadow cursor-pointer">
                  <div
                    className="w-full h-32 sm:h-36 bg-cover bg-center"
                    style={{
                      backgroundImage: `url(${route.destinationImageUrl || getCityImage(route.destinationCityName)})`,
                    }}
                  />
                  <div className="bg-white px-3 py-2.5">
                    <span className="inline-block bg-yellow-100 text-yellow-700 text-[10px] font-semibold px-1.5 py-0.5 rounded-full mb-1">
                      {route.tripCount} chuyến
                    </span>
                    <div className="font-semibold text-gray-800 text-sm truncate">
                      {route.originCityName} <span className="text-blue-500">→</span> {route.destinationCityName}
                    </div>
                    <div className="text-blue-700 font-bold text-sm mt-0.5">
                      Từ {formatPrice(route.minPrice)}
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>

        {routes.length > ITEMS_PER_VIEW && (
          <>
            <button
              onClick={goPrev}
              aria-label="Tuyến trước"
              className="absolute left-0 top-1/2 -translate-y-1/2 -translate-x-4 w-9 h-9 rounded-full bg-white hover:bg-gray-50 shadow-md flex items-center justify-center text-blue-700 font-bold"
            >
              ‹
            </button>
            <button
              onClick={goNext}
              aria-label="Tuyến tiếp theo"
              className="absolute right-0 top-1/2 -translate-y-1/2 translate-x-4 w-9 h-9 rounded-full bg-white hover:bg-gray-50 shadow-md flex items-center justify-center text-blue-700 font-bold"
            >
              ›
            </button>
          </>
        )}
      </div>
    </div>
  );
}
