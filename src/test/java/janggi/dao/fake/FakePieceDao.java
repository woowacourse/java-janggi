package janggi.dao.fake;

import janggi.dao.PieceDao;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.dto.PieceDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FakePieceDao extends PieceDao {

    private final List<PieceDto> pieces;

    public FakePieceDao() {
        super(null);
        pieces = new ArrayList<>();
    }

    @Override
    public void insertPieces(Map<Position, Piece> pieces) {
        int index = 1;

        for (Entry<Position, Piece> entry : pieces.entrySet()) {
            Position position = entry.getKey();
            this.pieces.add(new PieceDto(index, 1, 1, position.getX(), position.getY()));
        }
    }

    @Override
    public List<PieceDto> findAllPiece() {
        return getPieces();
    }

    @Override
    public void deletePieceByPositionIfExists(Position arrivalPosition) {
        PieceDto pieceDto = findPieceByPosition(arrivalPosition);
        pieces.remove(pieceDto);
    }

    @Override
    public void updatePiece(Position currentPosition, Position arrivalPosition) {
        PieceDto pieceDto = findPieceByPosition(currentPosition);
        pieces.remove(pieceDto);
        PieceDto newPieceDto = new PieceDto(pieceDto.id(), pieceDto.teamId(), pieceDto.pieceTypeId(),
                arrivalPosition.getX(), arrivalPosition.getY());
        pieces.add(newPieceDto);
    }

    @Override
    public void deleteAllPieceIfExists() {
        pieces.clear();
    }

    private PieceDto findPieceByPosition(Position position) {
        return pieces.stream()
                .filter(piece -> piece.x() == position.getX() && piece.y() == position.getY())
                .findFirst()
                .orElse(null);
    }

    public List<PieceDto> getPieces() {
        return pieces;
    }
}
