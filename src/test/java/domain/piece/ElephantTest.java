package domain.piece;

import static org.junit.jupiter.api.Assertions.*;

import domain.Position;
import domain.Team;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElephantTest {

    @DisplayName("상의 이동 경로를 계산할 수 있다")
    @Test
    void test1() {
        Elephant elephant = new Elephant(Team.RED);

        List<Move> moves = elephant.calculatePath(new Position(4,4),new Position(2,7));
        List<Move> expected = List.of(Move.RIGHT, Move.FRONT_RIGHT, Move.FRONT_RIGHT);

        Assertions.assertThat(moves).isEqualTo(expected);
    }

    @DisplayName("상으로 갈 수 없는 위치일 경우 예외를 발생시킨다")
    @Test
    void test2() {
        Elephant elephant = new Elephant(Team.RED);

        Assertions.assertThatThrownBy(() -> elephant.calculatePath(new Position(4,4),new Position(4,5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로 이동할 수 없습니다.");
    }

}