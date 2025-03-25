package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.value.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GungTest {

    static final Position START_POSITION = new Position(4, 4);
    static final Position DESTINATION_POSITION = new Position(5, 4);

    @DisplayName("장기말을 이동시킬 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void canMove(Position destination) {
        Gung gung = new Gung(START_POSITION);

        Piece movedGung = gung.move(destination, List.of(), List.of());

        assertThat(movedGung.getPosition()).isEqualTo(destination);
    }

    static Stream<Arguments> canMove() {
        return Stream.of(
                Arguments.of(new Position(START_POSITION.x() + 1, START_POSITION.y())),
                Arguments.of(new Position(START_POSITION.x() - 1, START_POSITION.y())),
                Arguments.of(new Position(START_POSITION.x(), START_POSITION.y() + 1)),
                Arguments.of(new Position(START_POSITION.x(), START_POSITION.y() - 1))
        );
    }


    @DisplayName("장기말의 이동 규칙에 어긋난 경우 이동이 불가능합니다.")
    @ParameterizedTest
    @MethodSource()
    void canNotMoveBecauseRuleOfMove(Position destination) {
        Gung gung = new Gung(START_POSITION);

        assertThatThrownBy(() -> gung.move(destination, List.of(), List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> canNotMoveBecauseRuleOfMove() {
        return Stream.of(
                Arguments.of(new Position(START_POSITION.x() + 2, START_POSITION.y())),
                Arguments.of(new Position(START_POSITION.x() - 2, START_POSITION.y())),
                Arguments.of(new Position(START_POSITION.x(), START_POSITION.y() + 2)),
                Arguments.of(new Position(START_POSITION.x(), START_POSITION.y() - 2))
        );
    }

    @DisplayName("아군 장기말이 장애물일 경우 해당 위치로 이동이 불가능하다.")
    @Test
    void canNotMoveToDestinationWithAllies() {
        Gung gung = new Gung(START_POSITION);
        Gung alliesPiece = new Gung(DESTINATION_POSITION);

        assertThatThrownBy(() -> gung.move(DESTINATION_POSITION, List.of(), List.of(alliesPiece)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    @DisplayName("상대 장기말이 장애물일 경우 장애물 위치까지 이동이 가능하다.")
    @Test
    void canMoveToDestinationWithEnemy() {
        Gung gung = new Gung(START_POSITION);
        Cha enemyPiece = new Cha(DESTINATION_POSITION);

        Piece movedGung = gung.move(DESTINATION_POSITION, List.of(enemyPiece), List.of());
        assertThat(movedGung.getPosition()).isEqualTo(DESTINATION_POSITION);
    }
}