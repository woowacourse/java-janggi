package domain.movestrategy;

import domain.piece.Piece;
import domain.piece.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CannonMoveStrategy extends FindFirstObstacleMoveStrategy {

    @Override
    public List<Position> calculateMovablePositions(final Position from, final Map<Position, Piece> pieces) {
        List<Position> movable = new ArrayList<>();

        for (final Direction direction : Direction.getVerticalAndHorizontal()) {
            movable.addAll(calculateMovableByDirection(from, pieces, direction));
        }

        return movable;
    }

    private List<Position> calculateMovableByDirection(
            final Position from,
            final Map<Position, Piece> pieces,
            final Direction direction
    ) {
        List<Position> movable = new ArrayList<>();

        boolean metPiece = false;
        Position delta = direction.getDelta();
        for (Position current = from.move(delta); inBoard(current); current = current.move(delta)) {
            // 빈 칸
            if (!pieces.containsKey(current)) {
                if (metPiece) {
                    movable.add(current);
                }
                continue;
            }

            // 기물 칸 - 두 번째 기물 (아군 여부는 이후 처리)
            if (metPiece) {
                if (!pieces.get(current).isCannon()) {
                    movable.add(current);
                }
                break;
            }

            // 첫 번째 기물
            if (pieces.get(current).isCannon()) {
                break; // 처음 만난 기물이 포면 스킵
            }

            movable.add(current);
            metPiece = true;
        }

        return movable;
    }

    /*
    @Override
    public List<Position> calculateMovablePositions(final Position from, final Map<Position, Piece> pieces) {
        List<Position> movable = new ArrayList<>();

        for (final Direction direction : Direction.getVerticalAndHorizontal()) {
            List<Position> positions = new ArrayList<>();
            for (Position currentPosition = from.move(direction.getDelta()); inBoard(currentPosition);
                 currentPosition = currentPosition.move(direction.getDelta())) {
                positions.add(currentPosition);
            }

            Optional<Piece> firstObstacle = findFirstObstacle(positions, from, pieces);

            if (firstObstacle.isEmpty()) {
                continue;
            }

            if (firstObstacle.get().isCannon()) {
                continue;

            }
            List<Position> positionsAfterFirstObstacle = getPositionsAfterObstacle(positions, from, pieces);

            if (positionsAfterFirstObstacle.isEmpty()) {
                continue;
            }

            List<Position> positionsBetweenFirstAndSecondObstacle = getPositionsBeforeObstacle(
                    positionsAfterFirstObstacle,
                    positionsAfterFirstObstacle.getFirst(), pieces);

            Position lastPosition = positionsBetweenFirstAndSecondObstacle.getLast();
            if (pieces.containsKey(lastPosition)) {
                Piece lastPiece = pieces.get(lastPosition);
                if (lastPiece.isCannon() || !lastPiece.isOpposite(pieces.get(from))) {
                    positionsBetweenFirstAndSecondObstacle.removeLast();
                }
            }

            movable.addAll(positionsBetweenFirstAndSecondObstacle);
        }

        return movable.stream()
                .filter(this::inBoard)
                .toList();
    }
    */

//    private static boolean inBoard(final Position current) {
//        return (current.column() >= Board.MIN_COLUMN_RANGE && current.column() <= Board.MAX_COLUMN_RANGE)
//                && (current.row() >= Board.MIN_ROW_RANGE && current.row() <= Board.MAX_ROW_RANGE);
//    }


    /*
    // TODO: 말 하나를 항상 넘어서 이동 가능, 첫 번째 조우하는 말과 두 번째 조우하는 말 사이만 이동 가능, 이동할 위치에 아군이 있으면 이동 불가, 포는 포를 넘거나 잡을 수 없음
    @Override
    public List<Position> calculateMovablePositions(final Position from, final Map<Position, Piece> pieces) {
        List<Position> movable = new ArrayList<>();

        // 장애물 저장
        Piece firstPiece = null;
        for (int column = from.column() + 1; column < Board.MAX_COLUMN_RANGE; column++) {
            Position currentPosition = Position.of(column, from.row()); // 현재칸
            if (pieces.containsKey(currentPosition)) { // 현재 위치에 기물 있는지
                if (firstPiece == null) { // 처음 만나는 기물이면 첫 번째에 저장
                    firstPiece = pieces.get(currentPosition);
                    if (firstPiece.isCannon()) {
                        break; // 처음 만난 기물이 포면 스킵
                    }
                    continue;
                }

                movable.add(currentPosition); // 두 번째 기물까지 이동가능 담고 종료
                break;
            }

            if (firstPiece != null) { // 한 번 이상 장애물 만난 상태이면 이동 가능
                movable.add(currentPosition);
            }
        }

        return movable;
    }
    */
}
