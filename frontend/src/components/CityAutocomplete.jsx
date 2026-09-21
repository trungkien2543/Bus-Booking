import { useEffect, useRef, useState } from "react";

/**
 * O nhap co the go de loc danh sach thanh pho, thay cho <select> tinh.
 * value/onChange dung theo cityId (UUID). label la tieu de co dinh
 * hien phia tren khung, luon hien du da chon hay chua (khac voi
 * placeholder, se bien mat sau khi chon).
 */
export default function CityAutocomplete({ cities, value, onChange, placeholder, icon, excludeId, label }) {
  const [query, setQuery] = useState("");
  const [isOpen, setIsOpen] = useState(false);
  const containerRef = useRef(null);

  const availableCities = cities.filter((c) => c.id !== excludeId);

  useEffect(() => {
    const selected = cities.find((c) => c.id === value);
    setQuery(selected ? selected.name : "");
  }, [value, cities]);

  useEffect(() => {
    function handleClickOutside(e) {
      if (containerRef.current && !containerRef.current.contains(e.target)) {
        setIsOpen(false);
        const selected = cities.find((c) => c.id === value);
        setQuery(selected ? selected.name : "");
      }
    }
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, [value, cities]);

  const filtered = availableCities.filter((c) =>
    c.name.toLowerCase().includes(query.toLowerCase())
  );

  function handleSelect(city) {
    onChange(city.id);
    setQuery(city.name);
    setIsOpen(false);
  }

  function handleInputChange(e) {
    setQuery(e.target.value);
    setIsOpen(true);
    if (e.target.value === "") {
      onChange("");
    }
  }

  return (
    <div className="flex-1 min-w-[160px]">
      {label && (
        <label className="block text-sm font-medium text-gray-600 mb-1.5">
          {label}
        </label>
      )}

      <div ref={containerRef} className="relative">
        <div className="flex items-center gap-2.5 border border-gray-300 rounded-xl px-4 py-3.5 bg-white focus-within:ring-2 focus-within:ring-blue-400 focus-within:border-transparent transition-shadow">
          <span className="text-blue-600 shrink-0">{icon}</span>
          <input
            type="text"
            className="flex-1 outline-none text-base placeholder-gray-400"
            placeholder={placeholder}
            value={query}
            onChange={handleInputChange}
            onFocus={() => setIsOpen(true)}
          />
        </div>

        {isOpen && filtered.length > 0 && (
          <ul className="absolute z-20 mt-1.5 w-full bg-white border border-gray-200 rounded-xl shadow-lg max-h-60 overflow-y-auto">
            {filtered.map((city) => (
              <li
                key={city.id}
                onClick={() => handleSelect(city)}
                className="px-4 py-3 text-base hover:bg-blue-50 cursor-pointer"
              >
                {city.name}
              </li>
            ))}
          </ul>
        )}

        {isOpen && query !== "" && filtered.length === 0 && (
          <div className="absolute z-20 mt-1.5 w-full bg-white border border-gray-200 rounded-xl shadow-lg px-4 py-3 text-sm text-gray-400">
            Không tìm thấy thành phố phù hợp
          </div>
        )}
      </div>
    </div>
  );
}