package janggi.board;

import janggi.team.TeamName;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private static final String INVALID_TURN = "턴이 올바르지 않습니다";
    private static final String INVALID_RANGE = "해당 좌표가 장기 판 범위를 벗어납니다";

    public void validateTeamTurn(TeamName oldTeamNameName, TeamName newTeamNameName) {
        if (oldTeamNameName.equals(newTeamNameName)) {
            throw new IllegalArgumentException(INVALID_TURN);
        }
    }

    public boolean validatePieceRange(Position destination) {
        boolean isValidRange =
                destination.x() >= 0 && destination.x() <= 8 && destination.y() >= 0 && destination.y() <= 9;
        if (!isValidRange) {
            throw new IllegalArgumentException(INVALID_RANGE);
        }
        return true;
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

        for (int i = 1; i < step; i++) {
            int x = startX + i * offsetX / step;
            int y = startY + i * offsetY / step;
            positionsOnPath.add(new Position(x, y));
        }
        return positionsOnPath;
    }
}
