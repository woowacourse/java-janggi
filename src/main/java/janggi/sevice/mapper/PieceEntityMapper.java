package janggi.sevice.mapper;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.repository.entity.PieceEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PieceEntityMapper {

    private PieceEntityMapper() {
    }

    public static Map<Location, Piece> toLocationMap(List<PieceEntity> pieceEntities) {
        return pieceEntities.stream()
                .collect(Collectors.toMap(
                        entity -> Location.of(entity.getRowIdx(), entity.getColIdx()),
                        PieceEntityMapper::convertToPiece
                ));
    }

    public static List<PieceEntity> toEntities(Long gameId, List<List<Piece>> pieces) {
        List<PieceEntity> pieceEntities = new ArrayList<>();
        for (int row = 0; row < pieces.size(); row++) {
            for (int col = 0; col < pieces.get(row).size(); col++) {
                Piece piece = pieces.get(row).get(col);
                if (piece.isEmpty()) {
                    continue;
                }
                pieceEntities.add(new PieceEntity(gameId, piece.getType().name(), piece.getSide().name(), row, col));
            }
        }
        return pieceEntities;
    }

    private static Piece convertToPiece(PieceEntity entity) {
        PieceType type = PieceType.valueOf(entity.getType());
        Side side = Side.valueOf(entity.getSide());

        return type.createPiece(side);
    }
}
