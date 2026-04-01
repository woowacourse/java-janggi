package domain.rule;

import domain.Side;
import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StepRule implements Rule {

    @Override
    public List<Position> getPossiblePositions(Side movingSide, Map<Position, Piece> pieceMap, List<Path> paths) {
        List<Position> result = new ArrayList<>();

        for (Path path : paths) {
            List<Position> pathPositions = path.getPositions();
            Position targetPosition = pathPositions.getFirst();
            Piece targetPiece = pieceMap.get(targetPosition);

            if (!targetPiece.isFriendly(movingSide)) {
                result.add(targetPosition);
            }
        }
        return result;
    }
}
