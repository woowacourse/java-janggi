import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationX {
    private static final Integer MIN_VALUE = 0;
    private static final Integer MAX_VALUE = 8;

    private static final Map<Integer, LocationX> CACHE = cacheLocationXs();

    private static Map<Integer,LocationX> cacheLocationXs() {
        Map<Integer, LocationX> caches = new HashMap<>();

        for(int i = MIN_VALUE; i <= MAX_VALUE; i++) {
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
        if (x < MIN_VALUE || x > MAX_VALUE) {
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
