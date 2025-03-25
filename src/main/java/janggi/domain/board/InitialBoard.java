package janggi.domain.board;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.TeamColor;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Soldier;
import java.util.HashMap;
import java.util.Map;

public class InitialBoard {
    private final Map<Position, Piece> initialPieces;

    private InitialBoard(Map<Position, Piece> initialPieces) {
        this.initialPieces = initialPieces;
    }

    public static InitialBoard createBoard(BoardSetup redSetup, BoardSetup blueSetup){
        Map<Position, Piece> piecePositions = new HashMap<>();

        piecePositions.put(Position.of(1, 1), new Chariot(TeamColor.RED));
        piecePositions.put(Position.of(1, 4), new Guard(TeamColor.RED));
        piecePositions.put(Position.of(1, 6), new Guard(TeamColor.RED));
        piecePositions.put(Position.of(1, 9), new Chariot(TeamColor.RED));
        piecePositions.put(Position.of(2, 5), new General(TeamColor.RED));
        piecePositions.put(Position.of(3, 2), new Cannon(TeamColor.RED));
        piecePositions.put(Position.of(3, 8), new Cannon(TeamColor.RED));
        piecePositions.put(Position.of(4, 1), new Soldier(TeamColor.RED));
        piecePositions.put(Position.of(4, 3), new Soldier(TeamColor.RED));
        piecePositions.put(Position.of(4, 5), new Soldier(TeamColor.RED));
        piecePositions.put(Position.of(4, 7), new Soldier(TeamColor.RED));
        piecePositions.put(Position.of(4, 9), new Soldier(TeamColor.RED));
        piecePositions.put(Position.of(7, 1), new Soldier(TeamColor.BLUE));
        piecePositions.put(Position.of(7, 3), new Soldier(TeamColor.BLUE));
        piecePositions.put(Position.of(7, 5), new Soldier(TeamColor.BLUE));
        piecePositions.put(Position.of(7, 7), new Soldier(TeamColor.BLUE));
        piecePositions.put(Position.of(7, 9), new Soldier(TeamColor.BLUE));
        piecePositions.put(Position.of(8, 2), new Cannon(TeamColor.BLUE));
        piecePositions.put(Position.of(8, 8), new Cannon(TeamColor.BLUE));
        piecePositions.put(Position.of(9, 5), new General(TeamColor.BLUE));
        piecePositions.put(Position.of(0, 1), new Chariot(TeamColor.BLUE));
        piecePositions.put(Position.of(0, 4), new Guard(TeamColor.BLUE));
        piecePositions.put(Position.of(0, 6), new Guard(TeamColor.BLUE));
        piecePositions.put(Position.of(0, 9), new Chariot(TeamColor.BLUE));

        piecePositions.put(Position.of(1, 2), createPiece(redSetup.getLeftSetup().get(0), TeamColor.RED));
        piecePositions.put(Position.of(1, 3), createPiece(redSetup.getLeftSetup().get(1), TeamColor.RED));
        piecePositions.put(Position.of(1, 7), createPiece(redSetup.getRightSetup().get(0), TeamColor.RED));
        piecePositions.put(Position.of(1, 8), createPiece(redSetup.getRightSetup().get(1), TeamColor.RED));

        piecePositions.put(Position.of(0, 2), createPiece(blueSetup.getLeftSetup().get(0), TeamColor.BLUE));
        piecePositions.put(Position.of(0, 3), createPiece(blueSetup.getLeftSetup().get(1), TeamColor.BLUE));
        piecePositions.put(Position.of(0, 7), createPiece(blueSetup.getRightSetup().get(0), TeamColor.BLUE));
        piecePositions.put(Position.of(0, 8), createPiece(blueSetup.getRightSetup().get(1), TeamColor.BLUE));

        return new InitialBoard(piecePositions);
    }

    private static Piece createPiece(PieceType type, TeamColor color) {
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
