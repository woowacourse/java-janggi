package domain.piece.category;

import domain.MoveInfos;
import domain.direction.Directions;
import domain.direction.PieceDirection;
import domain.piece.Piece;
import domain.spatial.Position;
import java.util.List;

public class Soldier extends Piece {

    private static final PieceCategory CATEGORY = PieceCategory.SOLDIER;

    public Soldier(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public List<Position> getPaths(final Position target) {
        List<Position> paths = directions.getPaths(position, target);
        if (position.isWithinPalace()) {
            paths = getPalacePaths(target, paths);
        }
        validatePaths(paths);
        if (position.isWithinPalace()) {
            validateDiagonalPaths(paths);
        }
        return paths;
    }

    @Override
    public Soldier move(final Position target, final MoveInfos moveInfos) {
        return new Soldier(target, directions);
    }

    @Override
    public PieceCategory getCategory() {
        return CATEGORY;
    }

    private List<Position> getPalacePaths(final Position target, final List<Position> paths) {
        if (directions == PieceDirection.HAN_SOLDIER.get()) {
            return PieceDirection.HAN_DIAGONAL.get().getPaths(position, target);
        }
        if (directions == PieceDirection.CHO_SOLDIER.get()) {
            return PieceDirection.CHO_DIAGONAL.get().getPaths(position, target);
        }
        return paths;
    }
}
