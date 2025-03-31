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
        return maxRankAmount() - value + 1;
    }

    public static PositionRank findByAmount(final int amount) {
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

    public PositionRank add(final int rankAmount) {
        return findByAmount(amount + rankAmount);
    }

    public boolean isValidToAdd(final int rankAmount) {
        return Arrays.stream(PositionRank.values())
                .anyMatch(r -> r.amount == this.amount + rankAmount);
    }

    /**
     * 두 랭크 사이의 랭크들을 반환하는 기능입니다.
     * 첫 랭크와 끝 랭크를 포함하지 않습니다.
     *
     * @param rank 끝 랭크
     * @return 두 랭크 사이의 랭크들
     */
    public List<PositionRank> getBetweenRanks(final PositionRank rank) {
        if (this.ordinal() > rank.ordinal()) {
            return rank.getBetweenRanks(this).reversed();
        }

        List<PositionRank> betweenRanks = new ArrayList<>();
        for (int newAmount = amount + 1; newAmount < rank.amount; newAmount++) {
            betweenRanks.add(findByAmount(newAmount));
        }
        return betweenRanks;
    }

    public boolean isBetween(final PositionRank minRank, final PositionRank maxRank) {
        return minRank.ordinal() <= ordinal() && ordinal() <= maxRank.ordinal();
    }

    public int amount() {
        return amount;
    }

    public static int maxRankAmount() {
        return RANK_10.amount;
    }
}
