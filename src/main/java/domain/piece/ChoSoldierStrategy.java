package domain.piece;

import domain.Path;
import domain.board.Position;

import java.util.List;

public class ChoSoldierStrategy implements MoveStrategy {

    @Override
    public List<Position> getPathPositions(Position from, Position to) {
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();

        int straight = 1;

        boolean isMoveStraight = dx == 0 && dy == straight;
        boolean isMoveLeft = dx == -1 && dy == 0;
        boolean isMoveRight = dx == 1 && dy == 0;

        if (!(isMoveLeft || isMoveRight || isMoveStraight)) {
            throw new IllegalArgumentException("움직일 수 없는 위치입니다.");
        }
        return List.of(to);
    }

    @Override
    public void canMove(List<Path> paths, Position to) {
        for (Path path : paths) {
            if (path.position() != to) {
                throw new IllegalArgumentException("이동 경로에 기물이 존재하면 이동할 수 없습니다.");
            }
        }
    }
}
