package janggi.domain.board.strategy;

import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.piece.PieceType;
import java.util.List;

public class DbRestoreArrangementStrategy implements ArrangementStrategy {

    private final List<PieceInfo> pieceInfos;

    private DbRestoreArrangementStrategy(List<PieceInfo> pieceInfos) {
        this.pieceInfos = pieceInfos;
    }

    public static DbRestoreArrangementStrategy from(List<PieceInfo> pieceInfos) {
        return new DbRestoreArrangementStrategy(pieceInfos);
    }

    @Override
    public void place(Piece[][] arrangement, PieceFactory pieceFactory) {
        for (PieceInfo pieceInfo : pieceInfos) {
            int row = pieceInfo.rowIndex();
            int col = pieceInfo.colIndex();
            PieceType type = pieceInfo.type();
            Side side = pieceInfo.side();
            arrangement[row][col] = pieceFactory.createActivePiece(type, side);
        }
    }
}
