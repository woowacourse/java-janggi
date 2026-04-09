package domain.board;

import domain.coordinate.Position;
import domain.Side;
import domain.piece.*;

import java.util.HashMap;
import java.util.Map;

public class BasicBoardInitializer implements BoardInitializer {

    public Map<Position, Piece> initialize() {
        Map<Position, Piece> pieceInitPlacements = new HashMap<>();

        placeHan(pieceInitPlacements);
        placeChu(pieceInitPlacements);

        return pieceInitPlacements;
    }

    private void placeHan(Map<Position, Piece> pieceInitPlacements) {
        pieceInitPlacements.put(new Position(0, 0), new Chariot(Side.HAN));
        pieceInitPlacements.put(new Position(0, 1), new Horse(Side.HAN));
        pieceInitPlacements.put(new Position(0, 2), new Elephant(Side.HAN));
        pieceInitPlacements.put(new Position(0, 3), new Guard(Side.HAN));
        pieceInitPlacements.put(new Position(0, 5), new Guard(Side.HAN));
        pieceInitPlacements.put(new Position(0, 6), new Elephant(Side.HAN));
        pieceInitPlacements.put(new Position(0, 7), new Horse(Side.HAN));
        pieceInitPlacements.put(new Position(0, 8), new Chariot(Side.HAN));

        pieceInitPlacements.put(new Position(1, 4), new King(Side.HAN));

        pieceInitPlacements.put(new Position(2, 1), new Cannon(Side.HAN));
        pieceInitPlacements.put(new Position(2, 7), new Cannon(Side.HAN));

        pieceInitPlacements.put(new Position(3, 0), new Pawn(Side.HAN));
        pieceInitPlacements.put(new Position(3, 2), new Pawn(Side.HAN));
        pieceInitPlacements.put(new Position(3, 4), new Pawn(Side.HAN));
        pieceInitPlacements.put(new Position(3, 6), new Pawn(Side.HAN));
        pieceInitPlacements.put(new Position(3, 8), new Pawn(Side.HAN));
    }

    private void placeChu(Map<Position, Piece> placements) {
        Map<Position, Piece> chuPlacements = new HashMap<>();

        for (Map.Entry<Position, Piece> entry : placements.entrySet()) {
            Piece piece = entry.getValue();
            if (!piece.isHan()) {
                continue;
            }

            Position pos = entry.getKey();
            Position chuPosition = translateChuPosition(pos);

            chuPlacements.put(chuPosition, copyAsChu(piece));
        }

        placements.putAll(chuPlacements);
    }

    private Position translateChuPosition(Position pos) {
        int row = 9 - pos.row();
        int col = pos.col();
        return new Position(row, col);
    }


    private Piece copyAsChu(Piece piece) {
        return piece.withSide(Side.CHU);
    }

    public Side getFirstTurnSide() {
        return Side.CHU;
    }
}
