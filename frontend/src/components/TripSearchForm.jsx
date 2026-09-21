import { useEffect, useRef, useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

import CityAutocomplete from "./CityAutocomplete";
import {
  OriginIcon,
  DestinationIcon,
  SwapIcon,
} from "./icons/RouteIcons";

const dateInputClass =
  "border border-gray-300 rounded-xl px-4 py-3.5 text-base " +
  "focus:outline-none focus:ring-2 focus:ring-blue-400 " +
  "focus:border-transparent w-full cursor-pointer bg-white";

export default function TripSearchForm({ cities, onSearch, loading }) {
  const [originCityId, setOriginCityId] = useState("");
  const [destinationCityId, setDestinationCityId] = useState("");

  const [departureDate, setDepartureDate] = useState(null);
  const [returnDate, setReturnDate] = useState(null);

  const [error, setError] = useState("");

  // Calendar đang chọn cho ngày đi hay ngày về
  const [activeDatePicker, setActiveDatePicker] = useState(null);

  const formRef = useRef(null);

  // Hôm nay
  const today = new Date();
  today.setHours(0, 0, 0, 0);

  // Đóng calendar khi click ra ngoài
  useEffect(() => {
    function handleClickOutside(event) {
      if (
        formRef.current &&
        !formRef.current.contains(event.target)
      ) {
        setActiveDatePicker(null);
      }
    }

    document.addEventListener("mousedown", handleClickOutside);

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  function handleSwap() {
    setOriginCityId(destinationCityId);
    setDestinationCityId(originCityId);
  }

  function handleDepartureChange(date) {
    setDepartureDate(date);
    setError("");

    // Nếu ngày về trước ngày đi mới
    // thì xóa ngày về
    if (date && returnDate && returnDate < date) {
      setReturnDate(null);
    }

    setActiveDatePicker(null);
  }

  function handleReturnChange(date) {
    if (date && departureDate && date < departureDate) {
      setError("Ngày về không được trước ngày đi");
      return;
    }

    setReturnDate(date);
    setError("");
    setActiveDatePicker(null);
  }

  function handleSubmit() {
    setError("");

    if (!originCityId || !destinationCityId || !departureDate) {
      setError(
        "Vui lòng chọn đầy đủ điểm đi, điểm đến và ngày đi"
      );
      return;
    }

    if (returnDate && returnDate < departureDate) {
      setError("Ngày về không được trước ngày đi");
      return;
    }

    function formatDate(date) {
      if (!date) return "";

      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");

      return `${year}-${month}-${day}`;
    }

    onSearch({
      originCityId,
      destinationCityId,
      departureDate: formatDate(departureDate),
      returnDate: formatDate(returnDate),
    });
  }

  return (
    <div ref={formRef} className="relative bg-white rounded-2xl shadow-xl p-6">
      {/* =========================
          SEARCH FORM
      ========================= */}
      <div className="flex items-end gap-3 flex-wrap lg:flex-nowrap">
        {/* Điểm đi + điểm đến */}
        <div className="relative flex gap-3 flex-1 min-w-[340px]">
          <CityAutocomplete
            cities={cities}
            value={originCityId}
            onChange={setOriginCityId}
            label="Điểm đi"
            placeholder="Chọn điểm đi"
            icon={<OriginIcon />}
            excludeId={destinationCityId}
          />

          <CityAutocomplete
            cities={cities}
            value={destinationCityId}
            onChange={setDestinationCityId}
            label="Điểm đến"
            placeholder="Chọn điểm đến"
            icon={<DestinationIcon />}
            excludeId={originCityId}
          />

          <button
            onClick={handleSwap}
            aria-label="Hoán đổi điểm đi và điểm đến"
            style={{ top: "53px" }}
            className="
              absolute
              left-1/2
              -translate-x-1/2
              -translate-y-1/2
              z-10
              w-9 h-9
              rounded-full
              bg-blue-600
              hover:bg-blue-700
              text-white
              shadow-md
              flex
              items-center
              justify-center
              transition-colors
            "
          >
            <SwapIcon />
          </button>
        </div>

        {/* =========================
            NGÀY ĐI
        ========================= */}
        <label className="flex flex-col gap-1.5 text-sm font-medium text-gray-600 shrink-0 w-[180px]">
          <span>
            Ngày đi <span className="text-yellow-600">*</span>
          </span>

          <input
            type="text"
            readOnly
            value={
              departureDate ? departureDate.toLocaleDateString("vi-VN") : ""
            }
            placeholder="Chọn ngày đi"
            className={dateInputClass}
            onClick={() => {
              setError("");
              setActiveDatePicker("departure");
            }}
          />
        </label>

        {/* =========================
            NGÀY VỀ
        ========================= */}
        <label className="flex flex-col gap-1.5 text-sm font-medium text-gray-600 shrink-0 w-[180px]">
          <span>Ngày về</span>

          <input
            type="text"
            readOnly
            value={returnDate ? returnDate.toLocaleDateString("vi-VN") : ""}
            placeholder="Chọn ngày về"
            className={dateInputClass}
            onClick={() => {
              setError("");
              setActiveDatePicker("return");
            }}
          />
        </label>

        {/* =========================
            SEARCH BUTTON
        ========================= */}
        <button
          onClick={handleSubmit}
          disabled={loading}
          className="
            shrink-0
            bg-blue-600
            hover:bg-blue-700
            disabled:bg-gray-300
            text-white
            font-semibold
            px-8
            py-3.5
            rounded-xl
            transition-colors
            text-base
          "
        >
          {loading ? "Đang tìm..." : "Tìm chuyến"}
        </button>
      </div>

      {/* =========================
          CALENDAR
          CĂN GIỮA TOÀN BỘ FORM
      ========================= */}
      {activeDatePicker && (
        <div
          className="
            absolute top-full left-1/2 -translate-x-1/2 mt-3 z-50
            bg-white rounded-2xl shadow-2xl border border-gray-200 p-4
            whitespace-nowrap
          "
        >
          <DatePicker
            inline
            selected={
              activeDatePicker === "departure" ? departureDate : returnDate
            }
            onChange={
              activeDatePicker === "departure"
                ? handleDepartureChange
                : handleReturnChange
            }
            minDate={
              activeDatePicker === "departure" ? today : departureDate || today
            }
            monthsShown={2}
            dateFormat="dd/MM/yyyy"
            showDisabledMonthNavigation
          />
        </div>
      )}

      {/* Error */}
      {error && <p className="text-red-500 text-sm mt-3">{error}</p>}
    </div>
  );
}

