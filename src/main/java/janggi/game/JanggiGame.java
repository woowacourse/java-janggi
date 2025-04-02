package janggi.game;

import janggi.dao.GameInformationDao;
import janggi.dao.MovePieceCommandDao;
import janggi.setting.CampType;
import janggi.setting.GameState;
import janggi.setting.PieceAssignType;
import janggi.view.GameInputOutput;
import janggi.view.answer.GameMenuAnswer;
import janggi.view.answer.TurnMenuAnswer;
import java.util.List;
import java.util.Optional;

public class JanggiGame {

    private final GameInputOutput gameInputOutput;
    private final GameInformationDao gameInformationDao;
    private final MovePieceCommandDao movePieceCommandDao;

    public JanggiGame(GameInputOutput gameInputOutput, GameInformationDao gameInformationDao,
            MovePieceCommandDao movePieceCommandDao) {
        this.gameInputOutput = gameInputOutput;
        this.gameInformationDao = gameInformationDao;
        this.movePieceCommandDao = movePieceCommandDao;
    }

    public void start() {
        while (true) {
            GameMenuAnswer gameMenuAnswer = gameInputOutput.readGameMenuAnswer();
            if (gameMenuAnswer == GameMenuAnswer.New_GAME) {
                GameInformation gameInformation = registerNewGameInformation();
                playGame(gameInformation);
            }
            if (gameMenuAnswer == GameMenuAnswer.CONTINUED_GAME) {
                Optional<GameInformation> optionalGameInformation = registerContinuedGameInformation();
                if (optionalGameInformation.isEmpty()) {
                    continue;
                }
                playGame(optionalGameInformation.get());
            }
            if (gameMenuAnswer == GameMenuAnswer.QUIT) {
                return;
            }
        }
    }

    private GameInformation registerNewGameInformation() {
        String gameTitle = gameInputOutput.readNewGameTitle();
        gameInputOutput.printStartMessage();
        PieceAssignType choAnswer = gameInputOutput.readPieceAssignType(CampType.CHO);
        PieceAssignType hanAnswer = gameInputOutput.readPieceAssignType(CampType.HAN);
        int gameId = gameInformationDao.addNew(gameTitle, choAnswer, hanAnswer);
        return new GameInformation(gameId, gameTitle, choAnswer, hanAnswer, GameState.PLAY);
    }

    private Optional<GameInformation> registerContinuedGameInformation() {
        List<GameInformation> allGameInformation = gameInformationDao.findAllInPlaying();
        return gameInputOutput.selectGame(allGameInformation);
    }

    public void playGame(GameInformation gameInformation) {
        List<MovePieceCommand> existingCommands = loadMovePieceCommand(gameInformation.getGameId());
        CampType campTypeInInitialTurn = calculateLastTurn(existingCommands);
        JanggiBoard board = prepareBoard(gameInformation, existingCommands);
        playTurns(gameInformation.getGameId(), board, campTypeInInitialTurn);
        endGame(gameInformation.getGameId());
    }

    private List<MovePieceCommand> loadMovePieceCommand(int gameId) {
        return movePieceCommandDao.finaAllMovePieceCommand(gameId);
    }

    private CampType calculateLastTurn(List<MovePieceCommand> commands) {
        if (commands.isEmpty()) {
            return CampType.HAN;
        }
        return commands.getLast().getCampType();
    }

    private JanggiBoard prepareBoard(GameInformation gameInformation, List<MovePieceCommand> commands) {
        JanggiBoard board = new JanggiBoard(gameInformation.getChoAssignType(), gameInformation.getHanAssignType());
        commands.forEach(board::movePiece);
        return board;
    }

    private void playTurns(int gameId, JanggiBoard janggiBoard, CampType campTypeInLastTurn) {
        gameInputOutput.printJanggiBoardState(janggiBoard);
        CampType campTypeInTurn = campTypeInLastTurn;
        while (true) {
            campTypeInTurn = campTypeInTurn.getEnemyCampType();
            TurnMenuAnswer turnMenuAnswer = gameInputOutput.readTurnMenuAnswer(campTypeInTurn);
            if (turnMenuAnswer == TurnMenuAnswer.MOVE_PIECE) {
                movePiece(gameId, janggiBoard, campTypeInTurn);
                if (janggiBoard.isGameEnd()) {
                    break;
                }
            }
            if (turnMenuAnswer == TurnMenuAnswer.REST_TURN) {
                continue;
            }
            if (turnMenuAnswer == TurnMenuAnswer.GAME_OVER) {
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
                movePieceCommandDao.addMovePieceCommand(gameId, movePieceCommand);
                gameInputOutput.printJanggiBoardState(janggiBoard);
                return;
            } catch (IllegalArgumentException exception) {
                gameInputOutput.printExceptionMessage(exception.getMessage());
            }
        }
    }

    private void endGame(int gameId) {
        gameInformationDao.updateGameStateToEnd(gameId);
    }
}
