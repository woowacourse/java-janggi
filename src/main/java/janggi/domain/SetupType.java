package janggi.domain;

import janggi.domain.piece.PieceType;
import java.util.Arrays;
import java.util.List;

public enum SetupType {
    LEFT_ELEPHANT(1, List.of(2, 7), List.of(3, 8)),
    RIGHT_ELEPHANT(2, List.of(3, 8), List.of(2, 7)),
    INNER_ELEPHANT(3, List.of(3, 7), List.of(2, 8)),
    OUTER_ELEPHANT(4, List.of(2, 8), List.of(3, 7)),
    ;

    private final int option;
    private final List<Integer> elephantColumnNumbers;
    private final List<Integer> horseColumnNumbers;

    SetupType(final int option, final List<Integer> elephantColumnNumbers, final List<Integer> horseColumnNumbers) {
        this.option = option;
        this.elephantColumnNumbers = elephantColumnNumbers;
        this.horseColumnNumbers = horseColumnNumbers;
    }

    public static SetupType findSetupType(int option) {
        return Arrays.stream(SetupType.values())
                .filter(setupType -> setupType.option == option)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 옵션입니다"));
    }

    public List<Integer> getElephantColumnNumbers(Team team) {
        return selectRowByTeam(team, elephantColumnNumbers);
    }

    public List<Integer> getHorseColumnNumbers(Team team) {
        return selectRowByTeam(team, horseColumnNumbers);
    }

    private List<Integer> selectRowByTeam(Team team, List<Integer> columns) {
        List<Integer> targetColumns = columns;
        if (this == LEFT_ELEPHANT || this == RIGHT_ELEPHANT) {
            targetColumns = reverseTeamColumn(team, columns);
        }
        return targetColumns;
    }

    private static List<Integer> reverseTeamColumn(final Team team, final List<Integer> columns) {
        if (team.isRed()) {
            return columns.stream()
                    .map(row -> 10 - row)
                    .toList();
        }
        return columns;
    }

    public static double getInitScore() {
        return 2 * PieceType.ELEPHANT.getScore().value() + 2 * PieceType.HORSE.getScore().value();
    }
}
