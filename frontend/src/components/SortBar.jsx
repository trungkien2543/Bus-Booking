const sortOptions = [
  { value: "departure_asc", label: "Giờ đi sớm nhất" },
  { value: "departure_desc", label: "Giờ đi muộn nhất" },
  { value: "price_asc", label: "Giá thấp nhất" },
];

export default function SortBar({ sortBy, onSortChange }) {
  return (
    <div className="w-full bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden">
      <h3 className="font-semibold text-gray-800 px-4 py-3 border-b border-gray-100">
        Sắp xếp
      </h3>
      <div className="flex flex-col py-1">
        {sortOptions.map((opt) => (
          <label
            key={opt.value}
            className={
              "flex items-center gap-2.5 px-4 py-2.5 cursor-pointer transition-colors " +
              (sortBy === opt.value ? "bg-blue-50" : "hover:bg-gray-50")
            }
          >
            <input
              type="radio"
              name="sortBy"
              className="w-4 h-4 accent-blue-600 shrink-0"
              checked={sortBy === opt.value}
              onChange={() => onSortChange(opt.value)}
            />
            <span
              className={
                "text-[15px] font-medium " +
                (sortBy === opt.value ? "text-blue-700" : "text-gray-800")
              }
            >
              {opt.label}
            </span>
          </label>
        ))}
      </div>
    </div>
  );
}

// Ham sap xep dung chung
export function sortTrips(trips, sortBy) {
  if (!trips) return trips;
  const sorted = [...trips];
  switch (sortBy) {
    case "departure_asc":
      return sorted.sort(
        (a, b) => new Date(a.departureTime) - new Date(b.departureTime),
      );
    case "departure_desc":
      return sorted.sort(
        (a, b) => new Date(b.departureTime) - new Date(a.departureTime),
      );
    case "price_asc":
      return sorted.sort((a, b) => a.price - b.price);
    default:
      return sorted;
  }
}
