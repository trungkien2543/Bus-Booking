import { useEffect, useState } from "react";
import { getCities, searchTrips } from "./api/tripApi";
import Header from "./components/Header";
import TripSearchForm from "./components/TripSearchForm";
import PopularRoutes from "./components/PopularRoutes";
import FilterSidebar from "./components/FilterSidebar";
import TripList from "./components/TripList";

function App() {
  const [cities, setCities] = useState([]);
  const [loading, setLoading] = useState(false);
  const [searched, setSearched] = useState(false);
  const [outboundTrips, setOutboundTrips] = useState([]);
  const [returnTrips, setReturnTrips] = useState(null);
  const [error, setError] = useState("");

  const [filters, setFilters] = useState({
    operators: new Set(),
    seatTypes: new Set(),
  });

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
    setFilters({ operators: new Set(), seatTypes: new Set() }); // reset bo loc moi lan tim moi

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
  function applyFilters(trips) {
    if (!trips) return trips;
    return trips.filter((t) => {
      const operatorOk =
        filters.operators.size === 0 || filters.operators.has(t.operatorName);
      const seatTypeOk =
        filters.seatTypes.size === 0 ||
        (t.seatTypes || []).some((st) => filters.seatTypes.has(st));
      return operatorOk && seatTypeOk;
    });
  }

  const allTrips = [...outboundTrips, ...(returnTrips || [])];
  const filteredOutbound = applyFilters(outboundTrips);
  const filteredReturn = applyFilters(returnTrips);

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

        {/* Da tim -> hien bo loc (1 cot) + ket qua */}
        {searched && (
          <div className="flex gap-6 -mt-10 items-start">
            <FilterSidebar
              allTrips={allTrips}
              filters={filters}
              onFilterChange={setFilters}
            />
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
