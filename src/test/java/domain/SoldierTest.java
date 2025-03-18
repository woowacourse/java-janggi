package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SoldierTest {

    @Test
    @DisplayName("말을 움직였을 때 해당 위치로 정확히 움직였는지 확인한다.")
    void movePieceTest() {
        // given
        Position currentPosition = new Position(0, 0);
        Position movePosition = new Position(1, 1);
        Piece solider = new Soldier(currentPosition);

        // when
        solider.moveTo(movePosition);

        // then
        Position position = solider.getPosition();
        assertThat(position).isEqualTo(movePosition);
    }
}
