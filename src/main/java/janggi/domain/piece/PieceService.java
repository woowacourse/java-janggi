package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.team.TeamType;
import janggi.dto.PieceDto;

import java.util.List;
import java.util.Map;

public class PieceService {

    private final PieceDao pieceDao;

    public PieceService(PieceDao pieceDao) {
        this.pieceDao = pieceDao;
    }

    public List<PieceDto> findPiecesByTurnId(Long turId) {
        return pieceDao.findPiecesByTurnId(turId);
    }

    public Map<Position, Piece> getPiecesByTeamType(List<PieceDto> pieceDtos, TeamType teamType) {
        return PieceDto.getPiecesByTeamType(pieceDtos, teamType);
    }

    public void saveAll(List<PieceDto> pieceDtos) {
        pieceDao.saveAll(pieceDtos);
    }
}
