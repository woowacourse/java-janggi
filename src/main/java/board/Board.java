package board;

import java.util.HashMap;
import java.util.Map;
import piece.Piece;
import piece.Country;

public class Board {

    private final Map<Position, Piece> map;

    public Board(final Map<Position, Piece> map) {
        this.map = new HashMap<>(map);
    }

    public boolean existPieceByPosition(final Position existPosition) {
        return map.containsKey(existPosition);
    }

    public boolean equalsTypeByPositionAndPiece(final Position position, final Piece piece) {
        if (map.containsKey(position)) {
            final Piece target = map.get(position);
            return target.equalsType(piece);
        }
        return false;
    }

    public boolean equalsTeamTypeByPosition(final Position position, final Country country) {
        if (map.containsKey(position)) {
            final Piece piece = map.get(position);
            return piece.equalsTeamType(country);
        }
        return false;
    }

    public void updatePosition(final Position source, final Position destination, final Country country) {
        validatePositionAndTeam(source, country);
        validatePieceCanMove(source, destination, country);

        movePieceToDestination(source, destination);
    }

    private void validatePositionAndTeam(final Position source, final Country country) {
        if (!map.containsKey(source) || !map.get(source).equalsTeamType(country)) {
            throw new IllegalArgumentException("scr 좌표에 기물이 존재하지 않거나, 해당 팀의 기물이 아닙니다.");
        }
    }

    private void validatePieceCanMove(final Position source, final Position destination, final Country country) {
        final Piece piece = map.get(source);

        if (!piece.isAbleToMove(source, destination, this)) {
            throw new IllegalArgumentException("해당 기물은 해당 위치로 이동할 수 없습니다.");
        }
    }

    private void movePieceToDestination(final Position source, final Position destination) {
        map.put(destination, map.get(source));
        map.remove(source);
    }

    public Map<Position, Piece> getMap() {
        return new HashMap<>(map);
    }
}
