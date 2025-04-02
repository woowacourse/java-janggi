package janggi.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.value.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SaStrategyTest {

    static final Position START_POSITION = new Position(4, 1);
    static final Position DESTINATION_POSITION = new Position(5, 1);
    SaStrategy strategy = new SaStrategy();

    @DisplayName("궁성안에서 8방향으로 1칸씩 이동할 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void canMove(Position destination) {
        boolean canMove = strategy.ableToMove(START_POSITION, destination, List.of(), List.of());
        assertThat(canMove).isTrue();
    }

    static Stream<Arguments> canMove() {
        return Stream.of(
                Arguments.of(new Position(START_POSITION.x() + 1, START_POSITION.y())),
                Arguments.of(new Position(START_POSITION.x() - 1, START_POSITION.y())),
                Arguments.of(new Position(START_POSITION.x(), START_POSITION.y() + 1)),
                Arguments.of(new Position(START_POSITION.x(), START_POSITION.y() - 1)),
                Arguments.of(new Position(START_POSITION.x() + 1, START_POSITION.y() + 1)),
                Arguments.of(new Position(START_POSITION.x() + 1, START_POSITION.y() - 1)),
                Arguments.of(new Position(START_POSITION.x() - 1, START_POSITION.y() + 1)),
                Arguments.of(new Position(START_POSITION.x() - 1, START_POSITION.y() - 1))
        );
    }

    @DisplayName("궁성 밖으로는 이동이 불가능하다.")
    @ParameterizedTest
    @MethodSource()
    void canNotMove(Position destination) {
        boolean canMove = strategy.ableToMove(START_POSITION, destination, List.of(), List.of());
        assertThat(canMove).isFalse();
    }

    static Stream<Arguments> canNotMove() {
        return Stream.of(
                Arguments.of(new Position(START_POSITION.x() + 2, START_POSITION.y())),
                Arguments.of(new Position(START_POSITION.x() - 2, START_POSITION.y())),
                Arguments.of(new Position(START_POSITION.x(), START_POSITION.y() + 2)),
                Arguments.of(new Position(START_POSITION.x(), START_POSITION.y() - 2))
        );
    }


    @DisplayName("장기말의 이동 규칙에 어긋난 경우 이동이 불가능합니다.")
    @ParameterizedTest
    @MethodSource()
    void canNotMoveBecauseRuleOfMove(Position destination) {
        boolean canMove = strategy.ableToMove(START_POSITION, destination, List.of(), List.of());
        assertThat(canMove).isFalse();
    }

    static Stream<Arguments> canNotMoveBecauseRuleOfMove() {
        return Stream.of(
                Arguments.of(new Position(START_POSITION.x() + 2, START_POSITION.y())),
                Arguments.of(new Position(START_POSITION.x() - 2, START_POSITION.y())),
                Arguments.of(new Position(START_POSITION.x(), START_POSITION.y() + 2)),
                Arguments.of(new Position(START_POSITION.x(), START_POSITION.y() - 2))
        );
    }

    @DisplayName("아군 장기말이 목적지에 있을 경우 해당 위치로 이동이 불가능하다.")
    @Test
    void canNotMoveBecauseAlliesInDestination() {
        Piece alliesPiece = new Piece(PieceType.JOL, DESTINATION_POSITION);
        boolean canMove = strategy.ableToMove(START_POSITION, DESTINATION_POSITION, List.of(), List.of(alliesPiece));
        assertThat(canMove).isFalse();
    }

    @DisplayName("상대 장기말이 목적지에 있을 경우 해당 위치로 이동이 가능하다.")
    @Test
    void canMoveWithEnemyInDestination() {
        Piece enemyPiece = new Piece(PieceType.JOL, DESTINATION_POSITION);
        boolean canMove = strategy.ableToMove(START_POSITION, DESTINATION_POSITION, List.of(enemyPiece), List.of());
        assertThat(canMove).isTrue();
    }
}