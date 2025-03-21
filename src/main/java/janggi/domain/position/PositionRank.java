package janggi.domain.position;

import janggi.domain.Country;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum PositionRank {
    RANK_1(1),
    RANK_2(2),
    RANK_3(3),
    RANK_4(4),
    RANK_5(5),
    RANK_6(6),
    RANK_7(7),
    RANK_8(8),
    RANK_9(9),
    RANK_10(10),
    ;

    private final int amount;

    PositionRank(final int amount) {
        this.amount = amount;
    }

    public static PositionRank of(final int value, final Country country) {
        validateCountry(country);
        if (country == Country.HAN) {
            return findByAmount(getReversedRankAmount(value));
        }
        return findByAmount(value);
    }

    private static int getReversedRankAmount(final int value) {
        return 11 - value;
    }

    private static PositionRank findByAmount(final int amount) {
        return Arrays.stream(PositionRank.values())
                .filter(rank -> rank.amount == amount)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 랭크를 찾을 수 없습니다."));
    }

    private static void validateCountry(final Country country) {
        if (country == null) {
            throw new IllegalArgumentException("나라는 필수값입니다.");
        }
    }

    public static List<PositionRank> getAllRanks() {
        return Arrays.stream(values()).toList();
    }

    public PositionRank add(final int i) {
        return findByAmount(amount + i);
    }

    public boolean validateAdd(final int rankAmount) {
        return Arrays.stream(PositionRank.values())
                .anyMatch(r -> r.amount == rankAmount);
    }

    public List<PositionRank> getBetweenRanks(final PositionRank rank) {
        List<PositionRank> betweenRanks = new ArrayList<>();
        for (int newAmount = Math.min(amount, rank.amount) + 1; newAmount < Math.max(amount, rank.amount); newAmount++) {
            betweenRanks.add(findByAmount(newAmount));
        }
        return betweenRanks;
    }

    public int distance(final PositionRank rank) {
        return Math.abs(amount - rank.amount);
    }
}
