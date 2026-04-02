package janggi.domain.position;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class PositionTest {

    @Test
    void 문자열_09로_위치를_생성하면_좌표_10_9를_가지고_있다() {
        Position position = Position.from("09");
        int rowValue = position.getRowValue();
        int columnValue = position.getColumnValue();

        assertAll(
                () -> assertThat(rowValue).isEqualTo(10),
                () -> assertThat(columnValue).isEqualTo(9)
        );
    }

    @Test
    void 숫자_10과_9로_위치를_생성하면_좌표_10_9를_가지고_있다() {
        Position position = Position.of(10, 9);
        int rowValue = position.getRowValue();
        int columnValue = position.getColumnValue();

        assertAll(
                () -> assertThat(rowValue).isEqualTo(10),
                () -> assertThat(columnValue).isEqualTo(9)
        );
    }

    @Test
    void 두_자리_숫자가_아닌_문자열로_위치를_생성하면_예외가_발생한다() {
        assertThatThrownBy(
                () -> Position.from("105"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 올바른 좌표값이 아닙니다.");
    }

    @Test
    void 존재하지_않는_좌표로_위치를_생성하면_예외가_발생한다() {
        assertThatThrownBy(
                () -> Position.from("00"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 열 좌표는 1~9까지 사용 가능 합니다");
    }

    @Test
    void 동일한_좌표를_가진_위치_객체는_동등하다() {
       Position position1 = Position.from("12");
       Position position2 = Position.from("12");

       assertThat(position1).isEqualTo(position2);
    }
}
