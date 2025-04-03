package janggi.dao;

import janggi.dao.utils.JanggiMapper;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.domain.team.TeamType;
import janggi.dto.PieceDto;
import janggi.dto.PieceTypeDto;
import janggi.dto.TeamTypeDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanggiDatabaseManager {

    private final PieceDao pieceDao;
    private final PieceTypeDao pieceTypeDao;
    private final TeamDao teamDao;
    private final JanggiMapper janggiMapper;

    public JanggiDatabaseManager(PieceDao pieceDao, PieceTypeDao pieceTypeDao, TeamDao teamDao,
                                 JanggiMapper janggiMapper) {
        this.pieceDao = pieceDao;
        this.pieceTypeDao = pieceTypeDao;
        this.teamDao = teamDao;
        this.janggiMapper = janggiMapper;
    }

    public Map<Position, Piece> loadPiecesForProgressingGame() {
        List<PieceDto> pieceDtos = pieceDao.findAllPiece();
        Map<Position, Piece> pieces = new HashMap<>();

        if (isExistProgressingGame(pieceDtos)) {
            pieceDtos.forEach(pieceDto -> pieces.put(janggiMapper.toPosition(pieceDto), createStoredPiece(pieceDto)));
        }
        return pieces;
    }

    public List<TeamType> loadOrdersForProgressingGame() {
        List<TeamTypeDto> teamTypeDtos = teamDao.findTeams();

        return teamTypeDtos.stream()
                .map(teamTypeDto -> TeamType.of(teamTypeDto.getName()))
                .toList();
    }

    public void saveInitialGame(TeamType currentTeam, Map<Position, Piece> pieces) {
        teamDao.insertInitialTeam(currentTeam);
        pieceTypeDao.insertInitialPieceType();
        pieceDao.insertPieces(pieces);
    }

    public void movePiece(Position currentPosition, Position arrivalPosition, TeamType currentTeam) {
        pieceDao.deletePieceByPositionIfExists(arrivalPosition);
        pieceDao.updatePiece(currentPosition, arrivalPosition);
        teamDao.updateTeamOrder(currentTeam);
    }

    public void endGame() {
        pieceDao.deleteAllPieceIfExists();
        pieceTypeDao.deleteAllPieceTypeIfExists();
        teamDao.deleteAllTeamIfExists();
    }

    private boolean isExistProgressingGame(List<PieceDto> pieceDtos) {
        return !pieceDtos.isEmpty();
    }

    private Piece createStoredPiece(PieceDto pieceDto) {
        PieceTypeDto pieceTypeDto = pieceTypeDao.findPieceTypeById(pieceDto.pieceTypeId());
        TeamTypeDto teamTypeDto = teamDao.findTeamById(pieceDto.teamId());
        return janggiMapper.toPiece(pieceTypeDto, teamTypeDto);
    }
}
