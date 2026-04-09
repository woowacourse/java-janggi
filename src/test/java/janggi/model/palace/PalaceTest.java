package janggi.model.palace;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import janggi.model.position.absolute.UndirectedLine;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PalaceTest {

    Palace palace;

    @BeforeEach
    void beforeEach() {
        Set<Position> palacePositions = Set.of(
                new Position(Row.NINE, Column.FOUR),
                new Position(Row.NINE, Column.FIVE),
                new Position(Row.NINE, Column.SIX)
        );

        Set<UndirectedLine> palaceEdges = Set.of(
                new UndirectedLine(new Position(Row.NINE, Column.FOUR), new Position(Row.NINE, Column.FIVE)),
                new UndirectedLine(new Position(Row.NINE, Column.SIX), new Position(Row.NINE, Column.FIVE))
        );

        palace = new Palace(palacePositions, palaceEdges);
    }

    @DisplayName("궁성 안에 포함돼 있는지 여부를 반환한다.")
    @Test
    void contains() {
        //given
        Position contained = new Position(Row.NINE, Column.FIVE);
        Position notContained = new Position(Row.FIVE, Column.FIVE);

        //when & then
        assertThat(palace.contains(contained))
                .isTrue();
        assertThat(palace.contains(notContained))
                .isFalse();
    }

    @DisplayName("궁성 안의 간선에 대해 인접해있는지 여부를 반환한다.")
    @Test
    void isAdjacent() {
        assertThat(palace.isAdjacent(new Position(Row.NINE, Column.FOUR), new Position(Row.NINE, Column.FIVE)))
                .isTrue();
        assertThat(palace.isAdjacent(new Position(Row.NINE, Column.SIX), new Position(Row.NINE, Column.FOUR)))
                .isFalse();
    }
}
