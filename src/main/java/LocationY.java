import java.util.HashMap;
import java.util.Map;

public class LocationY {
    private static final Integer MIN_VALUE = 0;
    private static final Integer MAX_VALUE = 9;

    private static final Map<Integer, LocationY> CACHE = cacheLocationYs();

    private static Map<Integer,LocationY> cacheLocationYs() {
        Map<Integer, LocationY> caches = new HashMap<>();

        for(int i = MIN_VALUE; i <= MAX_VALUE; i++) {
            caches.put(i, new LocationY(i));
        }

        return caches;
    }
    private final Integer y;

    public LocationY(int y) {
        validateRange(y);
        this.y = y;
    }

    public static LocationY getInstance(int y) {
        if(CACHE.containsKey(y)) {
            return CACHE.get(y);
        }
        return new LocationY(y);
    }

    private void validateRange(final int y) {
        if (y < MIN_VALUE || y > MAX_VALUE) {
            throw new IllegalArgumentException("[ERROR] y의 범위가 잘못되었습니다.");
        }
    }
}
