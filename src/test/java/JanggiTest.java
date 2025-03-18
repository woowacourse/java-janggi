import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class JanggiTest {
    @DisplayName("x좌표는 0~8의 범위가 아니면 에러를 던진다.")
    @Test
    void x_range_test() {
        // given
        int x  = 9;

        // when, then
        assertThatCode(() -> LocationX.getInstance(x)).isInstanceOf(IllegalArgumentException.class)
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

    @DisplayName("나라는 한나라와 초나라, 쉐도우 3가지 존재한다.")
    @Test
    void pieceHasCountry() {
        // given
        Dynasty[] dynasties = Dynasty.values();

        // when
        int actual = dynasties.length;

        // then
        assertThat(actual).isEqualTo(3);
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

    @DisplayName("칸은 x좌표와 y좌표를 기준으로 구분된다.")
    @Test
    void spaceHasLocationTest() {
        // given
        LocationX locationX = LocationX.getInstance(5);
        LocationY locationY = new LocationY(5);

        // when
        Space space1 = new Space(locationX, locationY);
        Space space2 = new Space(locationX, locationY);

        // then
        assertThat(space1).isEqualTo(space2);
    }

    @DisplayName("칸은 칸에 위치한 피스가 없다면 쉐도우 피스를 가진다")
    @Test
    void spaceHasShadowPieceTest() {
        // given
        Space space = new Space(LocationX.getInstance(5), new LocationY(5));

        // when
        boolean actual = space.isEmptySpace();

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("X 좌표는 0~8까지 캐싱되어있다")
    @Test
    void locationXCached() {
        // given
        LocationX locationX1 = LocationX.getInstance(0);
        LocationX locationX2 = LocationX.getInstance(0);

        // when
        boolean actual = locationX1 == locationX2;

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("Y 좌표는 0~9까지 캐싱되어있다")
    @Test
    void locationYCached() {
        // given
        LocationY locationY1 = LocationY.getInstance(0);
        LocationY locationY2 = LocationY.getInstance(0);

        // when
        boolean actual = locationY1 == locationY2;

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("장기판은 90개의 칸을 가진다.")
    @Test
    void Test() {
        // given

        // when, then
        assertThatCode(() -> new Board()).doesNotThrowAnyException();

        // TODO: 방법을 찾는다면 90개의 칸을 가지는지 확인할 것
    }

}
