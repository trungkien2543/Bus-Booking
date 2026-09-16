import { useState } from "react";

const inputClass =
  "border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-transparent";

export default function TripSearchForm({ cities, onSearch, loading }) {
  const [originCityId, setOriginCityId] = useState("");
  const [destinationCityId, setDestinationCityId] = useState("");
  const [departureDate, setDepartureDate] = useState("");
  const [returnDate, setReturnDate] = useState("");
  const [error, setError] = useState("");

  function handleSubmit() {
    setError("");

    if (!originCityId || !destinationCityId || !departureDate) {
      setError("Vui lòng chọn đầy đủ điểm đi, điểm đến và ngày đi");
      return;
    }
    if (originCityId === destinationCityId) {
      setError("Điểm đi và điểm đến không được trùng nhau");
      return;
    }

    onSearch({ originCityId, destinationCityId, departureDate, returnDate });
  }

  return (
    <div className="bg-white rounded-2xl shadow-xl p-6">
      <div className="flex gap-4 flex-wrap items-end">
        <label className="flex flex-col gap-1 text-sm font-medium text-gray-600">
          Điểm đi
          <select
            className={inputClass}
            value={originCityId}
            onChange={(e) => setOriginCityId(e.target.value)}
          >
            <option value="">-- Chọn điểm đi --</option>
            {cities.map((city) => (
              <option key={city.id} value={city.id}>
                {city.name}
              </option>
            ))}
          </select>
        </label>

        <label className="flex flex-col gap-1 text-sm font-medium text-gray-600">
          Điểm đến
          <select
            className={inputClass}
            value={destinationCityId}
            onChange={(e) => setDestinationCityId(e.target.value)}
          >
            <option value="">-- Chọn điểm đến --</option>
            {cities.map((city) => (
              <option key={city.id} value={city.id}>
                {city.name}
              </option>
            ))}
          </select>
        </label>

        <label className="flex flex-col gap-1 text-sm font-medium text-gray-600">
          <span>
            Ngày đi <span className="text-yellow-600">*</span>
          </span>
          <input
            type="date"
            className={inputClass}
            value={departureDate}
            onChange={(e) => setDepartureDate(e.target.value)}
          />
        </label>

        <label className="flex flex-col gap-1 text-sm font-medium text-gray-600">
          Ngày về (không bắt buộc)
          <input
            type="date"
            className={inputClass}
            value={returnDate}
            onChange={(e) => setReturnDate(e.target.value)}
          />
        </label>

        <button
          onClick={handleSubmit}
          disabled={loading}
          className="bg-blue-600 hover:bg-blue-700 disabled:bg-gray-300 text-white font-semibold px-6 py-2 rounded-lg transition-colors"
        >
          {loading ? "Đang tìm..." : "Tìm chuyến"}
        </button>
      </div>

      {error && <p className="text-red-500 text-sm mt-3">{error}</p>}
    </div>
  );
}
