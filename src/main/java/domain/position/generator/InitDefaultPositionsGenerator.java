package domain.position.generator;

import domain.janggiPiece.*;
import domain.position.JanggiPosition;
import domain.position.JanggiPositionFactory;
import domain.type.JanggiTeam;

import java.util.HashMap;
import java.util.Map;

public class InitDefaultPositionsGenerator implements DefaultPositionsGenerator {
    @Override
    public Map<JanggiPosition, JanggiChessPiece> generate() {
        Map<JanggiPosition, JanggiChessPiece> chessPieces = new HashMap<>();
        chessPieces.putAll(getCannonPieces());
        chessPieces.putAll(getChariotPieces());
        chessPieces.putAll(getElephantPieces());
        chessPieces.putAll(getGuardPieces());
        chessPieces.putAll(getHorsePieces());
        chessPieces.putAll(getKingPieces());
        chessPieces.putAll(getPawnPieces());
        return chessPieces;
    }

    private Map<JanggiPosition, JanggiChessPiece> getCannonPieces() {
        return Map.of(
                JanggiPositionFactory.of(2, 1), new Cannon(JanggiTeam.RED),
                JanggiPositionFactory.of(2, 7), new Cannon(JanggiTeam.RED),
                JanggiPositionFactory.of(7, 1), new Cannon(JanggiTeam.BLUE),
                JanggiPositionFactory.of(7, 7), new Cannon(JanggiTeam.BLUE)
        );
    }

    private Map<JanggiPosition, JanggiChessPiece> getChariotPieces() {
        return Map.of(
                JanggiPositionFactory.of(0, 0), new Chariot(JanggiTeam.RED),
                JanggiPositionFactory.of(0, 8), new Chariot(JanggiTeam.RED),
                JanggiPositionFactory.of(9, 0), new Chariot(JanggiTeam.BLUE),
                JanggiPositionFactory.of(9, 8), new Chariot(JanggiTeam.BLUE)
        );
    }

    private Map<JanggiPosition, JanggiChessPiece> getElephantPieces() {
        return Map.of(
                JanggiPositionFactory.of(0, 2), new Elephant(JanggiTeam.RED),
                JanggiPositionFactory.of(0, 6), new Elephant(JanggiTeam.RED),
                JanggiPositionFactory.of(9, 2), new Elephant(JanggiTeam.BLUE),
                JanggiPositionFactory.of(9, 6), new Elephant(JanggiTeam.BLUE)
        );
    }

    private Map<JanggiPosition, JanggiChessPiece> getGuardPieces() {
        return Map.of(
                JanggiPositionFactory.of(0, 3), new Guard(JanggiTeam.RED),
                JanggiPositionFactory.of(0, 5), new Guard(JanggiTeam.RED),
                JanggiPositionFactory.of(9, 3), new Guard(JanggiTeam.BLUE),
                JanggiPositionFactory.of(9, 5), new Guard(JanggiTeam.BLUE)
        );
    }

    private Map<JanggiPosition, JanggiChessPiece> getHorsePieces() {
        return Map.of(
                JanggiPositionFactory.of(0, 1), new Horse(JanggiTeam.RED),
                JanggiPositionFactory.of(0, 7), new Horse(JanggiTeam.RED),
                JanggiPositionFactory.of(9, 1), new Horse(JanggiTeam.BLUE),
                JanggiPositionFactory.of(9, 7), new Horse(JanggiTeam.BLUE)
        );
    }

    private Map<JanggiPosition, JanggiChessPiece> getKingPieces() {
        return Map.of(
                JanggiPositionFactory.of(1, 4), new King(JanggiTeam.RED),
                JanggiPositionFactory.of(8, 4), new King(JanggiTeam.BLUE)
        );
    }

    private Map<JanggiPosition, JanggiChessPiece> getPawnPieces() {
        return Map.of(
                JanggiPositionFactory.of(3, 0), new Pawn(JanggiTeam.RED),
                JanggiPositionFactory.of(3, 2), new Pawn(JanggiTeam.RED),
                JanggiPositionFactory.of(3, 4), new Pawn(JanggiTeam.RED),
                JanggiPositionFactory.of(3, 6), new Pawn(JanggiTeam.RED),
                JanggiPositionFactory.of(3, 8), new Pawn(JanggiTeam.RED),
                JanggiPositionFactory.of(6, 0), new Pawn(JanggiTeam.BLUE),
                JanggiPositionFactory.of(6, 2), new Pawn(JanggiTeam.BLUE),
                JanggiPositionFactory.of(6, 4), new Pawn(JanggiTeam.BLUE),
                JanggiPositionFactory.of(6, 6), new Pawn(JanggiTeam.BLUE),
                JanggiPositionFactory.of(6, 8), new Pawn(JanggiTeam.BLUE)
        );
    }
}
