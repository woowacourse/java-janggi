package janggi.game;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.movement.direction.Direction;
import janggi.piece.Byeong;
import janggi.point.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PalaceTest {
    @Nested
    @DisplayName("이동 확인 테스트")
    class MoveTest {
        @Test
        @DisplayName("초나라 병이 궁성 안에서 이동중인지 확인한다.")
        void checkByeongTeamChoInPalace() {
            Byeong byeong = new Byeong(Team.CHO, new Point(7, 4));
            Point targetPoint = new Point(8, 4);

            assertThat(Palace.movesInPalace(byeong.getPoint(), targetPoint)).isTrue();
        }

        @Test
        @DisplayName("한나라 병이 궁성 안에서 이동중인지 확인한다.")
        void checkByeongTeamHanInPalace() {
            Byeong byeong = new Byeong(Team.HAN, new Point(2, 3));
            Point targetPoint = new Point(1, 4);

            assertThat(Palace.movesInPalace(byeong.getPoint(), targetPoint)).isTrue();
        }

        @Test
        @DisplayName("병이 궁성 안에서 간선 위로 이동 가능한지 확인한다.")
        void checkByeongInPalaceOnEdge() {
            Byeong byeong = new Byeong(Team.CHO, new Point(7, 4));
            Direction direction = Direction.SOUTH;

            assertThat(Palace.movesOnEdge(byeong.getPoint(), direction)).isTrue();
        }

        @Test
        @DisplayName("병이 궁성 안에서 간선 위로 이동 가능한지 확인한다.")
        void checkByeongInPalaceOnEdge_2() {
            Byeong byeong = new Byeong(Team.CHO, new Point(7, 3));
            Direction direction = Direction.SOUTH_EAST;

            assertThat(Palace.movesOnEdge(byeong.getPoint(), direction)).isTrue();
        }

        @Test
        @DisplayName("병이 궁성 안에서 간선 위로 이동 가능한지 확인한다.")
        void checkByeongInPalaceOnEdge_3() {
            Byeong byeong = new Byeong(Team.CHO, new Point(7, 4));
            Direction direction = Direction.SOUTH_WEST;

            assertThat(Palace.movesOnEdge(byeong.getPoint(), direction)).isFalse();
        }
    }
}