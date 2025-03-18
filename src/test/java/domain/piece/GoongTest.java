package domain.piece;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.Coordinate;
import domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class GoongTest {

    @DisplayName("CHO 팀 궁의 움직임 테스트")
    @Nested
    class ChoTeam {

        @Test
        @DisplayName("CHO 팀의 궁이 왼쪽으로 움직이면 궁의 x 좌표 1 감소한다.")
        void test2() {
            // given
            Goong goong = new Goong(new Piece(new Coordinate(2, 1), Team.CHO));

            // when
            Goong movedGoong = goong.moveLeft();

            // then
            assertThat(movedGoong).isEqualTo(new Goong(new Piece(new Coordinate(1, 1), Team.CHO)));
        }

        @Test
        @DisplayName("CHO 팀의 궁이 오른쪽으로 움직이면 궁의 x 좌표가 1 증가한다.")
        void test4() {
            // given
            Goong goong = new Goong(new Piece(new Coordinate(2, 1), Team.CHO));

            // when
            Goong movedGoong = goong.moveRight();

            // then
            assertThat(movedGoong).isEqualTo(new Goong(new Piece(new Coordinate(3, 1), Team.CHO)));
        }

        @Test
        @DisplayName("CHO 팀의 궁이 앞쪽으로 움직이면 궁의 y 좌표가 1 감소한다.")
        void test6() {
            // given
            Goong goong = new Goong(new Piece(new Coordinate(2, 2), Team.CHO));

            // when
            Goong movedGoong = goong.moveForward();

            // then
            assertThat(movedGoong).isEqualTo(new Goong(new Piece(new Coordinate(2, 1), Team.CHO)));
        }

        @Test
        @DisplayName("CHO 팀의 궁이 뒤쪽으로 움직이면 궁의 y 좌표가 1 증가한다.")
        void test8() {
            // given
            Goong goong = new Goong(new Piece(new Coordinate(2, 2), Team.CHO));

            // when
            Goong movedGoong = goong.moveBackward();

            // then
            assertThat(movedGoong).isEqualTo(new Goong(new Piece(new Coordinate(2, 3), Team.CHO)));
        }
    }

    @DisplayName("HAN 팀 궁의 움직임 테스트 ")
    @Nested
    class HanTeam {

        @Test
        @DisplayName("HAN 팀의 궁이 왼쪽으로 움직이면 궁의 x 좌표 1 증가한다.")
        void test1() {
            // given
            Goong goong = new Goong(new Piece(new Coordinate(2, 1), Team.HAN));

            // when
            Goong movedGoong = goong.moveLeft();

            // then
            assertThat(movedGoong).isEqualTo(new Goong(new Piece(new Coordinate(3, 1), Team.HAN)));
        }

        @Test
        @DisplayName("HAN 팀의 궁이 오른쪽으로 움직이면 궁의 x 좌표가 1 감소한다.")
        void test3() {
            // given
            Goong goong = new Goong(new Piece(new Coordinate(2, 1), Team.HAN));

            // when
            Goong movedGoong = goong.moveRight();

            // then
            assertThat(movedGoong).isEqualTo(new Goong(new Piece(new Coordinate(1, 1), Team.HAN)));
        }

        @Test
        @DisplayName("HAN 팀의 궁이 앞쪽으로 움직이면 궁의 y 좌표가 1 증가한다.")
        void test5() {
            // given
            Goong goong = new Goong(new Piece(new Coordinate(2, 2), Team.HAN));

            // when
            Goong movedGoong = goong.moveForward();

            // then
            assertThat(movedGoong).isEqualTo(new Goong(new Piece(new Coordinate(2, 3), Team.HAN)));
        }

        @Test
        @DisplayName("HAN 팀의 궁이 뒤쪽으로 움직이면 궁의 y 좌표가 1 감소한다.")
        void test7() {
            // given
            Goong goong = new Goong(new Piece(new Coordinate(2, 2), Team.HAN));

            // when
            Goong movedGoong = goong.moveBackward();

            // then
            assertThat(movedGoong).isEqualTo(new Goong(new Piece(new Coordinate(2, 1), Team.HAN)));
        }
    }
}
