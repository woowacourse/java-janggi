package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Position;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SoldierTest {

    @Nested
    @DisplayName("일반 이동 테스트")
    class NormalMoveTest {

        @DisplayName("BLUE 병사가 위 혹은 좌우로 움직이지 않으면 false를 반환한다.")
        @ParameterizedTest
        @CsvSource(value = {
                "5, 4",
                "6, 6",
                "4, 6",
                "3, 5",
                "7, 5"
        })
        void shouldFalseWhenBlueSide(int destX, int destY) {
            // given
            Soldier soldier = new Soldier(Side.BLUE);
            Position start = new Position(5, 5);
            Position end = new Position(destX, destY);

            // when
            boolean canMove = soldier.canMove(start, end, Map.of());

            // then
            assertThat(canMove).isFalse();
        }

        @DisplayName("RED 병사가 아래 혹은 좌우로 움직이지 않으면 false를 반환한다.")
        @ParameterizedTest
        @CsvSource(value = {
                "5, 6",
                "6, 4",
                "4, 4",
                "3, 5",
                "7, 5"
        })
        void shouldFalseWhenRedSide(int destX, int destY) {
            // given
            Soldier soldier = new Soldier(Side.RED);
            Position start = new Position(5, 5);
            Position end = new Position(destX, destY);

            // when
            boolean canMove = soldier.canMove(start, end, Map.of());

            // then
            assertThat(canMove).isFalse();
        }
    }

    @Nested
    @DisplayName("궁성 내 이동 테스트")
    class PalaceMoveTest {

        @DisplayName("궁성 내에서 대각선으로 이동 가능하면 true를 반환한다.")
        @Test
        void shouldReturnTrueWhenMoveDiagonalInPalace() {
            // given
            Soldier soldier = new Soldier(Side.RED);
            Position start = new Position(6, 3);
            Position end = new Position(5, 2);

            // when
            boolean canMove = soldier.canMove(start, end, Map.of());

            // then
            assertThat(canMove).isTrue();
        }

        @DisplayName("궁성 내에서 대각선으로 이동 불가능하면 false를 반환한다.")
        @Test
        void shouldReturnFalseWhenCantMoveDiagonalInPalace() {
            // given
            Soldier soldier = new Soldier(Side.RED);
            Position start = new Position(5, 3);
            Position end = new Position(4, 2);

            // when
            boolean canMove = soldier.canMove(start, end, Map.of());

            // then
            assertThat(canMove).isFalse();
        }
    }
}
