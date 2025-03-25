package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

    public Map<Country, List<Piece>> getBoard() {
        final Map<Country, List<Piece>> board = new HashMap<>();
        board.put(team1.getCountry(), team1.getPieces());
        board.put(team2.getCountry(), team2.getPieces());
        return Collections.unmodifiableMap(board);
    }

    public void move(Position fromPosition, Position tagetPosition) {
        if (isTeam1Turn) {
            team1.move(fromPosition, tagetPosition, team2);
            nextTurn();
        }
        if (!isTeam1Turn) {
            team2.move(fromPosition, tagetPosition, team1);
            nextTurn();
        }
    }

    private void nextTurn() {
        isTeam1Turn = !isTeam1Turn;
    }

    public boolean isEnd() {
        return team1.isEnd() || team2.isEnd();
    }

    public Country getWinner() {
        if (team1.isEnd()) return Country.HAN;
        return Country.CHO;
    }

    public Country getCurrentCountry() {
        if (isTeam1Turn) return Country.CHO;
        return Country.HAN;
    }
}
