package game;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import piece.Piece;
import position.Position;

public final class Board {

    private final Team cho;
    private final Team han;
    private boolean isChoTurn = true;

    public Team getCho() {
        return cho;
    }

    public Team getHan() {
        return han;
    }

    public Board(final Team cho, final Team han) {
        validateTeamIsNotNull(cho, han);
        validateCountryIsNotSame(cho, han);
        this.cho = Team.getFirstTeam(cho, han);
        this.han = Team.getSecondTeam(cho, han);
    }

    private void validateTeamIsNotNull(final Team team1, final Team team2) {
        if (team1 == null || team2 == null) {
            throw new IllegalArgumentException("장기판은 필수값입니다.");
        }
    }

    private void validateCountryIsNotSame(final Team team1, final Team team2) {
        if (team1.getCountry().equals(team2.getCountry())) {
            throw new IllegalArgumentException("두 개의 장기판의 나라는 서로 달라야 합니다.");
        }
    }

    public Map<Position, Piece> getBoard() {
        final Map<Position, Piece> status = new HashMap<>();
        status.putAll(cho.getPieces());
        status.putAll(han.getPieces());
        return Collections.unmodifiableMap(status);
    }

    public void move(Position fromPosition, Position tagetPosition) {
        if (isChoTurn) {
            cho.move(fromPosition, tagetPosition, han.getPieces());
            nextTurn();
        } else {
            han.move(fromPosition, tagetPosition, cho.getPieces());
            nextTurn();
        }
    }

    private void nextTurn() {
        isChoTurn = !isChoTurn;
    }

    public Team getCurrentTurnTeam() {
        return isChoTurn ? cho : han;
    }
}
