package janggi.view;

import static janggi.board.Board.COLUMN;
import static janggi.board.Board.ROW;

import janggi.piece.Piece;
import janggi.piece.Type;
import janggi.position.Position;
import java.util.Map;

public class OutputView {

    private static final String CELL_UNIT = " ";
    private static final String CELL_SPACE = CELL_UNIT + CELL_UNIT + CELL_UNIT + CELL_UNIT;
    private static final String EMPTY_PIECE = "ㅤ";
    private static final String BOARD_LINE = CELL_UNIT + "|" + CELL_UNIT;

    public void displayErrorMessage(String message) {
        System.out.println(message);
    }

    public void displayStartBanner() {
        System.out.println("""
                ===================================
                    Welcome to the Janggi Game!
                ===================================
                """);
    }

    public void displayBoard(Map<Position, Piece> placedPieces) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = ROW - 1; i >= 0; i--) {
            stringBuilder.append(System.lineSeparator())
                    .append(formatBoardRow(placedPieces, i));
        }
        stringBuilder.append(System.lineSeparator())
                .append(CELL_SPACE)
                .append(BOARD_LINE);
        for (int i = 0; i < COLUMN; i++) {
            stringBuilder.append(formatBoardIndex(i));
        }
        System.out.println(stringBuilder);
    }

    private String formatBoardRow(Map<Position, Piece> placedPieces, int i) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(formatBoardIndex(i));
        for (int j = 0; j < COLUMN; j++) {
            Piece piece = placedPieces.get(new Position(j, i));
            stringBuilder.append(formatPiece(piece));
        }
        return stringBuilder.toString();
    }

    private String formatBoardIndex(int index) {
        char fullWidthDigit = (char) ('０' + index);
        return formatCell(String.valueOf(fullWidthDigit));
    }

    private String formatPiece(Piece piece) {
        String symbol = formatPieceSymbol(piece);
        return formatCell(symbol);
    }

    private String formatCell(String cell) {
        return CELL_UNIT + cell + CELL_UNIT + BOARD_LINE;
    }

    private String formatPieceSymbol(Piece piece) {
        if (piece.isEmpty()) {
            return EMPTY_PIECE;
        }
        Type pieceSymbol = piece.getPieceSymbol();
        return pieceSymbol.getDisplayAttributes(piece.getCamp());
    }
}
