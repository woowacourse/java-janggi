package movepolicy.rule;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import position.Position;

class GunsungTest {

    private static final int CHO_MIN_ROW = 0;
    private static final int CHO_MAX_ROW = 2;
    private static final int COLUMN_MIN_ROW = 9;
    private static final int COLUMN_MAX_ROW = 7;
    private static final int MIN_COLUMN = 3;
    private static final int MAX_COLUMN = 5;

    private final Gunsung gunsung = new Gunsung();

    @Nested
    @DisplayName("초의 궁성 내부인지 판단한다")
    class IsChoRange {

        @ParameterizedTest
        @CsvSource(delimiter = ',', value = {"0,3", "0,4", "0,5", "1,3", "1,4", "1,5", "2,3", "2,4", "2,5"})
        void 초의_궁성_내부인지_판단한다(final int row, final int column) {
            // given
            Position position = new Position(row, column);
            // when
            boolean contains = gunsung.isChoRange(position);
            // then
            assertThat(contains).isTrue();
        }

        @Test
        void ROW가_초의_궁성_범위를_벗어나는_경우_FALSE를_반환한다() {
            // given
            Position position = new Position(CHO_MAX_ROW + 1, MIN_COLUMN);
            // when
            boolean contains = gunsung.isChoRange(position);
            // then
            assertThat(contains).isFalse();
        }

        @ParameterizedTest
        @ValueSource(ints = {MIN_COLUMN - 1, MAX_COLUMN + 1})
        void COLUMN이_초의_궁성_범위를_벗어나는_경우_FALSE를_반환한다(final int invalidColumn) {
            // given
            Position position = new Position(CHO_MIN_ROW, invalidColumn);
            // when
            boolean contains = gunsung.isChoRange(position);
            // then
            assertThat(contains).isFalse();
        }
    }

    @Nested
    @DisplayName("초의 궁성 내부인지 판단한다")
    class IsHanRange {

        @ParameterizedTest
        @CsvSource(delimiter = ',', value = {"0,3", "0,4", "0,5", "1,3", "1,4", "1,5", "2,3", "2,4", "2,5"})
        void 한의_궁성_내부인지_판단한다(final int row, final int column) {
            // given
            Position position = new Position(row, column);
            Position hanPosition = position.reverse();
            // when
            boolean contains = gunsung.isHanRange(hanPosition);
            // then
            assertThat(contains).isTrue();
        }

        @Test
        void ROW가_한의_궁성_범위를_벗어나는_경우_FALSE를_반환한다() {
            // given
            Position position = new Position(CHO_MAX_ROW + 1, MIN_COLUMN);
            Position hanPosition = position.reverse();
            // when
            boolean contains = gunsung.isHanRange(hanPosition);
            // then
            assertThat(contains).isFalse();
        }

        @ParameterizedTest
        @ValueSource(ints = {MIN_COLUMN - 1, MAX_COLUMN + 1})
        void COLUMN이_한의_궁성_범위를_벗어나는_경우_FALSE를_반환한다(final int invalidColumn) {
            // given
            Position position = new Position(CHO_MIN_ROW, invalidColumn);
            Position hanPosition = position.reverse();
            // when
            boolean contains = gunsung.isHanRange(hanPosition);
            // then
            assertThat(contains).isFalse();
        }
    }
}