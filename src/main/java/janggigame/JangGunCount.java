package janggigame;

import domain.piece.Side;

import java.util.EnumMap;
import java.util.Map;

public class JangGunCount {
    private static final int JANGGUN_COUNT = 5;

    private final Map<Side, Integer> counts = new EnumMap<>(Side.class);

    public JangGunCount(int choJangGunCount, int hanJangGunCount) {
        counts.put(Side.CHO, choJangGunCount);
        counts.put(Side.HAN, hanJangGunCount);
    }

    public void increment(Side side) {
        counts.put(side, counts.get(side) + 1);
    }

    public void reset(Side side) {
        counts.put(side, 0);
    }

    public boolean isBigJang() {
        return counts.values().stream()
                .anyMatch(count -> count == JANGGUN_COUNT);
    }

    public int getCount(Side side) {
        return counts.get(side);
    }
}
