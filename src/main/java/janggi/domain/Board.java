package janggi.domain;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.domain.position.Route;
import java.util.List;
import java.util.Set;

public class Board {

    public static final int MIN_POSITION = 0;
    public static final int MAX_X_POSITION = 8;
    public static final int MAX_Y_POSITION = 9;

    private final Pieces pieces;
    private final Turn turn;

    public Board() {
        this.pieces = new Pieces(PiecesInitializer.initializePieces());
        this.turn = Turn.initialize();
    }

    public Piece selectPiece(final Position position) {
        Team team = turn.getCurrentTurn();
        return pieces.findPieceByPositionAndTeam(position, team);
    }

    public Set<Route> findPossibleRoutes(Piece piece) {
        if (piece.getClass() == Cannon.class) {
            return pieces.getPossibleRoutesForCannon(piece);
        }
        if (piece.getClass() == Chariot.class) {
            return pieces.getPossibleRoutesForChariot(piece);
        }
        return pieces.getPossibleRoutes(piece);
    }

    public void movePiece(final Position position, Piece piece, final Set<Route> possibleRoutes) {
        List<Position> possibleDestinations = possibleRoutes.stream()
                .map(Route::getDestination)
                .toList();

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
