package janggigame;

import domain.board.Board;
import domain.board.Placement;
import domain.piece.Side;
import domain.position.Position;
import dto.BoardResponseDto;
import dto.JanggiGameResultResponseDto;
import util.Parser;
import view.InputView;
import view.OutputView;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanggiGame {
    private static final int JANGGUN_COUNT = 5;
    private final Map<Side, Integer> jangGunCount = new HashMap<>();

    public void run() {
        selectSide();
        Board board = initBoard();
        gameStart(board);
        ScoreBoard scoreBoard = calculateScore(board);
        showResult(board, scoreBoard);
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

    private Board initBoard() {
        Board board = new Board();
        initPlacement(Side.HAN, board);
        initPlacement(Side.CHO, board);

        return board;
    }

    private void initPlacement(Side side, Board board) {
        while (true) {
            try {
                String input = inputPlacementCode(side);
                int code = Parser.parseToPlacementCode(input);
                board.placePieces(side, Placement.from(code));
                printBoard(board);
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
        Side currentTurnSide = Side.CHO;
        printBoard(board);
        while (!isGameOver(board, currentTurnSide)) {
            try {
                OutputView.printSide(currentTurnSide);
                board.move(selectFromPosition(), selectToPosition(), currentTurnSide);
                printBoard(board);

                Side nextTurnSide = changeSide(currentTurnSide);
                updateJangGunCount(board, nextTurnSide);
                currentTurnSide = nextTurnSide;
            } catch (Exception e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private boolean isGameOver(Board board, Side currentTurnSide) {
        return isBigJang() || board.isEmptyGeneral(currentTurnSide);
    }

    private boolean isBigJang() {
        return jangGunCount.values().stream()
                .anyMatch(count -> count == JANGGUN_COUNT);
    }

    private void updateJangGunCount(Board board, Side currentTurnSide) {
        if (board.isJangGun(currentTurnSide)) {
            OutputView.printIsJangGun();
            jangGunCount.put(currentTurnSide, jangGunCount.get(currentTurnSide) + 1);
            return;
        }
        jangGunCount.put(currentTurnSide, 0);
    }

    private Position selectFromPosition() {
        String input = InputView.inputFromPosition();
        return Parser.parseToPosition(input);
    }

    private Position selectToPosition() {
        String input = InputView.inputToPosition();
        return Parser.parseToPosition(input);
    }

    private void printBoard(Board board) {
        BoardResponseDto nowBoardState = BoardResponseDto.from(board);
        OutputView.printBoard(nowBoardState);
    }

    private Side changeSide(Side currentTurnSide) {
        if (currentTurnSide == Side.HAN) return Side.CHO;
        return Side.HAN;
    }

    private ScoreBoard calculateScore(Board board) {
        return board.calculateScore();
    }

    private void showResult(Board board, ScoreBoard scoreBoard) {
        if (board.isEmptyGeneral(Side.CHO)) {
            OutputView.printWinSide(Side.HAN);
            return;
        }
        if (board.isEmptyGeneral(Side.HAN)) {
            OutputView.printWinSide(Side.CHO);
            return;
        }
        OutputView.printScoreBothSide(JanggiGameResultResponseDto.from(scoreBoard));
    }
}
