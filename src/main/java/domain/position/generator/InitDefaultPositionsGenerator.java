package domain.position.generator;

import domain.janggiPiece.*;
import domain.position.JanggiPosition;

import java.util.HashMap;
import java.util.Map;

public class InitDefaultPositionsGenerator implements DefaultPositionsGenerator {
    @Override
    public Map<JanggiPosition, JanggiChessPiece> generate() {
        Map<JanggiPosition, JanggiChessPiece> chessPieces = new HashMap<>();
        chessPieces.putAll(Cannon.initPieces());
        chessPieces.putAll(Chariot.initPieces());
        chessPieces.putAll(Elephant.initPieces());
        chessPieces.putAll(Guard.initPieces());
        chessPieces.putAll(Horse.initPieces());
        chessPieces.putAll(King.initPieces());
        chessPieces.putAll(Pawn.initPieces());
        return chessPieces;
    }
}
