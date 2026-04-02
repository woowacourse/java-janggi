package domain.janggigame;

import domain.board.Board;
import domain.piece.Side;
import domain.players.Players;
import domain.position.Move;
import domain.position.Position;
import util.Parser;
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
        hanPlayerPlaceBoard();
        choPlayerPlaceBoard();
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
        String inputStartPosition = InputView.inputStartPosition();
        Position startPosition = Parser.parseToPosition(inputStartPosition);
        String inputEndPosition = InputView.inputEndPosition();
        Position endPosition = Parser.parseToPosition(inputEndPosition);
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

    private void hanPlayerPlaceBoard() {
        retry(() -> {
            String input = InputView.inputHanPlacementCode();
            int code = Parser.parseToPlacementCode(input);
            players.initPlacementBySide(Side.HAN, code, board);
            OutputView.printBoard(board.findState());
        });
    }

    private void choPlayerPlaceBoard() {
        retry(() -> {
            String input = InputView.inputChoPlacementCode();
            int code = Parser.parseToPlacementCode(input);
            players.initPlacementBySide(Side.CHO, code, board);
            OutputView.printBoard(board.findState());
        });
    }
}
