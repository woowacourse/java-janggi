package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.TeamColor;
import java.util.HashMap;
import java.util.Map;

public class InitialBoard {
    private final Map<Position, Piece> initialPieces;

    private InitialBoard(Map<Position, Piece> initialPieces) {
        this.initialPieces = initialPieces;
    }

    public static InitialBoard createBoard(BoardSetup redSetup, BoardSetup blueSetup){
        Map<Position, Piece> piecePositions = new HashMap<>();

        piecePositions.put(Position.of(1, 1), new Piece(TeamColor.RED, PieceType.CHARIOT));
        piecePositions.put(Position.of(1, 4), new Piece(TeamColor.RED, PieceType.GUARD));
        piecePositions.put(Position.of(1, 6), new Piece(TeamColor.RED, PieceType.GUARD));
        piecePositions.put(Position.of(1, 9), new Piece(TeamColor.RED, PieceType.CHARIOT));
        piecePositions.put(Position.of(2, 5), new Piece(TeamColor.RED, PieceType.GENERAL));
        piecePositions.put(Position.of(3, 2), new Piece(TeamColor.RED, PieceType.CANNON));
        piecePositions.put(Position.of(3, 8), new Piece(TeamColor.RED, PieceType.CANNON));
        piecePositions.put(Position.of(4, 1), new Piece(TeamColor.RED, PieceType.SOLDIER));
        piecePositions.put(Position.of(4, 3), new Piece(TeamColor.RED, PieceType.SOLDIER));
        piecePositions.put(Position.of(4, 5), new Piece(TeamColor.RED, PieceType.SOLDIER));
        piecePositions.put(Position.of(4, 7), new Piece(TeamColor.RED, PieceType.SOLDIER));
        piecePositions.put(Position.of(4, 9), new Piece(TeamColor.RED, PieceType.SOLDIER));
        piecePositions.put(Position.of(7, 1), new Piece(TeamColor.BLUE, PieceType.SOLDIER));
        piecePositions.put(Position.of(7, 3), new Piece(TeamColor.BLUE, PieceType.SOLDIER));
        piecePositions.put(Position.of(7, 5), new Piece(TeamColor.BLUE, PieceType.SOLDIER));
        piecePositions.put(Position.of(7, 7), new Piece(TeamColor.BLUE, PieceType.SOLDIER));
        piecePositions.put(Position.of(7, 9), new Piece(TeamColor.BLUE, PieceType.SOLDIER));
        piecePositions.put(Position.of(8, 2), new Piece(TeamColor.BLUE, PieceType.CANNON));
        piecePositions.put(Position.of(8, 8), new Piece(TeamColor.BLUE, PieceType.CANNON));
        piecePositions.put(Position.of(9, 5), new Piece(TeamColor.BLUE, PieceType.GENERAL));
        piecePositions.put(Position.of(0, 1), new Piece(TeamColor.BLUE, PieceType.CHARIOT));
        piecePositions.put(Position.of(0, 4), new Piece(TeamColor.BLUE, PieceType.GUARD));
        piecePositions.put(Position.of(0, 6), new Piece(TeamColor.BLUE, PieceType.GUARD));
        piecePositions.put(Position.of(0, 9), new Piece(TeamColor.BLUE, PieceType.CHARIOT));

        piecePositions.put(Position.of(1, 2), new Piece(TeamColor.RED, redSetup.getLeftSetup().get(0)));
        piecePositions.put(Position.of(1, 3), new Piece(TeamColor.RED, redSetup.getLeftSetup().get(1)));
        piecePositions.put(Position.of(1, 7), new Piece(TeamColor.RED, redSetup.getRightSetup().get(0)));
        piecePositions.put(Position.of(1, 8), new Piece(TeamColor.RED, redSetup.getRightSetup().get(1)));

        piecePositions.put(Position.of(0, 2), new Piece(TeamColor.BLUE, blueSetup.getLeftSetup().get(0)));
        piecePositions.put(Position.of(0, 3), new Piece(TeamColor.BLUE, blueSetup.getLeftSetup().get(1)));
        piecePositions.put(Position.of(0, 7), new Piece(TeamColor.BLUE, blueSetup.getRightSetup().get(0)));
        piecePositions.put(Position.of(0, 8), new Piece(TeamColor.BLUE, blueSetup.getRightSetup().get(1)));

        return new InitialBoard(piecePositions);
    }
    public Map<Position, Piece> getInitialBoard() {
        return new HashMap<>(initialPieces);
    }
}
