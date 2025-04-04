package dao;

import domain.chesspiece.ChessPiece;
import java.util.List;

public interface PieceDao {

    void saveAll(final List<ChessPiece> chessPieces);
    void clear();
    List<ChessPiece> findAll();
}
