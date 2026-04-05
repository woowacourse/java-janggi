package view;

import domain.pieces.PieceType;
import java.util.List;
import view.dto.PieceDto;

public class BoardRenderer {
    private static final int MAX_ROW = 9;
    private static final int MAX_COLUMN = 8;
    private static final String FULL_WIDTH_SPACE = "　";
    private static final String HORIZONTAL_LINE = "－";
    private static final String VERTICAL_LINE = "｜";
    private static final String DOWN_DIAGONAL = "＼";
    private static final String UP_DIAGONAL = "／";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String RESET = "\u001B[0m";

    public String render(List<List<PieceDto>> board) {
        StringBuilder result = new StringBuilder();

        for (int row = MAX_ROW; row >= 0; row--) {
            result.append(renderBoardRow(board, row)).append(System.lineSeparator());
            if (row > 0) {
                result.append(renderConnectorRow(row)).append(System.lineSeparator());
            }
        }
        result.append(renderColumnIndexes());
        return result.toString();
    }

    private String renderBoardRow(List<List<PieceDto>> board, int row) {
        StringBuilder line = new StringBuilder();
        line.append(axisLabel(row)).append(FULL_WIDTH_SPACE);

        for (int column = 0; column <= MAX_COLUMN; column++) {
            line.append(displayPiece(board.get(row).get(column)));
            if (column < MAX_COLUMN) {
                line.append(HORIZONTAL_LINE);
            }
        }
        return line.toString();
    }

    private String renderConnectorRow(int upperRow) {
        StringBuilder line = new StringBuilder(FULL_WIDTH_SPACE.repeat(2));

        for (int column = 0; column <= MAX_COLUMN; column++) {
            line.append(VERTICAL_LINE);
            if (column < MAX_COLUMN) {
                line.append(connectorSegment(upperRow, column));
            }
        }
        return line.toString();
    }

    private String connectorSegment(int upperRow, int leftColumn) {
        if (isTopPalaceUpperLeftDiagonal(upperRow, leftColumn)
                || isBottomPalaceUpperLeftDiagonal(upperRow, leftColumn)) {
            return DOWN_DIAGONAL;
        }
        if (isTopPalaceUpperRightDiagonal(upperRow, leftColumn)
                || isBottomPalaceUpperRightDiagonal(upperRow, leftColumn)) {
            return UP_DIAGONAL;
        }
        if (isTopPalaceLowerLeftDiagonal(upperRow, leftColumn)
                || isBottomPalaceLowerLeftDiagonal(upperRow, leftColumn)) {
            return UP_DIAGONAL;
        }
        if (isTopPalaceLowerRightDiagonal(upperRow, leftColumn)
                || isBottomPalaceLowerRightDiagonal(upperRow, leftColumn)) {
            return DOWN_DIAGONAL;
        }
        return FULL_WIDTH_SPACE;
    }

    private boolean isTopPalaceUpperLeftDiagonal(int upperRow, int leftColumn) {
        return upperRow == 9 && leftColumn == 3;
    }

    private boolean isTopPalaceUpperRightDiagonal(int upperRow, int leftColumn) {
        return upperRow == 9 && leftColumn == 4;
    }

    private boolean isTopPalaceLowerLeftDiagonal(int upperRow, int leftColumn) {
        return upperRow == 8 && leftColumn == 3;
    }

    private boolean isTopPalaceLowerRightDiagonal(int upperRow, int leftColumn) {
        return upperRow == 8 && leftColumn == 4;
    }

    private boolean isBottomPalaceUpperLeftDiagonal(int upperRow, int leftColumn) {
        return upperRow == 2 && leftColumn == 3;
    }

    private boolean isBottomPalaceUpperRightDiagonal(int upperRow, int leftColumn) {
        return upperRow == 2 && leftColumn == 4;
    }

    private boolean isBottomPalaceLowerLeftDiagonal(int upperRow, int leftColumn) {
        return upperRow == 1 && leftColumn == 3;
    }

    private boolean isBottomPalaceLowerRightDiagonal(int upperRow, int leftColumn) {
        return upperRow == 1 && leftColumn == 4;
    }

    private String displayPiece(PieceDto pieceDto) {
        String text = pieceSymbol(pieceDto);

        if (pieceDto.isEmpty()) {
            return text;
        }
        if (pieceDto.side().isCho()) {
            return GREEN + text + RESET;
        }
        return RED + text + RESET;
    }

    private String renderColumnIndexes() {
        StringBuilder line = new StringBuilder(FULL_WIDTH_SPACE.repeat(2));
        for (int column = 0; column <= MAX_COLUMN; column++) {
            line.append(axisLabel(column));
            if (column < MAX_COLUMN) {
                line.append(FULL_WIDTH_SPACE);
            }
        }
        return line.toString();
    }

    private String axisLabel(int number) {
        return String.valueOf((char) ('０' + number));
    }

    private String pieceSymbol(PieceDto pieceDto) {
        if (pieceDto.pieceType() == PieceType.EMPTY) {
            return "・";
        }
        if (pieceDto.pieceType() == PieceType.GUNG) {
            if (pieceDto.side().isCho()) {
                return "將";
            }
            return "宮";
        }
        if (pieceDto.pieceType() == PieceType.JOL_BYEONG) {
            if (pieceDto.side().isCho()) {
                return "兵";
            }
            return "卒";
        }
        return basicSymbol(pieceDto.pieceType());
    }

    private String basicSymbol(PieceType pieceType) {
        if (pieceType == PieceType.CHA) {
            return "車";
        }
        if (pieceType == PieceType.MA) {
            return "馬";
        }
        if (pieceType == PieceType.SANG) {
            return "象";
        }
        if (pieceType == PieceType.SA) {
            return "士";
        }
        if (pieceType == PieceType.PO) {
            return "包";
        }
        throw new IllegalArgumentException("지원하지 않는 기물입니다.");
    }
}
