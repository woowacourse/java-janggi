package janggi.board;

import janggi.team.Team;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private static final String INVALID_TURN = "턴이 올바르지 않습니다";
    private static final int NEXT_INDEX_FROM_CURRENT = 1;

    public void validateTeamTurn(Team oldTeam, Team newTeam) {
        if (oldTeam.equals(newTeam)) {
            throw new IllegalArgumentException(INVALID_TURN);
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
