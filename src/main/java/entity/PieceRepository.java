package entity;

import dao.PieceDao;

public class PieceRepository {
    private final PieceDao pieceDao;

    public PieceRepository(PieceDao pieceDao) {
        this.pieceDao = pieceDao;
    }

    public PieceEntity findById(long pieceId) {
        return pieceDao.findById(pieceId);
    }
}
