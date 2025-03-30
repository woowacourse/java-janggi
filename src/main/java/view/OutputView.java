package view;

import domain.board.BoardPoint;
import domain.board.Score;
import domain.pieces.Piece;
import java.util.Map;

public final class OutputView {

    private static final int MAX_COLUMN = 9;
    private static final int MAX_ROW = 10;

    public static void printGameEndMessage(Score score) {
        System.out.println("궁이 쓰러졌기에 게임을 종료합니다.");

        System.out.println("한나라의 점수 : " + score.hanScore());
        System.out.println("초나라의 점수 : " + score.choScore());
    }

    public void printBoard(final Map<BoardPoint, Piece> locations) {
        System.out.println(boardToString(locations));
    }

    private String boardToString(final Map<BoardPoint, Piece> locations) {
        final StringBuilder result = new StringBuilder();
        result.append("  ");
        for (int column = 0; column < MAX_COLUMN; column++) {
            result.append(column).append(" ");
        }
        result.append("\n");

        for (int row = MAX_ROW - 1; row >= 0; row--) {
            result.append((char) ('9' - (MAX_ROW - 1 - row))).append(" ");
            addPieceName(locations, row, result);
            result.append("\n");
        }

        return result.toString();
    }

    private void addPieceName(final Map<BoardPoint, Piece> locations, final int row,
                              StringBuilder result) {
        for (int column = 0; column < MAX_COLUMN; column++) {
            final BoardPoint boardPoint = new BoardPoint(row, column);
            if (locations.containsKey(boardPoint)) {
                final Piece piece = locations.get(boardPoint);
                result.append(piece.getName());
                result.append(" ");
                continue;
            }
            result.append("-");
            result.append(" ");
        }
    }
}
