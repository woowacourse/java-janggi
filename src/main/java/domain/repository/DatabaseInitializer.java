package domain.repository;

import domain.board.BoardInitializer;
import domain.coordinate.Position;
import domain.piece.Piece;

import java.util.Map;

public class DatabaseInitializer implements BoardInitializer {

    private final Map<Position, Piece> pieceMap;

    public DatabaseInitializer(Map<Position, Piece> pieceMap) {
        this.pieceMap = pieceMap;
    }

    @Override
    public Map<Position, Piece> initialize() {
        return pieceMap;
    }
}
