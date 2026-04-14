package domain;

import domain.board.Palace;
import domain.vo.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PalaceTest {

    @Test
    @DisplayName("초나라 궁성 내부 위치는 true를 반환한다")
    void shouldReturnTrueWhenPositionIsInsideChoPalace() {
        // given
        Palace palace = Palace.CHU;

        // when
        Position position = Position.of(2, 4);

        // then
        Assertions.assertTrue(palace.isInPalace(position));
    }

    @Test
    @DisplayName("초나라 궁성 행 초과하면 false를 반환한다")
    void shouldReturnFalseWhenChoPalaceRowIsExceeded() {
        // given
        Palace palace = Palace.CHU;

        // when
        Position position = Position.of(3, 1);

        // then
        Assertions.assertFalse(palace.isInPalace(position));
    }

    @Test
    @DisplayName("초나라 궁성 열 미만이면 false를 반환한다")
    void shouldReturnFalseWhenChoPalaceColumnIsInsufficient() {
        // given
        Palace palace = Palace.CHU;

        // when
        Position position = Position.of(0, 1);

        // then
        Assertions.assertFalse(palace.isInPalace(position));
    }

    @Test
    @DisplayName("한나라 궁성 내부 위치는 true를 반환한다")
    void shouldReturnTrueWhenPositionIsInsideHanPalace() {
        // given
        Palace palace = Palace.HAN;

        // when
        Position position = Position.of(8, 4);

        // then
        Assertions.assertTrue(palace.isInPalace(position));
    }

    @Test
    @DisplayName("한나라 궁성 행 미만이면 false를 반환한다")
    void shouldReturnFalseWhenHanPalaceRowIsInsufficient() {
        // given
        Palace palace = Palace.HAN;

        // when
        Position position = Position.of(6, 4);

        // then
        Assertions.assertFalse(palace.isInPalace(position));
    }

    @Test
    @DisplayName("한나라 궁성 열 초과하면 false를 반환한다")
    void shouldReturnFalseWhenHanPalaceColumnIsExceeded() {
        // given
        Palace palace = Palace.HAN;

        // when
        Position position = Position.of(9, 6);

        // then
        Assertions.assertFalse(palace.isInPalace(position));
    }

    @Test
    @DisplayName("궁성 꼭지점은 대각선 포인트이다.")
    void shouldReturnTrueWhenPositionIsPalaceCorner() {
        // given
        Palace palace = Palace.HAN;

        // when
        Position position = Position.of(7, 3);

        // then
        Assertions.assertTrue(palace.isDiagonalPoint(position));
    }

    @Test
    @DisplayName("궁성 중앙은 대각선 포인트이다.")
    void shouldReturnTrueWhenPositionIsChuPalaceCenter() {
        // given
        Palace palace = Palace.CHU;

        // when
        Position position = Position.of(1, 4);

        // then
        Assertions.assertTrue(palace.isDiagonalPoint(position));
    }

    @Test
    @DisplayName("궁성 밖의 위치는 대각선 포인트가 아니다")
    void shouldReturnFalseWhenPositionIsOutsidePalace() {
        // given
        Palace palace = Palace.HAN;

        // when
        Position position = Position.of(7, 2);

        // then
        Assertions.assertFalse(palace.isDiagonalPoint(position));
    }
}
