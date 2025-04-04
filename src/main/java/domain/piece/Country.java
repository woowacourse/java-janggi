package domain.piece;

import domain.position.LineDirection;

import java.util.EnumMap;
import java.util.Map;

public enum Country {

    CHO,
    HAN;

    private static final Map<Country, LineDirection> directionByCountry = new EnumMap<>(Country.class);

    public static void assignDirection(Country country, LineDirection direction) {
        directionByCountry.put(country, direction);
        directionByCountry.put(country.opposite(), direction.opposite());
    }

    public int getLineFarBy(int distanceFromEnd) {
        return getDirection().getLineFarBy(distanceFromEnd);
    }

    public LineDirection getDirection() {
        return directionByCountry.get(this);
    }

    public Country opposite() {
        return this == CHO ? HAN : CHO;
    }

    public static Map<Country, LineDirection> getDirectionByCountry() {
        return Map.copyOf(directionByCountry);
    }

    public static Country getDefaultTeam() {
        return CHO;
    }
}
