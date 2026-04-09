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

class PalacesTest {

    Palaces palaces;

    @BeforeEach
    void beforeEach() {
        Set<Position> choPositions = Set.of(
                new Position(Row.NINE, Column.FOUR),
                new Position(Row.NINE, Column.FIVE),
                new Position(Row.NINE, Column.SIX)
        );

        Set<UndirectedLine> choEdges = Set.of(
                new UndirectedLine(new Position(Row.NINE, Column.FOUR), new Position(Row.NINE, Column.FIVE)),
                new UndirectedLine(new Position(Row.NINE, Column.SIX), new Position(Row.NINE, Column.FIVE))
        );

        Palace choPalace = new Palace(choPositions, choEdges);

        Set<Position> hanPositions = Set.of(
                new Position(Row.TWO, Column.FOUR),
                new Position(Row.TWO, Column.FIVE),
                new Position(Row.TWO, Column.SIX)
        );

        Set<UndirectedLine> hanEdges = Set.of(
                new UndirectedLine(new Position(Row.TWO, Column.FOUR), new Position(Row.TWO, Column.FIVE)),
                new UndirectedLine(new Position(Row.TWO, Column.SIX), new Position(Row.TWO, Column.FIVE))
        );

        Palace hanPalace = new Palace(hanPositions, hanEdges);

        palaces = new Palaces(choPalace, hanPalace);
    }

    @DisplayName("두 지점이 같은 궁성 안에 있는지 여부를 반환한다.")
    @Test
    void areInSamePalace() {
        assertThat(
                palaces.areInSamePalace(
                        new Position(Row.NINE, Column.FIVE),
                        new Position(Row.NINE, Column.FOUR)
                )
        ).isTrue();

        assertThat(
                palaces.areInSamePalace(
                        new Position(Row.FIVE, Column.FIVE),
                        new Position(Row.NINE, Column.FOUR)
                )
        ).isFalse();

        assertThat(
                palaces.areInSamePalace(
                        new Position(Row.NINE, Column.FIVE),
                        new Position(Row.TWO, Column.FOUR)
                )
        ).isFalse();
    }

    @DisplayName("같은 궁성 안에 있고 인접해있는지 여부를 반환한다.")
    @Test
    void isAdjacentInSamePalace() {
        assertThat(
                palaces.isAdjacentInSamePalace(
                        new Position(Row.NINE, Column.FIVE),
                        new Position(Row.NINE, Column.FOUR)
                )
        ).isTrue();

        assertThat(
                palaces.isAdjacentInSamePalace(
                        new Position(Row.NINE, Column.FOUR),
                        new Position(Row.NINE, Column.SIX)
                )
        ).isFalse();

        assertThat(
                palaces.isAdjacentInSamePalace(
                        new Position(Row.NINE, Column.FIVE),
                        new Position(Row.TWO, Column.FOUR)
                )
        ).isFalse();
    }
}
