import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class JanggiTest {
    @DisplayName("x좌표는 0~8의 범위가 아니면 에러를 던진다.")
    @Test
    void x_range_test() {
        // given
        int x  = 9;

        // when, then
        assertThatCode(() -> new LocationX(x)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("y좌표는 0~9의 범위가 아니면 예외를 던진다.")
    @Test
    void y_range_test() {
        // given
        int y = 10;

        // when, then
        assertThatCode(() -> new LocationY(y)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("나라는 한나라와 초나라 2가지 존재한다.")
    @Test
    void pieceHasCountry() {
        // given
        Dynasty[] dynasties = Dynasty.values();

        // when
        int actual = dynasties.length;

        // then
        assertThat(actual).isEqualTo(2);
    }

    @DisplayName("Piece는 한나라 피스를 생성할 수 있다.")
    @Test
    void createHanTest() {
        // given
        Piece piece = Piece.createFromHan("차");

        // when
        Dynasty actual = piece.getDynasty();

        // then
        assertThat(actual).isEqualTo(Dynasty.HAN);
    }

    @DisplayName("Piece는 초나라 피스를 생성할 수 있다.")
    @Test
    void createChoTest() {
        // given
        Piece piece = Piece.createFromCho("차");

        // when
        Dynasty actual = piece.getDynasty();

        // then
        assertThat(actual).isEqualTo(Dynasty.CHO);
    }

    @DisplayName("다양한 타입의 Piece 생성할 수 있다.")
    @Test
    void createChariot() {

        // when // then
        assertThatCode(() -> Piece.createFromCho("차"))
                .doesNotThrowAnyException();
    }



}
