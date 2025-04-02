package janggi.service;

import janggi.board.JanggiBoard;
import janggi.board.dao.JanggiBoardDAO;
import janggi.board.dao.TeamDAO;
import janggi.board.dao.TurnDAO;
import janggi.board.dao.utils.DatabaseUtils;
import janggi.database.DBConnector;
import janggi.piece.Piece;
import janggi.setting.AssignType;
import janggi.setting.CampType;
import janggi.value.JanggiPosition;

import java.util.List;

public class JanggiGameService {
    private final JanggiBoardDAO janggiBoardDao;
    private final TurnDAO turnDAO;
    private final TeamDAO teamDAO;

    public JanggiGameService(final DatabaseUtils databaseUtils) {
        this.janggiBoardDao = new JanggiBoardDAO(databaseUtils);
        this.turnDAO = new TurnDAO(databaseUtils);
        this.teamDAO = new TeamDAO(databaseUtils);
    }

    public void initializeGame() {
        teamDAO.insertTeam();
        turnDAO.insertQuery(CampType.CHO);
    }

    public boolean hasExistingGame() {
        return !janggiBoardDao.selectChoRecords().isEmpty() && !janggiBoardDao.selectHanRecords().isEmpty();
    }

    public JanggiBoard loadBoard() {
        List<Piece> choPieces = janggiBoardDao.selectChoRecords();
        List<Piece> hanPieces = janggiBoardDao.selectHanRecords();
        return new JanggiBoard(choPieces, hanPieces);
    }

    public JanggiBoard createBoard(AssignType choAnswer, AssignType hanAnswer) {
        return new JanggiBoard(choAnswer, hanAnswer);
    }


    public CampType getCurrentTurn() {
        return turnDAO.selectQuery();
    }

    public void updateTurn(CampType nextTurn) {
        turnDAO.updateQuery(nextTurn);
    }

    public void saveBoardState(JanggiBoard board) {
        janggiBoardDao.insertPieces(board);
    }

    public void deletePieceRecord(JanggiPosition position, int teamId) {
        janggiBoardDao.deleteRecords(position, teamId);
    }

    public void updatePiecePosition(JanggiPosition from, JanggiPosition to, int teamId) {
        janggiBoardDao.updateRecords(from, to, teamId);
    }

    public void clearGameData() {
        turnDAO.dropTurnTable();
        janggiBoardDao.dropTables();
        teamDAO.dropTeamTable();
    }
}
