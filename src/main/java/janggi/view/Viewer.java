package janggi.view;

import janggi.common.ErrorMessage;
import janggi.domain.Board;
import janggi.domain.PiecePosition;
import janggi.domain.piece.Side;
import janggi.dto.PositionDto;
import java.util.Scanner;
import java.util.StringJoiner;

public class Viewer {

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String BLANK = "";
    private static final Scanner scanner = new Scanner(System.in);
    private static final String ERROR_SIGN = "[ERROR] ";

    public void printErrorMessage(Exception e) {
        System.out.println(ERROR_SIGN + e.getMessage());
    }

    public void printBoard(Board board) {
        StringJoiner enterJoiner = new StringJoiner(LINE_SEPARATOR).add(formatFirstRowOfBoard());
        for (int row = 1; row <= 10; row++) {
            StringJoiner lineJoiner = new StringJoiner(BLANK).add(String.valueOf(row));
            for (int column = 1; column <= 9; column++) {
                lineJoiner.add(board.getPieceName(row, column));
            }
            enterJoiner.add(lineJoiner.toString());
        }

        System.out.println(enterJoiner);
    }

    private String formatFirstRowOfBoard() {
        StringJoiner joiner = new StringJoiner(BLANK).add(" ");

        for (int i = 1; i <= 9; i++) {
            joiner.add(String.valueOf(i));
        }

        return joiner.toString();
    }

    public void printTurnInfo(Side side) {
        String sideName = "초나라";
        if (side == Side.HAN) {
            sideName = "한나라";
        }

        System.out.println("[안내] " + sideName + "의 차례입니다.");
    }

    public PositionDto readPieceSelection() {
        System.out.println("[안내] 기물의 좌표를 '세로,가로' 순으로 입력해주세요. (예: 3,5)");
        String input = scanner.nextLine();
        validatePosition(input);
        return parsePosition(input);
    }

    private void validatePosition(String input) {
        if (input == null || input.isBlank() || input.split(",").length != 2) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_POSITION_INPUT.getMessage());
        }
    }

    private PositionDto parsePosition(String input) {
        String[] values = input.split(",");
        try {
            return new PositionDto(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_NUMBER_INPUT.getMessage());
        }
    }

    public PositionDto readMove(PiecePosition piecePosition) {
        String pieceName = piecePosition.toName();
        System.out.println(String.format("[안내] %s이 움직일 좌표를 '세로,가로' 순으로 입력해주세요. (예: 3,5)", pieceName));
        String input = scanner.nextLine();
        validatePosition(input);
        return parsePosition(input);
    }
}
