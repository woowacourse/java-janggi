package janggi.piece;

import janggi.board.JanggiBoard;
import janggi.board.Position;
import janggi.board.Route;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HorseTest {

    @Test
    @DisplayName("마 이동 가능 후보군 리턴 테스트")
    void test1() {
        Horse horse = new Horse(Side.CHO);
        JanggiBoard board = JanggiBoard.initialize();

        List<Position> destinations = horse.filterReachableDestinations(new Position(1, 9), board);

        assertAll(
                () -> assertThat(destinations).hasSize(2),
                () -> assertThat(destinations.getFirst()).isEqualTo(new Position(0, 7))
        );
    }

}
