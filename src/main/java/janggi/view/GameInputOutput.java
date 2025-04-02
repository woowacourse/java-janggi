package janggi.view;

import janggi.game.GameInformation;
import janggi.game.JanggiBoard;
import janggi.rule.CampType;
import janggi.rule.PieceAssignType;
import janggi.value.Position;
import janggi.view.answer.GameMenuAnswer;
import janggi.view.answer.TurnMenuAnswer;
import java.util.List;
import java.util.Optional;

public class GameInputOutput {

    private final InputView inputView;
    private final OutputView outputView;

    public GameInputOutput(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public GameMenuAnswer readGameMenuAnswer() {
        while (true) {
            try {
                return inputView.readGameMenuAnswer();
            } catch (IllegalArgumentException exception) {
                outputView.printExceptionMessage(exception.getMessage());
            }
        }
    }

    public Optional<GameInformation> selectGame(List<GameInformation> gameInformations) {
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

    public String readNewGameTitle() {
        while (true) {
            try {
                return inputView.readNewGameTitle();
            } catch (IllegalArgumentException exception) {
                outputView.printExceptionMessage(exception.getMessage());
            }
        }
    }

    public PieceAssignType readPieceAssignType(CampType campType) {
        while (true) {
            try {
                return inputView.readPieceAssignType(campType);
            } catch (IllegalArgumentException exception) {
                outputView.printExceptionMessage(exception.getMessage());
            }
        }
    }

    public TurnMenuAnswer readTurnMenuAnswer(CampType campType) {
        while (true) {
            try {
                return inputView.readTurnMenuAnswer(campType);
            } catch (IllegalArgumentException exception) {
                outputView.printExceptionMessage(exception.getMessage());
            }
        }
    }

    public Position readTargetPiecePosition() {
        while (true) {
            try {
                return inputView.readMovedPiecePosition();
            } catch (IllegalArgumentException exception) {
                outputView.printExceptionMessage(exception.getMessage());
            }
        }
    }

    public Position readDestination() {
        while (true) {
            try {
                return inputView.readDestinationPosition();
            } catch (IllegalArgumentException exception) {
                outputView.printExceptionMessage(exception.getMessage());
            }
        }
    }

    public void printStartMessage() {
        outputView.writeStartMessage();
    }

    public void printTurn(CampType campType) {
        outputView.writeTurn(campType);
    }

    public void printExceptionMessage(String message) {
        outputView.printExceptionMessage(message);
    }

    public void printJanggiBoardState(JanggiBoard board) {
        outputView.writeScore(board.getScore(CampType.CHO), board.getScore(CampType.HAN));
        outputView.writeJanggiBoard(board.getPieces(CampType.CHO), board.getPieces(CampType.HAN));
    }

    public void printGameResult(JanggiBoard board) {
        outputView.writeGameEndMessage();
        outputView.writeWinning(board.whoWin());
    }

    public void printGameStopMessage(String gameTitle) {
        outputView.writeGameStopMessage(gameTitle);
    }
}
