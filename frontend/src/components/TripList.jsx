import TripCard from "./TripCard";

export default function TripList({ title, trips }) {
  if (!trips) return null;

  return (
    <div className="flex-1 min-w-[280px]">
      <h3 className="text-base font-semibold text-gray-700 mb-3">{title}</h3>
      {trips.length === 0 && (
        <p className="text-gray-400 text-sm">Không có chuyến nào phù hợp.</p>
      )}
      <div className="flex flex-col gap-3">
        {trips.map((trip) => (
          <TripCard key={trip.id} trip={trip} />
        ))}
      </div>
    </div>
  );
}
