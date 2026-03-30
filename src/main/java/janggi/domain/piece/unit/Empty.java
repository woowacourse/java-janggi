package janggi.domain.piece.unit;

import janggi.domain.board.path.Path;
import janggi.domain.board.point.Point;
import janggi.domain.piece.Pattern;
import janggi.domain.piece.PieceName;
import janggi.domain.side.Side;
import java.util.List;
import java.util.Map;

public class Empty extends Piece {
    public static final Piece INSTANCE = new Empty();
    private static final PieceName NAME = PieceName.NONE;

    public Empty() {
        super(NAME, Side.NONE, null);
    }

    @Override
    protected boolean isValidPath(Path path, Map<Point, Piece> piecesOnPaths) {
        return false;
    }

    @Override
    public List<Pattern> patterns() {
        throw new IllegalStateException("Piece Empty 객체입니다.");
    }

    @Override
    protected Path refinePath(Path path, Map<Point, Piece> piecesOnPaths) {
        throw new IllegalStateException("Piece Empty 객체입니다.");
    }
}
