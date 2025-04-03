package model.piece;

import model.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PieceFactory {
    private static final Map<String, Function<Team, Piece>> PIECE_MAP = new HashMap<>();

    static {
        PIECE_MAP.put("漢", Jang::new);
        PIECE_MAP.put("士", Sa::new);
        PIECE_MAP.put("象", Sang::new);
        PIECE_MAP.put("馬", Ma::new);
        PIECE_MAP.put("車", Cha::new);
        PIECE_MAP.put("包", Po::new);
        PIECE_MAP.put("兵", Byeong::new);
    }

    public static Piece createPiece(String name, Team team) {
        if (!PIECE_MAP.containsKey(name)) {
            throw new IllegalArgumentException("잘못된 기물 이름: " + name);
        }
        return PIECE_MAP.get(name).apply(team);
    }
}
