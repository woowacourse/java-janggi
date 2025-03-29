package service;

import dao.PieceDao;
import domain.board.BoardLocation;
import domain.game.JanggiGame;
import dto.BoardDto;
import java.util.Optional;

public class PieceService {
    private final PieceDao pieceDao;

    public PieceService(PieceDao pieceDao) {
        this.pieceDao = pieceDao;
    }

    public void insertInitializePieceIfNotExists(JanggiGame janggiGame) {
        pieceDao.initializePieceIfNotExists(janggiGame.getBoard());
    }

    public Optional<BoardDto> getAlivePieces() {
        return pieceDao.findByAllAlivePieces();
    }

    public void movePiece(BoardLocation current, BoardLocation destination) {
        pieceDao.deleteBoard(destination);
        pieceDao.updateBoard(current, destination);
    }
}
