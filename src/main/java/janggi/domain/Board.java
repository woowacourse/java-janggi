package janggi.domain;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import java.util.List;
import java.util.Set;

public class Board {

    private final Pieces pieces;
    private final Turn turn;

    public Board() {
        this.pieces = new Pieces(PiecesInitializer.initializePieces());
        this.turn = Turn.initialize();
    }

    public Piece selectPiece(final Position position) {
        final Team team = turn.getCurrentTurn();
        return pieces.findPieceByPositionAndTeam(position, team);
    }

    public Set<Route> findPossibleRoutes(final Piece piece) {
        if (piece.getClass() == Cannon.class) {
            return pieces.getPossibleRoutesForCannon(piece);
        }
        if (piece.getClass() == Chariot.class) {
            return pieces.getPossibleRoutesForChariot(piece);
        }
        return pieces.getPossibleRoutes(piece);
    }

    public void movePiece(final Position position, final Piece piece) {
        pieces.move(position, piece);
    }

    public List<Piece> getPieces() {
        return pieces.getPieces();
    }

    public Team getTurn() {
        return turn.getCurrentTurn();
    }

    public void changeTurn() {
        turn.changeTurn();
    }
}
