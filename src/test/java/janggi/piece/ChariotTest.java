package janggi.piece;

import janggi.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ChariotTest {
    @DisplayName("차가 이동 가능한 position 목록 반환 확인")
    @Test
    void chariotPositionTest() {
        Piece chariot = new Chariot(new Position(2,0));
        List<Position> expected = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            expected.add(new Position(i,0));
        }
        for (int i = 0; i < 9; i++) {
            expected.add(new Position(2, i));
        }

        assertThat(chariot.checkPossibleMoves()).containsAll(expected);
    }
}
