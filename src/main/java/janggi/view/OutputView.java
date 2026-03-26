package janggi.view;

import janggi.dto.BoardDto;
import janggi.dto.PositionDto;
import java.util.List;
import java.util.StringJoiner;

public class OutputView {

    private static final String ERROR_PREFIX = "[ERROR] ";

    public void printBoard(BoardDto boardDto) {
        // 상단 가로 좌표 출력 (1~9)
        System.out.print("   ");
        for (int col = 1; col <= 9; col++) {
            System.out.print(col + "  ");
        }
        System.out.println();

        int rowIndex = 1;
        for (List<String> piecesByRow : boardDto.board()) {
            // 좌측 세로 좌표 출력 (1~10)
            System.out.printf("%2d ", rowIndex++);

            for (String pieceName : piecesByRow) {
                System.out.print(pieceName + " ");
            }
            System.out.println();
        }
    }

    public void printCanMovePositions(List<PositionDto> positions) {
        System.out.print("현재 이동 가능한 위치는");

        StringJoiner stringJoiner = new StringJoiner(",");
        for (PositionDto position : positions) {
            stringJoiner.add(" (" + position.row() + "," + position.column() + ")");
        }
        System.out.println(stringJoiner + "입니다.");
        System.out.println();
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(ERROR_PREFIX + errorMessage);
        System.out.println();
    }

}
