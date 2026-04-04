package domain;

import domain.vo.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PalaceTest {

    @Test
    @DisplayName("초나라 궁성 내부 위치는 true를 반환한다")
    void shouldReturnTrueWhenPositionIsInsideChoPalace() {
        // given
        Palace palace = Palace.of(Team.CHU);

        // when
        Position position = Position.of(2, 4);

        // then
        Assertions.assertTrue(palace.isInPalace(position));
    }

    @Test
    @DisplayName("초나라 궁성 행 초과하면 false를 반환한다")
    void shouldReturnFalseWhenChoPalaceRowIsExceeded() {
        // given
        Palace palace = Palace.of(Team.CHU);

        // when
        Position position = Position.of(3, 1);

        // then
        Assertions.assertFalse(palace.isInPalace(position));
    }

    @Test
    @DisplayName("초나라 궁성 열 미만이면 false를 반환한다")
    void shouldReturnFalseWhenChoPalaceColumnIsInsufficient() {
        // given
        Palace palace = Palace.of(Team.CHU);

        // when
        Position position = Position.of(0, 1);

        // then
        Assertions.assertFalse(palace.isInPalace(position));
    }

    @Test
    @DisplayName("한나라 궁성 내부 위치는 true를 반환한다")
    void shouldReturnTrueWhenPositionIsInsideHanPalace() {
        // given
        Palace palace = Palace.of(Team.HAN);

        // when
        Position position = Position.of(8, 4);

        // then
        Assertions.assertTrue(palace.isInPalace(position));
    }

    @Test
    @DisplayName("한나라 궁성 행 미만이면 false를 반환한다")
    void shouldReturnFalseWhenHanPalaceRowIsInsufficient() {
        // given
        Palace palace = Palace.of(Team.HAN);

        // when
        Position position = Position.of(6, 4);

        // then
        Assertions.assertFalse(palace.isInPalace(position));
    }

    @Test
    @DisplayName("한나라 궁성 열 초과하면 false를 반환한다")
    void shouldReturnFalseWhenHanPalaceColumnIsExceeded() {
        // given
        Palace palace = Palace.of(Team.HAN);

        // when
        Position position = Position.of(9, 6);

        // then
        Assertions.assertFalse(palace.isInPalace(position));
    }
}
