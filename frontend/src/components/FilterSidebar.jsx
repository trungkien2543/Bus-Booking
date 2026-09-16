import { useMemo } from "react";

function CheckboxGroup({ label, options, selected, onToggle }) {
  return (
    <div className="mb-5">
      <div className="text-sm font-semibold text-gray-700 mb-2">{label}</div>
      <div className="flex flex-col gap-2">
        {options.map((opt) => (
          <label key={opt} className="flex items-center gap-2 text-sm text-gray-600 cursor-pointer">
            <input
              type="checkbox"
              className="accent-blue-600"
              checked={selected.has(opt)}
              onChange={() => onToggle(opt)}
            />
            {opt}
          </label>
        ))}
      </div>
    </div>
  );
}

/**
 * allTrips: gop ca outboundTrips + returnTrips de suy ra day du
 * cac gia tri co the loc (nha xe, loai ghe...).
 * filters: { operators: Set, seatTypes: Set } - Set rong = khong loc gi ca (hien tat ca).
 */
export default function FilterSidebar({ allTrips, filters, onFilterChange }) {
  const operatorOptions = useMemo(
    () => [...new Set(allTrips.map((t) => t.operatorName))],
    [allTrips]
  );
  const seatTypeOptions = useMemo(
    () => [...new Set(allTrips.flatMap((t) => t.seatTypes || []))],
    [allTrips]
  );

  function toggle(set, value) {
    const next = new Set(set);
    next.has(value) ? next.delete(value) : next.add(value);
    return next;
  }

  return (
    <aside className="w-56 shrink-0 bg-white rounded-2xl shadow-sm border border-gray-100 p-4 h-fit">
      <h3 className="font-semibold text-gray-800 mb-4">Bộ lọc</h3>

      <CheckboxGroup
        label="Nhà xe"
        options={operatorOptions}
        selected={filters.operators}
        onToggle={(v) =>
          onFilterChange({ ...filters, operators: toggle(filters.operators, v) })
        }
      />

      <CheckboxGroup
        label="Loại ghế"
        options={seatTypeOptions}
        selected={filters.seatTypes}
        onToggle={(v) =>
          onFilterChange({ ...filters, seatTypes: toggle(filters.seatTypes, v) })
        }
      />
    </aside>
  );
}
