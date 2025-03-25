package janggi.board;

import janggi.team.Team;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private static final String INVALID_TURN = "턴이 올바르지 않습니다";
    private static final String INVALID_RANGE = "해당 좌표가 장기 판 범위를 벗어납니다";

    private static final int BOARD_AXIS_MIN = 0;
    private static final int BOARD_X_AXIS_MAX = 8;
    private static final int BOARD_Y_AXIS_MAX = 9;
    private static final int NEXT_INDEX_FROM_CURRENT = 1;

    public void validateTeamTurn(Team oldTeam, Team newTeam) {
        if (oldTeam.equals(newTeam)) {
            throw new IllegalArgumentException(INVALID_TURN);
        }
    }

    public void validatePieceRange(Position destination) {
        boolean isValidRange = destination.x() >= BOARD_AXIS_MIN && destination.x() <= BOARD_X_AXIS_MAX
                && destination.y() >= BOARD_AXIS_MIN && destination.y() <= BOARD_Y_AXIS_MAX;
        if (!isValidRange) {
            throw new IllegalArgumentException(INVALID_RANGE);
        }
    }

    public List<Position> findPositionsOnPath(Position start, Position end) {
        List<Position> positionsOnPath = new ArrayList<>();

        int startX = start.x();
        int startY = start.y();
        int endX = end.x();
        int endY = end.y();

        int offsetX = endX - startX;
        int offsetY = endY - startY;
        int step = Math.max(Math.abs(offsetX), Math.abs(offsetY));

        for (int i = NEXT_INDEX_FROM_CURRENT; i < step; i++) {
            int x = startX + i * offsetX / step;
            int y = startY + i * offsetY / step;
            positionsOnPath.add(new Position(x, y));
        }
        return positionsOnPath;
    }
}
