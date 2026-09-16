export default function Header() {
  return (
    <header className="bg-white shadow-sm sticky top-0 z-10">
      <div className="max-w-6xl mx-auto px-4 py-4 flex items-center justify-between">
        <div className="text-xl font-bold text-blue-700">
          Bus<span className="text-yellow-500">Booking</span>
        </div>
        <nav className="text-sm text-gray-600 flex gap-6">
          <a href="#" className="hover:text-blue-700">Trang chủ</a>
          <a href="#" className="hover:text-blue-700">Vé của tôi</a>
          <a href="#" className="hover:text-blue-700">Liên hệ</a>
        </nav>
      </div>
    </header>
  );
}
