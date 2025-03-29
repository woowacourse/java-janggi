package janggi.manager;

import janggi.board.JanggiBoard;
import janggi.board.dao.JanggiBoardDAO;
import janggi.board.dao.TeamDAO;
import janggi.board.dao.TurnDAO;
import janggi.database.DBConnector;
import janggi.piece.Piece;
import janggi.setting.AssignType;
import janggi.setting.CampType;
import janggi.value.JanggiPosition;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class JanggiGame {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiGame(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        final DBConnector dbConnector = new DBConnector();
        dbConnector.createTable();

        final TeamDAO teamDAO = new TeamDAO(dbConnector);
        teamDAO.insertTeam();

        TurnDAO turnDAO = new TurnDAO(dbConnector);
        CampType currentCampType;

        outputView.writeStartMessage();

        JanggiBoardDAO janggiBoardDAO = new JanggiBoardDAO(dbConnector);

        final JanggiBoard janggiBoard = generateJanggiBoard(janggiBoardDAO, turnDAO);
        currentCampType = turnDAO.selectQuery();

        janggiBoardDAO.insertPieces(janggiBoard);
        outputView.writeJanggiBoard(janggiBoard.getChoPieces(), janggiBoard.getHanPieces());
        outputView.writeStart(currentCampType.getName());

        while (true) {
            currentCampType = turnDAO.selectQuery();
            if (isChoTurn(currentCampType, janggiBoard, janggiBoardDAO, turnDAO)) {
                break;
            }
            if (isHanTurn(currentCampType, janggiBoard, janggiBoardDAO, turnDAO)) {
                break;
            }
        }

        outputView.writeTotalScore(janggiBoard.requestChoTotalScore(), janggiBoard.requestHanTotalScore());
        closeGame(turnDAO, janggiBoardDAO);
    }

    private boolean isHanTurn(CampType currentCampType, JanggiBoard janggiBoard, JanggiBoardDAO janggiBoardDAO,
                              TurnDAO turnDAO) {
        if (currentCampType == CampType.HAN) {
            boolean isHanNotCollapse = playHanTurn(janggiBoard, janggiBoardDAO);
            if (!isHanNotCollapse) {
                return true;
            }
            turnDAO.updateQuery(CampType.CHO);
        }
        return false;
    }

    private boolean isChoTurn(CampType currentCampType, JanggiBoard janggiBoard, JanggiBoardDAO janggiBoardDAO,
                              TurnDAO turnDAO) {
        if (currentCampType == CampType.CHO) {
            boolean isChoNotCollapse = playChoTurn(janggiBoard, janggiBoardDAO);
            if (!isChoNotCollapse) {
                return true;
            }
            turnDAO.updateQuery(CampType.HAN);
        }
        return false;
    }

    private JanggiBoard generateJanggiBoard(JanggiBoardDAO janggiBoardDAO, TurnDAO turnDAO) {
        final List<Piece> choPieces = janggiBoardDAO.selectChoRecords();
        final List<Piece> hanPieces = janggiBoardDAO.selectHanRecords();
        if (!choPieces.isEmpty() && !hanPieces.isEmpty()) {
            return new JanggiBoard(choPieces, hanPieces);
        }
        AssignType choAnswer = inputView.readAnswer(CampType.CHO);
        AssignType hanAnswer = inputView.readAnswer(CampType.HAN);
        turnDAO.insertQuery(CampType.CHO);
        return new JanggiBoard(choAnswer, hanAnswer);
    }

    private void closeGame(TurnDAO turnDAO, JanggiBoardDAO janggiBoardDAO) {
        turnDAO.dropTurnTable();
        janggiBoardDAO.dropTables();
    }

    private boolean playHanTurn(final JanggiBoard janggiBoard, final JanggiBoardDAO janggiBoardDAO) {
        outputView.writeTurn(CampType.HAN);

        boolean isValid = false;
        while (!isValid) {
            isValid = handleMoveException(() -> {
                JanggiPosition movedPieceJanggiPosition = inputView.readMovedPiecePosition();
                JanggiPosition destination = inputView.readDestinationPosition();
                janggiBoard.startTurn(movedPieceJanggiPosition, destination, CampType.HAN);
                janggiBoardDAO.deleteRecords(destination, 1);
                janggiBoardDAO.updateRecords(movedPieceJanggiPosition, destination, 2);
            });
        }

        outputView.writeJanggiBoard(janggiBoard.getChoPieces(), janggiBoard.getHanPieces());

        return !janggiBoard.isChoCampCollapse();
    }

    private boolean playChoTurn(final JanggiBoard janggiBoard, final JanggiBoardDAO janggiBoardDAO) {
        outputView.writeTurn(CampType.CHO);

        boolean isValid = false;
        while (!isValid) {
            isValid = handleMoveException(() -> {
                JanggiPosition movedPieceJanggiPosition = inputView.readMovedPiecePosition();
                JanggiPosition destination = inputView.readDestinationPosition();
                janggiBoard.startTurn(movedPieceJanggiPosition, destination, CampType.CHO);
                janggiBoardDAO.deleteRecords(destination, 2);
                janggiBoardDAO.updateRecords(movedPieceJanggiPosition, destination, 1);
            });
        }

        outputView.writeJanggiBoard(janggiBoard.getChoPieces(), janggiBoard.getHanPieces());

        return !janggiBoard.isHanCampCollapse();
    }

    private boolean handleMoveException(Runnable action) {
        try {
            action.run();
            return true;
        } catch (IllegalArgumentException e) {
            outputView.writeErrorMessage(e.getMessage());
            return false;
        }
    }
}
