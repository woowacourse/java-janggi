package janggi.domain.game;

import janggi.domain.piece.Score;
import janggi.domain.piece.unit.General;
import janggi.domain.piece.unit.Piece;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.Map;
import java.util.Map.Entry;

public class BigJangRule implements Rule {
    private static final General CHO_GENERAL = new General(Side.CHO);
    private static final General HAN_GENERAL = new General(Side.HAN);
    private static final Score CHO_ADVANTAGE = new Score(1);
    private static final int GENERAL_LINE_SIZE = 2;

    @Override
    public boolean isEnd(Map<Point, Piece> pieces) {
        if (!pieces.containsValue(CHO_GENERAL) || !pieces.containsValue(HAN_GENERAL)) {
            return false;
        }
        Point choGeneralPoint = findPoint(pieces, CHO_GENERAL);
        Point hanGeneralPoint = findPoint(pieces, HAN_GENERAL);

        return choGeneralPoint.y() == hanGeneralPoint.y()
                && isThereNonePieceInCol(pieces, choGeneralPoint.y());
    }

    @Override
    public Side getWinSide(Map<Point, Piece> pieces) {
        Score choScore = getScore(pieces, Side.CHO).add(CHO_ADVANTAGE);
        Score hanScore = getScore(pieces, Side.HAN);

        if (choScore.isBigger(hanScore)) {
            return Side.CHO;
        }
        return Side.HAN;
    }

    private Score getScore(Map<Point, Piece> pieces, Side side) {
        return pieces.values().stream()
                .filter(piece -> !piece.isDifferentSide(side))
                .map(Piece::getScore)
                .reduce(Score::add)
                .orElse(Score.NONE);
    }

    private boolean isThereNonePieceInCol(Map<Point, Piece> pieces, int y) {
        return pieces.keySet().stream()
                .filter(point -> point.y() == y)
                .toList()
                .size() == GENERAL_LINE_SIZE;

    }

    private Point findPoint(Map<Point, Piece> pieces, Piece piece) {
        return pieces.entrySet().stream()
                .filter(e -> e.getValue().equals(piece))
                .map(Entry::getKey)
                .findAny()
                .get();

    }
}
