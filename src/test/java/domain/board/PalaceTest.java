package domain.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PalaceTest {

    Palace palace;

    @BeforeEach
    void init() {
        palace = new Palace();
    }

    @Test
    @DisplayName("현재 좌표가 초나라의 궁성 내부라면 true를 반환한다")
    void currentPositionIsInChoPalace_Then_ReturnTrue() {
        Position position1 = new Position(4, 1);
        Position position2 = new Position(6, 3);

        assertTrue(palace.isInPalace(position1));
        assertTrue(palace.isInPalace(position2));
    }

    @Test
    @DisplayName("현재 좌표가 한나라의 궁성 내부라면 true를 반환한다")
    void currentPositionIsInHanPalace_Then_ReturnTrue() {
        Position position1 = new Position(4, 8);
        Position position2 = new Position(6, 10);

        assertTrue(palace.isInPalace(position1));
        assertTrue(palace.isInPalace(position2));
    }

    @Test
    @DisplayName("현재 좌표가 궁성 외부라면 false를 반환한다")
    void currentPositionIsOutOfPalace_Then_ReturnFalse() {
        Position position1 = new Position(3, 4);
        Position position2 = new Position(7, 7);

        assertFalse(palace.isInPalace(position1));
        assertFalse(palace.isInPalace(position2));
    }

    @Test
    @DisplayName("현재 좌표가 궁성의 중앙이라면 true를 반환한다")
    void currentPositionIsOnPalaceCenter_Then_ReturnTrue() {
        Position hanCenter = new Position(5, 2);
        Position choCenter = new Position(5, 9);

        assertTrue(palace.isOnPalaceCenter(hanCenter));
        assertTrue(palace.isOnPalaceCenter(choCenter));
    }
}
