package janggi.piece;

import static janggi.PieceType.*;

import janggi.PieceType;
import janggi.Team;
import java.util.Map;
import java.util.function.BiFunction;

public class PieceCreator {
    private static Map<PieceType, BiFunction<Team, PieceType, Piece>> TYPE_TO_PIECE = Map.of(
            CANON, Canon::new,
            CHARIOT, Chariot::new,
            ELEPHANT, Elephant::new,
            GENERAL, General::new,
            GUARD, Guard::new,
            HORSE, Horse::new,
            SOLDIER, Soldier::new
    );

    public static Piece create(Team team, PieceType type) {
        BiFunction<Team, PieceType, Piece> creator = TYPE_TO_PIECE.get(type);
        return creator.apply(team, type);
    }
}
