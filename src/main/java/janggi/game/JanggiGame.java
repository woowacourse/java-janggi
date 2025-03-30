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

    public JanggiGame(GameInputOutput gameInputOutput, GameDao gameDao) {
        this.gameInputOutput = gameInputOutput;
        this.gameDao = gameDao;
    }

    public void start() {
        GameInformation gameInformation = registerGameInformation();
        List<MovePieceCommand> existingCommands = loadMovePieceCommand(gameInformation.getGameId());
        CampType campTypeInInitialTurn = calculateLastTurn(existingCommands);
        JanggiBoard board = prepareBoard(gameInformation, existingCommands);
        playGame(gameInformation.getGameId(), board, campTypeInInitialTurn);
        endGame(gameInformation.getGameId());
    }

    private GameInformation registerGameInformation() {
        while (true) {
            GameMenuAnswer gameMenuAnswer = gameInputOutput.readGameMenuAnswer();
            if (gameMenuAnswer == GameMenuAnswer.ONE) {
                return registerNewGameInformation();
            }
            if (gameMenuAnswer == GameMenuAnswer.TWO) {
                Optional<GameInformation> optionalGameInformation = registerExistingGameInformation();
                if (optionalGameInformation.isPresent()) {
                    return optionalGameInformation.get();
                }
            }
        }
    }

    private GameInformation registerNewGameInformation() {
        String gameTitle = gameInputOutput.readNewGameTitle();
        gameInputOutput.printStartMessage();
        PieceAssignType choAnswer = gameInputOutput.readPieceAssignType(CampType.CHO);
        PieceAssignType hanAnswer = gameInputOutput.readPieceAssignType(CampType.HAN);
        int gameId = gameDao.addNewGameInformation(gameTitle, choAnswer, hanAnswer);
        return new GameInformation(gameId, gameTitle, choAnswer, hanAnswer, GameState.PLAY);
    }

    private Optional<GameInformation> registerExistingGameInformation() {
        List<GameInformation> allGameInformation = gameDao.findAllGameInformation();
        return gameInputOutput.selectGame(allGameInformation);
    }

    private List<MovePieceCommand> loadMovePieceCommand(int gameId) {
        return gameDao.finaAllMovePieceCommand(gameId);
    }

    private JanggiBoard prepareBoard(GameInformation gameInformation, List<MovePieceCommand> commands) {
        JanggiBoard board = new JanggiBoard(gameInformation.getChoAssignType(), gameInformation.getHanAssignType());
        commands.forEach(board::movePiece);
        return board;
    }

    private CampType calculateLastTurn(List<MovePieceCommand> commands) {
        if (commands.isEmpty()) {
            return CampType.HAN;
        }
        return commands.getLast().getCampType();
    }

    private void playGame(int gameId, JanggiBoard janggiBoard, CampType campTypeInLastTurn) {
        gameInputOutput.printJanggiBoardState(janggiBoard);
        CampType campTypeInTurn = campTypeInLastTurn;
        while (true) {
            campTypeInTurn = campTypeInTurn.getEnemyCampType();
            TurnMenuAnswer turnMenuAnswer = gameInputOutput.readTurnMenuAnswer(campTypeInTurn);
            if (turnMenuAnswer == TurnMenuAnswer.ONE) {
                movePiece(gameId, janggiBoard, campTypeInTurn);
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

    private void movePiece(int gameId, JanggiBoard janggiBoard, CampType campType) {
        while (true) {
            try {
                gameInputOutput.printTurn(campType);
                MovePieceCommand movePieceCommand = gameInputOutput.readMoveInformation(campType);
                janggiBoard.movePiece(movePieceCommand);
                gameDao.addMovePieceCommand(gameId, movePieceCommand);
                gameInputOutput.printJanggiBoardState(janggiBoard);
                return;
            } catch (IllegalArgumentException exception) {
                gameInputOutput.printExceptionMessage(exception.getMessage());
            }
        }
    }

    private void endGame(int gameId) {
        gameDao.updateGameInformationToEnd(gameId);
    }
}
