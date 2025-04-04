package domain.piece.category;

import domain.MoveInfos;
import domain.direction.Directions;
import domain.direction.PieceDirection;
import domain.piece.Piece;
import domain.piece.validation.CannonInIntermediatePathValidator;
import domain.piece.validation.CannonIntermediatePieceCountValidator;
import domain.piece.validation.DiagonalPalacePathValidator;
import domain.piece.validation.MoveValidation;
import domain.piece.validation.TargetPieceIsCannonValidator;
import domain.spatial.Position;
import java.util.List;

public class Cannon extends Piece {

    private static final PieceCategory CATEGORY = PieceCategory.CANNON;

    public Cannon(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public List<Position> getPaths(final Position target) {
        List<Position> paths = directions.getPaths(position, target);
        if (position.isWithinPalace()) {
            return PieceDirection.REPEATED_DIAGONAL.get().getPaths(position, target);
        }
        return paths;
    }

    @Override
    public PieceCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public Cannon move(final Position target, final MoveInfos moveInfos) {
        validateMove(moveInfos);
        return new Cannon(target, directions);
    }

    private void validateMove(final MoveInfos moveInfos) {
        for (MoveValidation validation : validations) {
            validation.validate(moveInfos);
        }
    }

    private final List<MoveValidation> validations = List.of(
            new DiagonalPalacePathValidator(),
            new CannonIntermediatePieceCountValidator(),
            new TargetPieceIsCannonValidator(),
            new CannonInIntermediatePathValidator()
    );
}
