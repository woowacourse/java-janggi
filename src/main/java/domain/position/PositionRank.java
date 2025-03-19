package domain.position;

import domain.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class PositionRank {

    public static final int MIN_VALUE = 1;
    public static final int MAX_VALUE = 10;

    private final int value;

    private PositionRank(final int value) {
        validateValue(value);
        this.value = value;
    }

    private void validateValue(final int value) {
        if (value < MIN_VALUE) {
            throw new IllegalArgumentException("랭크는 %d 이상이어야 합니다.".formatted(MIN_VALUE));
        }
        if (value > MAX_VALUE) {
            throw new IllegalArgumentException("랭크는 %d 이하이어야 합니다.".formatted(MAX_VALUE));
        }
    }

    public static PositionRank of(final int value, final Country country) {
        validateCountry(country);
        if (country == Country.한나라) {
            return new PositionRank(11 - value);
        }
        return new PositionRank(value);
    }

    private static void validateCountry(final Country country) {
        if (country == null) {
            throw new IllegalArgumentException("나라는 필수값입니다.");
        }
    }

    public static List<PositionRank> getAllRanks() {
        return IntStream.rangeClosed(MIN_VALUE, MAX_VALUE)
                .mapToObj(PositionRank::new)
                .toList();
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final PositionRank that)) return false;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "PositionRank{" +
                "value=" + value +
                '}';
    }

    public PositionRank add(final int i) {
        return new PositionRank(value + i);
    }

    public boolean validateAdd(final int rankAmount) {
        final int newValue = value + rankAmount;
        return newValue >= MIN_VALUE && newValue <= MAX_VALUE;
    }

    public List<PositionRank> getBetweenRanks(final PositionRank rank) {
        List<PositionRank> ranks = new ArrayList<>();
        for (int newValue = Math.min(value, rank.value) + 1; newValue < Math.max(value, rank.value); newValue++) {
            ranks.add(new PositionRank(newValue));
        }
        return ranks;
    }

    public int distance(final PositionRank rank) {
        return Math.abs(value - rank.value);
    }
}
