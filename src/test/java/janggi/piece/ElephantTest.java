package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.board.JanggiBoard;
import janggi.board.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElephantTest {

    @Test
    @DisplayName("상 이동 가능 후보군 리턴 테스트")
    void test1() {
        Elephant elephant = new Elephant(Side.CHO);
        JanggiBoard board = JanggiBoard.initialize();

        List<Position> destinations = elephant.filterReachableDestinations(new Position(2, 9), board);

        assertAll(
                () -> assertThat(destinations).isEmpty()
        );
    }

}
