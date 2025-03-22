package janggi.domain.board;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceColor;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Soldier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class InitialBoard {
    private final Map<Position, Piece> initialPieces;

    private InitialBoard(Map<Position, Piece> initialPieces) {
        this.initialPieces = initialPieces;
    }

    public static InitialBoard createBoard(BoardSetup redSetup, BoardSetup blueSetup){
        Map<Position, Piece> piecePositions = new HashMap<>();

        piecePositions.put(Position.of(1, 1), new Chariot(PieceColor.RED));
        piecePositions.put(Position.of(1, 4), new Guard(PieceColor.RED));
        piecePositions.put(Position.of(1, 6), new Guard(PieceColor.RED));
        piecePositions.put(Position.of(1, 9), new Chariot(PieceColor.RED));
        piecePositions.put(Position.of(2, 5), new General(PieceColor.RED));
        piecePositions.put(Position.of(3, 2), new Cannon(PieceColor.RED));
        piecePositions.put(Position.of(3, 8), new Cannon(PieceColor.RED));
        piecePositions.put(Position.of(4, 1), new Soldier(PieceColor.RED));
        piecePositions.put(Position.of(4, 3), new Soldier(PieceColor.RED));
        piecePositions.put(Position.of(4, 5), new Soldier(PieceColor.RED));
        piecePositions.put(Position.of(4, 7), new Soldier(PieceColor.RED));
        piecePositions.put(Position.of(4, 9), new Soldier(PieceColor.RED));
        piecePositions.put(Position.of(7, 1), new Soldier(PieceColor.BLUE));
        piecePositions.put(Position.of(7, 3), new Soldier(PieceColor.BLUE));
        piecePositions.put(Position.of(7, 5), new Soldier(PieceColor.BLUE));
        piecePositions.put(Position.of(7, 7), new Soldier(PieceColor.BLUE));
        piecePositions.put(Position.of(7, 9), new Soldier(PieceColor.BLUE));
        piecePositions.put(Position.of(8, 2), new Cannon(PieceColor.BLUE));
        piecePositions.put(Position.of(8, 8), new Cannon(PieceColor.BLUE));
        piecePositions.put(Position.of(9, 5), new General(PieceColor.BLUE));
        piecePositions.put(Position.of(0, 1), new Chariot(PieceColor.BLUE));
        piecePositions.put(Position.of(0, 4), new Guard(PieceColor.BLUE));
        piecePositions.put(Position.of(0, 6), new Guard(PieceColor.BLUE));
        piecePositions.put(Position.of(0, 9), new Chariot(PieceColor.BLUE));

        piecePositions.put(Position.of(1, 2), createPiece(redSetup.getLeftSetup().get(0), PieceColor.RED));
        piecePositions.put(Position.of(1, 3), createPiece(redSetup.getLeftSetup().get(1), PieceColor.RED));
        piecePositions.put(Position.of(1, 7), createPiece(redSetup.getRightSetup().get(0), PieceColor.RED));
        piecePositions.put(Position.of(1, 8), createPiece(redSetup.getRightSetup().get(1), PieceColor.RED));

        piecePositions.put(Position.of(0, 2), createPiece(blueSetup.getLeftSetup().get(0), PieceColor.BLUE));
        piecePositions.put(Position.of(0, 3), createPiece(blueSetup.getLeftSetup().get(1), PieceColor.BLUE));
        piecePositions.put(Position.of(0, 7), createPiece(blueSetup.getRightSetup().get(0), PieceColor.BLUE));
        piecePositions.put(Position.of(0, 8), createPiece(blueSetup.getRightSetup().get(1), PieceColor.BLUE));

        return new InitialBoard(piecePositions);
    }

    private static Piece createPiece(PieceType type, PieceColor color) {
        if(type == PieceType.HORSE) {
            return new Horse(color);
        }
        if(type == PieceType.ELEPHANT) {
            return new Elephant(color);
        }
        throw new IllegalStateException();
    }

    public Map<Position, Piece> getInitialBoard() {
        return new HashMap<>(initialPieces);
    }
}
