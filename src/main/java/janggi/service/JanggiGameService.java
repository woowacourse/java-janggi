package janggi.service;

import janggi.board.JanggiBoard;
import janggi.board.dao.JanggiBoardDAO;
import janggi.board.dao.TeamDAO;
import janggi.board.dao.TurnDAO;
import janggi.piece.Piece;
import janggi.setting.AssignType;
import janggi.setting.CampType;
import janggi.value.JanggiPosition;

import java.util.List;

public class JanggiGameService {
    private final JanggiBoardDAO janggiBoardDAO;
    private final TurnDAO turnDAO;
    private final TeamDAO teamDAO;

    public JanggiGameService(final JanggiBoardDAO janggiBoardDAO, final TurnDAO turnDAO, final TeamDAO teamDAO) {
        this.janggiBoardDAO = janggiBoardDAO;
        this.turnDAO = turnDAO;
        this.teamDAO = teamDAO;
    }

    public void initializeGame() {
        teamDAO.insertTeam();
        turnDAO.insertQuery(CampType.CHO);
    }

    public boolean hasExistingGame() {
        return !janggiBoardDAO.selectChoRecords().isEmpty() && !janggiBoardDAO.selectHanRecords().isEmpty();
    }

    public JanggiBoard loadBoard() {
        List<Piece> choPieces = janggiBoardDAO.selectChoRecords();
        List<Piece> hanPieces = janggiBoardDAO.selectHanRecords();
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
        janggiBoardDAO.insertPieces(board);
    }

    public void deletePieceRecord(JanggiPosition position, int teamId) {
        janggiBoardDAO.deleteRecords(position, teamId);
    }

    public void updatePiecePosition(JanggiPosition from, JanggiPosition to, int teamId) {
        janggiBoardDAO.updateRecords(from, to, teamId);
    }

    public void clearGameData() {
        turnDAO.dropTurnTable();
        janggiBoardDAO.dropTables();
        teamDAO.dropTeamTable();
    }
}
