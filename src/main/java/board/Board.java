package board;

import piece.Piece;
import piece.TeamType;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> positionDatas;

    public Board(final Map<Position, Piece> map) {
        this.positionDatas = new HashMap<>(map);
    }

    public boolean existPieceByPosition(final Position existPosition) {
        return positionDatas.containsKey(existPosition);
    }

    public boolean equalsTypeByPositionAndPiece(final Position position, final Piece piece) {
        if (positionDatas.containsKey(position)) {
            final Piece target = positionDatas.get(position);
            return target.equalsType(piece);
        }
        return false;
    }

    public boolean equalsTeamTypeByPosition(final Position position, final TeamType teamType) {
        if (positionDatas.containsKey(position)) {
            final Piece piece = positionDatas.get(position);
            return piece.equalsTeamType(teamType);
        }
        return false;
    }

    public void updatePosition(final Position source, final Position destination, final TeamType teamType) {
        validatePositionAndTeam(source, teamType);
        validatePieceCanMove(source, destination, teamType);

        movePieceToDestination(source, destination);
    }

    private void validatePositionAndTeam(final Position source, final TeamType teamType) {
        if (!positionDatas.containsKey(source) || !positionDatas.get(source).equalsTeamType(teamType)) {
            throw new IllegalArgumentException("scr 좌표에 기물이 존재하지 않거나, 해당 팀의 기물이 아닙니다.");
        }
    }

    private void validatePieceCanMove(final Position source, final Position destination, final TeamType teamType) {
        final Piece piece = positionDatas.get(source);

        if (!piece.isAbleToMove(source, destination, this)) {
            throw new IllegalArgumentException("해당 기물은 해당 위치로 이동할 수 없습니다.");
        }
    }

    private void movePieceToDestination(final Position source, final Position destination) {
        positionDatas.put(destination, positionDatas.get(source));
        positionDatas.remove(source);
    }

    public Map<Position, Piece> getPositionDatas() {
        return new HashMap<>(positionDatas);
    }
}
