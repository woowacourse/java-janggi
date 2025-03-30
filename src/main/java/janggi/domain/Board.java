package janggi.domain;

import janggi.domain.piece.Piece;
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

    public Board(final Pieces pieces, final Team currentTeam) {
        this.pieces = pieces;
        this.turn = Turn.initialize(currentTeam);
    }

    public Piece selectPiece(final Position position) {
        Team team = turn.getCurrentTurn();

        return pieces.findPieceByPositionAndTeam(position, team);
    }

    public Set<Position> findDestinations(final Piece piece) {
        Set<Route> routes = pieces.classifyPossibleRoutes(piece);

        return new Routes(routes).getDestinations();
    }

    public void movePiece(final Position position, final Piece piece) {
        Set<Position> possibleDestinations = findDestinations(piece);
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

    public double getTeamScore(final Team team) {
        return pieces.calculateTeamScore().calculateTeamScore(team);
    }

    public boolean isGameEnd(final Team currentTeam) {
        Team otherTeam = Team.getOtherTeam(currentTeam);
        return turn.isDraw() || isGeneralDead(otherTeam);
    }

    public Team getWinner(final Team currentTeam) {
        if (turn.isDraw()) {
            return getWinnerWithScore(currentTeam);
        }
        return currentTeam;
    }

    private Team getWinnerWithScore(final Team currentTeam) {
        Team otherTeam = Team.getOtherTeam(currentTeam);
        double currentTeamPoint = getTeamScore(currentTeam);
        double otherTeamPoint = getTeamScore(otherTeam);

        if (currentTeamPoint > otherTeamPoint) {
            return currentTeam;
        }
        return otherTeam;
    }

    private boolean isGeneralDead(Team otherTeam) {
        return pieces.isGeneralDead(otherTeam);
    }
}
