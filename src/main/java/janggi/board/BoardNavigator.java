package janggi.board;

import janggi.position.Position;

import java.util.ArrayList;
import java.util.List;

public class BoardNavigator {
    // 시작과 끝 사이에 직선을 따라 이동하는 위치에 있는 좌표들의 모음 (시작 좌표, 끝 좌표 포함 안함)
    public List<Position> findPositionsOnPath(Position start, Position end) {
        List<Position> positionsOnPath = new ArrayList<>();

        int startX = start.getX();
        int startY = start.getY();
        int endX = end.getX();
        int endY = end.getY();

        int dx = endX - startX;
        int dy = endY - startY;

        int step = Math.max(Math.abs(dx), Math.abs(dy));

        for (int i = 1; i < step; i++) {
            int x = startX + i * dx / step;
            int y = startY + i * dy / step;
            positionsOnPath.add(new Position(x, y));
        }
        return positionsOnPath;
    }
}
