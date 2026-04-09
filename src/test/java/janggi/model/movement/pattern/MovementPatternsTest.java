package janggi.model.movement.pattern;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import janggi.model.position.relative.RelativePosition;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MovementPatternsTest {

    @DisplayName("from과 to에 매칭되는 패턴을 찾아서 반환한다.")
    @Test
    void findMatchingPattern() {
        //given
        Position from = new Position(Row.TWO, Column.TWO);
        Position to = new Position(Row.FOUR, Column.THREE);

        MovementPattern pattern = MovementPattern.of(List.of(
                new RelativePosition(1, 0),
                new RelativePosition(1, 1)
        ));

        MovementPatterns patterns = MovementPatterns.of(List.of(pattern));

        //when & then
        assertThat(patterns.findMatchingPattern(from, to).get())
                .isEqualTo(pattern);
    }

    @DisplayName("매칭되는 패턴이 없으면 Optional.empty를 반환한다.")
    @Test
    void findMatchingPattern_not_matched() {
        //given
        Position from = new Position(Row.TWO, Column.TWO);
        Position to = new Position(Row.SIX, Column.THREE);

        MovementPattern pattern = MovementPattern.of(List.of(
                new RelativePosition(1, 0),
                new RelativePosition(1, 1)
        ));

        MovementPatterns patterns = MovementPatterns.of(List.of(pattern));

        //when & then
        assertThat(patterns.findMatchingPattern(from, to))
                .isEmpty();
    }
}
