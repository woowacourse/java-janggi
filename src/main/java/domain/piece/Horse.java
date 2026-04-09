package domain.piece;

import domain.board.MoveContext;
import domain.coordination.Coordination;
import domain.coordination.MoveDelta;
import domain.piece.error.PieceException;
import java.util.List;

public class Horse extends Piece {

    private static final List<MoveDelta> MOVABLE_ABSOLUTE_LOCATION = List.of(
            new MoveDelta(1, 2),
            new MoveDelta(2, 1)
    );
    private static final int MAX_STEP = 2;


    public Horse(Team team) {
        super(team);
    }

    @Override
    public void validateRule(MoveContext moveContext) {
        Coordination from = moveContext.from();
        Coordination to = moveContext.to();
        MoveDelta different = MoveDelta.between(from, to);
        MoveDelta absDifferent = different.absolute();

        validateLocation(absDifferent);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.HORSE;
    }

    private void validateLocation(MoveDelta absDifferent) {
        if (!MOVABLE_ABSOLUTE_LOCATION.contains(absDifferent)) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }

    @Override
    public List<Coordination> resolvePath(MoveContext moveContext) {
        Coordination from = moveContext.from();
        Coordination to = moveContext.to();
        MoveDelta different = MoveDelta.between(from, to);
        MoveDelta absDifferent = different.absolute();
        int colDifferent = different.deltaColumn();
        int rowDifferent = different.deltaRow();
        int absColumnDifferent = absDifferent.deltaColumn();
        int absRowDifferent = absDifferent.deltaRow();

        Coordination intermediateColumn = from.plus(colDifferent / MAX_STEP, 0);
        Coordination intermediateRow = from.plus(0, rowDifferent / MAX_STEP);

        return resolvePath(absColumnDifferent, intermediateColumn, absRowDifferent, intermediateRow);
    }

    private List<Coordination> resolvePath(int absColumnDifferent,
                                           Coordination intermediateColumn,
                                           int absRowDifferent,
                                           Coordination intermediateRow) {
        if (absColumnDifferent == MAX_STEP) {
            return List.of(intermediateColumn);
        }
        if (absRowDifferent == MAX_STEP) {
            return List.of(intermediateRow);
        }
        return List.of();
    }

    @Override
    public void validatePath(List<Piece> piecesOnPath) {
        if (!piecesOnPath.isEmpty()) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }
}
