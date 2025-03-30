package janggi.game;

import janggi.dao.GameDao;
import janggi.setting.CampType;
import janggi.setting.GameState;
import janggi.setting.PieceAssignType;
import janggi.view.GameInputOutput;
import janggi.view.GameMenuAnswer;
import janggi.view.TurnMenuAnswer;
import java.util.List;
import java.util.Optional;

public class JanggiGame {

    private final GameInputOutput gameInputOutput;
    private final GameDao gameDao;
    private GameInformation gameInformation;

    public JanggiGame(GameInputOutput gameInputOutput, GameDao gameDao) {
        this.gameInputOutput = gameInputOutput;
        this.gameDao = gameDao;
    }

    public void start() {
        while (true) {
            GameMenuAnswer gameMenuAnswer = gameInputOutput.readGameMenuAnswer();
            if (gameMenuAnswer == GameMenuAnswer.ONE) {
                JanggiBoard janggiBoard = prepareNewGame();
                playGame(janggiBoard);
                endGame();
                break;
            }
            if (gameMenuAnswer == GameMenuAnswer.TWO) {
                List<GameInformation> allGameInformation = gameDao.findAllGameInformation();
                Optional<GameInformation> optionalGameInformation = gameInputOutput.selectGame(allGameInformation);
                if (optionalGameInformation.isEmpty()) {
                    continue;
                }
                this.gameInformation = optionalGameInformation.get();
                JanggiBoard janggiBoard = prepareExistingGame();
                playGame(janggiBoard);
                endGame();
                break;
            }
            if (gameMenuAnswer == GameMenuAnswer.QUIT) {
                break;
            }
        }
    }

    private JanggiBoard prepareNewGame() {
        String gameTitle = gameInputOutput.readNewGameTitle();
        gameInputOutput.printStartMessage();
        PieceAssignType choAnswer = gameInputOutput.readPieceAssignType(CampType.CHO);
        PieceAssignType hanAnswer = gameInputOutput.readPieceAssignType(CampType.HAN);
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
        gameInputOutput.printJanggiBoardState(janggiBoard);
        CampType campTypeInturn = CampType.HAN;
        while (true) {
            campTypeInturn = campTypeInturn.getEnemyCampType();
            TurnMenuAnswer turnMenuAnswer = gameInputOutput.readTurnMenuAnswer(campTypeInturn);
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
        gameInputOutput.printGameResult(janggiBoard);
    }

    private void movePiece(JanggiBoard janggiBoard, CampType campType) {
        while (true) {
            try {
                gameInputOutput.printTurn(campType);
                MovePieceCommand movePieceCommand = gameInputOutput.readMoveInformation(campType);
                janggiBoard.movePiece(movePieceCommand);
                gameDao.addMovePieceCommand(gameInformation.getGameId(), movePieceCommand);
                gameInputOutput.printJanggiBoardState(janggiBoard);
                return;
            } catch (IllegalArgumentException exception) {
                gameInputOutput.printExceptionMessage(exception.getMessage());
            }
        }
    }

    private void endGame() {
        gameDao.updateGameInformationToEnd(gameInformation.getGameId());
    }
}
