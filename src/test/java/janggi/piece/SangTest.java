package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.value.Position;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SangTest {

    static final Position START_POSITION = new Position(4, 4);
    static final Position DIRECT_PATH_POSITION = new Position(5, 4);
    static final Position FIRST_DIAGONAL_PATH_POSITION = new Position(6, 3);
    static final Position SECOND_DIAGONAL_PATH_POSITION = new Position(6, 5);
    static final Position FIRST_DESTINATION = new Position(7, 2);
    static final Position SECOND_DESTINATION = new Position(7, 6);

    @DisplayName("장기말을 이동시킬 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void canMove(Position destination) {
        Sang sang = new Sang(START_POSITION);

        Piece movedSang = sang.move(destination, List.of(), List.of());

        assertThat(movedSang.getPosition()).isEqualTo(destination);
    }

    static Stream<Arguments> canMove() {
        return Stream.of(
                Arguments.of(new Position(START_POSITION.x() - 3, START_POSITION.y() - 2)),
                Arguments.of(new Position(START_POSITION.x() - 3, START_POSITION.y() + 2)),
                Arguments.of(new Position(START_POSITION.x() + 3, START_POSITION.y() - 2)),
                Arguments.of(new Position(START_POSITION.x() + 3, START_POSITION.y() + 2)),
                Arguments.of(new Position(START_POSITION.x() - 2, START_POSITION.y() - 3)),
                Arguments.of(new Position(START_POSITION.x() + 2, START_POSITION.y() - 3)),
                Arguments.of(new Position(START_POSITION.x() - 2, START_POSITION.y() + 3)),
                Arguments.of(new Position(START_POSITION.x() + 2, START_POSITION.y() + 3))
        );
    }


    @DisplayName("장기말의 이동 규칙에 어긋난 경우 이동이 불가능합니다.")
    @ParameterizedTest
    @MethodSource()
    void canNotMoveBecauseRuleOfMove(Position destination) {
        Sang sang = new Sang(START_POSITION);

        assertThatThrownBy(() -> sang.move(destination, List.of(), List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> canNotMoveBecauseRuleOfMove() {
        return Stream.of(
                Arguments.of(new Position(START_POSITION.x() + 1, START_POSITION.y() + 1)),
                Arguments.of(new Position(START_POSITION.x() + 1, START_POSITION.y() - 1)),
                Arguments.of(new Position(START_POSITION.x() - 1, START_POSITION.y() + 1)),
                Arguments.of(new Position(START_POSITION.x() - 1, START_POSITION.y() - 1))
        );
    }

    @DisplayName("아군 장기말이 경로안에 장애물로 있을 경우 이동이 불가능하다.")
    @ParameterizedTest
    @MethodSource()
    void canNotMoveBecauseAlliesInPath(Position pathPosition, Position destination) {
        Sang sang = new Sang(START_POSITION);
        Sang alliesPiece = new Sang(pathPosition);

        assertAll(
                () -> assertThatThrownBy(() -> sang.move(destination, List.of(), List.of(alliesPiece)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다."),
                () -> assertThatThrownBy(() -> sang.move(destination, List.of(), List.of(alliesPiece)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다.")
        );
    }

    static Stream<Arguments> canNotMoveBecauseAlliesInPath() {
        return Stream.of(
                Arguments.of(DIRECT_PATH_POSITION, FIRST_DESTINATION),
                Arguments.of(DIRECT_PATH_POSITION, SECOND_DESTINATION),
                Arguments.of(FIRST_DIAGONAL_PATH_POSITION, FIRST_DESTINATION),
                Arguments.of(FIRST_DIAGONAL_PATH_POSITION, SECOND_DESTINATION),
                Arguments.of(SECOND_DIAGONAL_PATH_POSITION, FIRST_DESTINATION),
                Arguments.of(SECOND_DIAGONAL_PATH_POSITION, SECOND_DESTINATION)
        );
    }

    @DisplayName("아군 장기말이 목적지에 장애물로 있을 경우 이동이 불가능하다.")
    @Test
    void canNotMoveBecauseAlliesInDestination() {
        Sang sang = new Sang(START_POSITION);
        Sang alliesPiece = new Sang(FIRST_DESTINATION);

        assertThatThrownBy(() -> sang.move(FIRST_DESTINATION, List.of(), List.of(alliesPiece)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    @DisplayName("상대 장기말이 경로안에 장애물로 있을 경우 이동이 불가능하다.")
    @ParameterizedTest
    @MethodSource()
    void canNotMoveBecauseEnemyInPath(Position pathPosition, Position destination) {
        Sang sang = new Sang(START_POSITION);
        Sang enemyPiece = new Sang(pathPosition);

        assertAll(
                () -> assertThatThrownBy(() -> sang.move(destination, List.of(enemyPiece), List.of()))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다."),
                () -> assertThatThrownBy(() -> sang.move(destination, List.of(enemyPiece), List.of()))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다.")
        );
    }

    static Stream<Arguments> canNotMoveBecauseEnemyInPath() {
        return Stream.of(
                Arguments.of(DIRECT_PATH_POSITION, FIRST_DESTINATION),
                Arguments.of(DIRECT_PATH_POSITION, SECOND_DESTINATION),
                Arguments.of(FIRST_DIAGONAL_PATH_POSITION, FIRST_DESTINATION),
                Arguments.of(FIRST_DIAGONAL_PATH_POSITION, SECOND_DESTINATION),
                Arguments.of(SECOND_DIAGONAL_PATH_POSITION, FIRST_DESTINATION),
                Arguments.of(SECOND_DIAGONAL_PATH_POSITION, SECOND_DESTINATION)
        );
    }

    @DisplayName("상대 장기말이 목적지에 있을 경우 이동이 가능하다.")
    @Test
    void canMoveWithEnemyInDestination() {
        Sang sang = new Sang(START_POSITION);
        Sang enemyPiece = new Sang(FIRST_DESTINATION);

        Piece movedSang = sang.move(FIRST_DESTINATION, List.of(enemyPiece), List.of());

        Assertions.assertThat(movedSang.getPosition()).isEqualTo(FIRST_DESTINATION);
    }
}