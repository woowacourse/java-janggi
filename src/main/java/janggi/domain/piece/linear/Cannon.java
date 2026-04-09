package janggi.domain.piece.linear;

import janggi.domain.path.CandidatePath;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceName;
import janggi.domain.piece.Score;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cannon extends LinearPiece {
    private static final PieceName PIECE_NAME = PieceName.CANNON;
    private static final Score PIECE_SCORE = new Score(7);

    public Cannon(Side side) {
        super(PIECE_NAME, side, PIECE_SCORE);
    }


    @Override
    protected CandidatePath refinePath(CandidatePath candidatePath, Map<Point, Piece> piecesOnPaths) {
        List<Point> points = candidatePath.getPath();
        int count = 0;
        List<Point> cutPoints = new ArrayList<>();

        for (Point point : points) {
            if (piecesOnPaths.getOrDefault(point, null) instanceof Cannon) {
                break;
            }
            if (piecesOnPaths.containsKey(point)) {
                count++;
                if (count == 1) {
                    continue;
                }
            }
            if (count >= 1) {
                cutPoints.add(point);
            }
            if (count == 2) {
                break;
            }
        }
        return new CandidatePath(cutPoints);
    }
}
