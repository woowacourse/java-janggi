package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PiecesInitializer;
import janggi.domain.piece.direction.Position;
import janggi.domain.piece.direction.Route;
import java.util.List;
import java.util.Set;

public class Board {

    private final Pieces pieces;
    private final Turn turn;

    public Board(final BoardSetup redBoardSetup, final BoardSetup blueBoardSetup) {
        this.pieces = new Pieces(PiecesInitializer.initializePieces(redBoardSetup, blueBoardSetup));
        this.turn = Turn.initialize();
    }

    public Piece selectPiece(final Position position) {
        final Team team = turn.getCurrentTurn();
        return pieces.findPieceByPositionAndTeam(position, team);
    }

    public Set<Route> findPossibleRoutes(final Piece piece) {
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
