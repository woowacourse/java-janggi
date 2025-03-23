package janggi.board;

import janggi.Team.Team;
import janggi.piece.Elephant;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.position.Position;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public enum ElephantSetting {

    INNER_ELEPHANT("1", (team, y) -> {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(y, 2), new Elephant(team));
        pieces.put(new Position(y, 3), new Horse(team));
        pieces.put(new Position(y, 7), new Elephant(team));
        pieces.put(new Position(y, 8), new Horse(team));
        return pieces;
    }),
    OUTER_ELEPHANT("2", (team, y) -> {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(y, 2), new Horse(team));
        pieces.put(new Position(y, 3), new Elephant(team));
        pieces.put(new Position(y, 7), new Horse(team));
        pieces.put(new Position(y, 8), new Elephant(team));
        return pieces;
    }),
    RIGHT_ELEPHANT("3", (team, y) -> {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(y, 2), new Elephant(team));
        pieces.put(new Position(y, 3), new Horse(team));
        pieces.put(new Position(y, 7), new Horse(team));
        pieces.put(new Position(y, 8), new Elephant(team));
        return pieces;
    }),
    LEFT_ELEPHANT("4", (team, y) -> {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(y, 2), new Horse(team));
        pieces.put(new Position(y, 3), new Elephant(team));
        pieces.put(new Position(y, 7), new Elephant(team));
        pieces.put(new Position(y, 8), new Horse(team));
        return pieces;
    }),
    ;

    private final String menu;
    private final BiFunction<Team, Integer, Map<Position, Piece>> biFunction;

    ElephantSetting(String menu, BiFunction<Team, Integer, Map<Position, Piece>> biFunction) {
        this.menu = menu;
        this.biFunction = biFunction;
    }

    public static ElephantSetting selectSetting(String input) {
        return Arrays.stream(ElephantSetting.values())
                .filter(elephantSetting -> elephantSetting.menu.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 1~4까지의 값만 입력할 수 있습니다."));
    }

    public Map<Position, Piece> getElephantSetting(Team team, int y) {
        return biFunction.apply(team, y);
    }
}
