package domain.piece.category;

import domain.MoveInfos;
import domain.direction.Directions;
import domain.direction.PieceDirection;
import domain.piece.Piece;
import domain.piece.validation.DiagonalPalacePathValidator;
import domain.piece.validation.IntermediatePieceCountValidator;
import domain.piece.validation.MoveValidation;
import domain.spatial.Position;
import java.util.List;

public class Chariot extends Piece {

    private static final PieceCategory CATEGORY = PieceCategory.CHARIOT;

    public Chariot(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public List<Position> getPaths(final Position target) {
        List<Position> paths = directions.getPaths(position, target);
        if (position.isWithinPalace()) {
            paths = PieceDirection.REPEATED_DIAGONAL.get().getPaths(position, target);
        }
        return paths;
    }

    @Override
    public PieceCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public Chariot move(final Position target, final MoveInfos moveInfos) {
        validateMove(moveInfos);
        return new Chariot(target, directions);
    }

    private void validateMove(final MoveInfos moveInfos) {
        for (MoveValidation validation : validations) {
            validation.validate(moveInfos);
        }
    }

    private final List<MoveValidation> validations = List.of(
            new DiagonalPalacePathValidator(),
            new IntermediatePieceCountValidator()
    );
}
