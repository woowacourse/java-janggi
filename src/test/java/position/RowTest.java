package position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RowTest {

    private static final int MINIMUM_BOUNDARY = 0;
    private static final int MAXIMUM_BOUNDARY = 9;

    @ParameterizedTest
    @ValueSource(ints = {MINIMUM_BOUNDARY - 1, MAXIMUM_BOUNDARY + 1})
    void ROW의_범위를_벗어날_경우_예외를_던진다(int index) {
        assertThatThrownBy(() -> new Row(index))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Nested
    @DisplayName("서로 다른 ROW의 합을 검증한다")
    class Add {

        @ParameterizedTest
        @ValueSource(ints = {-1, 1})
        void DELTA_의_ROW_INDEX를_합한_새로운_ROW를_생성한다(int deltaValue) {
            // given
            Row before = new Row(1);
            Delta delta = new Delta(deltaValue, 0);
            // when
            Row added = before.add(delta);
            // then
            int expectedRowIndex = before.index() + delta.rowDelta();
            assertThat(added.index()).isEqualTo(expectedRowIndex);
        }

        @ParameterizedTest
        @ValueSource(ints = {MINIMUM_BOUNDARY - 1, MAXIMUM_BOUNDARY + 1})
        void 합산_값이_범위를_벗어나면_예외를_던진다(int deltaValue) {
            // given
            Row before = new Row(MINIMUM_BOUNDARY);
            Delta delta = new Delta(deltaValue, 0);
            // when & then
            assertThatThrownBy(() -> before.add(delta))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void 최소_최대값을_기준으로_상하_반전_위치를_반환한다() {
        // given
        Row row = new Row(3);
        // when
        Row reversed = row.reverse();
        // then
        int expected = MAXIMUM_BOUNDARY - row.index();
        assertThat(reversed.index()).isEqualTo(expected);
    }
}
// dao, jdbc, model, resources, support