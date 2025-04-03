package janggi;

import janggi.database.JanggiDao;
import janggi.piece.Color;
import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.position.Position;
import java.util.Map;

public class JanggiGame {
    private static final double DEOM_SCORE = 1.5;

    private final Pieces pieces;
    private final JanggiDao janggiDao;


    public JanggiGame(final Pieces pieces) {
        this.pieces = pieces;
        this.janggiDao = new JanggiDao();
    }

    public JanggiGame() {
        this.pieces = Pieces.init();
        this.janggiDao = new JanggiDao();
        // NOTE: DB에 피스 정보를 저장한다
        janggiDao.deleteAllPiece();
        janggiDao.saveAllPiece(pieces.getPieces());
    }

    public static JanggiGame continueGame() {
        JanggiDao janggiDao = new JanggiDao();
        // NOTE: DB에 피스 정보를 가져온다
        Pieces pieces = janggiDao.findAllPiece();
        return new JanggiGame(pieces);
    }

    public boolean move(final Position start, final Position end) {
        if (!pieces.isMoveable(start, end)) {
            return false;
        }
        pieces.moveForward(start, end);
        janggiDao.deleteAllPiece();
        janggiDao.saveAllPiece(pieces.getPieces());
        return true;
    }

    public Map<Position, Piece> getBoard() {
        return pieces.getPieces();
    }

    public double getScore(final Color color) {
        if (color == Color.RED) {
            return pieces.calculatePieceScore(color) + DEOM_SCORE;
        }
        return pieces.calculatePieceScore(color);
    }
}
