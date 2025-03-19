package domain;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import domain.piece.Piece;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public final class BoardTest {
    @Nested
    @DisplayName("보드가 생성될 때")
    class TestMakeBoard {
        @Test
        @DisplayName("보드는 9x10 크기로 구성되어야 한다")
        void test_generateBoard() {
            //given
            final Board board = new Board();

            //when
            final Map<Point, Piece> locations = board.getLocations();

            //then
            assertThat(locations.keySet().size()).isEqualTo(90);
        }
    }
}
