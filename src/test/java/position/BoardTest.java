package position;

import static org.assertj.core.api.Assertions.assertThat;
import static position.PositionFixtures.E0;
import static position.PositionFixtures.E1;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import piece.Palace;
import piece.Piece;

public class BoardTest {

    @ParameterizedTest
    @EnumSource()
    @DisplayName("위치에 기물이 존재하는지 확인할 수 있다.")
    void isBlankTest_1() {
        // given
        Piece palace = new Palace(E1);
        Board board = new Board(Set.of(palace));

        // when - then
        assertThat(board.isBlank(E0)).isTrue();
    }

    @Test
    @DisplayName("위치에 기물이 존재하는지 확인할 수 있다.")
    void isBlankTest_2() {
        // given
        Piece palace = new Palace(E1);
        Board board = new Board(Set.of(palace));

        // when - then
        assertThat(board.isBlank(E1)).isFalse();
    }

}
