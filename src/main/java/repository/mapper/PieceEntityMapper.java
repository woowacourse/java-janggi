package repository.mapper;

import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.position.Position;
import repository.entity.GamePieceEntity;

public final class PieceEntityMapper {
    public Piece toDomain(GamePieceEntity entity) {
        return PieceFactory.create(PieceType.valueOf(entity.pieceType()), Team.valueOf(entity.team()));
    }

    public GamePieceEntity toNewEntity(Long gameId, Piece piece, Position pos) {
        return new GamePieceEntity(
                null,
                gameId,
                piece.getPieceType().name(),
                piece.getTeam().name(),
                pos.getRow().value(),
                pos.getColumn().value(),
                true
        );
    }
}
