package janggi.view;

import static janggi.domain.board.Board.MAX_COLUMN;
import static janggi.domain.board.Board.MAX_ROW;

import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Type;
import janggi.domain.position.Position;
import java.util.List;
import java.util.Map;

public class OutputView {

    private static final String CELL_UNIT = " ";
    private static final String CELL_SPACE = CELL_UNIT + CELL_UNIT + CELL_UNIT + CELL_UNIT;
    private static final String EMPTY_PIECE = "ㅤ";
    private static final String BOARD_LINE = CELL_UNIT + "|" + CELL_UNIT;
    private static final String DISPLAY_POINT = " * ";
    private static final String DISPLAY_LINE = "===================================";

    public void displayErrorMessage(String message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.lineSeparator())
                .append(message);
        System.out.println(stringBuilder);
    }

    public void displayGameBanner() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DISPLAY_LINE).append(System.lineSeparator())
                .append(DISPLAY_POINT)
                .append("Welcome to the Janggi Game!").append(System.lineSeparator())
                .append(DISPLAY_LINE);
        System.out.println(stringBuilder);
    }

    public void displayBoardIds(List<Long> boardIds) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.lineSeparator())
                .append("[보드 ID 목록]")
                .append(System.lineSeparator())
                .append(formatBoardIds(boardIds));
        System.out.println(stringBuilder);
    }

    public void displayBoard(Map<Position, Piece> placedPieces) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = MAX_ROW - 1; i >= 0; i--) {
            stringBuilder.append(System.lineSeparator())
                    .append(formatBoardRow(placedPieces, i));
        }
        stringBuilder.append(System.lineSeparator())
                .append(CELL_SPACE)
                .append(BOARD_LINE);
        for (int i = 0; i < MAX_COLUMN; i++) {
            stringBuilder.append(formatBoardIndex(i));
        }
        System.out.println(stringBuilder);
    }

    public void displayScores(Map<Camp, Double> scores) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.lineSeparator())
                .append(DISPLAY_LINE).append(System.lineSeparator())
                .append("[점수 현황판]").append(System.lineSeparator())
                .append(formatScore(Camp.CHO, scores.get(Camp.CHO))).append(System.lineSeparator())
                .append(formatScore(Camp.HAN, scores.get(Camp.HAN))).append(System.lineSeparator())
                .append(DISPLAY_LINE);
        System.out.println(stringBuilder);
    }

    public void displayWinner(Camp winner) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.lineSeparator())
                .append(DISPLAY_LINE).append(System.lineSeparator())
                .append("[게임 우승자]").append(System.lineSeparator())
                .append(String.format("%s", winner.getName())).append(System.lineSeparator())
                .append(DISPLAY_LINE);
        System.out.println(stringBuilder);
    }

    private String formatBoardRow(Map<Position, Piece> placedPieces, int i) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(formatBoardIndex(i));
        for (int j = 0; j < MAX_COLUMN; j++) {
            Piece piece = placedPieces.get(Position.of(j, i));
            stringBuilder.append(formatPiece(piece));
        }
        return stringBuilder.toString();
    }

    private String formatBoardIds(List<Long> boardIds) {
        StringBuilder stringBuilder = new StringBuilder();
        if (boardIds.isEmpty()) {
            return "없음";
        }
        for (Long boardId : boardIds) {
            stringBuilder.append(boardId).append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    private String formatBoardIndex(int index) {
        char fullWidthDigit = (char) ('０' + index);
        return formatCell(String.valueOf(fullWidthDigit));
    }

    private String formatScore(Camp camp, double score) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%s : %.1f점", camp.getName(), score));
        return stringBuilder.toString();
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
        Type pieceSymbol = piece.getType();
        return pieceSymbol.getDisplayAttributes(piece.getCamp());
    }
}
