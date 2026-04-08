package janggi.infrastructure.mapper;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.team.TeamType;
import janggi.infrastructure.entity.BoardCellEntity;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class BoardMapper {

    private BoardMapper() {

    }

    public static Map<Position, Piece> toDomain(final List<BoardCellEntity> boardCellEntities) {
        final Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
        boardCellEntities.forEach(boardCellEntity -> {
            final Position position = Position.valueOf(boardCellEntity.row(),
                boardCellEntity.column());
            final TeamType teamType = TeamType.valueOf(boardCellEntity.team());
            final Piece piece = PieceType.valueOf(boardCellEntity.piece_type()).toPiece(teamType);
            positionPieceMap.put(position, piece);
        });

        return positionPieceMap;
    }

    public static List<BoardCellEntity> toEntity(final long gameId,
        final Map<Position, Piece> positionPieceMap) {
        return positionPieceMap.entrySet().stream()
            .map(positionPieceEntry ->
                BoardCellEntity.from(gameId, positionPieceEntry.getKey(),
                    positionPieceEntry.getValue()))
            .toList();
    }

}
