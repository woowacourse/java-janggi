package domain;

import domain.piece.Piece;
import domain.position.Position;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Team team1;
    private final Team team2;

    public Board(final Team team1, final Team team2) {
        validateTeamIsNotNull(team1, team2);
        validateCountryIsNotSame(team1,team2);
        this.team1 = team1;
        this.team2 = team2;
    }

    private void validateTeamIsNotNull(final Team team1, final Team team2) {
        if (team1 == null || team2 == null) {
            throw new IllegalArgumentException("장기판은 필수값입니다.");
        }
    }

    private void validateCountryIsNotSame(final Team team1, final Team team2) {
        if(team1.getCountry().equals(team2.getCountry())) {
            throw new IllegalArgumentException("두 개의 장기판의 나라는 서로 달라야 합니다.");
        }
    }

    public Map<Position, Piece> getBoard() {
        final Map<Position, Piece> status = new HashMap<>();
        status.putAll(team1.getPieces());
        status.putAll(team2.getPieces());
        return Collections.unmodifiableMap(status);
    }
}
