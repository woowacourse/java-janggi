package domain.position;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ColumnTest {

    @Nested
    class InRangeTest {
        @Test
        @DisplayName("범위 내에 있으면 true을 반환한다")
        void inRange_true() {
            //given
            int testStart = 1;
            int testEnd = 2;
            Column testColumn = new Column(2);

            //when
            boolean result = testColumn.isColumnInRange(testStart, testEnd);

            //then
            assertTrue(result);
        }


        @Test
        @DisplayName("범위 내에 없으면 false을 반환한다")
        void inRange_false() {
            //given
            int testStart = 1;
            int testEnd = 1;
            Column testColumn = new Column(2);

            //when
            boolean result = testColumn.isColumnInRange(testStart, testEnd);

            //then
            assertFalse(result);
        }
    }
}
