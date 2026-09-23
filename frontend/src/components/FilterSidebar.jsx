import { useMemo, useState } from "react";

function ChevronIcon({ open }) {
  return (
    <svg
      className={
        "w-4 h-4 text-gray-400 transition-transform duration-200 shrink-0 " +
        (open ? "rotate-180" : "")
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
  );
}

// Moi nhom loc la 1 section dong/mo doc lap, mac dinh mo (defaultOpen).
function CollapsibleSection({ title, badge, defaultOpen = true, children }) {
  const [open, setOpen] = useState(defaultOpen);
  return (
    <div className="border-b border-gray-100 last:border-b-0 py-3.5">
      <button
        type="button"
        onClick={() => setOpen((o) => !o)}
        className="w-full flex items-center justify-between gap-2 text-left"
      >
        <span className="text-[15px] font-semibold text-gray-900 flex items-center gap-1.5">
          {title}
          {badge > 0 && (
            <span className="text-xs font-semibold text-blue-600 bg-blue-50 rounded-full px-1.5 py-0.5 leading-none">
              {badge}
            </span>
          )}
        </span>
        <ChevronIcon open={open} />
      </button>
      {open && <div className="mt-3">{children}</div>}
    </div>
  );
}

function CheckboxList({ options, selected, onToggle, renderLabel }) {
  const getLabel = renderLabel || ((opt) => opt);
  return (
    <div className="flex flex-col gap-2.5 max-h-44 overflow-y-auto pr-1">
      {options.map((opt) => (
        <label
          key={opt}
          className="flex items-center gap-2.5 text-[15px] font-medium text-gray-800 cursor-pointer"
        >
          <input
            type="checkbox"
            className="w-4 h-4 accent-blue-600 shrink-0"
            checked={selected.has(opt)}
            onChange={() => onToggle(opt)}
          />
          {getLabel(opt)}
        </label>
      ))}
    </div>
  );
}

function PriceRangeInputs({ min, max, value, onChange }) {
  const [curMin, curMax] = value;
  return (
    <div>
      <div className="flex items-center gap-2 text-[15px] font-medium text-gray-800">
        <input
          type="number"
          className="w-full border border-gray-300 rounded-lg px-2 py-1.5"
          value={curMin}
          min={min}
          max={curMax}
          onChange={(e) => onChange([Number(e.target.value), curMax])}
        />
        <span className="text-gray-400">–</span>
        <input
          type="number"
          className="w-full border border-gray-300 rounded-lg px-2 py-1.5"
          value={curMax}
          min={curMin}
          max={max}
          onChange={(e) => onChange([curMin, Number(e.target.value)])}
        />
      </div>
      <div className="text-xs text-gray-500 mt-1.5">
        {min.toLocaleString("vi-VN")}đ – {max.toLocaleString("vi-VN")}đ
      </div>
    </div>
  );
}

// ------------------------------------------------------------------
// Loc theo gio di: thanh keo 2 dau (tu - den), step 30 phut/lan keo.
// Gia tri luu duoi dang so phut trong ngay (0-1440).
// ------------------------------------------------------------------
const DAY_MINUTES = 24 * 60;
const STEP = 30;

function minutesToTime(min) {
  const h = String(Math.floor(min / 60)).padStart(2, "0");
  const m = String(min % 60).padStart(2, "0");
  return `${h}:${m}`;
}

function timeToMinutes(str) {
  const [h, m] = str.split(":").map(Number);
  return h * 60 + (m || 0);
}

function roundToStep(min) {
  return Math.min(DAY_MINUTES, Math.max(0, Math.round(min / STEP) * STEP));
}

export function getDepartureMinutes(departureTime) {
  const d = new Date(departureTime);
  return d.getHours() * 60 + d.getMinutes();
}

const THUMB_CLASS =
  "[&::-webkit-slider-thumb]:pointer-events-auto [&::-webkit-slider-thumb]:appearance-none " +
  "[&::-webkit-slider-thumb]:w-4 [&::-webkit-slider-thumb]:h-4 [&::-webkit-slider-thumb]:rounded-full " +
  "[&::-webkit-slider-thumb]:bg-blue-600 [&::-webkit-slider-thumb]:border-2 [&::-webkit-slider-thumb]:border-white " +
  "[&::-webkit-slider-thumb]:shadow [&::-webkit-slider-thumb]:cursor-pointer " +
  "[&::-moz-range-thumb]:pointer-events-auto [&::-moz-range-thumb]:w-4 [&::-moz-range-thumb]:h-4 " +
  "[&::-moz-range-thumb]:rounded-full [&::-moz-range-thumb]:bg-blue-600 [&::-moz-range-thumb]:border-2 " +
  "[&::-moz-range-thumb]:border-white [&::-moz-range-thumb]:shadow [&::-moz-range-thumb]:cursor-pointer";

function TimeRangeFilter({ value, onChange }) {
  const [from, to] = value;
  const fromPct = (from / DAY_MINUTES) * 100;
  const toPct = (to / DAY_MINUTES) * 100;

  function handleFromSlider(e) {
    const v = Math.min(Number(e.target.value), to - STEP);
    onChange([Math.max(0, v), to]);
  }
  function handleToSlider(e) {
    const v = Math.max(Number(e.target.value), from + STEP);
    onChange([from, Math.min(DAY_MINUTES, v)]);
  }
  function handleFromInput(e) {
    if (!e.target.value) return;
    const v = Math.min(roundToStep(timeToMinutes(e.target.value)), to - STEP);
    onChange([Math.max(0, v), to]);
  }
  function handleToInput(e) {
    if (!e.target.value) return;
    const v = Math.max(roundToStep(timeToMinutes(e.target.value)), from + STEP);
    onChange([from, Math.min(DAY_MINUTES, v)]);
  }

  return (
    <div>
      {/* Thanh keo 2 dau */}
      <div className="relative h-4 flex items-center mb-4">
        <div className="absolute w-full h-1.5 bg-gray-200 rounded-full" />
        <div
          className="absolute h-1.5 bg-blue-500 rounded-full"
          style={{ left: `${fromPct}%`, right: `${100 - toPct}%` }}
        />
        <input
          type="range"
          min={0}
          max={DAY_MINUTES}
          step={STEP}
          value={from}
          onChange={handleFromSlider}
          className={
            "absolute w-full h-1.5 appearance-none bg-transparent pointer-events-none " +
            THUMB_CLASS
          }
        />
        <input
          type="range"
          min={0}
          max={DAY_MINUTES}
          step={STEP}
          value={to}
          onChange={handleToSlider}
          className={
            "absolute w-full h-1.5 appearance-none bg-transparent pointer-events-none " +
            THUMB_CLASS
          }
        />
      </div>

      {/* O nhap gio truc tiep, cung snap 30 phut */}
      <div className="flex items-center gap-2">
        <input
          type="time"
          step={STEP * 60}
          value={minutesToTime(from)}
          onChange={handleFromInput}
          className="w-full border border-gray-300 rounded-lg px-2 py-1.5 text-[15px] font-medium text-gray-800"
        />
        <span className="text-gray-400">–</span>
        <input
          type="time"
          step={STEP * 60}
          value={minutesToTime(to)}
          onChange={handleToInput}
          className="w-full border border-gray-300 rounded-lg px-2 py-1.5 text-[15px] font-medium text-gray-800"
        />
      </div>
    </div>
  );
}

const SEAT_POSITION_LABEL = { LOWER: "Tầng dưới", UPPER: "Tầng trên" };

/**
 * allTrips: gop outboundTrips + returnTrips de suy ra cac gia tri co the loc.
 * Cac field mang (pickupPoints, dropoffPoints, seatPositions, seatTypes)
 * duoc flatMap ra vi 1 trip co the co nhieu gia tri cho 1 field.
 *
 * filters: {
 *   timeRange: [fromMinutes, toMinutes],  // 0-1440, mac dinh [0, 1440] = ca ngay
 *   operators: Set, pickupPoints: Set, dropoffPoints: Set,
 *   busTypes: Set, seatPositions: Set, seatTypes: Set,
 *   priceRange: [min, max] | null
 * }
 */
export default function FilterSidebar({ allTrips, filters, onFilterChange }) {
  const priceMin = useMemo(
    () => (allTrips.length ? Math.min(...allTrips.map((t) => t.price)) : 0),
    [allTrips],
  );
  const priceMax = useMemo(
    () => (allTrips.length ? Math.max(...allTrips.map((t) => t.price)) : 0),
    [allTrips],
  );

  const operatorOptions = useMemo(
    () => [...new Set(allTrips.map((t) => t.operatorName))],
    [allTrips],
  );
  const busTypeOptions = useMemo(
    () => [...new Set(allTrips.map((t) => t.busType))],
    [allTrips],
  );
  const seatTypeOptions = useMemo(
    () => [...new Set(allTrips.flatMap((t) => t.seatTypes || []))],
    [allTrips],
  );
  const seatPositionOptions = useMemo(
    () => [...new Set(allTrips.flatMap((t) => t.seatPositions || []))],
    [allTrips],
  );
  const pickupOptions = useMemo(
    () => [...new Set(allTrips.flatMap((t) => t.pickupPoints || []))],
    [allTrips],
  );
  const dropoffOptions = useMemo(
    () => [...new Set(allTrips.flatMap((t) => t.dropoffPoints || []))],
    [allTrips],
  );

  function toggle(set, value) {
    const next = new Set(set);
    next.has(value) ? next.delete(value) : next.add(value);
    return next;
  }

  function update(key, value) {
    onFilterChange({ ...filters, [key]: value });
  }

  function handleReset() {
    onFilterChange({
      timeRange: [0, DAY_MINUTES],
      operators: new Set(),
      pickupPoints: new Set(),
      dropoffPoints: new Set(),
      busTypes: new Set(),
      seatPositions: new Set(),
      seatTypes: new Set(),
      priceRange: null,
    });
  }

  const isTimeActive =
    filters.timeRange[0] !== 0 || filters.timeRange[1] !== DAY_MINUTES;

  const activeCount =
    (isTimeActive ? 1 : 0) +
    filters.operators.size +
    filters.pickupPoints.size +
    filters.dropoffPoints.size +
    filters.busTypes.size +
    filters.seatPositions.size +
    filters.seatTypes.size +
    (filters.priceRange ? 1 : 0);

  return (
    <div className="w-full bg-white rounded-2xl shadow-sm border border-gray-100 px-4">
      <div className="flex items-center justify-between py-3.5 border-b border-gray-100">
        <h3 className="font-semibold text-gray-900 text-[15px]">Bộ lọc</h3>
        {activeCount > 0 && (
          <button
            onClick={handleReset}
            className="text-xs text-blue-600 hover:underline"
          >
            Xóa lọc ({activeCount})
          </button>
        )}
      </div>

      <CollapsibleSection title="Giờ đi" badge={isTimeActive ? 1 : 0}>
        <TimeRangeFilter
          value={filters.timeRange}
          onChange={(range) => update("timeRange", range)}
        />
      </CollapsibleSection>

      <CollapsibleSection title="Nhà xe" badge={filters.operators.size}>
        <CheckboxList
          options={operatorOptions}
          selected={filters.operators}
          onToggle={(v) => update("operators", toggle(filters.operators, v))}
        />
      </CollapsibleSection>

      <CollapsibleSection title="Điểm đón" badge={filters.pickupPoints.size}>
        <CheckboxList
          options={pickupOptions}
          selected={filters.pickupPoints}
          onToggle={(v) =>
            update("pickupPoints", toggle(filters.pickupPoints, v))
          }
        />
      </CollapsibleSection>

      <CollapsibleSection title="Điểm trả" badge={filters.dropoffPoints.size}>
        <CheckboxList
          options={dropoffOptions}
          selected={filters.dropoffPoints}
          onToggle={(v) =>
            update("dropoffPoints", toggle(filters.dropoffPoints, v))
          }
        />
      </CollapsibleSection>

      <CollapsibleSection title="Khoảng giá" badge={filters.priceRange ? 1 : 0}>
        <PriceRangeInputs
          min={priceMin}
          max={priceMax}
          value={filters.priceRange || [priceMin, priceMax]}
          onChange={(range) => update("priceRange", range)}
        />
      </CollapsibleSection>

      <CollapsibleSection title="Loại xe" badge={filters.busTypes.size}>
        <CheckboxList
          options={busTypeOptions}
          selected={filters.busTypes}
          onToggle={(v) => update("busTypes", toggle(filters.busTypes, v))}
        />
      </CollapsibleSection>

      <CollapsibleSection title="Vị trí ghế" badge={filters.seatPositions.size}>
        <CheckboxList
          options={seatPositionOptions}
          renderLabel={(key) => SEAT_POSITION_LABEL[key] || key}
          selected={filters.seatPositions}
          onToggle={(v) =>
            update("seatPositions", toggle(filters.seatPositions, v))
          }
        />
      </CollapsibleSection>

      <CollapsibleSection title="Loại ghế" badge={filters.seatTypes.size}>
        <CheckboxList
          options={seatTypeOptions}
          selected={filters.seatTypes}
          onToggle={(v) => update("seatTypes", toggle(filters.seatTypes, v))}
        />
      </CollapsibleSection>
    </div>
  );
}
