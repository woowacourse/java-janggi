package janggi.domain.board;

import janggi.domain.piece.Ma;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Sang;
import janggi.domain.position.Position;
import janggi.domain.team.TeamType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public enum SangSetting {

    INNER_SANG("1", (team, y) -> {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(y, 2), new Sang(team));
        pieces.put(new Position(y, 3), new Ma(team));
        pieces.put(new Position(y, 7), new Sang(team));
        pieces.put(new Position(y, 8), new Ma(team));
        return pieces;
    }),
    OUTER_SANG("2", (team, y) -> {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(y, 2), new Ma(team));
        pieces.put(new Position(y, 3), new Sang(team));
        pieces.put(new Position(y, 7), new Ma(team));
        pieces.put(new Position(y, 8), new Sang(team));
        return pieces;
    }),
    RIGHT_SANG("3", (team, y) -> {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(y, 2), new Sang(team));
        pieces.put(new Position(y, 3), new Ma(team));
        pieces.put(new Position(y, 7), new Ma(team));
        pieces.put(new Position(y, 8), new Sang(team));
        return pieces;
    }),
    LEFT_SANG("4", (team, y) -> {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(y, 2), new Ma(team));
        pieces.put(new Position(y, 3), new Sang(team));
        pieces.put(new Position(y, 7), new Sang(team));
        pieces.put(new Position(y, 8), new Ma(team));
        return pieces;
    }),
    ;

    private final String menu;
    private final BiFunction<TeamType, Integer, Map<Position, Piece>> biFunction;

    SangSetting(String menu, BiFunction<TeamType, Integer, Map<Position, Piece>> biFunction) {
        this.menu = menu;
        this.biFunction = biFunction;
    }

    public static SangSetting selectSetting(String input) {
        return Arrays.stream(SangSetting.values())
                .filter(sangSetting -> sangSetting.menu.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 1~4까지의 값만 입력할 수 있습니다."));
    }

    public Map<Position, Piece> getSangSetting(TeamType teamType, int y) {
        return biFunction.apply(teamType, y);
    }
}
