package position;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DeltaTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, 1})
    void ROW_DELTA와_COLUMN_DELTA를_합한_새로운_DELTA를_생성한다(int deltaValue) {
        // given
        Delta before = new Delta(0, 0);
        Delta other = new Delta(deltaValue, deltaValue);
        // when
        Delta after = before.add(other);
        // then
        int expectedRowIndex = before.rowDelta() + other.rowDelta();
        int expectedColumnIndex = before.columnDelta() + other.columnDelta();
        assertThat(after.rowDelta()).isEqualTo(expectedRowIndex);
        assertThat(after.columnDelta()).isEqualTo(expectedColumnIndex);
    }
}