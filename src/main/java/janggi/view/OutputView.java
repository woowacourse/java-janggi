package janggi.view;

import janggi.view.dto.BoardDto;
import janggi.view.dto.PieceDto;
import janggi.view.dto.PositionDto;
import janggi.view.mapper.DynastyColorMapper;
import janggi.domain.dynasty.Dynasty;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class OutputView {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ERROR_PREFIX = "[ERROR] ";

    public void printBoard(BoardDto boardDto) {
        printRow();
        printColumn(boardDto);
        printColorInfoByDynasty();
    }

    private static void printRow() {
        System.out.print("   ");
        for (int col = 1; col <= 9; col++) {
            System.out.print(col + "  ");
        }
        System.out.println();
    }

    private static void printColumn(BoardDto boardDto) {
        int rowIndex = 1;
        for (List<PieceDto> piecesByRow : boardDto.board()) {
            System.out.printf("%2d ", rowIndex++);

            for (PieceDto piece : piecesByRow) {
                System.out.print(piece.name() + " " + ANSI_RESET);
            }
            System.out.println();
        }
    }

    private static void printColorInfoByDynasty() {
        StringBuilder sb = new StringBuilder("나라별 색상: ");
        Arrays.stream(Dynasty.values()).forEach(dynasty -> {
            sb.append(DynastyColorMapper.getColorInfoByDynasty(dynasty));
        });
        System.out.println(sb);
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
