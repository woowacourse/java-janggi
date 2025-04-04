package domain.piece.category;

import domain.MoveInfos;
import domain.direction.Directions;
import domain.piece.Piece;
import domain.piece.validation.IntermediatePieceCountValidator;
import domain.piece.validation.MoveValidation;
import domain.spatial.Position;
import java.util.List;

public class Horse extends Piece {

    private static final PieceCategory CATEGORY = PieceCategory.HORSE;

    public Horse(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public List<Position> getPaths(final Position target) {
        List<Position> paths = directions.getPaths(position, target);
        validatePaths(paths);
        return paths;
    }

    @Override
    public PieceCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public Horse move(final Position target, final MoveInfos moveInfos) {
        validateMove(moveInfos);
        return new Horse(target, directions);
    }

    private void validateMove(final MoveInfos moveInfos) {
        for (MoveValidation validation : validations) {
            validation.validate(moveInfos);
        }
    }

    private final List<MoveValidation> validations = List.of(
            new IntermediatePieceCountValidator()
    );
}
