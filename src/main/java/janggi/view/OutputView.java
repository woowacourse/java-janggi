package janggi.view;

import janggi.domain.position.Column;
import janggi.domain.position.Row;
import janggi.dto.BoardDto;
import janggi.dto.DynastyDto;
import janggi.dto.PositionDto;
import java.util.List;
import java.util.StringJoiner;

public class OutputView {

    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String WARNING_PREFIX = "[WARNING] ";

    public void printPlayableGameIds(List<Long> gameIds) {
        System.out.println("불러올 수 있는 게임 ID:");
        gameIds.forEach(id -> System.out.println("- " + id));
        System.out.println();
    }

    public void printBoard(BoardDto pieces) {
        printCell("");
        for (int column = Column.MIN_COLUMN; column <= Column.MAX_COLUMN; column++) {
            printCell(String.valueOf(column));
        }
        System.out.println();

        printAllPieces(pieces);
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

    public void printWinner(DynastyDto dynasty) {
        System.out.println(dynasty.dynastyName() + "나라 승리!");
        System.out.println();
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(ERROR_PREFIX + errorMessage);
        System.out.println();
    }

    public void printWarningMessage(String errorMessage) {
        System.out.println(WARNING_PREFIX + errorMessage);
        System.out.println();
    }

    private void printAllPieces(BoardDto pieces) {
        for (int row = Row.MIN_ROW; row <= Row.MAX_ROW; row++) {
            printPiecesByRow(pieces, row);
            System.out.println();
        }
    }

    private void printPiecesByRow(BoardDto pieces, int row) {
        printCell(String.valueOf(row));
        for (int column = Column.MIN_COLUMN; column <= Column.MAX_COLUMN; column++) {
            printPiece(pieces, row, column);
        }
    }

    private void printPiece(BoardDto pieces, int row, int column) {
        if (pieces.isExist(row, column)) {
            printCell(pieces.get(row, column));
            return;
        }
        printCell(".");
    }

    private void printCell(String value) {
        System.out.print(value + "\t");
    }

}
