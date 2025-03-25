package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class JolTest {

    static final Position START_POSITION = new Position(4, 4);
    static final Position DESTINATION_POSITION = new Position(5, 4);

    @DisplayName("초의 장기말을 이동시킬 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void canMoveWhenCho(Position destination) {
        Jol jol = new Jol(START_POSITION, CampType.CHO);

        Piece movedJol = jol.move(destination, List.of(), List.of());

        assertThat(movedJol.getPosition()).isEqualTo(destination);
    }

    static Stream<Arguments> canMoveWhenCho() {
        return Stream.of(
                Arguments.of(new Position(START_POSITION.x() + 1, START_POSITION.y())),
                Arguments.of(new Position(START_POSITION.x() - 1, START_POSITION.y())),
                Arguments.of(new Position(START_POSITION.x(), START_POSITION.y() - 1))
        );
    }

    @DisplayName("한의 장기말을 이동시킬 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void canMoveWhenHan(Position destination) {
        Jol jol = new Jol(START_POSITION, CampType.HAN);

        Piece movedJol = jol.move(destination, List.of(), List.of());

        assertThat(movedJol.getPosition()).isEqualTo(destination);
    }

    static Stream<Arguments> canMoveWhenHan() {
        return Stream.of(
                Arguments.of(new Position(START_POSITION.x() + 1, START_POSITION.y())),
                Arguments.of(new Position(START_POSITION.x() - 1, START_POSITION.y())),
                Arguments.of(new Position(START_POSITION.x(), START_POSITION.y() + 1))
        );
    }

    @DisplayName("졸은 뒤로 이동이 불가능하다.")
    @ParameterizedTest
    @MethodSource()
    void canNotMoveToBackPosition(Jol jol, Position backPosition) {
        assertThatThrownBy(() -> jol.move(backPosition, List.of(), List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> canNotMoveToBackPosition() {
        return Stream.of(
                Arguments.of(new Jol(START_POSITION, CampType.CHO), new Position(
                        START_POSITION.x(), START_POSITION.y() + 1)),
                Arguments.of(new Jol(START_POSITION, CampType.HAN), new Position(
                        START_POSITION.x(), START_POSITION.y() - 1))
        );
    }

    @DisplayName("장기말의 이동 규칙에 어긋난 경우 이동이 불가능합니다.")
    @ParameterizedTest
    @MethodSource()
    void canNotMoveBecauseRuleOfMove(Position destination) {
        Jol jol = new Jol(START_POSITION, CampType.CHO);

        assertThatThrownBy(() -> jol.move(destination, List.of(), List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> canNotMoveBecauseRuleOfMove() {
        return Stream.of(
                Arguments.of(new Position(START_POSITION.x() + 2, START_POSITION.y())),
                Arguments.of(new Position(START_POSITION.x() - 2, START_POSITION.y())),
                Arguments.of(new Position(START_POSITION.x(), START_POSITION.y() - 2))
        );
    }

    @DisplayName("아군 장기말이 장애물일 경우 해당 위치로 이동이 불가능하다.")
    @Test
    void canNotMoveToDestinationWithAllies() {
        Jol jol = new Jol(START_POSITION, CampType.CHO);
        Jol alliesPiece = new Jol(DESTINATION_POSITION, CampType.CHO);

        assertThatThrownBy(() -> jol.move(DESTINATION_POSITION, List.of(), List.of(alliesPiece)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    @DisplayName("상대 장기말이 장애물일 경우 해당 위치로 이동이 가능하다.")
    @Test
    void canMoveToDestinationWithEnemy() {
        Jol jol = new Jol(START_POSITION, CampType.CHO);
        Jol enemyPiece = new Jol(DESTINATION_POSITION, CampType.CHO);

        Piece movedJol = jol.move(DESTINATION_POSITION, List.of(enemyPiece), List.of());

        assertThat(movedJol.getPosition()).isEqualTo(DESTINATION_POSITION);
    }
}