package domain.piece;

import domain.Position;
import domain.Side;

import java.util.HashMap;
import java.util.Map;

public class BoardInitializer {

    public Map<Position, Piece> initialize() {
        Map<Position, Piece> piecesPosition = new HashMap<>();

        piecesPosition.put(new Position(0,0), new Chariot(Side.HAN));
        piecesPosition.put(new Position(0,1), new Horse(Side.HAN));
        piecesPosition.put(new Position(0,2), new Elephant(Side.HAN));
        piecesPosition.put(new Position(0,3), new Guard(Side.HAN));
        piecesPosition.put(new Position(0,5), new Guard(Side.HAN));
        piecesPosition.put(new Position(0,6), new Elephant(Side.HAN));
        piecesPosition.put(new Position(0,7), new Horse(Side.HAN));
        piecesPosition.put(new Position(0,8), new Chariot(Side.HAN));
        piecesPosition.put(new Position(1,4), new King(Side.HAN));
        piecesPosition.put(new Position(2,1), new Cannon(Side.HAN));
        piecesPosition.put(new Position(2,7), new Cannon(Side.HAN));

        piecesPosition.put(new Position(3,0), new Pawn(Side.HAN));
        piecesPosition.put(new Position(3,2), new Pawn(Side.HAN));
        piecesPosition.put(new Position(3,4), new Pawn(Side.HAN));
        piecesPosition.put(new Position(3,6), new Pawn(Side.HAN));
        piecesPosition.put(new Position(3,8), new Pawn(Side.HAN));


        piecesPosition.put(new Position(6,0), new Pawn(Side.CHU));
        piecesPosition.put(new Position(6,2), new Pawn(Side.CHU));
        piecesPosition.put(new Position(6,4), new Pawn(Side.CHU));
        piecesPosition.put(new Position(6,6), new Pawn(Side.CHU));
        piecesPosition.put(new Position(6,8), new Pawn(Side.CHU));

        piecesPosition.put(new Position(7,1), new Cannon(Side.CHU));
        piecesPosition.put(new Position(7,7), new Cannon(Side.CHU));
        piecesPosition.put(new Position(8,4), new King(Side.CHU));
        piecesPosition.put(new Position(9,0), new Chariot(Side.CHU));
        piecesPosition.put(new Position(9,1), new Horse(Side.CHU));
        piecesPosition.put(new Position(9,2), new Elephant(Side.CHU));
        piecesPosition.put(new Position(9,3), new Guard(Side.CHU));
        piecesPosition.put(new Position(9,5), new Guard(Side.CHU));
        piecesPosition.put(new Position(9,6), new Elephant(Side.CHU));
        piecesPosition.put(new Position(9,7), new Horse(Side.CHU));
        piecesPosition.put(new Position(9,8), new Chariot(Side.CHU));

        return piecesPosition;
    }
}
