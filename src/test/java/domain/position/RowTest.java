package domain.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.position.exception.InvalidPositionException;
import domain.position.exception.PositionErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RowTest {

    private static final int MINIMUM_BOUNDARY = 0;
    private static final int MAXIMUM_BOUNDARY = 9;
    private static final int ONE_SPACE = 1;

    @Test
    void ROW의_범위가_9를_넘을_경우_예외를_던진다() {
        assertThatThrownBy(() -> new Row(10))
            .isInstanceOf(InvalidPositionException.class)
            .hasMessage(PositionErrorMessage.INVALID_ROW.message());
    }

    @Test
    void ROW의_범위가_0보다_작을_경우_예외를_던진다() {
        assertThatThrownBy(() -> new Row(-1))
            .isInstanceOf(InvalidPositionException.class)
            .hasMessage(PositionErrorMessage.INVALID_ROW.message());
    }

    @Nested
    @DisplayName("ROW의 이동을 검증한다")
    class Move {

        @Test
        void ROW가_위로_한_칸_이동한다() {
            // given
            Row prev = new Row(1);
            // when
            Row cur = prev.up();
            // then
            assertThat(cur.index()).isEqualTo(prev.index() + ONE_SPACE);
        }

        @Test
        void ROW가_아래로_한_칸_이동한다() {
            // given
            Row prev = new Row(1);
            // when
            Row cur = prev.down();
            // then
            assertThat(cur.index()).isEqualTo(prev.index() - ONE_SPACE);
        }

        @Test
        void ROW가_위로_한_칸_이동했을_때_범위를_벗어날경우_예외를_던진다() {
            // given
            Row prev = new Row(MAXIMUM_BOUNDARY);
            // when & then
            assertThatThrownBy(prev::up)
                .isInstanceOf(InvalidPositionException.class)
                .hasMessage(PositionErrorMessage.INVALID_ROW.message());
        }

        @Test
        void ROW가_아래로_한_칸_이동했을_때_범위를_벗어날경우_예외를_던진다() {
            // given
            Row prev = new Row(MINIMUM_BOUNDARY);
            // when & then
            assertThatThrownBy(prev::down)
                .isInstanceOf(InvalidPositionException.class)
                .hasMessage(PositionErrorMessage.INVALID_ROW.message());
        }
    }

    @Nested
    @DisplayName("Row의 높이를 비교한다")
    class IsLowerThan {

        @Test
        void ROW가_다른_ROW_보다_낮을_경우_TRUE를_반환한다() {
            // given
            Row row = new Row(1);
            Row other = new Row(2);
            // when
            boolean isLower = row.isLowerThan(other);
            // then
            assertThat(isLower).isTrue();
        }

        @Test
        void ROW가_다른_ROW_보다_높을_경우_FALSE를_반환한다() {
            // given
            Row row = new Row(1);
            Row other = new Row(0);
            // when
            boolean isLower = row.isLowerThan(other);
            // then
            assertThat(isLower).isFalse();
        }
    }
}
