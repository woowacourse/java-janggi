package janggi.service;

import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Pieces;
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
}
