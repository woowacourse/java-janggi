package view;

import domain.Point;
import domain.pieces.Piece;
import java.util.Map;

public class OutputView {

    public void print(String output) {
        System.out.println(output);
    }

    public void print(Map<Point, Piece> locations) {
        System.out.println(boardToString(locations));
    }

    private static String boardToString(Map<Point, Piece> locations) {
        int width = 9;
        int height = 10;

        StringBuilder result = new StringBuilder();

        result.append("  ");
        for (int x = 1; x <= width; x++) {
            result.append(x).append(" ");
        }
        result.append("\n");

        for (int y = height - 1; y >= 0; y--) {
            result.append((char) ('A' + (height - 1 - y))).append(" ");

            for (int x = 0; x < width; x++) {
                Point p = new Point(y, x);
                Piece piece = locations.get(p);
                if (piece != null) {
                    result.append(piece.getName());
                } else {
                    result.append("-");
                }
                result.append(" ");
            }
            result.append("\n");
        }

        return result.toString();
    }
}
