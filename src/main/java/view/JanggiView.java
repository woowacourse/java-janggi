package view;

import board.SangSetupType;
import core.GameStatus;
import core.GameSummary;
import java.util.List;
import participant.Score;
import pieces.Side;
import position.Position;
import util.Retry;

public class JanggiView {

    private final InputView in = new InputView();
    private final OutputView out = new OutputView();

    public SangSetupType askSangSetupUntilSuccess(final Side side) {
        return Retry.untilSuccess(() -> {
            out.askSangSetup(side);
            return in.readSangSetup();
        });
    }

    public void printBoard(final String board) {
        out.printBoard(board);
    }

    public void printTurnSide(final Side side) {
        out.printTurnSide(side);
    }

    public boolean askEndByScore(final Side turnSide) {
        return Retry.untilSuccess(() -> {
            out.askEndByScore();
            if (!in.readYesOrNo()) {
                return false;
            }
            out.askConfirmEndByScore(turnSide.other());
            return in.readYesOrNo();
        });
    }

    public Position askDeparture() {
        out.askDeparture();
        return readPositionUntilSuccess();
    }

    public Position askDestination() {
        out.askDestination();
        return readPositionUntilSuccess();
    }

    private Position readPositionUntilSuccess() {
        return Retry.untilSuccess(in::readPosition);
    }

    public void printGameResult(final GameStatus status) {
        out.printGameResult(status);
    }

    public void printScore(final Side side, final Score score) {
        out.printScore(side, score);
    }

    public InputGameId askGameId(final List<GameSummary> gameSummaries) {
        return Retry.untilSuccess(() -> {
            out.printSavedGames(gameSummaries);
            out.askGameId();
            final InputGameId gameId = in.readGameId();
            if (!gameId.isNewGame()) {
                gameId.validateWith(gameSummaries);
            }
            return gameId;
        });
    }

    public ServiceMenu askServiceMenu() {
        return Retry.untilSuccess(() -> {
            out.askServiceMenu();
            return in.askServiceMenu();
        });
    }
}
