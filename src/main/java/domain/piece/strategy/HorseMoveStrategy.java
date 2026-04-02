package domain.piece.strategy;

import domain.board.Position;
import domain.path.Direction;
import domain.path.PathGenerator;
import domain.path.PathInfo;

import java.util.List;

public class HorseMoveStrategy implements MoveStrategy {
    @Override
    public List<Position> getPath(Position departure, Position destination) {
        int deltaX = departure.calculateDeltaX(destination);
        int deltaY = departure.calculateDeltaY(destination);

        if (!isHorseMove(deltaX, deltaY)) {
            throw new IllegalArgumentException("마는 직진 후, 대각선 방향으로 한 칸 이동 가능합니다.");
        }

        Direction firstDirection = decidefirstDirection(deltaX, deltaY);
        Position node = departure.move(firstDirection.getDeltaX(), firstDirection.getDeltaY());
        Direction secondDirection = Direction.decideDirection(
                node.calculateDeltaX(destination),
                node.calculateDeltaY(destination)
        );

        return PathGenerator.generateComplexPath(departure, destination, List.of(firstDirection, secondDirection));
    }

    @Override
    public void validateBlockingPiece(List<PathInfo> pathInfos, Position destination) {
        boolean hasBlockingPiece = pathInfos.stream()
                .filter(path -> !path.position().equals(destination))
                .anyMatch(PathInfo::hasPiece);

        if (hasBlockingPiece) {
            throw new IllegalArgumentException("이동 경로에 있는 다른 기물을 뛰어넘을 수 없습니다.");
        }
    }

    private boolean isHorseMove(int deltaX, int deltaY) {
        int absoluteX = Math.abs(deltaX);
        int absoluteY = Math.abs(deltaY);

        return (absoluteX == 1 && absoluteY == 2) || (absoluteX == 2 && absoluteY == 1);
    }

    private Direction decidefirstDirection(int deltaX, int deltaY){
        if((Math.abs(deltaX) == 2)){
            return Direction.decideDirection(deltaX, 0);
        }
        return Direction.decideDirection(0, deltaY);
    }
}
