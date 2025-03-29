package service;

import dao.PieceDao;
import domain.game.JanggiGame;
import dto.BoardDto;
import java.util.Optional;

public class PieceService {
    private final PieceDao pieceDao;

    public PieceService(PieceDao pieceDao) {
        this.pieceDao = pieceDao;
    }

    public void insertInitializePieceIfNotExists(JanggiGame janggiGame){
        pieceDao.initializePieceIfNotExists(janggiGame.getBoard());
    }

    public Optional<BoardDto> getAlivePieces(){
        return pieceDao.findByAllAlivePieces();
    }

    public void updateBoardPieceLocation(JanggiGame janggiGame){
        pieceDao.updateBoard(janggiGame);
    }
}
