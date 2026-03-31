package dto;

import domain.Position;
import domain.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record BoardDto(Map<Position, Piece> board) {

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
            return " . ";
        }
        if (piece.isRedTeam()) {
            return "R" + piece.pieceName();
        }
        return "G" + piece.pieceName();
    }
}
