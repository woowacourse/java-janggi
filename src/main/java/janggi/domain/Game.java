package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.piece.direction.Position;
import janggi.domain.piece.direction.Route;
import java.util.List;
import java.util.Set;

public class Game {

    private static final double PLUS_SCORE = 1.5;

    private final Pieces pieces;
    private final Turn turn;

    public Game(final Pieces pieces, final Turn turn) {
        this.pieces = pieces;
        this.turn = turn;
    }

    public Piece selectPiece(final Position position) {
        final Team team = turn.getTurn();
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

    public Turn getTurn() {
        return turn;
    }

    public void changeTurn() {
        turn.changeTurn();
    }

    public double getScoreByTeam(final Team team) {
        return plusScoreByTurn(team);
    }

    private double plusScoreByTurn(final Team team) {
        if (turn.getTurn().equals(team)) {
            return pieces.calculatePiecesScoreByTeam(team) + PLUS_SCORE;
        }
        return pieces.calculatePiecesScoreByTeam(team);
    }

    public GameStatus getStatus() {
        return GameStatus.checkStatus(pieces);
    }
}
