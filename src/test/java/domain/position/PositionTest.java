package domain.position;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PositionTest {

    @Test
    @DisplayName("이동할 때마다 새로운 Position을 제공한다")
    void go_success() {
        //given
        Position originPosition = Position.of(1, 1);
        Position newPosition = originPosition.go(1, 1);

        //when
        boolean compareResult = originPosition.hashCode() != newPosition.hashCode();

        //then
        assertTrue(compareResult);
    }

    @Nested
    class isSameXXXTest {
        Position position = Position.of(1, 1);

        @Test
        void isSameRow_true() {
            Row row = position.getRow();

            assertTrue(
                    position.isSameRow(row)
            );
        }

        @Test
        void isSameRow_false() {
            Row row = new Row(10);

            assertFalse(
                    position.isSameRow(row)
            );
        }

        @Test
        void isSameColumn_true() {
            Column column = position.getColumn();

            assertTrue(
                    position.isSameColumn(column)
            );
        }

        @Test
        void isSameColumn_false() {
            Column column = new Column(10);

            assertFalse(
                    position.isSameColumn(column)
            );
        }
    }

    @Test
    @DisplayName("같은 column 이지만 사이에 존재하는 다른 row들을 가지는 Position들을 제공한다")
    void getSameColumnPositionToDestination_test() {
        //given
        Position start = Position.of(1, 1);
        Position destination = Position.of(5, 1);
        List<Position> expectResult = List.of(
                Position.of(2, 1),
                Position.of(3, 1),
                Position.of(4, 1)
        );

        //when
        List<Position> result = start.getSameColumnPositionsToDestination(destination);

        //then
        assertThat(result).isEqualTo(expectResult);
    }

    @Test
    @DisplayName("같은 row 이지만 사이에 존재하는 다른 column들을 가지는 Position들을 제공한다")
    void getSameRowPositionToDestination_test() {
        //given
        Position start = Position.of(1, 1);
        Position destination = Position.of(1, 5);
        List<Position> expectResult = List.of(
                Position.of(1, 2),
                Position.of(1, 3),
                Position.of(1, 4)
        );

        //when
        List<Position> result = start.getSameRowPositionsToDestination(destination);

        //then
        assertThat(result).isEqualTo(expectResult);
    }

    @Test
    @DisplayName("row와 column이 같으면 같은 Position으로 판단한다")
    void equalsTrue_when_row_and_column_is_same() {
        Position positionA = Position.of(1, 1);
        Position positionB = Position.of(1, 1);

        assertEquals(positionA, positionB);
    }
}
