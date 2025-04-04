package entity;

import dao.PieceDao;
import execptions.JanggiArgumentException;
import java.util.Optional;

public class PieceRepository {
    private final PieceDao pieceDao;

    public PieceRepository(PieceDao pieceDao) {
        this.pieceDao = pieceDao;
    }

    public PieceEntity findById(long pieceId) {
        Optional<PieceEntity> pieceEntity = pieceDao.findById(pieceId);

        if (pieceEntity.isEmpty()) {
            throw new JanggiArgumentException("해당 아이디와 일치하는 기물이 존재하지 않습니다.");
        }
        return pieceEntity.get();
    }
}
