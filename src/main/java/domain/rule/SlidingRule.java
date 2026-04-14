package domain.rule;

import domain.Side;
import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SlidingRule implements Rule {

    @Override
    public List<Position> getPossiblePositions(Side movingSide, Map<Position, Piece> pathPieces, List<Path> paths) {
        List<Position> result = new ArrayList<>();
        for (Path path : paths) {
            result.addAll(slideAlong(movingSide, pathPieces, path));
        }
        return result;
    }

    private List<Position> slideAlong(Side movingSide, Map<Position, Piece> pathPieces, Path path) {
        List<Position> result = new ArrayList<>();
        for (Position position : path.getPositions()) {
            Piece piece = pathPieces.get(position);
            if (piece.isEmpty()) {
                result.add(position);
                continue;
            }
            if (piece.isFriendly(movingSide)) {
                break;
            }
            result.add(position);
            break;
        }
        return result;
    }
}
