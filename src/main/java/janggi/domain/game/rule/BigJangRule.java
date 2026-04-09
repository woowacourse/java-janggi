package janggi.domain.game.rule;

import janggi.domain.piece.Piece;
import janggi.domain.piece.Score;
import janggi.domain.piece.single.General;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.Map;
import java.util.Map.Entry;

public class BigJangRule implements Rule {
    private static final Piece CHO_GENERAL = new General(Side.CHO);
    private static final Piece HAN_GENERAL = new General(Side.HAN);
    private static final Score CHO_ADVANTAGE = new Score(1.5);
    private boolean otherPlayerIsEnd = false;

    @Override
    public boolean isEnd(Map<Point, Piece> pieces) {
        if (!pieces.containsValue(CHO_GENERAL) || !pieces.containsValue(HAN_GENERAL)) {
            otherPlayerIsEnd = false;
            return false;
        }
        Point choGeneralPoint = findPoint(pieces, CHO_GENERAL);
        Point hanGeneralPoint = findPoint(pieces, HAN_GENERAL);

        if (choGeneralPoint.y() == hanGeneralPoint.y()
                && isThereNonePieceInCol(pieces, choGeneralPoint, hanGeneralPoint) && otherPlayerIsEnd) {
            return true;
        }
        if (choGeneralPoint.y() == hanGeneralPoint.y()
                && isThereNonePieceInCol(pieces, choGeneralPoint, hanGeneralPoint)) {
            otherPlayerIsEnd = true;
            return false;
        }
        otherPlayerIsEnd = false;
        return false;
    }

    @Override
    public Side getWinSide(Map<Point, Piece> pieces) {
        Score choScore = getScore(pieces, Side.CHO).add(CHO_ADVANTAGE);
        Score hanScore = getScore(pieces, Side.HAN);

        if (choScore.isBiggerOrSame(hanScore)) {
            return Side.CHO;
        }
        return Side.HAN;
    }

    private boolean isThereNonePieceInCol(Map<Point, Piece> pieces, Point choGeneralPoint, Point hanGeneralPoint) {
        return pieces.keySet().stream()
                .filter(point -> point.y() == hanGeneralPoint.y())
                .noneMatch(point -> point.x() > choGeneralPoint.x() && point.x() < hanGeneralPoint.x());
    }

    private Score getScore(Map<Point, Piece> pieces, Side side) {
        return pieces.values().stream()
                .filter(piece -> !piece.isDifferentSide(side))
                .map(Piece::getScore)
                .reduce(Score::add)
                .orElse(Score.NONE);
    }

    private Point findPoint(Map<Point, Piece> pieces, Piece piece) {
        return pieces.entrySet().stream()
                .filter(e -> e.getValue().equals(piece))
                .map(Entry::getKey)
                .findAny()
                .get();

    }
}
