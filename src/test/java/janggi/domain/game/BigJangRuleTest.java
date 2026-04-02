package janggi.domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.unit.Advisor;
import janggi.domain.piece.unit.General;
import janggi.domain.piece.unit.Piece;
import janggi.domain.piece.unit.Soldier;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BigJangRuleTest {
    private static Rule RULE = new BigJangRule();

    @Test
    void getWinSide() {
    }

    @Nested
    class IsEnd {
        @Test
        @DisplayName("왕 중간에 아무 기물도 없다면 끝난다.")
        void isEnd() {
            Map<Point, Piece> pieces = Map.of(
                    new Point(1, 4), new General(Side.CHO),
                    new Point(8, 4), new General(Side.HAN),
                    new Point(1, 3), new Advisor(Side.CHO));
            assertThat(RULE.isEnd(pieces)).isEqualTo(true);
        }

        @Test
        @DisplayName("왕 중간에 기물이 있다면 끝나지 않는다.")
        void notEndWhenPiecesOnX() {
            Map<Point, Piece> pieces = Map.of(
                    new Point(1, 4), new General(Side.CHO),
                    new Point(8, 4), new General(Side.HAN),
                    new Point(3, 4), new Soldier(Side.CHO));
            assertThat(RULE.isEnd(pieces)).isEqualTo(false);
        }

        @Test
        @DisplayName("왕이 같은 선에 놓여있지 않다면 끝나지 않는다.")
        void notEndWhenGeneralNotInLineY() {
            Map<Point, Piece> pieces = Map.of(
                    new Point(1, 4), new General(Side.CHO),
                    new Point(8, 3), new General(Side.HAN));
            assertThat(RULE.isEnd(pieces)).isEqualTo(false);
        }

        @Test
        @DisplayName("왕이 없다면 해당하지 않는다.")
        void notEndWhenGeneralAbsence() {
            Map<Point, Piece> pieces = Map.of(
                    new Point(1, 4), new General(Side.CHO));
            assertThat(RULE.isEnd(pieces)).isEqualTo(false);

        }
    }
}
