package janggi.domain;

import janggi.domain.piece.Piece;
import java.util.Set;

public class Board {
    
    private final Pieces pieces;
    private final Turn turn;

    public Board() {
        this.pieces = new Pieces(PiecesInitializer.initializePieces());
        this.turn = Turn.initialize();
    }

    public Piece selectPiece(final int x, final int y) {
        Team team = turn.getCurrentTurn();

        return pieces.findPieceByPositionAndTeam(x, y, team);
    }

    public Set<Route> findPossibleRoutes(Piece piece) {
        return piece.calculateRoutes();
    }

    public void movePiece(final int x, final int y, Piece piece) {
        pieces.move(piece, x, y);
    }
}
