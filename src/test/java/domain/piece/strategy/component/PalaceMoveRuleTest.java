package domain.piece.strategy.component;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PalaceMoveRuleTest {

    private final PalaceMoveRule rule = new PalaceMoveRule();


    @Test
    @DisplayName("목적지가 궁성 안에 있으면 오류가 발생하지 않는다")
    void not_throw_exception_when_destination_in_palace() {
        Position start = Position.of(1, 4);
        Position destination = Position.of(2, 4);

        assertDoesNotThrow(
                () -> rule.isPalacePath(start, destination)
        );
    }

    @Test
    @DisplayName("목적지가 궁성 밖에 있으면 false을 반환한다")
    void throw_exception_when_destination_in_palace() {
        Position start = Position.of(1, 4);
        Position destination = Position.of(5, 5);

        boolean result = rule.isPalacePath(start, destination);

        assertFalse(result);
    }

    @Test
    @DisplayName("목적지까지 수직 이동이 가능하다")
    void can_go_to_destination_vertical() {
        //given
        Position start = Position.of(1, 4);
        Position destination = Position.of(2, 4);

        //when
        boolean result = rule.isPalacePath(start, destination);

        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("목적지까지 수평 이동이 가능하다")
    void can_go_to_destination_horizontal() {
        //given
        Position start = Position.of(1, 4);
        Position destination = Position.of(1, 5);

        //when
        boolean result = rule.isPalacePath(start, destination);

        //then
        assertTrue(result);
    }

    @Nested
    class DiagonalMoveTest {
        @Test
        @DisplayName("궁성의 꼭지에서 중앙까지 목적지까지 대각선 이동이 가능하다")
        void can_go_to_destination_diagonal() {
            //given
            Position start = Position.of(1, 4);
            Position destination = Position.of(2, 5);

            //when
            boolean result = rule.isPalacePath(start, destination);

            //then
            assertTrue(result);
        }

        @Test
        @DisplayName("궁성의 꼭지에 위치하지 않으면 대각선 이동이 불가능하다")
        void cannot_go_to_destination_diagonal() {
            //given
            Position start = Position.of(1, 5);
            Position destination = Position.of(2, 4);

            //when
            boolean result = rule.isPalacePath(start, destination);

            //then
            assertFalse(result);
        }
    }
}
