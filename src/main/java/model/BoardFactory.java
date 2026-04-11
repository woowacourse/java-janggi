package model;

import model.coordinate.Position;
import model.piece.Cannon;
import model.piece.Chariot;
import model.piece.General;
import model.piece.Guard;
import model.piece.Piece;
import model.piece.Soldier;

import java.util.HashMap;
import java.util.Map;

import static model.Team.CHO;
import static model.Team.HAN;

public class BoardFactory {

    private static final Map<Position, Piece> RED_PIECES = Map.ofEntries(
            Map.entry(new Position(0, 0), new Chariot(HAN)),
            Map.entry(new Position(0, 3), new Guard(HAN)),
            Map.entry(new Position(0, 5), new Guard(HAN)),
            Map.entry(new Position(0, 8), new Chariot(HAN)),
            Map.entry(new Position(1, 4), new General(HAN)),
            Map.entry(new Position(2, 1), new Cannon(HAN)),
            Map.entry(new Position(2, 7), new Cannon(HAN)),
            Map.entry(new Position(3, 0), new Soldier(HAN)),
            Map.entry(new Position(3, 2), new Soldier(HAN)),
            Map.entry(new Position(3, 4), new Soldier(HAN)),
            Map.entry(new Position(3, 6), new Soldier(HAN)),
            Map.entry(new Position(3, 8), new Soldier(HAN))
    );

    private static final Map<Position, Piece> GREEN_PIECES = Map.ofEntries(
            Map.entry(new Position(9, 0), new Chariot(CHO)),
            Map.entry(new Position(9, 3), new Guard(CHO)),
            Map.entry(new Position(9, 5), new Guard(CHO)),
            Map.entry(new Position(9, 8), new Chariot(CHO)),
            Map.entry(new Position(8, 4), new General(CHO)),
            Map.entry(new Position(7, 1), new Cannon(CHO)),
            Map.entry(new Position(7, 7), new Cannon(CHO)),
            Map.entry(new Position(6, 0), new Soldier(CHO)),
            Map.entry(new Position(6, 2), new Soldier(CHO)),
            Map.entry(new Position(6, 4), new Soldier(CHO)),
            Map.entry(new Position(6, 6), new Soldier(CHO)),
            Map.entry(new Position(6, 8), new Soldier(CHO))
    );

    public static Board generatePieces(Map<Position, Piece> pieceByFormation) {
        Map<Position, Piece> allPieces = new HashMap<>();
        allPieces.putAll(RED_PIECES);
        allPieces.putAll(GREEN_PIECES);
        allPieces.putAll(pieceByFormation);
        return new Board(allPieces);
    }
}
