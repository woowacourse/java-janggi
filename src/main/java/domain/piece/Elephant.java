package domain.piece;

import domain.Board;
import domain.PieceType;
import domain.Position;
import domain.Team;

public class Elephant extends Piece {
    public Elephant(Team team) {
        super(team, PieceType.ELEPHANT);
    }

    /**
     * 1. 직진 1칸 + 대각선 2칸만 이동 가능 2. 도착지에 같은 팀이 존재하는 경우 이동 불가 3. 도착지랑 출발지 사이에 말이 존재하지 않는 경우 이동 가능 4. 이외는 이동 가능
     */
    @Override
    boolean canMove(Position from, Position to, Board board) {
        // 1. 직진 1칸 + 대각선 2칸만 이동 가능
        if (!isCorrectMoveDistanceAndDirection(from, to)) {
            return false;
        }

        // 2. 도착지에 같은 팀이 존재하는 경우 이동 불가
        if (board.hasSameTeamOn(to, this)) {
            return false;
        }

        // 3. 도착지랑 출발지 사이에 말이 존재하지 않는 경우 이동 가능
        if (hasPieceInPath(from, to, board)) {
            return false;
        }

        return true;
    }

    private boolean isCorrectMoveDistanceAndDirection(Position from, Position to) {
        int columnDistance = Math.abs(from.columnDistanceTo(to));
        int rowDistance = Math.abs(from.rowDistanceTo(to));

        return (columnDistance == 2 && rowDistance == 3) || (columnDistance == 3 && rowDistance == 2);
    }

    private boolean hasPieceInPath(Position from, Position to, Board board) {
        int rowDistance = from.rowDistanceTo(to);
        int columnDistance = from.columnDistanceTo(to);

        // 0보다 크면 1, 0보다 작으면 -1
        int row = Integer.signum(rowDistance);
        int col = Integer.signum(columnDistance);

        Position duff = Position.from(row, col);

        for (int i = 0; i < 2; i++) {
            Position pathPosition = to.diff(duff);
            if (!board.isEmpty(pathPosition)) {
                return true;
            }
        }
        return false;
    }
}
