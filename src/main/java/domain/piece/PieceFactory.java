package domain.piece;

import java.util.Map;
import java.util.function.Function;

public class PieceFactory {

    private static final Map<String, Function<Team, Piece>> FACTORY_MAP = Map.of(
            PieceType.CHARIOT.name(), Chariot::new,
            PieceType.HORSE.name(), Horse::new,
            PieceType.ELEPHANT.name(), Elephant::new,
            PieceType.GUARD.name(), Guard::new,
            PieceType.GENERAL.name(), General::new,
            PieceType.CANNON.name(), Cannon::new,
            PieceType.SOLDIER.name(), Soldier::new,
            PieceType.EMPTY.name(), EmptyPiece::new
    );

    public static Piece create(String pieceType, String team) {
        return FACTORY_MAP.get(pieceType).apply(Team.valueOf(team));
    }
}
