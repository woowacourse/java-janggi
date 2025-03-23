package domain.piece;

import domain.Board;
import domain.Color;
import domain.Direction;
import domain.Path;
import domain.Position;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Horse extends Piece {

    public Horse(final Position position, final Color color, final Board board) {
        super(position, color, board);
    }

    @Override
    protected Set<Position> getMovablePositions() {
        return generatePath().stream()
                .filter(path -> !board.anyMatchSameTeam(this, position.move(path)))
                .filter(path -> !containsCornerPiece(path.cornerPositions()))
                .map(Path::targetPosition)
                .collect(Collectors.toSet());
    }

    private boolean containsCornerPiece(List<Position> positions) {
        return positions.stream().anyMatch(board::isExists);
    }

    private List<Path> generatePath() {
        return Direction.getStraightDirection().stream()
                .flatMap(direction -> direction.nextCrossDirection().stream()
                        .map(cross -> List.of(direction, cross))
                        .filter(pathDirections -> position.canMove(pathDirections))
                        .map(pathDirections -> new Path(pathDirections, position))
                ).toList();
    }

    @Override
    public String getDisplayName() {
        return "마";
    }
}
