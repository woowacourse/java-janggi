package domain.piece;

import domain.Board;
import domain.Color;
import domain.Direction;
import domain.Path;
import domain.Position;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Elephant extends Piece {

    public Elephant(final Position position, final Color color, final Board board) {
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

    private List<Path> generatePath() {
        return Direction.getStraightDirection().stream()
                .flatMap(direction -> direction.nextCrossDirection().stream()
                        .map(cross -> List.of(direction, cross, cross))
                        .filter(pathDirections -> position.canMove(pathDirections))
                        .map(pathDirections -> new Path(pathDirections, position))
                ).toList();
    }

    private boolean containsCornerPiece(List<Position> positions) {
        return positions.stream().anyMatch(board::isExists);
    }

    @Override
    public String getDisplayName() {
        return "상";
    }
}
