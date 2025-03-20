package board;

import java.util.HashMap;
import java.util.Map;
import piece.Piece;
import piece.PieceType;
import piece.TeamType;

public class Board {

    private final Map<Position, Piece> map;

    public Board(final Map<Position, Piece> map) {
        this.map = new HashMap<>(map);
    }

    public boolean existPieceByPosition(final Position existPosition) {
        return map.containsKey(existPosition);
    }

    public boolean isCannonByPosition(final Position position) {
        if (map.containsKey(position)) {
            final Piece piece = map.get(position);
            return piece.equalsPieceType(PieceType.CANNON);
        }
        return false;
    }

    public boolean equalsTeamTypeByPosition(final Position position, final TeamType teamType) {
        if (map.containsKey(position)) {
            final Piece piece = map.get(position);
            return piece.equalsTeamType(teamType);
        }
        return false;
    }

    public void updatePosition(final Position source, final Position destination, final TeamType teamType) {
        if (!map.containsKey(source) || !map.get(source).equalsTeamType(teamType)) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
        final Piece piece = map.get(source);

        if (!piece.isAbleToMove(source, destination, this, teamType)) {
            throw new IllegalArgumentException("해당 기물은 해당 위치로 이동할 수 없습니다.");
        }

        map.put(destination, map.get(source));
        map.remove(source);
    }

    public Map<Position, Piece> getMap() {
        return new HashMap<>(map);
    }
}
