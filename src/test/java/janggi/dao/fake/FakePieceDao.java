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
    public List<PieceDto> findPieces() {
        return getPieces();
    }

    @Override
    public void deleteAllPieceIfExists() {
        pieces.clear();
    }

    public List<PieceDto> getPieces() {
        return pieces;
    }
}
