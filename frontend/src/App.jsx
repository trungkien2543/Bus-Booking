import { useEffect, useState } from "react";
import { getCities, searchTrips } from "./api/tripApi";
import Header from "./components/Header";
import TripSearchForm from "./components/TripSearchForm";
import PopularRoutes from "./components/PopularRoutes";
import FilterSidebar, { getDepartureMinutes } from "./components/FilterSidebar";
import SortBar, { sortTrips } from "./components/SortBar";
import TripList from "./components/TripList";

const EMPTY_FILTERS = {
  timeRange: [0, 1440],
  operators: new Set(),
  pickupPoints: new Set(),
  dropoffPoints: new Set(),
  busTypes: new Set(),
  seatPositions: new Set(),
  seatTypes: new Set(),
  priceRange: null,
};

function App() {
  const [cities, setCities] = useState([]);
  const [loading, setLoading] = useState(false);
  const [searched, setSearched] = useState(false);
  const [outboundTrips, setOutboundTrips] = useState([]);
  const [returnTrips, setReturnTrips] = useState(null);
  const [error, setError] = useState("");

  const [filters, setFilters] = useState(EMPTY_FILTERS);
  const [sortBy, setSortBy] = useState("departure_asc");

  useEffect(() => {
    getCities()
      .then(setCities)
      .catch((err) => console.error("Lỗi khi gọi API:", err));
  }, []);

  function handleSearch({
    originCityId,
    destinationCityId,
    departureDate,
    returnDate,
  }) {
    setError("");
    setLoading(true);
    setFilters(EMPTY_FILTERS); // reset bo loc moi lan tim moi

    searchTrips(originCityId, destinationCityId, departureDate, returnDate)
      .then((data) => {
        setOutboundTrips(data.outboundTrips);
        setReturnTrips(data.returnTrips);
        setSearched(true);
      })
      .catch((err) => {
        console.error("Lỗi khi tìm chuyến:", err);
        setError("Đã có lỗi xảy ra khi tìm kiếm, vui lòng thử lại");
      })
      .finally(() => setLoading(false));
  }

  // Loc client-side: Set rong = khong loc gi (hien tat ca)
  // Cac field mang (pickupPoints, dropoffPoints, seatPositions, seatTypes)
  // dung .some() vi 1 trip co the co nhieu gia tri cho 1 field.
  function applyFilters(trips) {
    if (!trips) return trips;
    return trips.filter((t) => {
      const departureMin = getDepartureMinutes(t.departureTime);
      const timeOk =
        departureMin >= filters.timeRange[0] &&
        departureMin <= filters.timeRange[1];
      const operatorOk =
        filters.operators.size === 0 || filters.operators.has(t.operatorName);
      const busTypeOk =
        filters.busTypes.size === 0 || filters.busTypes.has(t.busType);
      const priceOk =
        !filters.priceRange ||
        (t.price >= filters.priceRange[0] && t.price <= filters.priceRange[1]);
      const pickupOk =
        filters.pickupPoints.size === 0 ||
        (t.pickupPoints || []).some((p) => filters.pickupPoints.has(p));
      const dropoffOk =
        filters.dropoffPoints.size === 0 ||
        (t.dropoffPoints || []).some((p) => filters.dropoffPoints.has(p));
      const seatPositionOk =
        filters.seatPositions.size === 0 ||
        (t.seatPositions || []).some((p) => filters.seatPositions.has(p));
      const seatTypeOk =
        filters.seatTypes.size === 0 ||
        (t.seatTypes || []).some((st) => filters.seatTypes.has(st));

      return (
        timeOk &&
        operatorOk &&
        busTypeOk &&
        priceOk &&
        pickupOk &&
        dropoffOk &&
        seatPositionOk &&
        seatTypeOk
      );
    });
  }

  const allTrips = [...outboundTrips, ...(returnTrips || [])];
  const filteredOutbound = sortTrips(applyFilters(outboundTrips), sortBy);
  const filteredReturn = sortTrips(applyFilters(returnTrips), sortBy);

  return (
    <div className="min-h-screen bg-gray-50">
      <Header />

      {/* Banner nen theo chu de + thanh tim kiem noi ben tren, canh giua */}
      <div className="bg-gradient-to-br from-blue-700 to-blue-900 pt-10 pb-20 px-4">
        <div className="max-w-6xl mx-auto text-center mb-6">
          <h1 className="text-3xl font-bold text-white">
            Đặt vé xe khách <span className="text-yellow-400">nhanh chóng</span>
          </h1>
          <p className="text-blue-100 mt-2">
            Hàng nghìn chuyến xe, hàng trăm nhà xe uy tín
          </p>
        </div>
        <div className="max-w-6xl mx-auto relative">
          <TripSearchForm
            cities={cities}
            onSearch={handleSearch}
            loading={loading}
          />
        </div>
      </div>

      <div className="max-w-6xl mx-auto px-4">
        {error && <p className="text-red-500 mt-4">{error}</p>}

        {/* Chua tim gi -> hien tuyen pho bien */}
        {!searched && <PopularRoutes />}

        {/* Da tim -> cot trai: sap xep + loc, cot phai: ket qua */}
        {searched && (
          <div className="flex gap-6 -mt-10 items-start">
            <div className="w-64 shrink-0 flex flex-col gap-4">
              <SortBar sortBy={sortBy} onSortChange={setSortBy} />
              <FilterSidebar
                allTrips={allTrips}
                filters={filters}
                onFilterChange={setFilters}
              />
            </div>
            <div className="flex-1 flex gap-6 flex-wrap bg-white rounded-2xl shadow-sm border border-gray-100 p-6">
              <TripList title="Chuyến đi" trips={filteredOutbound} />
              <TripList title="Chuyến về" trips={filteredReturn} />
            </div>
          </div>
        )}
      </div>

      <div className="h-16" />
    </div>
  );
}

export default App;
