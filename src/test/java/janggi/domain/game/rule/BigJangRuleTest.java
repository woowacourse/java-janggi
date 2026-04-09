package janggi.domain.game.rule;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.Piece;
import janggi.domain.piece.single.Advisor;
import janggi.domain.piece.stepped.Elephant;
import janggi.domain.piece.single.General;
import janggi.domain.piece.stepped.Horse;
import janggi.domain.piece.single.Soldier;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BigJangRuleTest {
    private static final Rule RULE = new BigJangRule();

    @Nested
    class GetWinSide {
        @Test
        @DisplayName("초 진영이 어드밴티지(1.5)때문에 이긴다")
        void choWin() {
            Map<Point, Piece> pieces = Map.of(
                    new Point(1, 4), new General(Side.CHO),
                    new Point(8, 4), new General(Side.HAN),
                    new Point(0, 1), new Horse(Side.CHO),       //5점
                    new Point(9, 2), new Elephant(Side.HAN),    //3점
                    new Point(9, 6), new Elephant(Side.HAN));   //3점
            assertThat(RULE.getWinSide(pieces)).isEqualTo(Side.CHO);
        }

        @Test
        @DisplayName("한 진영이 점수가 더 높다면 이긴다.")
        void hanWin() {
            Map<Point, Piece> pieces = Map.of(
                    new Point(1, 4), new General(Side.CHO),
                    new Point(8, 4), new General(Side.HAN),
                    new Point(0, 1), new Horse(Side.HAN),       //5점
                    new Point(9, 2), new Elephant(Side.CHO)     //3점
            );
            assertThat(RULE.getWinSide(pieces)).isEqualTo(Side.HAN);
        }
    }

    @Nested
    class IsEnd {
        @Test
        @DisplayName("왕 중간에 아무 기물도 없고, 다음차례에 왕이 움직이지 않으면 게임이 끝난다.")
        void isEnd() {
            Map<Point, Piece> pieces = Map.of(
                    new Point(1, 4), new General(Side.CHO),
                    new Point(8, 4), new General(Side.HAN),
                    new Point(1, 3), new Advisor(Side.CHO),
                    new Point(0, 4), new Advisor(Side.CHO));
            assertThat(RULE.isEnd(pieces)).isFalse();
            assertThat(RULE.isEnd(pieces)).isTrue();
        }

        @Test
        @DisplayName("왕이 계속 움직인다면 게임이 끝나지 않는다.")
        void isEndWhen() {
            Map<Point, Piece> pieces = Map.of(
                    new Point(1, 4), new General(Side.CHO),
                    new Point(8, 4), new General(Side.HAN)
            );
            Map<Point, Piece> movePieces = Map.of(
                    new Point(1, 3), new General(Side.CHO),
                    new Point(8, 4), new General(Side.HAN)
            );

            assertThat(RULE.isEnd(pieces)).isFalse();
            assertThat(RULE.isEnd(movePieces)).isFalse();
            assertThat(RULE.isEnd(pieces)).isFalse();
            assertThat(RULE.isEnd(movePieces)).isFalse();
            assertThat(RULE.isEnd(pieces)).isFalse();
            assertThat(RULE.isEnd(pieces)).isTrue();
        }

        @Test
        @DisplayName("왕 중간에 기물이 있다면 끝나지 않는다.")
        void notEndWhenPiecesOnX() {
            Map<Point, Piece> pieces = Map.of(
                    new Point(1, 4), new General(Side.CHO),
                    new Point(8, 4), new General(Side.HAN),
                    new Point(3, 4), new Soldier(Side.CHO));
            assertThat(RULE.isEnd(pieces)).isFalse();
        }

        @Test
        @DisplayName("왕이 같은 선에 놓여있지 않다면 끝나지 않는다.")
        void notEndWhenGeneralNotInLineY() {
            Map<Point, Piece> pieces = Map.of(
                    new Point(1, 4), new General(Side.CHO),
                    new Point(8, 3), new General(Side.HAN));
            assertThat(RULE.isEnd(pieces)).isFalse();
        }

        @Test
        @DisplayName("왕이 없다면 해당하지 않는다.")
        void notEndWhenGeneralAbsence() {
            Map<Point, Piece> pieces = Map.of(
                    new Point(1, 4), new General(Side.CHO));
            assertThat(RULE.isEnd(pieces)).isFalse();
        }
    }
}
