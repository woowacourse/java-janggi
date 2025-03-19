package domain;

import domain.piece.Piece;
import java.util.Map;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
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
            assertThat(locations.size()).isEqualTo(90);
        }

        @Test
        @DisplayName("보드는 0,0부터 9,8까지 포함된다")
        void test_locationRange() {
            //given
            final Board board = new Board();

            //when
            final Map<Point, Piece> locations = board.getLocations();

            // then
            assertAll(
                    () -> assertThat(locations.keySet()).contains(new Point(0, 0)),
                    () -> assertThat(locations.keySet()).contains(new Point(9, 8)),
                    () -> assertThat(locations.keySet()).doesNotContain(new Point(10, 9))
            );
        }
    }
}
