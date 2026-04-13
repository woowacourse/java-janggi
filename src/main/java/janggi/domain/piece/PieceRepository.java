package janggi.domain.piece;

import janggi.domain.Position;
import janggi.dto.PieceDto;

import java.util.List;
import java.util.Map;

public class PieceRepository {

    private final PieceDao pieceDao;

    public PieceRepository(PieceDao pieceDao) {
        this.pieceDao = pieceDao;
    }

    public Map<Position, Piece> findAllPiecesByTurnId(Long turId) {
        List<PieceDto> pieceDtos = pieceDao.findPiecesByTurnId(turId);
        return PieceDto.getAllPieces(pieceDtos);
    }

    public void saveAll(List<PieceDto> pieceDtos) {
        pieceDao.saveAll(pieceDtos);
    }
}
