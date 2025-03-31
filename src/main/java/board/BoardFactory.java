package board;

import piece.Country;
import piece.Piece;
import position.LineDirection;
import position.PieceInitialPosition;
import position.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardFactory {

    public BoardFactory(Country country, LineDirection direction) {
        Country.assignDirection(country, direction);
    }

    public Board generateBoard() {
        final Map<Position, Piece> initMap = new HashMap<>();
        for (Country country : Country.values()) {
            for (PieceInitialPosition pieceType : PieceInitialPosition.values()) {
                initMap.putAll(pieceType.getAbsolutePositions(country));
            }
        }
        return new Board(initMap);
    }

    public static Board fromDatabase(List<Piece> pieces, Map<Country, Integer> scores, Map<Country, LineDirection> loadedDirections) {
        loadedDirections.forEach(Country::assignDirection);
        Board board = new Board(pieces.stream()
                .collect(Collectors.toMap(Piece::getPosition, p -> p)));

        board.getScoreByCountry().putAll(scores);
        return board;
    }
}
