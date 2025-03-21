package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Board {

    private final Team team1;
    private final Team team2;

    private boolean isTeam1Turn = true;

    public Board(final Team team1, final Team team2) {
        validateTeamIsNotNull(team1, team2);
        validateCountryIsNotSame(team1,team2);
        this.team1 = Team.getFirstTeam(team1, team2);
        this.team2 = Team.getSecondTeam(team1, team2);
    }

    private void validateTeamIsNotNull(final Team team1, final Team team2) {
        if (team1 == null || team2 == null) {
            throw new IllegalArgumentException("장기판은 필수값입니다.");
        }
    }

    private void validateCountryIsNotSame(final Team team1, final Team team2) {
        if(team1.isSameCountry(team2)) {
            throw new IllegalArgumentException("두 개의 장기판의 나라는 서로 달라야 합니다.");
        }
    }

    public Map<Position, Piece> getBoard() {
        final Map<Position, Piece> status = new HashMap<>();
        status.putAll(team1.getPieces());
        status.putAll(team2.getPieces());
        return Collections.unmodifiableMap(status);
    }

    public void move(Position fromPosition, Position tagetPosition) {
        if (isTeam1Turn) {
            team1.move(fromPosition, tagetPosition, team2.getPieces());
            nextTurn();
        }
        if (!isTeam1Turn) {
            team2.move(fromPosition, tagetPosition, team1.getPieces());
            nextTurn();
        }
    }

    private void nextTurn() {
        isTeam1Turn = !isTeam1Turn;
    }
}
