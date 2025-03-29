package piece;

import static org.assertj.core.api.Assertions.assertThat;

import movementRule.Jol;
import movementRule.PieceRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pieceProperty.Position;

class PieceRuleTest {
    @Test
    @DisplayName("같은 위치 판단 테스트")
    void isSamePositionTest() {
        //give
        PieceRule pieceRule = new Jol(new Position(5, 5));
        Position position = new Position(5, 5);

        //when - then
        assertThat(pieceRule.isSamePosition(position)).isTrue();
    }

    @Test
    @DisplayName("업데이트 테스트")
    void updateTest() {
        //given
        Position position = new Position(5, 5);
        PieceRule pieceRule = new Jol(position);

        //when
        pieceRule.updateChessPiecePositionBy(new Position(5, 6));

        //then
        assertThat(pieceRule.isSamePosition(new Position(5, 6))).isTrue();
    }

}
