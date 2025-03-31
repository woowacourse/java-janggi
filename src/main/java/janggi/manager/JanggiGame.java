package janggi.manager;

import janggi.board.JanggiBoard;
import janggi.board.dao.JanggiBoardDAOImp;
import janggi.board.dao.JanggiBoardDao;
import janggi.board.dao.TeamDAOImp;
import janggi.board.dao.TeamDao;
import janggi.board.dao.TurnDAO;
import janggi.board.dao.TurnDAOImp;
import janggi.database.DBConnector;
import janggi.database.DBInitializer;
import janggi.database.MySQLDBConnector;
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
        final DBConnector connector = new MySQLDBConnector();
        final DBInitializer dbInitializer = new DBInitializer(connector);
        dbInitializer.createTables();

        final TeamDao teamDAO = new TeamDAOImp(connector);
        teamDAO.insertTeam();

        TurnDAO turnDAO = new TurnDAOImp(connector);
        CampType currentCampType;

        outputView.writeStartMessage();

        JanggiBoardDao janggiBoardDao = new JanggiBoardDAOImp(connector);

        final JanggiBoard janggiBoard = generateJanggiBoard(janggiBoardDao, turnDAO);
        currentCampType = turnDAO.selectQuery();

        janggiBoardDao.insertPieces(janggiBoard);
        outputView.writeJanggiBoard(janggiBoard.getChoPieces(), janggiBoard.getHanPieces());
        outputView.writeStart(currentCampType.getName());

        while (true) {
            currentCampType = turnDAO.selectQuery();
            if (isChoTurn(currentCampType, janggiBoard, janggiBoardDao, turnDAO)) {
                break;
            }
            if (isHanTurn(currentCampType, janggiBoard, janggiBoardDao, turnDAO)) {
                break;
            }
        }

        outputView.writeTotalScore(janggiBoard.requestChoTotalScore(), janggiBoard.requestHanTotalScore());
        closeGame(turnDAO, janggiBoardDao);
    }

    private boolean isHanTurn(CampType currentCampType, JanggiBoard janggiBoard, JanggiBoardDao janggiBoardDao,
                              TurnDAO turnDAO) {
        if (currentCampType == CampType.HAN) {
            boolean isHanNotCollapse = playHanTurn(janggiBoard, janggiBoardDao);
            if (!isHanNotCollapse) {
                return true;
            }
            turnDAO.updateQuery(CampType.CHO);
        }
        return false;
    }

    private boolean isChoTurn(CampType currentCampType, JanggiBoard janggiBoard, JanggiBoardDao janggiBoardDao,
                              TurnDAO turnDAO) {
        if (currentCampType == CampType.CHO) {
            boolean isChoNotCollapse = playChoTurn(janggiBoard, janggiBoardDao);
            if (!isChoNotCollapse) {
                return true;
            }
            turnDAO.updateQuery(CampType.HAN);
        }
        return false;
    }

    private JanggiBoard generateJanggiBoard(JanggiBoardDao janggiBoardDao, TurnDAO turnDAO) {
        final List<Piece> choPieces = janggiBoardDao.selectChoRecords();
        final List<Piece> hanPieces = janggiBoardDao.selectHanRecords();
        if (!choPieces.isEmpty() && !hanPieces.isEmpty()) {
            return new JanggiBoard(choPieces, hanPieces);
        }
        AssignType choAnswer = inputView.readAnswer(CampType.CHO);
        AssignType hanAnswer = inputView.readAnswer(CampType.HAN);
        turnDAO.insertQuery(CampType.CHO);
        return new JanggiBoard(choAnswer, hanAnswer);
    }

    private void closeGame(TurnDAO turnDAO, JanggiBoardDao janggiBoardDao) {
        turnDAO.dropTurnTable();
        janggiBoardDao.dropTables();
    }

    private boolean playHanTurn(final JanggiBoard janggiBoard, final JanggiBoardDao janggiBoardDao) {
        outputView.writeTurn(CampType.HAN);

        boolean isValid = false;
        while (!isValid) {
            isValid = handleMoveException(() -> {
                JanggiPosition movedPieceJanggiPosition = inputView.readMovedPiecePosition();
                JanggiPosition destination = inputView.readDestinationPosition();
                janggiBoard.startTurn(movedPieceJanggiPosition, destination, CampType.HAN);
                janggiBoardDao.deleteRecords(destination, 1);
                janggiBoardDao.updateRecords(movedPieceJanggiPosition, destination, 2);
            });
        }

        outputView.writeJanggiBoard(janggiBoard.getChoPieces(), janggiBoard.getHanPieces());

        return !janggiBoard.isChoCampCollapse();
    }

    private boolean playChoTurn(final JanggiBoard janggiBoard, final JanggiBoardDao janggiBoardDao) {
        outputView.writeTurn(CampType.CHO);

        boolean isValid = false;
        while (!isValid) {
            isValid = handleMoveException(() -> {
                JanggiPosition movedPieceJanggiPosition = inputView.readMovedPiecePosition();
                JanggiPosition destination = inputView.readDestinationPosition();
                janggiBoard.startTurn(movedPieceJanggiPosition, destination, CampType.CHO);
                janggiBoardDao.deleteRecords(destination, 2);
                janggiBoardDao.updateRecords(movedPieceJanggiPosition, destination, 1);
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
