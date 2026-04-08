package janggi.domain;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class MultiKeyMap<K1, K2, V> {

    private final Map<K1, Map<K2, V>> map;

    public MultiKeyMap(final List<K1> firstKeys) {
        this.map = initMap(firstKeys);
    }

    public MultiKeyMap(final Map<K1, Map<K2, V>> map) {
        this.map = map;
    }

    private Map<K1, Map<K2, V>> initMap(final List<K1> firstKeys) {
        final Map<K1, Map<K2, V>> map = new LinkedHashMap<>();
        firstKeys.forEach(firstKey -> map.put(firstKey, new LinkedHashMap<>()));

        return map;
    }

    public boolean containsKey(final K1 k1, final K2 k2) {
        return map.containsKey(k1) && map.get(k1).containsKey(k2);
    }

    public V get(final K1 k1, final K2 k2) {
        if (!containsKey(k1, k2)) {
            throw new IllegalArgumentException("요청한 키 세트에 대응하는 값이 존재하지 않습니다.");
        }

        return map.get(k1).get(k2);
    }

    public V put(final K1 k1, final K2 k2, final V v) {
        if (!map.containsKey(k1)) {
            map.put(k1, new LinkedHashMap<>());
        }

        return map.get(k1).put(k2, v);
    }

}
