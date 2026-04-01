package domain.board;

import domain.board.exception.FormationException;

import java.util.Arrays;
import java.util.List;

import static domain.board.exception.BoardError.FORMATION_IS_NOT_NUMERIC;
import static domain.board.exception.BoardError.FORMATION_NUMBER_RANGE_IS_INVALID;

public enum Formation {

    ELEPHANT_HORSE_ELEPHANT_HORSE(1, List.of(1, 6), List.of(2, 7)),
    ELEPHANT_HORSE_HORSE_ELEPHANT(2, List.of(1, 7), List.of(2, 6)),
    HORSE_ELEPHANT_ELEPHANT_HORSE(3, List.of(2, 6), List.of(1, 7)),
    HORSE_ELEPHANT_HORSE_ELEPHANT(4, List.of(2, 7), List.of(1, 6)),
    ;

    private final int formatNumber;
    private final List<Integer> elephantFormations;
    private final List<Integer> horseFormations;

    Formation(int formatNumber, List<Integer> elephantFormations, List<Integer> horseFormations) {
        this.formatNumber = formatNumber;
        this.elephantFormations = elephantFormations;
        this.horseFormations = horseFormations;
    }

    public static Formation from(String input) {
        validateNumeric(input);
        return valueOf(Integer.parseInt(input));
    }

    private static Formation valueOf(int formatNumber) {
        return Arrays.stream(Formation.values())
                .filter(formation -> formation.formatNumber == formatNumber)
                .findFirst()
                .orElseThrow(() -> new FormationException(FORMATION_NUMBER_RANGE_IS_INVALID.getMessage()));
    }

    private static void validateNumeric(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new FormationException(FORMATION_IS_NOT_NUMERIC.getMessage());
        }
    }

    public List<Integer> elephantFormations() {
        return elephantFormations;
    }

    public List<Integer> horseFormations() {
        return horseFormations;
    }

}
