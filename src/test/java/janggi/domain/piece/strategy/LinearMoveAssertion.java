package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.Position;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public interface LinearMoveAssertion {
    int MIN_INDEX = 0;
    int MAX_ROW_INDEX = 9;
    int MAX_COL_INDEX = 8;

    default void assertLinearStrategy(MoveStrategy strategy) {
        int row = 4;
        int column = 4;
        List<Path> paths = strategy.findMovablePaths(Position.of(row, column));
        List<Position> destinations = paths.stream()
                .map(Path::destination)
                .toList();

        verifyHorizontalPaths(destinations, row, column);
        verifyVerticalPaths(destinations, row, column);
    }

    private void verifyHorizontalPaths(List<Position> destinations, int row, int column) {
        for (int i = MIN_INDEX; i <= MAX_COL_INDEX; i++) {
            checkHorizontalPresence(destinations, row, column, i);
        }
    }

    private void verifyVerticalPaths(List<Position> destinations, int row, int column) {
        for (int i = MIN_INDEX; i <= MAX_ROW_INDEX; i++) {
            checkVerticalPresence(destinations, row, column, i);
        }
    }

    private void checkHorizontalPresence(List<Position> destinations, int row, int column, int colIndex) {
        Position target = Position.of(row, colIndex);
        if (colIndex == column) {
            assertThat(destinations).doesNotContain(target);
            return;
        }
        assertThat(destinations).contains(target);
    }

    private void checkVerticalPresence(List<Position> destinations, int row, int column, int rowIndex) {
        Position target = Position.of(rowIndex, column);
        if (rowIndex == row) {
            assertThat(destinations).doesNotContain(target);
            return;
        }
        assertThat(destinations).contains(target);
    }
}
