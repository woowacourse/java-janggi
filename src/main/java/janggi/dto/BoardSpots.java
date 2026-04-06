package janggi.dto;

import janggi.domain.Position;
import janggi.domain.piece.Piece;

import java.util.HashMap;
import java.util.Map;

public record BoardSpots(Map<Position, BoardSpot> value) {

    public static BoardSpots from(Map<Position, Piece> pieces) {
        Map<Position, BoardSpot> boardSpots = new HashMap<>();
        for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();

            BoardSpot boardSpot = BoardSpot.from(piece);
            boardSpots.put(position, boardSpot);
        }
        return new BoardSpots(boardSpots);
    }
}
