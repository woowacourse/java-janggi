package movementRule.linearMover;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static pieceProperty.PieceType.CHA;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pieceProperty.Position;
import pieceProperty.Positions;

class ChaTest {

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @Test
    void nonCanMoveTo() {
        //given
        Cha cha = new Cha();
        Position position = new Position(0, 0);

        //when //then
        assertThatThrownBy(() -> cha.canMoveTo(position, new Position(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("차는 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        Cha cha = new Cha();
        Position startPosition = new Position(5, 5);
        Position futurePosition = new Position(0, 5);

        //when
        Positions actual = cha.makeRoute(startPosition, futurePosition);

        //then
        assertThat(actual.getPositions()).containsExactly(
                new Position(4, 5),
                new Position(3, 5),
                new Position(2, 5),
                new Position(1, 5)
        );
    }

    @Test
    @DisplayName("자신의 타입 리턴 테스트")
    void pieceTypeTest() {
        Cha cha = new Cha();

        assertThat(cha.getPieceType().equals(CHA)).isTrue();
    }

    @Test
    @DisplayName("왕인지 물어보는 테스트")
    void isJanggunTest() {
        Cha cha = new Cha();

        assertThat(cha.isJanggun()).isFalse();
    }

    @Test
    @DisplayName("포 판별 테스트")
    void isPoTest() {
        Cha cha = new Cha();

        assertThat(cha.isPo()).isFalse();
    }

}
