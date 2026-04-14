package domain.rule;

import domain.Side;
import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.piece.Cannon;
import domain.piece.Piece;

import domain.piece.PieceType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CannonRule implements Rule {

    @Override
    public List<Position> getPossiblePositions(Side movingSide, Map<Position, Piece> pathPieces, List<Path> paths) {
        List<Position> result = new ArrayList<>();
        for (Path path : paths) {
            result.addAll(cannonMoves(movingSide, pathPieces, path));
        }
        return result;
    }

    private List<Position> cannonMoves(Side movingSide, Map<Position, Piece> pathPieces, Path path) {
        List<Position> positions = path.getPositions();
        int pivotIndex = findPivotIndex(positions, pathPieces);
        if (pivotIndex == -1 || pathPieces.get(positions.get(pivotIndex)) instanceof Cannon) {
            return List.of();
        }
        return landingPositions(movingSide, pathPieces, positions, pivotIndex);
    }

    private int findPivotIndex(List<Position> positions, Map<Position, Piece> pathPieces) {
        for (int i = 0; i < positions.size(); i++) {
            if (!pathPieces.get(positions.get(i)).isEmpty()) {
                return i;
            }
        }
        return -1;
    }

    private List<Position> landingPositions(Side movingSide, Map<Position, Piece> pathPieces, List<Position> positions,
                                            int pivotIndex) {
        List<Position> result = new ArrayList<>();
        for (int i = pivotIndex + 1; i < positions.size(); i++) {
            Position pos = positions.get(i);
            Piece piece = pathPieces.get(pos);
            if (isCannon(piece) || piece.isFriendly(movingSide)) {
                break;
            }
            result.add(pos);
            if (!piece.isEmpty()) {
                break;
            }
        }
        return result;
    }

    private boolean isCannon(Piece piece) {
        return piece.getType() == PieceType.CANNON;
    }
}
