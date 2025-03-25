package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.value.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MaTest {

    static final Position START_POSITION = new Position(4, 4);
    static final Position PATH_POSITION = new Position(5, 4);
    static final Position FIRST_DESTINATION = new Position(6, 3);
    static final Position SECOND_DESTINATION = new Position(6, 3);


    @DisplayName("장기말을 이동시킬 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void canMove(Position destination) {
        Ma ma = new Ma(START_POSITION);

        Piece movedMa = ma.move(destination, List.of(), List.of());

        assertThat(movedMa.getPosition()).isEqualTo(destination);
    }

    static Stream<Arguments> canMove() {
        return Stream.of(
                Arguments.of(new Position(START_POSITION.x() + 2, START_POSITION.y() + 1)),
                Arguments.of(new Position(START_POSITION.x() + 2, START_POSITION.y() - 1)),
                Arguments.of(new Position(START_POSITION.x() + 1, START_POSITION.y() - 2)),
                Arguments.of(new Position(START_POSITION.x() + 1, START_POSITION.y() + 2)),
                Arguments.of(new Position(START_POSITION.x() - 1, START_POSITION.y() - 2)),
                Arguments.of(new Position(START_POSITION.x() - 1, START_POSITION.y() + 2)),
                Arguments.of(new Position(START_POSITION.x() - 2, START_POSITION.y() - 1)),
                Arguments.of(new Position(START_POSITION.x() - 2, START_POSITION.y() + 1))
        );
    }

    @DisplayName("장기말의 이동 규칙에 어긋난 경우 이동이 불가능합니다.")
    @ParameterizedTest
    @MethodSource()
    void canNotMoveBecauseRuleOfMove(Position destination) {
        Ma ma = new Ma(START_POSITION);

        assertThatThrownBy(() -> ma.move(destination, List.of(), List.of()))
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

    @DisplayName("아군 장기말이 경로상에 장애물로 있을 경우 이동이 불가능하다.")
    @Test
    void canNotMoveBecauseAlliesInPath() {
        Ma ma = new Ma(START_POSITION);
        Ma alliesPieceInPath = new Ma(PATH_POSITION);

        assertAll(
                () -> assertThatThrownBy(() -> ma.move(FIRST_DESTINATION, List.of(), List.of(alliesPieceInPath)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다."),
                () -> assertThatThrownBy(() -> ma.move(SECOND_DESTINATION, List.of(), List.of(alliesPieceInPath)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다.")
        );
    }

    @DisplayName("아군 장기말이 목적지에 장애물로 있을 경우 이동이 불가능하다.")
    @Test
    void canNotMoveBecauseAlliesInDestination() {
        Ma ma = new Ma(START_POSITION);
        Ma alliesPieceInDestination = new Ma(FIRST_DESTINATION);

        assertThatThrownBy(() -> ma.move(FIRST_DESTINATION, List.of(), List.of(alliesPieceInDestination)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    @DisplayName("상대 장기말이 경로에 장애물로 있을 경우 이동이 불가능하다.")
    @Test
    void canNotMoveBecauseEnemyInPath() {
        Ma ma = new Ma(START_POSITION);
        Ma enemyPiece = new Ma(PATH_POSITION);

        assertAll(
                () -> assertThatThrownBy(() -> ma.move(FIRST_DESTINATION, List.of(enemyPiece), List.of()))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다."),
                () -> assertThatThrownBy(() -> ma.move(SECOND_DESTINATION, List.of(enemyPiece), List.of()))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다.")
        );
    }

    @DisplayName("상대 장기말이 목적지에 있을 경우 이동이 가능하다.")
    @Test
    void canMoveWithEnemyInDestination() {
        Ma ma = new Ma(START_POSITION);
        Ma enemyPiece = new Ma(FIRST_DESTINATION);

        Piece movedMa = ma.move(FIRST_DESTINATION, List.of(enemyPiece), List.of());

        assertThat(movedMa.getPosition()).isEqualTo(FIRST_DESTINATION);
    }
}
