package janggi.view;

import janggi.common.ErrorMessage;
import janggi.domain.Board;
import janggi.domain.Side;
import janggi.domain.move.Position;
import janggi.domain.piece.Piece;
import janggi.dto.PositionDto;
import java.util.Scanner;
import java.util.StringJoiner;

public class Viewer {

    private static final Scanner scanner = new Scanner(System.in);

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String BLANK = " ";
    private static final String ERROR_HEADER = "[ERROR] ";
    private static final String INFO_HEADER = "[안내] ";

    private static final int POSITION_INPUT_SIZE = 2;

    private static final int START_COLUMN = Position.getStartColumn();
    private static final int LAST_COLUMN = Position.getLastColumn();
    private static final int START_ROW = Position.getStartRow();
    private static final int LAST_ROW = Position.getLastRow();

    public void printErrorMessage(Exception e) {
        System.out.println(Formatter.formatMessageWithHeader(ERROR_HEADER, e.getMessage()));
    }

    public MaSangPosition settingMaSangPlacement(Side side) {
        System.out.println(Formatter.formatSide(side) + "의 차림을 숫자로 선택해주세요");
        System.out.println("1. 상마상마");
        System.out.println("2. 마상마상");
        System.out.println("3. 마상상마");
        System.out.println("4. 상마마상");

        return MaSangPosition.find(scanner.nextLine());
    }

    public void printBoard(Board board) {
        StringJoiner enterJoiner = new StringJoiner(LINE_SEPARATOR).add(formatFirstRowOfBoard());
        for (int row = START_ROW; row <= LAST_ROW; row++) {
            StringJoiner lineJoiner = new StringJoiner(BLANK);
            for (int column = START_COLUMN; column <= LAST_COLUMN; column++) {
                lineJoiner.add(board.getPieceName(row, column));
            }

            lineJoiner.add(Formatter.formatFullWidthNumber(row));
            enterJoiner.add(lineJoiner.toString());
        }

        System.out.println(enterJoiner);
        printScore(board);
    }

    private String formatFirstRowOfBoard() {
        StringJoiner joiner = new StringJoiner(BLANK);

        for (int i = START_COLUMN; i <= LAST_COLUMN; i++) {
            joiner.add(Formatter.formatFullWidthNumber(i));
        }

        return joiner.toString();
    }

    public void printTurnInfo(Side side) {
        System.out.println(Formatter.formatMessageWithHeader(INFO_HEADER, Formatter.formatSide(side) + "의 차례입니다."));
    }

    public Option readChooseOption() {
        System.out.println(Formatter.formatMessageWithHeader(INFO_HEADER, "원하는 옵션을 선택해주세요!"));
        System.out.println("1. 기물 선택");
        System.out.println("2. 점수 확인");
        System.out.println("3. 종료");

        return Option.find(scanner.nextLine());
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

    public PositionDto readMove(Piece piece) {
        String pieceName = piece.toName();
        System.out.println(
                Formatter.formatMessageWithHeader(INFO_HEADER, pieceName + "이 움직일 좌표를 '세로,가로' 순으로 입력해주세요. (예: 3,5)"));
        String input = scanner.nextLine();

        validatePosition(input);
        return parsePosition(input);
    }

    public void printScore(Board board) {
        System.out.println(Formatter.formatMessageWithHeader(INFO_HEADER, "현재 점수입니다"));
        System.out.println(Formatter.formatScoreBySide(Side.HAN, board.getScore(Side.HAN)));
        System.out.println(Formatter.formatScoreBySide(Side.CHO, board.getScore(Side.CHO)));
    }

    public void result(Board board) {
        System.out.println(Formatter.formatMessageWithHeader(INFO_HEADER, "최종 점수입니다"));
        double hanScore = board.getScore(Side.HAN);
        double choScore = board.getScore(Side.CHO);
        System.out.println(Formatter.formatScoreBySide(Side.HAN, hanScore));
        System.out.println(Formatter.formatScoreBySide(Side.CHO, choScore));

        if (hanScore > choScore) {
            result(Side.HAN);
            return;
        }

        result(Side.CHO);
    }

    public void result(Side side) {
        System.out.println(Formatter.formatMessageWithHeader(INFO_HEADER, Formatter.formatSide(side) + "가 이겼습니다!"));
    }
}
