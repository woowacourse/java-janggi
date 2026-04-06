package view;

import board.SangSetupType;
import core.GameStatus;
import db.model.GameEntity;
import java.util.List;
import participant.Score;
import pieces.Side;
import position.Position;
import util.Retry;

public class JanggiView {

    private final InputView in = new InputView();
    private final OutputView out = new OutputView();

    public SangSetupType askSangSetupUntilSuccess(Side side) {
        return Retry.untilSuccess(() -> {
            out.askSangSetup(side);
            return in.readSangSetup();
        });
    }

    public void printBoard(String board) {
        out.printBoard(board);
    }

    public void printTurnSide(Side side) {
        out.printTurnSide(side);
    }

    public boolean askEndByScore(Side turnSide) {
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

    public void printGameIsOver(GameStatus status) {
        out.printGameIsOver(status);
    }

    public void printScore(Side side, Score score) {
        out.printScore(side, score);
    }

    public Long askGameId(List<GameEntity> savedGames) {
        return Retry.untilSuccess(() -> {
            out.printSavedGames(savedGames);
            out.askGameId();
            Long gameId = in.readGameId();
            if (gameId == 0) {
                return 0L;
            }
            boolean nonMatch = savedGames.stream()
                .noneMatch(game -> game.id().equals(gameId));
            if (nonMatch) {
                throw new IllegalArgumentException("게임 ID를 잘못 입력하셨습니다.");
            }
            return gameId;
        });
    }
}
