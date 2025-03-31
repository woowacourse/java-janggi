package janggi.manager;

import janggi.dao.JanggiDao;
import janggi.dto.PieceDto;
import janggi.dto.PieceTypeDto;
import janggi.dto.TeamTypeDto;
import janggi.piece.Piece;
import janggi.position.Position;
import janggi.team.TeamType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanggiDatabaseManager {

    private final JanggiDao janggiDao;
    private final JanggiMapper janggiMapper;

    public JanggiDatabaseManager(JanggiDao janggiDao, JanggiMapper janggiMapper) {
        this.janggiDao = janggiDao;
        this.janggiMapper = janggiMapper;
    }

    public Map<Position, Piece> loadPiecesForProgressingGame() {
        List<PieceDto> pieceDtos = janggiDao.findPieces();
        Map<Position, Piece> pieces = new HashMap<>();

        if (isExistProgressingGame(pieceDtos)) {
            pieceDtos.forEach(pieceDto -> pieces.put(janggiMapper.toPosition(pieceDto), createStoredPiece(pieceDto)));
        }
        return pieces;
    }

    public List<TeamType> loadOrdersForProgressingGame() {
        List<TeamTypeDto> teamTypeDtos = janggiDao.findTeams();

        return teamTypeDtos.stream()
                .map(teamTypeDto -> TeamType.of(teamTypeDto.getName()))
                .toList();
    }

    public void saveGame(TeamType currentTeam, Map<Position, Piece> pieces) {
        janggiDao.deleteAllPieceIfExists();
        janggiDao.deleteAllPieceTypeIfExists();
        janggiDao.deleteAllTeamIfExists();

        janggiDao.insertInitialTeam(currentTeam);
        janggiDao.insertInitialPieceType();
        janggiDao.insertPieces(pieces);
    }

    public void endGame() {
        janggiDao.deleteAllPieceIfExists();
        janggiDao.deleteAllPieceTypeIfExists();
        janggiDao.deleteAllTeamIfExists();
    }

    private boolean isExistProgressingGame(List<PieceDto> pieceDtos) {
        return !pieceDtos.isEmpty();
    }

    private Piece createStoredPiece(PieceDto pieceDto) {
        PieceTypeDto pieceTypeDto = janggiDao.findPieceTypeById(pieceDto.pieceTypeId());
        TeamTypeDto teamTypeDto = janggiDao.findTeamById(pieceDto.teamId());
        return janggiMapper.toPiece(pieceTypeDto, teamTypeDto);
    }
}
