package service;

import dao.PieceDao;
import domain.board.Board;
import domain.board.BoardLocation;
import domain.game.JanggiGame;
import java.util.Optional;

public class PieceService {
    private final PieceDao pieceDao;

    public PieceService(PieceDao pieceDao) {
        this.pieceDao = pieceDao;
    }

    public void initializePieceIfNotExists (JanggiGame janggiGame){
        pieceDao.initializePieceIfNotExists(janggiGame.getBoard());
    }

    public Optional<Board> findInitializeBoard(){
        return pieceDao.findByAllAlivePieces();
    }

    public void pieceMove(BoardLocation current, BoardLocation destination) {
        pieceDao.deleteBoard(destination);
        pieceDao.updateBoard(current, destination);
    }
}
