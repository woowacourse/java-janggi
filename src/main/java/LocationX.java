import java.util.HashMap;
import java.util.Map;

public class LocationX {
    public static final Integer LOCATION_X_MIN_VALUE = 0;
    public static final Integer LOCATION_X_MAX_VALUE = 8; // TODO

    private static final Map<Integer, LocationX> CACHE = cacheLocationXs();

    private static Map<Integer,LocationX> cacheLocationXs() {
        Map<Integer, LocationX> caches = new HashMap<>();

        for(int i = LOCATION_X_MIN_VALUE; i <= LOCATION_X_MAX_VALUE; i++) {
            caches.put(i, new LocationX(i));
        }

        return caches;
    }

    private final Integer x;

    private LocationX(int x) {
        validateRange(x);
        this.x = x;
    }

    private void validateRange(final int x) {
        if (x < LOCATION_X_MIN_VALUE || x > LOCATION_X_MAX_VALUE) {
            throw new IllegalArgumentException("[ERROR] x의 범위가 잘못되었습니다.");
        }
    }

    public static LocationX getInstance(int x) {
        if(CACHE.containsKey(x)) {
            return CACHE.get(x);
        }
        return new LocationX(x);
    }
}
