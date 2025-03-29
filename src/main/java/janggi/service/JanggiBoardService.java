package janggi.service;

import janggi.domain.piece.Side;
import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Pieces;
import janggi.domain.position.Position;
import janggi.repository.JanggiDao;

import java.util.List;

public class JanggiBoardService {

    private final JanggiDao janggiDao;

    public JanggiBoardService(JanggiDao janggiDao) {
        this.janggiDao = janggiDao;
    }

    public void saveInitialBoard(JanggiBoard janggiBoard) {
        janggiDao.createPieceTable();
        janggiDao.createTurnTable();

        Pieces pieces = janggiBoard.getPieces();
        List<Piece> allPieces = pieces.getPieces();
        for (Piece piece : allPieces) {
            janggiDao.insertPiece(piece);
        }
        janggiDao.insertTurn(janggiBoard.getTurn());
    }

    public boolean hasGameData() {
        return janggiDao.hasGamePiece();
    }

    public JanggiBoard loadGame() {
        List<Piece> pieces = janggiDao.loadPieces();
        Side turn = janggiDao.loadTurn();

        return new JanggiBoard(new Pieces(pieces), turn);
    }

    public void updateGame(Position start, Position destination, Side turn) {
        janggiDao.removeDestinationPiece(destination);
        janggiDao.updateMovingPiece(start, destination);
        janggiDao.updateTurn(turn);
    }

    public void resetGame() {
        janggiDao.removePieces();
        janggiDao.removeTurn();
    }
}
