package domain.piece;

import domain.Path;
import domain.board.Position;

import java.util.List;

public class GuardStrategy implements MoveStrategy {
    @Override
    public List<Position> getPathPositions(Position from, Position to) {

        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();

        boolean isMoveUp = dx == 0 && dy == 1;
        boolean isMoveLeft = dx == -1 && dy == 0;
        boolean isMoveRight = dx == 1 && dy == 0;
        boolean isMoveDown = dx == 0 && dy == -1;

        if(!(isMoveLeft || isMoveRight || isMoveUp || isMoveDown)) {
            throw new IllegalArgumentException("움직일 수 없는 위치입니다.");
        }
        return List.of();
    }

    @Override
    public void canMove(List<Path> paths, Piece to) {
        if (!paths.isEmpty()) {
            throw new IllegalArgumentException("이동 경로에 기물이 존재하면 이동할 수 없습니다.");
        }
    }
}
