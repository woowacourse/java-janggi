package domain.piece;

import domain.coordination.Coordination;

import java.util.Map;

public interface Piece {

    boolean isEmpty();

    Team team();

    void validateMovable(Coordination from, Coordination to, Map<Coordination, Piece> board);

    void isSameTeam(Piece piece);

    boolean isSameTeam(Team team);
}
