package janggi.domain.piece;

import janggi.domain.board.BoardInfo;
import janggi.domain.coordination.PalaceCoordination;
import janggi.domain.coordination.PalaceMovements;
import janggi.domain.piece.path.CandidatePath;
import janggi.domain.piece.path.generator.PathStrategy;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.List;

public abstract class PalacePiece extends Piece {

    protected PalacePiece(PieceType name, Side side,
                          PathStrategy pathStrategy, Score score) {
        super(name, side, pathStrategy, score);
    }

    @Override
    protected List<CandidatePath> createCandidatePaths(Point from, BoardInfo boardInfo) {
        List<CandidatePath> candidatePaths = super.createCandidatePaths(from, boardInfo);

        if (PalaceCoordination.isInRange(from)) {
            candidatePaths.addAll(createPalacePath(from));
        }

        return candidatePaths;
    }

    private List<CandidatePath> createPalacePath(Point from) {
        return PalaceMovements.getMovements(from).stream()
                .map(movement -> new CandidatePath(from,
                        pathStrategy.calculate(movement, from, PalaceCoordination::isInRange)))
                .toList();
    }
}
