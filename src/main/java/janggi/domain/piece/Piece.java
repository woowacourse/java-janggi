package janggi.domain.piece;

import java.util.Map;
import java.util.function.Consumer;

public abstract class Piece {
    protected final Team team;
    private final String name;

    public Piece(final String name, final Team team) {
        this.name = name;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public abstract Consumer<Map<Position, Piece>> getMovableValidator(
            final Position beforePosition,
            final Position afterPosition
    );

    protected void validateIsSameTeamNotInPositionToMove(
            final Map<Position, Piece> board,
            final Position positionToMove) {
        if (board.get(positionToMove).getTeam().equals(team)) {
            throw new IllegalArgumentException("같은 팀 기물이 있는 위치로는 이동할 수 없습니다.");
        }
    }

    public boolean isCannon() {
        return false;
    }

    public boolean isNone() {
        return false;
    }
}