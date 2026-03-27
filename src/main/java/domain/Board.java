package domain;

import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;
import strategy.InitializeStrategy;

public class Board {
    protected final Map<Position, Piece> pieces = new HashMap<>();

    public Board(InitializeStrategy choInitializeStrategy, InitializeStrategy hanInitializeStrategy) {
        initTeamBoard(choInitializeStrategy, Team.CHO);
        initTeamBoard(hanInitializeStrategy, Team.HAN);
    }

    /**
     * TODO: 테스트용 함수 제거 필요
     *
     * @param position
     * @param piece
     * @return
     */
    public boolean isExistSameType(Position position, Piece piece) {
        return pieces.get(position).getType()
                .equals(piece.getType());
    }

    public boolean isEmpty(Position position) {
        return !pieces.containsKey(position);
    }

    public boolean hasSameTeamOn(Position position, Piece piece) {
        return pieces.get(position).isSameTeam(piece);
    }

    private void initTeamBoard(InitializeStrategy strategy, Team team) {
        pieces.putAll(strategy.initialize(team));
    }
}
