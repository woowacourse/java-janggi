package pieceProperty;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionsTest {

    @Test
    @DisplayName("Positions 는 List<Position> 을 필드로 가진다.")
    void positionsTest() {
        //given
        List<Position> positionsList = List.of(new Position(5, 5));

        //when-then
        assertDoesNotThrow(() -> new Positions(positionsList));
    }

    @Test
    @DisplayName("Position 추가 테스트")
    void addPositionTest() {
        //given
        List<Position> positionsList = List.of(new Position(5, 5));
        Positions positions = new Positions(positionsList);
        Position position = new Position(6, 5);

        //when
        positions.addPosition(position);

        //then
        assertThat(positions.getPositions().contains(position)).isTrue();
    }


}
