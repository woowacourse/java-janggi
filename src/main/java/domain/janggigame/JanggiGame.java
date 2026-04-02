package domain.janggigame;

import domain.board.Board;
import domain.piece.Side;
import domain.players.Players;
import domain.position.Move;
import domain.position.Position;
import view.InputView;
import view.OutputView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static util.Retry.retry;

public class JanggiGame {
    private final Players players;
    private final Board board;

    public JanggiGame(Players players, Board board) {
        this.players = players;
        this.board = board;
    }

    public void run() {
        selectSide();
        placeBoardBySide();
        playGame();
    }

    private void playGame() {
        retry(() -> {
            while (!board.isFinished()) {
                Move move = inputAndParseToMove();
                players.playTurn(board, move);
                OutputView.printBoard(board.findState());
            }
        });
    }

    private Move inputAndParseToMove() {
        Position startPosition = InputView.inputStartPosition();
        Position endPosition = InputView.inputEndPosition();
        return new Move(startPosition, endPosition);
    }

    private void selectSide() {
        retry(() -> {
            int sideCode = InputView.inputSideChoice();
            Side side = generateSide(sideCode);
            OutputView.printSideChoiceResult(side);
        });
    }

    private Side generateSide(int sideCode) {
        List<Side> sides = Arrays.asList(Side.values());
        Collections.shuffle(sides);
        return sides.get(sideCode - 1);
    }

    private void placeBoardBySide() {
        initPlacementBySide(Side.HAN);
        initPlacementBySide(Side.CHO);
    }

    private void initPlacementBySide(Side side) {
        retry(() -> {
            int placementCode = InputView.inputPlacementCodeBy(side);
            players.initPlacementBySide(side, placementCode, board);
            OutputView.printBoard(board.findState());
        });
    }
}
