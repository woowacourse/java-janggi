package repository.mapper;

import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Soldier;
import domain.piece.Team;
import java.util.Map;
import java.util.function.Function;

public class PieceMapper {

    private static final String INVALID_PIECE_TYPE = "지원하지 않는 기물 타입입니다: ";

    private final Map<repository.snapshot.PieceType, Function<Team, Piece>> pieceFactories = Map.of(
            repository.snapshot.PieceType.GENERAL, General::new,
            repository.snapshot.PieceType.GUARD, Guard::new,
            repository.snapshot.PieceType.HORSE, Horse::new,
            repository.snapshot.PieceType.ELEPHANT, Elephant::new,
            repository.snapshot.PieceType.CHARIOT, Chariot::new,
            repository.snapshot.PieceType.CANNON, Cannon::new,
            repository.snapshot.PieceType.SOLDIER, Soldier::new
    );

    public repository.snapshot.PieceType toPieceType(Piece piece) {
        return repository.snapshot.PieceType.valueOf(piece.pieceType().name());
    }

    public Piece toPiece(repository.snapshot.PieceType pieceType, Team team) {
        return pieceFactory(pieceType).apply(team);
    }

    private Function<Team, Piece> pieceFactory(repository.snapshot.PieceType pieceType) {
        Function<Team, Piece> pieceFactory = pieceFactories.get(pieceType);
        if (pieceFactory != null) {
            return pieceFactory;
        }
        throw new IllegalArgumentException(INVALID_PIECE_TYPE + pieceType.name());
    }
}
