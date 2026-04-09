package repository.mapper;

import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Soldier;
import domain.piece.Team;
import java.util.Map;
import java.util.function.Function;

public class PieceMapper {

    private static final String INVALID_PIECE_TYPE = "지원하지 않는 기물 타입입니다: ";

    private final Map<PieceType, Function<Team, Piece>> pieceFactories = Map.of(
            PieceType.GENERAL, General::new,
            PieceType.GUARD, Guard::new,
            PieceType.HORSE, Horse::new,
            PieceType.ELEPHANT, Elephant::new,
            PieceType.CHARIOT, Chariot::new,
            PieceType.CANNON, Cannon::new,
            PieceType.SOLDIER, Soldier::new
    );

    public PieceType toPieceType(Piece piece) {
        return piece.pieceType();
    }

    public Piece toPiece(PieceType pieceType, Team team) {
        return pieceFactory(pieceType).apply(team);
    }

    private Function<Team, Piece> pieceFactory(PieceType pieceType) {
        Function<Team, Piece> pieceFactory = pieceFactories.get(pieceType);
        if (pieceFactory != null) {
            return pieceFactory;
        }
        throw new IllegalArgumentException(INVALID_PIECE_TYPE + pieceType.name());
    }
}
