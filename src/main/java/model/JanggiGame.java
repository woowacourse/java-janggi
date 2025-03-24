package model;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import utils.InputParser;

public class JanggiGame {

    private final Pieces pieces;

    public JanggiGame() {
        Map<Position, Piece> pieces = PieceInitializer.generate();
        this.pieces = new Pieces(pieces);
    }

    public Position createPositionFrom(String choiceDeparture) {
        List<Integer> columnAndRowOfDeparture = InputParser.splitAndConvert(choiceDeparture);
        return new Position(columnAndRowOfDeparture);
    }

    public Piece findPieceBy(Position departure) {
        return pieces.findPieceBy(departure);
    }

    public void move(Position departure, Position arrival) {
        pieces.move(departure, arrival);
    }

    public String showCurrentPositionOfPieces() {
        StringBuilder sb = new StringBuilder();
        for (Column column : Column.values()) {
            for (Row row : Row.values()) {
                Optional<Piece> piece = pieces.findPieceOfNullable(new Position(column, row));
                if (piece.isEmpty()) {
                    sb.append("－");
                } else {
                    sb.append(piece.get());
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
