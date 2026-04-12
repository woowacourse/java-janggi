package janggi.model.position.absolute;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UndirectedLineTest {
    @DisplayName("양 끝점의 집합이 같은 두 무방향 선은 서로 같다.")
    @Test
    void equals() {
        assertThat(new UndirectedLine(
                new Position(Row.TWO, Column.THREE),
                new Position(Row.FOUR, Column.THREE))
        ).isEqualTo(new UndirectedLine(
                new Position(Row.FOUR, Column.THREE),
                new Position(Row.TWO, Column.THREE))
        );
    }
}
