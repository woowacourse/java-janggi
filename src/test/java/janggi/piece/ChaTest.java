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

class ChaTest {

    static final Position START_POSITION = new Position(4, 4);
    static final Position MIDDLE_POSITION = new Position(5, 4);
    static final Position DESTINATION_POSITION = new Position(6, 4);
    static final Position OVER_POSITION = new Position(7, 4);


    @DisplayName("장기말을 이동시킬 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void canMove(Position destination) {
        Cha cha = new Cha(START_POSITION);

        Piece movedCha = cha.move(destination, List.of(), List.of());

        assertThat(movedCha.getPosition()).isEqualTo(destination);
    }

    static Stream<Arguments> canMove() {
        return Stream.of(
                Arguments.of(new Position(START_POSITION.x() + 1, START_POSITION.y())),
                Arguments.of(new Position(START_POSITION.x() - 1, START_POSITION.y())),
                Arguments.of(new Position(START_POSITION.x(), START_POSITION.y() + 1)),
                Arguments.of(new Position(START_POSITION.x(), START_POSITION.y() - 1)),
                Arguments.of(new Position(8, START_POSITION.y())),
                Arguments.of(new Position(0, START_POSITION.y())),
                Arguments.of(new Position(START_POSITION.x(), 0)),
                Arguments.of(new Position(START_POSITION.x(), 9))
        );
    }


    @DisplayName("장기말의 이동 규칙에 어긋난 경우 이동이 불가능합니다.")
    @ParameterizedTest
    @MethodSource()
    void canNotMoveBecauseRuleOfMove(Position destination) {
        Cha cha = new Cha(START_POSITION);

        assertThatThrownBy(() -> cha.move(destination, List.of(), List.of()))
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

    @DisplayName("아군 장기말이 장애물일 경우 장애물 이전 위치까지 이동이 가능하다.")
    @Test
    void canMoveToPreviousPositionWithAllies() {
        Cha cha = new Cha(START_POSITION);
        Cha alliesPiece = new Cha(DESTINATION_POSITION);

        Piece movedCha = cha.move(MIDDLE_POSITION, List.of(), List.of(alliesPiece));
        assertThat(movedCha.getPosition()).isEqualTo(MIDDLE_POSITION);
    }

    @DisplayName("아군 장기말이 장애물일 경우 장애물 위치를 포함해 너머로 이동이 불가능하다.")
    @Test
    void canNotMoveToOverPositionWithAllies() {
        Cha cha = new Cha(START_POSITION);
        Cha alliesPiece = new Cha(DESTINATION_POSITION);

        assertAll(
                () -> assertThatThrownBy(() -> cha.move(DESTINATION_POSITION, List.of(), List.of(alliesPiece)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다."),
                () -> assertThatThrownBy(() -> cha.move(OVER_POSITION, List.of(), List.of(alliesPiece)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 이동이 불가능합니다.")
        );
    }

    @DisplayName("상대 장기말이 장애물일 경우 장애물 위치까지 이동이 가능하다.")
    @Test
    void canMoveToDestinationWithEnemy() {
        Cha cha = new Cha(START_POSITION);
        Cha enemyPiece = new Cha(DESTINATION_POSITION);

        Piece movedCha = cha.move(DESTINATION_POSITION, List.of(enemyPiece), List.of());
        assertThat(movedCha.getPosition()).isEqualTo(DESTINATION_POSITION);
    }

    @DisplayName("상대 장기말이 장애물일 경우 장애물 위치를 제외하고 너머로 이동이 불가능하다.")
    @Test
    void canMoveToOverPositionWith() {
        Cha cha = new Cha(START_POSITION);
        Cha enemyPiece = new Cha(DESTINATION_POSITION);

        assertThatThrownBy(() -> cha.move(OVER_POSITION, List.of(enemyPiece), List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }
}