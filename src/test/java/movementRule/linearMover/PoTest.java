package movementRule.linearMover;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static pieceProperty.PieceType.PO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pieceProperty.Position;
import pieceProperty.Positions;

class PoTest {

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @Test
    void nonCanMoveTo() {
        //given
        Po po = new Po();
        Position startPosition = new Position(0, 0);

        //when //then
        assertThatThrownBy(() -> po.canMoveTo(startPosition, new Position(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("포는 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        Po po = new Po();
        Position startPosition = new Position(0, 0);

        Position futurePosition = new Position(5, 0);

        //when
        Positions actual = po.makeRoute(startPosition, futurePosition);

        //then
        assertThat(actual.getPositions()).containsExactly(
                new Position(1, 0),
                new Position(2, 0),
                new Position(3, 0),
                new Position(4, 0)
        );
    }

}
