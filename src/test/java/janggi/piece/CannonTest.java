package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.Point;
import janggi.camp.Camp;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CannonTest {

    @DisplayName("포는 수평 혹은 수직으로 움직이지 않은 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,2,2",
            "HAN,4,2",
            "HAN,2,4",
            "HAN,4,4",
    })
    void shouldThrowException_WhenInvalidMove(Camp camp, int toX, int toY) {
        // given
        Cannon cannon = new Cannon(camp);
        Point fromPoint = new Point(3, 3);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> cannon.validateMove(fromPoint, toPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 수평 혹은 수직으로만 움직여야 합니다.");
    }

    @DisplayName("포는 수평 혹은 수직으로 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,3,0",
            "HAN,3,5",
            "HAN,0,3",
            "HAN,5,3",
    })
    void validateMoveTest(Camp camp, int toX, int toY) {
        // given
        Cannon cannon = new Cannon(camp);
        Point fromPoint = new Point(3, 3);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> cannon.validateMove(fromPoint, toPoint))
                .doesNotThrowAnyException();
    }

    @DisplayName("포는 다른 포를 잡을 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCatchOtherCannon() {
        // given
        Cannon chuCannon = new Cannon(Camp.CHU);
        Cannon hanCannon = new Cannon(Camp.HAN);

        // when & then
        assertThatCode(() -> chuCannon.validateCatch(hanCannon))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포를 잡을 수 없습니다.");
    }

    @DisplayName("포의 경로에 기물이 없는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenJumpOverZeroPiece() {
        // given
        Cannon cannon = new Cannon(Camp.CHU);
        Set<Piece> piecesOnRoute = Set.of();

        // when & then
        assertThatCode(() -> cannon.validatePathObstacles(piecesOnRoute))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 정확히 하나의 기물만 넘을 수 있습니다. 넘은 기물 수: 0");
    }

    @DisplayName("포의 경로에 기물이 2개인 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenJumpOverTwoPiece() {
        // given
        Cannon cannon = new Cannon(Camp.CHU);
        Set<Piece> piecesOnRoute = Set.of(new Soldier(Camp.CHU), new Soldier(Camp.CHU));

        // when & then
        assertThatCode(() -> cannon.validatePathObstacles(piecesOnRoute))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 정확히 하나의 기물만 넘을 수 있습니다. 넘은 기물 수: 2");
    }

    @DisplayName("포가 포를 넘어갈 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCannonJumpOverCannon() {
        // given
        Cannon cannon = new Cannon(Camp.CHU);
        Set<Piece> piecesOnRoute = Set.of(new Cannon(Camp.CHU));

        // when & then
        assertThatCode(() -> cannon.validatePathObstacles(piecesOnRoute))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포를 넘을 수 없습니다.");
    }
}
