package janggi.domain.piece.fixed;

import janggi.domain.path.generator.FixedPathStrategy;
import janggi.domain.path.generator.PathStrategy;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceName;
import janggi.domain.piece.Score;
import janggi.domain.side.Side;

public abstract class FixedPiece extends Piece {
    private static final PathStrategy DEFAULT_STRATEGY = new FixedPathStrategy();

    protected FixedPiece(PieceName name, Side side, Score score) {
        super(name, side, DEFAULT_STRATEGY, score);
    }
}
