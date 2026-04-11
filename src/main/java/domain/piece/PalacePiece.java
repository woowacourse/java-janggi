package domain.piece;

import domain.Board;
import domain.Palace;
import domain.PieceType;
import domain.Position;
import domain.Team;

public abstract class PalacePiece extends Piece {
    public PalacePiece(Team team, PieceType type) {
        super(team, type);
    }

    /**
     * 궁성 지역에서만 움직일 수 있는 기물들의 움직임을 판단한다.
     *
     * 궁성 지역에서만 이동 가능한 기물 : 궁, 사
     * 이동 가능한 조건
     * 1. 목적지가 궁성 지역 내부여야 한다
     * 2. 이동 규칙을 준수해야 한다
     *      - 궁성 가운데서 이동 할 경우 궁성 내 모든 지역으로 이동할 수 있다
     *      - 궁성의 코너에서 이동할 경우 앞뒤좌우로 한칸 or 궁성의 가운데로 이동할 수 있다
     *      - 그외는 앞뒤좌우 한칸 이동 가능하다
     * 3. 목적지에 같은 팀 기물이 존재하면 안된다
     *
     * @param from 이동을 원하는 기물의 위치
     * @param to 기물의 목적지
     * @param board 현재 진행중인 게임판
     * @return 이동 가능 여부
     */
    @Override
    public boolean canMove(Position from, Position to, Board board) {

        if (!Palace.isPalace(to)) {
            return false;
        }

        if (!isCorrectMoveDistanceAndDirection(from, to, board)) {
            return false;
        }

        if (isEmptySpace(to, board)) {
            return true;
        }

        return !board.hasSameTeamOn(to, this);
    }

    private boolean isEmptySpace(Position to, Board board) {
        return board.isEmpty(to);
    }

    private boolean isCorrectMoveDistanceAndDirection(Position from, Position to, Board board) {
        // 센터에서 이동 가능
        if (Palace.isPalaceCenter(from)) {
            return Palace.isPalace(to);
        }

        // 궁성 코너에서 이동 가능
        if (Palace.isPalaceCorner(from)) {
            return Palace.isPalaceCenter(to) || canMoveStraight(to, from);
        }

        return canMoveStraight(to, from);
    }

    private boolean canMoveStraight(Position from, Position to) {
        return (Math.abs(from.columnDistanceTo(to)) == 1 && from.rowDistanceTo(to) == 0)
                || (Math.abs(from.rowDistanceTo(to)) == 1 && from.columnDistanceTo(to) == 0);
    }
}
