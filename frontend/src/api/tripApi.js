const API_BASE_URL = "http://localhost:8080/api";

/**
 * Lay danh sach thanh pho de do vao dropdown diem di/den.
 */
export async function getCities() {
  const res = await fetch(`${API_BASE_URL}/cities`);
  if (!res.ok) {
    throw new Error("Không thể tải danh sách thành phố");
  }
  return res.json();
}

/**
 * Tim kiem chuyen di. returnDate la optional - chi truyen khi
 * nguoi dung muon tim khu hoi.
 */
export async function searchTrips(originCityId, destinationCityId, departureDate, returnDate) {
  const params = new URLSearchParams({ originCityId, destinationCityId, departureDate });
  if (returnDate) {
    params.append("returnDate", returnDate);
  }

  const res = await fetch(`${API_BASE_URL}/trips/search?${params.toString()}`);
  if (!res.ok) {
    throw new Error("Tìm kiếm chuyến đi thất bại");
  }
  return res.json();
}
