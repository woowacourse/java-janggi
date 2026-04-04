package position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ColumnTest {

    private static final int MINIMUM_BOUNDARY = 0;
    private static final int MAXIMUM_BOUNDARY = 8;

    @ParameterizedTest
    @ValueSource(ints = {MINIMUM_BOUNDARY - 1, MAXIMUM_BOUNDARY + 1})
    void COLUMN의_범위가_벗어날_경우_예외를_던진다(int index) {
        assertThatThrownBy(() -> new Column(index))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Nested
    @DisplayName("서로 다른 COLUMN의 합을 검증한다")
    class Add {

        @ParameterizedTest
        @ValueSource(ints = {-1, 1})
        void DELTA의_COLUMN_INDEX를_합한_새로운_COLUMN을_생성한다(int deltaValue) {
            // given
            Column before = new Column(1);
            Delta delta = new Delta(0, deltaValue);
            // when
            Column added = before.move(delta);
            // then
            int expectedColumnIndex = before.index() + delta.columnDelta();
            assertThat(added.index()).isEqualTo(expectedColumnIndex);
        }

        @ParameterizedTest
        @ValueSource(ints = {MINIMUM_BOUNDARY - 1, MAXIMUM_BOUNDARY + 1})
        void 합산_값이_범위를_벗어나면_예외를_던진다(int deltaValue) {
            // given
            Column before = new Column(MINIMUM_BOUNDARY);
            Delta delta = new Delta(0, deltaValue);
            // when & then
            assertThatThrownBy(() -> before.move(delta))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void 최소_최대값을_기준으로_좌우_반전_위치를_반환한다() {
        // given
        Column column = new Column(3);
        // when
        Column reversed = column.reverse();
        // then
        int expected = MAXIMUM_BOUNDARY - column.index();
        assertThat(reversed.index()).isEqualTo(expected);
    }
}
