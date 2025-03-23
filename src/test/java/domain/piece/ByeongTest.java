package domain.piece;

import domain.Position;
import domain.Team;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ByeongTest {

    @DisplayName("졸(병)은 오른쪽으로 이동할 수 있다.")
    @Test
    void test() {

        //given
        Byeong byeong = new Byeong(Team.HAN);

        //when
        List<Position> move = byeong.calculatePath(new Position(4, 1), new Position(4, 2));

        // then
        Assertions.assertThat(move).isEqualTo(List.of());
    }

    @DisplayName("졸(병)이 이동할 수 없는 위치라면 예외가 발생한다.")
    @Test
    void test1() {

        //given
        Byeong byeong = new Byeong(Team.HAN);

        // when & then
        Assertions.assertThatThrownBy(() -> byeong.calculatePath(new Position(4, 1), new Position(4, 3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로 이동할 수 없습니다.");
    }
}