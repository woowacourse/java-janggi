package view;

import domain.Position;
import domain.ScoreStatus;
import domain.Side;
import domain.Turn;
import domain.piece.Piece;

import java.util.List;
import java.util.Map;

import static constant.BoardConstant.*;
import static constant.ErrorMessage.ERROR_PREFIX;

public class OutputView {

    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "\u001B[0m";

    public void printBoardStatus(Long id, Map<Position, Piece> board, ScoreStatus scoreStatus) {
        printBoardHeader(id, scoreStatus);
        for (int y = MIN_Y; y <= MAX_Y; y++) {
            printRowNumber(y);
            for (int x = MIN_X; x <= MAX_X; x++) {
                printPiece(board.get(Position.of(x, y)));
            }
            System.out.println(RESET);
        }
    }

    public void printAvailablePath(Long id, List<Position> availablePositions, Map<Position, Piece> board, ScoreStatus scoreStatus) {
        printBoardHeader(id, scoreStatus);
        for (int y = MIN_Y; y <= MAX_Y; y++) {
            printRowNumber(y);
            for (int x = MIN_X; x <= MAX_X; x++) {
                Position position = Position.of(x, y);
                printCellWithPath(position, board.get(position), availablePositions);
            }
            System.out.println(RESET);
        }
    }

    public void printCurrentTurn(Turn turn) {
        System.out.println();
        System.out.println(getSideName(turn.current()) + "의 차례입니다.");
    }

    public void printWinner(Side winner) {
        System.out.println(getSideName(winner) + "이(가) 승리했습니다.");
    }

    public void printResultByScore(ScoreStatus scoreStatus) {
        printWinner(scoreStatus.winner());
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(ERROR_PREFIX + errorMessage);
    }

    private void printBoardHeader(Long id, ScoreStatus scoreStatus) {
        System.out.println();
        System.out.println(id + "번 게임");
        System.out.println(BLUE + " 초: " + scoreStatus.choScoreText() + "            " + RED + "한: " + scoreStatus.hanScoreText() + RESET);
        System.out.println("    1  2  3  4  5  6  7  8  9");
    }

    private void printRowNumber(int y) {
        if (y < MAX_Y) {
            System.out.print(" " + y + "  ");
            return;
        }
        System.out.print(y + "  ");
    }

    private void printCellWithPath(Position position, Piece piece, List<Position> availablePositions) {
        if (!availablePositions.contains(position)) {
            printPiece(piece);
            return;
        }
        if (!piece.isEmpty()) {
            printPiece(piece, YELLOW);
            return;
        }
        System.out.print(YELLOW + "。 " + RESET);
    }

    private void printPiece(Piece piece) {
        printPiece(piece, getColor(piece.getSide()));
    }

    private void printPiece(Piece piece, String color) {
        System.out.print(color + piece.getName() + " ");
    }

    private String getColor(Side side) {
        if (side == Side.HAN) {
            return RED;
        }
        if (side == Side.CHO) {
            return BLUE;
        }
        return RESET;
    }

    private String getSideName(Side side) {
        if (side == Side.CHO) {
            return "초";
        }
        return "한";
    }
}
