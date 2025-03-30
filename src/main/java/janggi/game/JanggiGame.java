package janggi.game;

import janggi.dao.GameDao;
import janggi.setting.CampType;
import janggi.setting.GameState;
import janggi.setting.PieceAssignType;
import janggi.value.Position;
import janggi.view.GameMenuAnswer;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.TurnMenuAnswer;
import java.util.List;
import java.util.Optional;

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
        while (true) {
            GameMenuAnswer gameMenuAnswer = readGameMenuAnswer();
            if (gameMenuAnswer == GameMenuAnswer.ONE) {
                JanggiBoard janggiBoard = prepareNewGame();
                playGame(janggiBoard);
                gameDao.updateGameInformationToEnd(gameInformation.getGameId());
                break;
            }
            if (gameMenuAnswer == GameMenuAnswer.TWO) {
                List<GameInformation> allGameInformation = gameDao.findAllGameInformation();
                Optional<GameInformation> optionalGameInformation = selectGame(allGameInformation);
                if (optionalGameInformation.isEmpty()) {
                    continue;
                }
                this.gameInformation = optionalGameInformation.get();
                JanggiBoard janggiBoard = prepareExistingGame();
                playGame(janggiBoard);
                gameDao.updateGameInformationToEnd(gameInformation.getGameId());
                break;
            }
            if (gameMenuAnswer == GameMenuAnswer.QUIT) {
                break;
            }
        }
    }

    private JanggiBoard prepareNewGame() {
        String gameTitle = readNewGameTitle();
        outputView.writeStartMessage();
        PieceAssignType choAnswer = readPieceAssignType(CampType.CHO);
        PieceAssignType hanAnswer = readPieceAssignType(CampType.HAN);
        JanggiBoard janggiBoard = new JanggiBoard(choAnswer, hanAnswer);
        int gameId = gameDao.addNewGameInformation(gameTitle, choAnswer, hanAnswer);
        gameInformation = new GameInformation(gameId, gameTitle, choAnswer, hanAnswer, GameState.PLAY);
        return janggiBoard;
    }

    private JanggiBoard prepareExistingGame() {
        JanggiBoard janggiBoard = new JanggiBoard(
                gameInformation.getChoAssignType(),
                gameInformation.getHanAssignType());
        List<MovePieceCommand> commands = gameDao.finaAllMovePieceCommand(gameInformation.getGameId());
        commands.forEach(janggiBoard::movePiece);
        return janggiBoard;
    }

    private void playGame(JanggiBoard janggiBoard) {
        printJanggiBoardState(janggiBoard);
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

    private void endGame() {

    }

    private void movePiece(JanggiBoard janggiBoard, CampType campType) {
        while (true) {
            try {
                outputView.writeTurn(campType);
                MovePieceCommand movePieceCommand = readMoveInformation(campType);
                janggiBoard.movePiece(movePieceCommand);
                gameDao.addMovePieceCommand(gameInformation.getGameId(), movePieceCommand);
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

    private Optional<GameInformation> selectGame(List<GameInformation> gameInformations) {
        while (true) {
            try {
                int command = inputView.selectGame(gameInformations);
                if (gameInformations.size() <= command) {
                    break;
                }
                return Optional.of(gameInformations.get(command));
            } catch (IllegalArgumentException exception) {
                outputView.printExceptionMessage(exception.getMessage());
            }
        }
        return Optional.empty();
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
        while (true) {
            try {
                Position movedPiecePosition = inputView.readMovedPiecePosition();
                Position destination = inputView.readDestinationPosition();
                return new MovePieceCommand(campType, movedPiecePosition, destination);
            } catch (IllegalArgumentException exception) {
                outputView.printExceptionMessage(exception.getMessage());
            }
        }
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
