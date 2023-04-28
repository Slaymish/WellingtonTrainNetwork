// route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id,wheelchair_accessible,bikes_allowed,etm_id

public record Trip(String routeId, String serviceId, String tripId, String tripHeadsign, String directionId, String blockId, String shapeId, String wheelchairAccessible, String bikesAllowed, String etmId) {
    public Trip {
        if (routeId == null || serviceId == null || tripId == null || tripHeadsign == null || blockId == null || shapeId == null || etmId == null) {
            throw new IllegalArgumentException("Null argument");
        }
    }
}