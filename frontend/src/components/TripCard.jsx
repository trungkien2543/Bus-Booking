import { useState } from "react";
import { formatTime, formatPrice } from "../utils/format";

function formatDuration(start, end) {
  const totalMin = Math.round((new Date(end) - new Date(start)) / 60000);
  const h = Math.floor(totalMin / 60);
  const m = totalMin % 60;
  return m > 0 ? `${h}h${m}p` : `${h}h`;
}

// Chua co field anh xe tu API -> dung icon placeholder.
// Sau nay co trip.imageUrl thi doi thanh <img src={trip.imageUrl} ... />
function BusThumbnail() {
  return (
    <div className="w-28 h-28 shrink-0 rounded-xl bg-blue-50 flex items-center justify-center overflow-hidden">
      <svg
        className="w-12 h-12 text-blue-400"
        fill="none"
        stroke="currentColor"
        viewBox="0 0 24 24"
      >
        <path
          strokeLinecap="round"
          strokeLinejoin="round"
          strokeWidth={1.5}
          d="M3 13.5V7a2 2 0 012-2h14a2 2 0 012 2v6.5M3 13.5a2 2 0 002 2h.5m-2.5-2h18m-2.5 2a2 2 0 002-2M5.5 15.5v2a1 1 0 001 1h1a1 1 0 001-1v-2m8 0v2a1 1 0 001 1h1a1 1 0 001-1v-2M7 9h4m4 0h2"
        />
      </svg>
    </div>
  );
}

export default function TripCard({ trip, onSelect }) {
  const [open, setOpen] = useState(false);
  const [activeTab, setActiveTab] = useState("points"); // "points" | "vehicle"

  return (
    <div className="border border-gray-200 rounded-xl p-4 shadow-sm hover:shadow-md transition-shadow bg-white">
      <div className="flex gap-4">
        <BusThumbnail />

        {/* Cot giua: ten nha xe, loai xe, timeline gio di - den */}
        <div className="flex-1 min-w-0">
          <div className="font-semibold text-gray-900 truncate">
            {trip.operatorName}
          </div>
          <div className="text-sm text-gray-500">{trip.busType}</div>

          <div className="flex gap-3 mt-3">
            {/* Cham + duong noi doc */}
            <div className="flex flex-col items-center pt-1.5">
              <span className="w-2.5 h-2.5 rounded-full border-2 border-blue-600" />
              <span className="w-px flex-1 bg-gray-300 my-1" />
              <span className="w-2.5 h-2.5 rounded-full bg-blue-600" />
            </div>

            <div className="flex-1 flex flex-col justify-between">
              <div>
                <span className="font-semibold text-gray-900">
                  {formatTime(trip.departureTime)}
                </span>
                <span className="text-gray-500"> · {trip.originCityName}</span>
              </div>
              <div className="text-xs text-gray-400 my-1">
                {formatDuration(trip.departureTime, trip.arrivalTime)}
              </div>
              <div>
                <span className="font-semibold text-gray-900">
                  {formatTime(trip.arrivalTime)}
                </span>
                <span className="text-gray-500">
                  {" "}
                  · {trip.destinationCityName}
                </span>
              </div>
            </div>
          </div>
        </div>

        {/* Cot phai: gia, so cho, nut chon cho */}
        <div className="flex flex-col items-end justify-between shrink-0 text-right">
          <div className="text-lg font-bold text-blue-700">
            {formatPrice(trip.price)}
          </div>
          <div className="text-sm text-green-600 font-medium">
            còn {trip.availableSeats}/{trip.totalSeats} chỗ
          </div>
          <button
            onClick={() => onSelect?.(trip)}
            className="bg-blue-600 hover:bg-blue-700 text-white text-sm font-semibold px-5 py-2 rounded-lg transition-colors"
          >
            Chọn chỗ
          </button>
        </div>
      </div>

      {/* Nut xo xuong + panel chi tiet */}
      <button
        onClick={() => setOpen((o) => !o)}
        className="flex items-center gap-1 text-sm text-blue-600 font-medium mt-3"
      >
        {open ? "Ẩn chi tiết" : "Thông tin chi tiết"}
        <svg
          className={
            "w-4 h-4 transition-transform " + (open ? "rotate-180" : "")
          }
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth={2}
            d="M19 9l-7 7-7-7"
          />
        </svg>
      </button>

      {open && (
        <div className="mt-2 pt-3 border-t border-gray-100">
          {/* Tab: Diem don - tra / Thong tin xe */}
          <div className="flex gap-1 border-b border-gray-100">
            <button
              onClick={() => setActiveTab("points")}
              className={
                "px-3 py-2 text-[15px] font-semibold border-b-2 -mb-px transition-colors " +
                (activeTab === "points"
                  ? "border-blue-600 text-blue-600"
                  : "border-transparent text-gray-500 hover:text-gray-700")
              }
            >
              Điểm đón - trả
            </button>
            <button
              onClick={() => setActiveTab("vehicle")}
              className={
                "px-3 py-2 text-[15px] font-semibold border-b-2 -mb-px transition-colors " +
                (activeTab === "vehicle"
                  ? "border-blue-600 text-blue-600"
                  : "border-transparent text-gray-500 hover:text-gray-700")
              }
            >
              Thông tin xe
            </button>
          </div>

          {activeTab === "points" && (
            <div className="grid grid-cols-2 gap-5 mt-3">
              <div>
                <div className="text-sm font-semibold text-gray-500 mb-2">
                  Điểm đón ({(trip.pickupPoints || []).length})
                </div>
                <ul className="space-y-2.5">
                  {(trip.pickupPoints || []).map((point, i) => (
                    <li
                      key={i}
                      className="flex items-start gap-2 text-[15px] font-medium text-gray-800"
                    >
                      <span className="w-1.5 h-1.5 rounded-full bg-blue-600 mt-2 shrink-0" />
                      {point}
                    </li>
                  ))}
                </ul>
              </div>
              <div>
                <div className="text-sm font-semibold text-gray-500 mb-2">
                  Điểm trả ({(trip.dropoffPoints || []).length})
                </div>
                <ul className="space-y-2.5">
                  {(trip.dropoffPoints || []).map((point, i) => (
                    <li
                      key={i}
                      className="flex items-start gap-2 text-[15px] font-medium text-gray-800"
                    >
                      <span className="w-1.5 h-1.5 rounded-full bg-blue-600 mt-2 shrink-0" />
                      {point}
                    </li>
                  ))}
                </ul>
              </div>
            </div>
          )}

          {activeTab === "vehicle" && (
            <div className="mt-3 space-y-3 text-[15px] font-medium text-gray-800">
              <div>
                <span className="text-gray-500 font-semibold">Loại ghế: </span>
                {(trip.seatTypes || []).join(", ")}
              </div>
              <div>
                <span className="text-gray-500 font-semibold">Xe: </span>
                {trip.busBrand} · {trip.licensePlate}
              </div>
            </div>
          )}
        </div>
      )}
    </div>
  );
}
