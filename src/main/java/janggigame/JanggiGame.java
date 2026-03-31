package janggigame;

import domain.board.Board;
import domain.board.Placement;
import domain.piece.Side;
import domain.position.Position;
import dto.BoardResponseDto;
import util.Parser;
import view.InputView;
import view.OutputView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JanggiGame {
    private final Board board;

    public JanggiGame(Board board) {
        this.board = board;
    }

    public void run() {
        selectSide();
        initBoard();
        gameStart(board);
    }

    private void selectSide() {
        while (true) {
            try {
                String input = InputView.inputSideChoice();
                int sideCode = Parser.parseToSideCode(input);
                Side side = generateSide(sideCode);
                OutputView.printSideChoiceResult(side);
                return;
            } catch (Exception e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Side generateSide(int sideCode) {
        List<Side> sides = Arrays.asList(Side.values());
        Collections.shuffle(sides);

        return sides.get(sideCode - 1);
    }

    private void initBoard() {
        initPlacement(Side.CHO);
        initPlacement(Side.HAN);
    }

    private void initPlacement(Side side) {
        while (true) {
            try {
                String input = inputPlacementCode(side);
                int code = Parser.parseToPlacementCode(input);
                board.placePieces(side, Placement.from(code));
                printBoard();
                return;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private String inputPlacementCode(Side side) {
        if (side == Side.HAN) return InputView.inputHanPlacementCode();
        return InputView.inputChoPlacementCode();
    }

    private void gameStart(Board board) {
        Side attackerSide = Side.HAN;
        while (true) {
            // TODO: 궁성을 구현하지 않아 다음 사이클에서 종료조건을 구현할 예정...
            try {
                printBoard();
                OutputView.printSide(attackerSide);
                board.move(selectFromPosition(), selectToPosition(), attackerSide);
                attackerSide = changeSide(attackerSide);
            } catch (Exception e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void printBoard() {
        BoardResponseDto nowBoardState = BoardResponseDto.from(board.findState());
        OutputView.printBoard(nowBoardState);
    }

    private Position selectFromPosition() {
        String input = InputView.inputFromPosition();
        return Parser.parseToPosition(input);
    }

    private Position selectToPosition() {
        String input = InputView.inputToPosition();
        return Parser.parseToPosition(input);
    }

    private Side changeSide(Side attackerSide) {
        if (attackerSide == Side.HAN) return Side.CHO;
        return Side.HAN;
    }
}
