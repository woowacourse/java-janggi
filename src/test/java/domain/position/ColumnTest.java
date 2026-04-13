package domain.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.position.exception.InvalidPositionException;
import domain.position.exception.PositionErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ColumnTest {

    private static final int MINIMUM_BOUNDARY = 0;
    private static final int MAXIMUM_BOUNDARY = 8;
    private static final int ONE_SPACE = 1;

    @Test
    void COLUMN의_범위가_8을_넘을_경우_예외를_던진다() {
        assertThatThrownBy(() -> new Column(9))
            .isInstanceOf(InvalidPositionException.class)
            .hasMessage(PositionErrorMessage.INVALID_COLUMN.message());
    }

    @Test
    void COLUMN의_범위가_0보다_작을_경우_예외를_던진다() {
        assertThatThrownBy(() -> new Column(-1))
            .isInstanceOf(InvalidPositionException.class)
            .hasMessage(PositionErrorMessage.INVALID_COLUMN.message());
    }

    @Nested
    @DisplayName("COLUMN의 이동을 검증한다")
    class Move {

        @Test
        void COLUMN이_오른쪽으로_한_칸_이동한다() {
            // given
            Column prev = new Column(1);
            // when
            Column cur = prev.right();
            // then
            assertThat(cur.index()).isEqualTo(prev.index() + ONE_SPACE);
        }

        @Test
        void COLUMN이_왼쪽으로_한_칸_이동한다() {
            // given
            Column prev = new Column(1);
            // when
            Column cur = prev.left();
            // then
            assertThat(cur.index()).isEqualTo(prev.index() - ONE_SPACE);
        }

        @Test
        void COLUMN이_오른쪽으로_한_칸_이동했을_때_범위를_벗어날경우_예외를_던진다() {
            // given
            Column prev = new Column(MAXIMUM_BOUNDARY);
            // when & then
            assertThatThrownBy(prev::right)
                .isInstanceOf(InvalidPositionException.class)
                .hasMessage(PositionErrorMessage.INVALID_COLUMN.message());
        }

        @Test
        void COLUMN이_왼쪽으로_한_칸_이동했을_때_범위를_벗어날경우_예외를_던진다() {
            // given
            Column prev = new Column(MINIMUM_BOUNDARY);
            // when & then
            assertThatThrownBy(prev::left)
                .isInstanceOf(InvalidPositionException.class)
                .hasMessage(PositionErrorMessage.INVALID_COLUMN.message());
        }
    }
}
