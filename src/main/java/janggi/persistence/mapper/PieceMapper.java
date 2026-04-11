package janggi.persistence.mapper;

import janggi.domain.Janggi;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.position.Position;
import janggi.persistence.entity.PieceEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieceMapper {

    public List<PieceEntity> toPieceEntity(String gameId, Janggi janggi) {
        return janggi.getBoard().entrySet().stream()
                .map(entry -> new PieceEntity(
                        null,
                        gameId,
                        entry.getValue().pieceName(),
                        entry.getValue().pieceCamp().toString(),
                        entry.getKey().row(),
                        entry.getKey().column()
                ))
                .toList();
    }

    public Map<Position, Piece> toBoard(List<PieceEntity> pieceEntities) {
        Map<Position, Piece> pieceMap = new HashMap<>();

        for (PieceEntity pieceEntity : pieceEntities) {
            Position position = Position.of(pieceEntity.row(), pieceEntity.column());
            Piece piece = PieceFactory.create(pieceEntity.pieceName(), pieceEntity.pieceCamp());
            pieceMap.put(position, piece);
        }

        return pieceMap;
    }
}
