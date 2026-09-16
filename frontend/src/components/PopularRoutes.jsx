import { useEffect, useRef, useState } from "react";

const popularRoutes = [
  { from: "Hồ Chí Minh", to: "Đà Lạt", price: "220.000đ" },
  { from: "Hồ Chí Minh", to: "Hà Nội", price: "550.000đ" },
  { from: "Hồ Chí Minh", to: "Cần Thơ", price: "120.000đ" },
  { from: "Hồ Chí Minh", to: "Đà Nẵng", price: "320.000đ" },
  { from: "Hà Nội", to: "Hải Phòng", price: "90.000đ" },
  { from: "Hà Nội", to: "Sa Pa", price: "250.000đ" },
  { from: "Hồ Chí Minh", to: "Vũng Tàu", price: "150.000đ" },
  { from: "Đà Nẵng", to: "Huế", price: "80.000đ" },
];

// Chua co anh that, dung anh placeholder theo dung ten diem den
function getRouteImage(cityName) {
  return `https://picsum.photos/seed/${encodeURIComponent(cityName)}/500/500`;
}

const ITEMS_PER_VIEW = 4;
const AUTO_SLIDE_INTERVAL_MS = 50000; // 50 giay
const maxIndex = Math.max(0, popularRoutes.length - ITEMS_PER_VIEW);

export default function PopularRoutes() {
  const [current, setCurrent] = useState(0);
  const timerRef = useRef(null);

  function goNext() {
    setCurrent((prev) => (prev >= maxIndex ? 0 : prev + 1));
  }

  function goPrev() {
    setCurrent((prev) => (prev <= 0 ? maxIndex : prev - 1));
  }

  useEffect(() => {
    clearInterval(timerRef.current);
    timerRef.current = setInterval(goNext, AUTO_SLIDE_INTERVAL_MS);
    return () => clearInterval(timerRef.current);
  }, [current]);

  return (
    <div className="max-w-6xl mx-auto px-4 mt-10">
      <h2 className="text-lg font-semibold text-gray-800 mb-4">
        Tuyến đường phổ biến
      </h2>

      <div className="relative">
        <div className="overflow-hidden rounded-2xl">
          {/* Moi lan trot 1 item = dich 100/ITEMS_PER_VIEW % */}
          <div
            className="flex transition-transform duration-500 ease-in-out"
            style={{
              transform: `translateX(-${current * (100 / ITEMS_PER_VIEW)}%)`,
            }}
          >
            {popularRoutes.map((route, i) => (
              <div key={i} className="w-1/4 shrink-0 px-2">
                <div className="rounded-xl overflow-hidden shadow-sm hover:shadow-md transition-shadow cursor-pointer">
                  {/* Anh nen - hinh vuong keo rong nhe */}
                  <div
                    className="w-full h-32 sm:h-36 bg-cover bg-center"
                    style={{
                      backgroundImage: `url(${getRouteImage(route.to)})`,
                    }}
                  />
                  {/* Khung noi dung rieng ben duoi anh */}
                  <div className="bg-white px-3 py-2.5">
                    <span className="inline-block bg-yellow-100 text-yellow-700 text-[10px] font-semibold px-1.5 py-0.5 rounded-full mb-1">
                      Phổ biến
                    </span>
                    <div className="font-semibold text-gray-800 text-sm truncate">
                      {route.from} <span className="text-blue-500">→</span>{" "}
                      {route.to}
                    </div>
                    <div className="text-blue-700 font-bold text-sm mt-0.5">
                      {route.price}
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Nut trai/phai */}
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
      </div>
    </div>
  );
}
