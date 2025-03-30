package janggi.game;

import janggi.dao.GameDao;
import janggi.setting.CampType;
import janggi.setting.PieceAssignType;
import janggi.value.Position;
import janggi.view.GameMenuAnswer;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.TurnMenuAnswer;

public class JanggiGame {

    private final InputView inputView;
    private final OutputView outputView;
    private final GameDao gameDao;
    private GameInformation gameInformation;

    public JanggiGame(InputView inputView, OutputView outputView, GameDao gameDao) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameDao = gameDao;
    }

    public void start() {
        GameMenuAnswer gameMenuAnswer = readGameMenuAnswer();
        if (gameMenuAnswer == GameMenuAnswer.ONE) {
            JanggiBoard janggiBoard = prepareNewGame();
            playGame(janggiBoard);
        }
        if (gameMenuAnswer == GameMenuAnswer.TWO) {

        }
    }

    private JanggiBoard prepareNewGame() {
        String gameTitle = readNewGameTitle();
        outputView.writeStartMessage();
        PieceAssignType choAnswer = readPieceAssignType(CampType.CHO);
        PieceAssignType hanAnswer = readPieceAssignType(CampType.HAN);
        JanggiBoard janggiBoard = new JanggiBoard(choAnswer, hanAnswer);
        printJanggiBoardState(janggiBoard);
        int gameId = gameDao.addNewGame(gameTitle, choAnswer, hanAnswer);
        gameInformation = new GameInformation(gameId, gameTitle);
        return janggiBoard;
    }

    private void playGame(JanggiBoard janggiBoard) {
        CampType campTypeInturn = CampType.HAN;
        while (true) {
            campTypeInturn = campTypeInturn.getEnemyCampType();
            TurnMenuAnswer turnMenuAnswer = readTurnMenuAnswer(campTypeInturn);
            if (turnMenuAnswer == TurnMenuAnswer.ONE) {
                movePiece(janggiBoard, campTypeInturn);
                if (janggiBoard.isGameEnd()) {
                    break;
                }
            }
            if (turnMenuAnswer == TurnMenuAnswer.TWO) {
                continue;
            }
            if (turnMenuAnswer == TurnMenuAnswer.THREE) {
                break;
            }
        }
        printGameResult(janggiBoard);
    }

    private void movePiece(JanggiBoard janggiBoard, CampType campType) {
        while (true) {
            try {
                outputView.writeTurn(campType);
                MovePieceCommand movePieceCommand = readMoveInformation(campType);
                janggiBoard.movePiece(movePieceCommand);
                gameDao.addMovePieceCommand(gameInformation.gameId(), movePieceCommand);
                printJanggiBoardState(janggiBoard);
                return;
            } catch (IllegalArgumentException exception) {
                outputView.printExceptionMessage(exception.getMessage());
            }
        }
    }

    private GameMenuAnswer readGameMenuAnswer() {
        while (true) {
            try {
                return inputView.readGameMenuAnswer();
            } catch (IllegalArgumentException exception) {
                outputView.printExceptionMessage(exception.getMessage());
            }
        }
    }

    private String readNewGameTitle() {
        while (true) {
            try {
                return inputView.readNewGameTitle();
            } catch (IllegalArgumentException exception) {
                outputView.printExceptionMessage(exception.getMessage());
            }
        }
    }

    private PieceAssignType readPieceAssignType(CampType campType) {
        while (true) {
            try {
                return inputView.readPieceAssignType(campType);
            } catch (IllegalArgumentException exception) {
                outputView.printExceptionMessage(exception.getMessage());
            }
        }
    }

    private TurnMenuAnswer readTurnMenuAnswer(CampType campType) {
        while (true) {
            try {
                return inputView.readTurnMenuAnswer(campType);
            } catch (IllegalArgumentException exception) {
                outputView.printExceptionMessage(exception.getMessage());
            }
        }
    }

    private MovePieceCommand readMoveInformation(CampType campType) {
        Position movedPiecePosition = inputView.readMovedPiecePosition();
        Position destination = inputView.readDestinationPosition();
        return new MovePieceCommand(campType, movedPiecePosition, destination);
    }

    private void printJanggiBoardState(JanggiBoard board) {
        outputView.writeScore(board.getScore(CampType.CHO), board.getScore(CampType.HAN));
        outputView.writeJanggiBoard(board.getPieces(CampType.CHO), board.getPieces(CampType.HAN));
    }

    private void printGameResult(JanggiBoard board) {
        outputView.writeGameEndMessage();
        outputView.writeWinning(board.whoWin());
    }
}
