/**
 * Anh that theo ten thanh pho, luu ngay trong code FE - KHONG luu DB.
 * Nguon: Wikimedia Commons (Creative Commons, duoc phep dung).
 * Thanh pho nao chua co trong danh sach nay se fallback ve anh
 * placeholder ngau nhien (xem PopularRoutes.jsx).
 *
 * Cach them anh moi: tim tren commons.wikimedia.org, lay ten file,
 * ghep URL theo mau:
 * https://commons.wikimedia.org/wiki/Special:FilePath/<ten_file>?width=500
 */
export const CITY_IMAGES = {
  "Hồ Chí Minh": "https://commons.wikimedia.org/wiki/Special:FilePath/Ho_Chi_Minh_City_Skyline.jpg?width=500",
  "Hà Nội": "https://commons.wikimedia.org/wiki/Special:FilePath/Hanoi_skyline_at_night.jpg?width=500",
  "Lâm Đồng": "https://commons.wikimedia.org/wiki/Special:FilePath/Da_Lat_-_Viet_Nam.jpg?width=500",
};

/**
 * Lay anh cho 1 thanh pho: uu tien anh that trong CITY_IMAGES,
 * fallback ve anh placeholder ngau nhien (nhung on dinh theo ten)
 * neu chua co anh that.
 */
export function getCityImage(cityName) {
  if (CITY_IMAGES[cityName]) {
    return CITY_IMAGES[cityName];
  }
  return `https://picsum.photos/seed/${encodeURIComponent(cityName)}/500/500`;
}
