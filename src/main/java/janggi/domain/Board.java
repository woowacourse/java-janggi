package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;
import janggi.domain.position.Route;
import janggi.domain.position.Routes;
import java.util.List;
import java.util.Set;

public class Board {

    public static final int MIN_POSITION = 0;
    public static final int MAX_X_POSITION = 8;
    public static final int MAX_Y_POSITION = 9;

    private final Pieces pieces;
    private final Turn turn;

    public Board(Pieces pieces) {
        this.pieces = pieces;
        this.turn = Turn.initialize();
    }

    public Piece selectPiece(final Position position) {
        Team team = turn.getCurrentTurn();
        return pieces.findPieceByPositionAndTeam(position, team);
    }

    public Set<Position> findDestinations(Piece piece) {
        Set<Route> routes;
        if (piece.isSameType(PieceType.CANNON)) {
            routes = pieces.getPossibleRoutesForCannon(piece);
            return new Routes(routes).getDestinations();
        }
        if (piece.isSameType(PieceType.CHARIOT)) {
            routes = pieces.getPossibleRoutesForChariot(piece);
            return new Routes(routes).getDestinations();
        }
        routes = pieces.getPossibleRoutes(piece);
        return new Routes(routes).getDestinations();
    }

    public void movePiece(final Position position, Piece piece, final Set<Position> possibleDestinations) {
        if (!possibleDestinations.contains(position)) {
            throw new IllegalArgumentException("움직일 수 없는 위치입니다.");
        }
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
