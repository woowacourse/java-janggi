package janggi.manager;

import janggi.board.JanggiBoard;
import janggi.board.JanggiBoardDAO;
import janggi.board.TeamDAO;
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
        DBConnector dbConnector = new DBConnector();
        dbConnector.createTable();
        TeamDAO teamDAO = new TeamDAO(dbConnector);
        teamDAO.insertTeam();

        outputView.writeStartMessage();

        JanggiBoardDAO janggiBoardDAO = new JanggiBoardDAO(dbConnector);
        final List<Piece> choPieces = janggiBoardDAO.selectChoRecords();
        final List<Piece> hanPieces = janggiBoardDAO.selectHanRecords();

        final JanggiBoard janggiBoard;
        if (!choPieces.isEmpty() && !hanPieces.isEmpty()) {
            janggiBoard = new JanggiBoard(choPieces, hanPieces);
        } else {
            AssignType choAnswer = inputView.readAnswer(CampType.CHO);
            AssignType hanAnswer = inputView.readAnswer(CampType.HAN);
            janggiBoard = new JanggiBoard(choAnswer, hanAnswer);
        }

        janggiBoardDAO.insertPieces(janggiBoard);

        outputView.writeJanggiBoard(janggiBoard.getChoPieces(), janggiBoard.getHanPieces());
        outputView.writeChoStart();

        while (true) {
            boolean isChoNotCollapse = playChoTurn(janggiBoard, janggiBoardDAO);
            if (!isChoNotCollapse) {
                break;
            }
            boolean isHanNotCollapse = playHanTurn(janggiBoard, janggiBoardDAO);
            if (!isHanNotCollapse) {
                break;
            }
        }

        outputView.writeTotalScore(janggiBoard.requestChoTotalScore(), janggiBoard.requestHanTotalScore());
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
