package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.board.JanggiBoard;
import janggi.board.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotTest {

    @Test
    @DisplayName("차 이동 가능 후보군 리턴 테스트")
    void test1() {
        Chariot chariot = new Chariot(Side.CHO);
        JanggiBoard board = JanggiBoard.initialize();

        List<Position> destinations = chariot.filterReachableDestinations(new Position(0, 9), board);

        assertAll(
                () -> assertThat(destinations).hasSize(2),
                () -> assertThat(destinations.getFirst()).isEqualTo(new Position(0, 8))
        );
    }

}
