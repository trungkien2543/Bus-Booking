import { useEffect, useState } from "react";

function App() {
  const [cities, setCities] = useState([]);
  const [originCityId, setOriginCityId] = useState("");
  const [destinationCityId, setDestinationCityId] = useState("");
  const [departureDate, setDepartureDate] = useState("");
  const [returnDate, setReturnDate] = useState("");

  useEffect(() => {
    fetch("http://localhost:8080/api/cities")
      .then((res) => res.json())
      .then((data) => setCities(data))
      .catch((err) => console.error("Lỗi khi gọi API:", err));
  }, []);

  return (
    <div style={{ padding: 20 }}>
      <h1>Tìm chuyến xe</h1>

      <div style={{ display: "flex", gap: 12 }}>
        <label>
          Điểm đi
          <br />
          <select
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

        <label>
          Điểm đến
          <br />
          <select
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

        <label>
          Ngày đi <span style={{ color: "red" }}>*</span>
          <br />
          <input
            type="date"
            value={departureDate}
            onChange={(e) => setDepartureDate(e.target.value)}
          />
        </label>

        <label>
          Ngày về (không bắt buộc)
          <br />
          <input
            type="date"
            value={returnDate}
            onChange={(e) => setReturnDate(e.target.value)}
          />
        </label>
      </div>

      {/* Debug tạm thời - xóa sau khi xác nhận chạy đúng */}
      <p style={{ marginTop: 16, color: "gray" }}>
        Đã chọn: điểm đi = {originCityId || "(chưa chọn)"} | điểm đến ={" "}
        {destinationCityId || "(chưa chọn)"} | ngày đi ={" "}
        {departureDate || "(chưa chọn)"} | ngày về ={" "}
        {returnDate || "(không có)"}
      </p>
    </div>
  );
}

export default App;
