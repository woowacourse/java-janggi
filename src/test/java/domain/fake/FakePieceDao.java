package domain.fake;

import dao.PieceDao;
import domain.chesspiece.ChessPiece;
import java.util.List;

public class FakePieceDao implements PieceDao {
    @Override
    public void saveAll(final List<ChessPiece> chessPieces) {

    }

    @Override
    public void clear() {

    }

    @Override
    public List<ChessPiece> findAll() {
        return List.of();
    }
}
