package domain.piece.noPathPiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.BoardFixture;
import domain.Coordinate;
import domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ByeongTest {

    @DisplayName("병은 HAN 팀이다.")
    @Test
    void byeongisChoTeam() {
        //given
        Byeong byeong = new Byeong(new Coordinate(1, 4));

        //when
        Team team = byeong.getTeam();

        //then
        assertThat(team).isEqualTo(Team.HAN);
    }

    @Nested
    @DisplayName("이동 가능 여부 반환하는 테스트")
    class CanMoveTest {

        @ParameterizedTest
        @DisplayName("병은 기본적으로 하,좌,우 방향으로 이동할 수 있다.")
        @CsvSource({"3,5", "2,4", "4,4"})
        void test1(int x, int y) {
            // given
            Byeong byeong = new Byeong(new Coordinate(3, 4));

            // when
            final var arrival = new Coordinate(x, y);
            final var canMove = byeong.canMove(arrival, BoardFixture.emptyBoard());

            // then
            assertThat(canMove).isTrue();
        }
    }

    @Nested
    @DisplayName("병이 궁성 내에서 대각선을 따라 움직일 수 있다.")
    class InCastleMoveTest {

        @ParameterizedTest
        @DisplayName("병이 궁성 내에 있을 때 아래쪽 대각선을 따라 움직일 수 있다.")
        @CsvSource({"4,10", "6,10"})
        void test1(int x, int y) {
            // given
            final var byeong = new Byeong(new Coordinate(5, 9));

            // when
            final var canMove = byeong.canMove(new Coordinate(x, y), BoardFixture.emptyBoard());

            // then
            assertThat(canMove).isTrue();
        }

        @ParameterizedTest
        @DisplayName("궁성 내에 위쪽 대각선이 있다고 하더라도 병은 위쪽 방향으로 움직일 수 없다.")
        @CsvSource({"4,8", "6,8"})
        void test2(int x, int y) {
            //given
            final var byeong = new Byeong(new Coordinate(5, 9));

            //when
            final var canMove = byeong.canMove(new Coordinate(x, y), BoardFixture.emptyBoard());

            //then
            assertThat(canMove).isFalse();
        }
    }
}
