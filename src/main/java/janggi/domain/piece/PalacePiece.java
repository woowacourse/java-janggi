package janggi.domain.piece;

import janggi.domain.board.BoardInfo;
import janggi.domain.coordination.PalaceCoordination;
import janggi.domain.coordination.PalaceMovements;
import janggi.domain.piece.path.CandidatePath;
import janggi.domain.piece.path.generator.PathStrategy;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.ArrayList;
import java.util.List;

public abstract class PalacePiece extends Piece {

    protected PalacePiece(PieceType name, Side side,
                          PathStrategy pathStrategy, Score score) {
        super(name, side, pathStrategy, score);
    }

    @Override
    protected List<CandidatePath> createCandidatePaths(Point from, BoardInfo boardInfo) {
        List<CandidatePath> paths = new ArrayList<>(createBoardPath(from, boardInfo));

        if (PalaceCoordination.isInRange(from)) {
            paths.addAll(createPalacePath(from));
        }

        return paths;
    }

    private List<CandidatePath> createPalacePath(Point from) {
        return PalaceMovements.getMovements(from).stream()
                .map(movement -> new CandidatePath(from,
                        pathStrategy.calculate(movement, from, PalaceCoordination::isInRange)))
                .toList();
    }

    private List<CandidatePath> createBoardPath(Point from, BoardInfo boardInfo) {
        return getMovements().stream()
                .map(movement -> new CandidatePath(from, pathStrategy.calculate(movement, from, boardInfo::isInRange)))
                .toList();
    }


}
