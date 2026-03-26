package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.coordinate.Path;
import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.unit.Soldier;
import janggi.domain.side.Side;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SoldierTest {

    public static Stream<Arguments> path() {
        return null;
    }

    @Test
    @DisplayName("")
    void availablePoints() {

    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("path(): ")
    void path(Side side, List<Directions> expected) {
        Soldier soldier = new Soldier(side);

        List<Directions> directions = soldier.directions();

        assertThat(directions.equals(expected)).isTrue();
    }
}
