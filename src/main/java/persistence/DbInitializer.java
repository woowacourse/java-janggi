package persistence;

import domain.board.BoardInitializer;
import domain.coordinate.Position;
import domain.piece.Piece;

import java.util.Map;

public class DbInitializer implements BoardInitializer {

    private final Map<Position, Piece> pieceMap;

    public DbInitializer(Map<Position, Piece> pieceMap) {
        this.pieceMap = pieceMap;
    }

    @Override
    public Map<Position, Piece> initialize() {
        return pieceMap;
    }
}
