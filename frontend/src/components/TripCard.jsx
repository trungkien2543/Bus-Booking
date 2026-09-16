import { formatTime, formatPrice } from "../utils/format";

export default function TripCard({ trip }) {
  return (
    <div className="border border-gray-200 rounded-xl p-4 shadow-sm hover:shadow-md transition-shadow bg-white">
      <div className="flex items-baseline justify-between">
        <span className="text-lg font-semibold text-gray-900">
          {formatTime(trip.departureTime)}
          <span className="text-gray-400 mx-2">→</span>
          {formatTime(trip.arrivalTime)}
        </span>
        <span className="text-lg font-bold text-blue-700">
          {formatPrice(trip.price)}
        </span>
      </div>
      <div className="text-sm text-gray-500 mt-1">
        {trip.operatorName} · {trip.busType} ·{" "}
        <span className="text-green-600 font-medium">
          còn {trip.availableSeats}/{trip.totalSeats} chỗ
        </span>
      </div>
    </div>
  );
}
