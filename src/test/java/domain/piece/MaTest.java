package domain.piece;

import domain.Position;
import domain.Team;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MaTest {

    @DisplayName("마의 이동 경로를 계산할 수 있다")
    @Test
    void test1() {
        Ma ma = new Ma(Team.HAN);

        List<Position> moves = ma.calculatePath(new Position(4, 4), new Position(3, 6));
        List<Position> expected = List.of(new Position(4, 5));

        Assertions.assertThat(moves).isEqualTo(expected);
    }

    @DisplayName("마로 갈 수 없는 위치일 경우 예외를 발생시킨다")
    @Test
    void test2() {
        Ma ma = new Ma(Team.HAN);

        Assertions.assertThatThrownBy(() -> ma.calculatePath(new Position(4, 4), new Position(4, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로 이동할 수 없습니다.");
    }
}
