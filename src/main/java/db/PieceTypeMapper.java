package db;

import domain.game.Team;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Soldier;
import java.util.Map;
import java.util.function.Function;

public class PieceTypeMapper {
    private static final Map<Class<? extends Piece>, String> TO_TYPE_NAME = Map.of(
            Chariot.class, "CHARIOT",
            Cannon.class, "CANNON",
            Horse.class, "HORSE",
            Elephant.class, "ELEPHANT",
            Guard.class, "GUARD",
            General.class, "GENERAL",
            Soldier.class, "SOLDIER"
    );

    private static final Map<String, Function<Team, Piece>> TO_PIECE = Map.of(
            "CHARIOT", Chariot::new,
            "CANNON", Cannon::new,
            "HORSE", Horse::new,
            "ELEPHANT", Elephant::new,
            "GUARD", Guard::new,
            "GENERAL", General::new,
            "SOLDIER", Soldier::new
    );

    public static String toTypeName(Piece piece) {
        String typeName = TO_TYPE_NAME.get(piece.getClass());
        if (typeName == null) {
            throw new IllegalArgumentException("알 수 없는 기물 타입: " + piece.getClass().getSimpleName());
        }
        return typeName;
    }

    public static Piece toPiece(String typeName, Team team) {
        Function<Team, Piece> factory = TO_PIECE.get(typeName);
        if (factory == null) {
            throw new IllegalArgumentException("알 수 없는 기물 타입: " + typeName);
        }
        return factory.apply(team);
    }
}
