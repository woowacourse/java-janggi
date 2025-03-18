package domain.piece;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.Coordinate;
import domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ChaTest {

    @DisplayName("CHO 팀 차의 움직임 테스트")
    @Nested
    class ChoTeam {

        @Test
        @DisplayName("CHO 팀의 차가 왼쪽으로 3만큼 움직이면 차의 x 좌표 3 감소한다.")
        void test2() {
            // given
            Cha cha = new Cha(new Piece(new Coordinate(5, 1), Team.CHO));

            // when
            Cha movedCha = cha.moveLeft(3);

            // then
            assertThat(movedCha).isEqualTo(new Cha(new Piece(new Coordinate(2, 1), Team.CHO)));
        }

        @Test
        @DisplayName("CHO 팀의 차가 오른쪽으로 3만큼 움직이면 차의 x 좌표가 3 증가한다.")
        void test4() {
            // given
            Cha cha = new Cha(new Piece(new Coordinate(2, 1), Team.CHO));

            // when
            Cha movedCha = cha.moveRight(3);

            // then
            assertThat(movedCha).isEqualTo(new Cha(new Piece(new Coordinate(5, 1), Team.CHO)));
        }

        @Test
        @DisplayName("CHO 팀의 차가 앞쪽으로 3만큼 움직이면 차의 y 좌표가 3 감소한다.")
        void test6() {
            // given
            Cha cha = new Cha(new Piece(new Coordinate(2, 5), Team.CHO));

            // when
            Cha movedCha = cha.moveForward(3);

            // then
            assertThat(movedCha).isEqualTo(new Cha(new Piece(new Coordinate(2, 2), Team.CHO)));
        }

        @Test
        @DisplayName("CHO 팀의 차가 뒤쪽으로 3만큼 움직이면 차의 y 좌표가 3 증가한다.")
        void test8() {
            // given
            Cha cha = new Cha(new Piece(new Coordinate(2, 5), Team.CHO));

            // when
            Cha movedCha = cha.moveBackward(3);

            // then
            assertThat(movedCha).isEqualTo(new Cha(new Piece(new Coordinate(2, 8), Team.CHO)));
        }
    }

    @DisplayName("HAN 팀 차의 움직임 테스트 ")
    @Nested
    class HanTeam {

        @Test
        @DisplayName("HAN 팀의 차가 왼쪽으로 3만큼 움직이면 차의 x 좌표 3 증가한다.")
        void test1() {
            // given
            Cha cha = new Cha(new Piece(new Coordinate(5, 1), Team.HAN));

            // when
            Cha movedCha = cha.moveLeft(3);

            // then
            assertThat(movedCha).isEqualTo(new Cha(new Piece(new Coordinate(8, 1), Team.HAN)));
        }

        @Test
        @DisplayName("HAN 팀의 차가 오른쪽으로 3만큼 움직이면 차의 x 좌표가 3 감소한다.")
        void test3() {
            // given
            Cha cha = new Cha(new Piece(new Coordinate(5, 1), Team.HAN));

            // when
            Cha movedCha = cha.moveRight(3);

            // then
            assertThat(movedCha).isEqualTo(new Cha(new Piece(new Coordinate(2, 1), Team.HAN)));
        }

        @Test
        @DisplayName("HAN 팀의 차가 앞쪽으로 3만큼 움직이면 차의 y 좌표가 3 증가한다.")
        void test5() {
            // given
            Cha cha = new Cha(new Piece(new Coordinate(2, 5), Team.HAN));

            // when
            Cha movedCha = cha.moveForward(3);

            // then
            assertThat(movedCha).isEqualTo(new Cha(new Piece(new Coordinate(2, 8), Team.HAN)));
        }

        @Test
        @DisplayName("HAN 팀의 차가 뒤쪽으로 3만큼 움직이면 차의 y 좌표가 3 감소한다.")
        void test7() {
            // given
            Cha cha = new Cha(new Piece(new Coordinate(2, 5), Team.HAN));

            // when
            Cha movedCha = cha.moveBackward(3);

            // then
            assertThat(movedCha).isEqualTo(new Cha(new Piece(new Coordinate(2, 2), Team.HAN)));
        }
    }
}
