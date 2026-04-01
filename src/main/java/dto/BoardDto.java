package dto;

import domain.Position;
import domain.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record BoardDto(Map<Position, Piece> board) {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";

    public List<List<String>> convertRows() {
        List<List<String>> result = new ArrayList<>();

        for (int row = 0; row < 10; row++) {
            List<String> line = new ArrayList<>();
            for (int col = 0; col < 9; col++) {
                Position position = new Position(row, col);
                Piece piece = board.get(position);
                line.add(toSymbol(piece));
            }
            result.add(line);
        }
        return result;

    }

    private String toSymbol(Piece piece) {
        if (piece.isNoneTeam()) {
            return "";
        }
        return coloredPieceName(piece);
    }

    private String coloredPieceName(Piece piece) {
        if (piece.isRedTeam()) {
            return colorize(piece.pieceName(), ANSI_RED);
        }
        return colorize(piece.pieceName(), ANSI_GREEN);
    }

    private String colorize(String text, String color) {
        return color + text + ANSI_RESET;
    }
}
