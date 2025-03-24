package position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static piece.Team.HAN;
import static position.PositionFixtures.E0;
import static position.PositionFixtures.E1;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Palace;
import piece.Piece;

public class BoardTest {

    @Test
    @DisplayName("위치에 기물이 존재하는지 확인할 수 있다.")
    void isBlankTest_1() {
        // given
        Piece palace = new Palace(HAN, E1);
        Board board = new Board(Set.of(palace));

        // when - then
        assertThat(board.isBlank(E0)).isTrue();
    }

    @Test
    @DisplayName("위치에 기물이 존재하는지 확인할 수 있다.")
    void isBlankTest_2() {
        // given
        Piece palace = new Palace(HAN, E1);
        Board board = new Board(Set.of(palace));

        // when - then
        assertThat(board.isBlank(E1)).isFalse();
    }

    @Test
    @DisplayName("같은 팀 기물을 움직이려 하면 예외가 발생한다.")
    void validateTeamTest(){
        // given
        Piece palace = new Palace(HAN, E1);
        Board board = new Board(Set.of(palace));

        // when - then
        assertThatThrownBy(() -> board.validateTeam(HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 같은 팀 기물만 움직일 수 있습니다.");
    }

}
