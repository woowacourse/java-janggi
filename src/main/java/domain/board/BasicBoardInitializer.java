package domain.board;

import domain.coordinate.Position;
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

    private void placeChu(Map<Position, Piece> pieceInitPlacements) {
        pieceInitPlacements.put(new Position(9,0), new Chariot(Side.CHU));
        pieceInitPlacements.put(new Position(9,1), new Horse(Side.CHU));
        pieceInitPlacements.put(new Position(9,2), new Elephant(Side.CHU));
        pieceInitPlacements.put(new Position(9,3), new Guard(Side.CHU));
        pieceInitPlacements.put(new Position(9,5), new Guard(Side.CHU));
        pieceInitPlacements.put(new Position(9,6), new Elephant(Side.CHU));
        pieceInitPlacements.put(new Position(9,7), new Horse(Side.CHU));
        pieceInitPlacements.put(new Position(9,8), new Chariot(Side.CHU));

        pieceInitPlacements.put(new Position(8,4), new King(Side.CHU));

        pieceInitPlacements.put(new Position(7,1), new Cannon(Side.CHU));
        pieceInitPlacements.put(new Position(7,7), new Cannon(Side.CHU));

        pieceInitPlacements.put(new Position(6,0), new Pawn(Side.CHU));
        pieceInitPlacements.put(new Position(6,2), new Pawn(Side.CHU));
        pieceInitPlacements.put(new Position(6,4), new Pawn(Side.CHU));
        pieceInitPlacements.put(new Position(6,6), new Pawn(Side.CHU));
        pieceInitPlacements.put(new Position(6,8), new Pawn(Side.CHU));
    }

    public Side getFirstTurnSide() {
        return Side.CHU;
    }
}
