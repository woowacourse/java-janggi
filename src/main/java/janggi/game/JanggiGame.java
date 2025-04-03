package janggi.game;

import janggi.dao.GameInformationDao;
import janggi.dao.MovePieceCommandDao;
import janggi.rule.CampType;
import janggi.rule.PieceAssignType;
import janggi.value.Position;
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
            if (gameMenuAnswer == GameMenuAnswer.NEW_GAME) {
                GameInformation gameInformation = registerNewGameInformation();
                playGame(gameInformation);
            }
            if (gameMenuAnswer == GameMenuAnswer.CONTINUED_GAME) {
                Optional<GameInformation> optionalGameInformation = selectGameInformationInPlaying();
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
        GameInformation gameInformation = gameInformationDao.addNew(gameTitle, choAnswer, hanAnswer);
        return gameInformation;
    }

    private Optional<GameInformation> selectGameInformationInPlaying() {
        List<GameInformation> allGameInformation = gameInformationDao.findAllInPlaying();
        return gameInputOutput.selectGame(allGameInformation);
    }

    public void playGame(GameInformation gameInformation) {
        List<MovePieceCommand> existingCommands = loadMovePieceCommand(gameInformation.gameId());
        CampType campTypeInInitialTurn = calculateLastTurn(existingCommands);
        JanggiBoard board = prepareBoard(gameInformation, existingCommands);
        playTurns(gameInformation, board, campTypeInInitialTurn);
    }

    private List<MovePieceCommand> loadMovePieceCommand(int gameId) {
        return movePieceCommandDao.findAllInGameId(gameId);
    }

    private CampType calculateLastTurn(List<MovePieceCommand> commands) {
        if (commands.isEmpty()) {
            return CampType.HAN;
        }
        return commands.getLast().campType();
    }

    private JanggiBoard prepareBoard(GameInformation gameInformation, List<MovePieceCommand> commands) {
        JanggiBoard board = new JanggiBoard(gameInformation.choAssignType(), gameInformation.hanAssignType());
        commands.forEach(board::movePiece);
        return board;
    }

    private void playTurns(GameInformation gameInformation, JanggiBoard janggiBoard, CampType campTypeInLastTurn) {
        gameInputOutput.printJanggiBoardState(janggiBoard);
        CampType campTypeInTurn = campTypeInLastTurn;
        while (janggiBoard.canContinueGame()) {
            campTypeInTurn = campTypeInTurn.getEnemyCampType();
            TurnMenuAnswer turnMenuAnswer = gameInputOutput.readTurnMenuAnswer(campTypeInTurn);
            if (turnMenuAnswer == TurnMenuAnswer.MOVE_PIECE) {
                movePiece(gameInformation.gameId(), janggiBoard, campTypeInTurn);
            }
            if (turnMenuAnswer == TurnMenuAnswer.REST_TURN) {
                continue;
            }
            if (turnMenuAnswer == TurnMenuAnswer.GAME_STOP) {
                gameInputOutput.printGameStopMessage(gameInformation.gameTitle());
                break;
            }
            if (turnMenuAnswer == TurnMenuAnswer.GAME_END) {
                endGame(gameInformation.gameId());
                gameInputOutput.printGameResult(janggiBoard);
                break;
            }
        }
    }

    private void movePiece(int gameId, JanggiBoard janggiBoard, CampType campType) {
        while (true) {
            try {
                gameInputOutput.printTurn(campType);
                Position targetPiecePosition = gameInputOutput.readTargetPiecePosition();
                Position destination = gameInputOutput.readDestination();
                MovePieceCommand command =
                        movePieceCommandDao.addNew(gameId, campType, targetPiecePosition, destination);
                janggiBoard.movePiece(command);
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
