package janggi.view;

import janggi.common.ErrorMessage;
import janggi.domain.Side;
import janggi.domain.movement.Position;
import janggi.domain.piece.Piece;
import janggi.dto.PositionDto;
import java.util.Map;
import java.util.Scanner;
import java.util.StringJoiner;

public class Viewer {

    private static final Scanner scanner = new Scanner(System.in);

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String BLANK = " ";
    private static final String ERROR_HEADER = "[ERROR] ";
    private static final String INFO_HEADER = "[안내] ";

    private static final int POSITION_INPUT_SIZE = 2;

    public int readGameId() {
        System.out.println(Formatter.formatMessageWithHeader(INFO_HEADER, "게임방 아이디를 숫자로 입력해 주세요. (예: 8)"));
        String input = scanner.nextLine();

        validateGameId(input);
        return Integer.parseInt(input);
    }

    private void validateGameId(String input) {
        int id;
        try {
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_NUMBER_INPUT.getMessage());
        }
        if (id < 1 || id > 99) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ID_RANGE.getMessage());
        }
    }

    public void printErrorMessage(Exception e) {
        System.out.println(Formatter.formatMessageWithHeader(ERROR_HEADER, e.getMessage()));
    }

    public void printBoard(Map<Position, Piece> pieces) {
        StringJoiner enterJoiner = new StringJoiner(LINE_SEPARATOR).add(formatFirstRowOfBoard());
        for (int row = 1; row <= 10; row++) {
            StringJoiner lineJoiner = new StringJoiner(BLANK);
            for (int column = 1; column <= 9; column++) {
                Position position = Position.of(row, column);
                lineJoiner.add(getPieceName(pieces, position));
            }

            lineJoiner.add(Formatter.formatFullWidthNumber(row));
            enterJoiner.add(lineJoiner.toString());
        }

        System.out.println(enterJoiner);
    }

    private String getPieceName(Map<Position, Piece> pieces, Position position) {
        if (pieces.containsKey(position)) {
            Piece piece = pieces.get(position);
            return Formatter.formatPieceName(piece);
        }
        return "＿";
    }

    private String formatFirstRowOfBoard() {
        StringJoiner joiner = new StringJoiner(BLANK);

        for (int i = 1; i <= 9; i++) {
            joiner.add(Formatter.formatFullWidthNumber(i));
        }

        return joiner.toString();
    }

    public void printTurnInfo(Side side) {
        String sideName = Formatter.formatSideName(side);
        System.out.println(Formatter.formatMessageWithHeader(INFO_HEADER, sideName + "의 차례입니다."));
    }

    public PositionDto readPieceSelection() {
        System.out.println(Formatter.formatMessageWithHeader(INFO_HEADER, "기물의 좌표를 '세로,가로' 순으로 입력해주세요. (예: 3,5)"));
        String input = scanner.nextLine();

        validatePosition(input);
        return parsePosition(input);
    }

    private void validatePosition(String input) {
        if (input == null || input.isBlank() || input.split(",").length != POSITION_INPUT_SIZE) {
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

    public PositionDto readMove() {
        System.out.println(
                Formatter.formatMessageWithHeader(INFO_HEADER, "기물을 움직일 좌표를 '세로,가로' 순으로 입력해주세요. (예: 3,5)"));
        String input = scanner.nextLine();

        validatePosition(input);
        return parsePosition(input);
    }

    public void printWinner(Side side) {
        String sideName = Formatter.formatSideName(side);
        System.out.println(Formatter.formatMessageWithHeader(INFO_HEADER, sideName + "가 이겼습니다!"));
        System.out.println("게임을 종료합니다.");
    }

    public void printPoints(Map<Side, Double> points) {
        for (Map.Entry<Side, Double> entry : points.entrySet()) {
            String sideName = Formatter.formatSideName(entry.getKey());
            System.out.println(sideName + ": " + entry.getValue());
        }
    }
}
