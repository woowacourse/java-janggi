package domain.janggi.piece;

import domain.janggi.domain.Board;
import domain.janggi.domain.Color;
import domain.janggi.domain.Direction;
import domain.janggi.domain.Path;
import domain.janggi.domain.Position;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Horse extends Piece {

    public Horse(final Position position, final Color color, final Board board) {
        super(position, color, board);
    }

    @Override
    public Set<Position> getMovablePositions() {
        return generatePath().stream()
                .filter(path -> !board.anyMatchSameTeam(this, position.moveByPath(path)))
                .filter(path -> !containsCornerPiece(path.cornerPositions()))
                .map(Path::targetPosition)
                .collect(Collectors.toSet());
    }

    private List<Path> generatePath() {
        return Direction.getStraightDirection().stream()
                .flatMap(direction -> direction.nextCrossDirection().stream()
                        .map(cross -> List.of(direction, cross))
                        .filter(pathDirections -> position.canMove(pathDirections))
                        .map(pathDirections -> new Path(pathDirections, position))
                ).toList();
    }

    private boolean containsCornerPiece(List<Position> positions) {
        return positions.stream().anyMatch(board::isExists);
    }

    @Override
    public String getDisplayName() {
        return "마";
    }
}
