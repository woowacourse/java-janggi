package domain.rule;

import domain.Side;
import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LeapRule implements Rule {

    @Override
    public List<Position> getPossiblePositions(Side movingSide, Map<Position, Piece> pathPieces, List<Path> paths) {
        List<Position> result = new ArrayList<>();
        for (Path path : paths) {
            List<Position> positions = path.getPositions();
            if (positions.isEmpty() || !isWaypointClear(positions, pathPieces)) {
                continue;
            }
            Position destination = positions.getLast();
            if (!pathPieces.get(destination).isFriendly(movingSide)) {
                result.add(destination);
            }
        }
        return result;
    }

    private boolean isWaypointClear(List<Position> positions, Map<Position, Piece> pathPieces) {
        for (int i = 0; i < positions.size() - 1; i++) {
            if (!pathPieces.get(positions.get(i)).isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
