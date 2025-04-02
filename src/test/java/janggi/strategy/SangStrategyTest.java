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

class SangStrategyTest {

    static final Position START_POSITION = new Position(4, 4);
    static final Position DIRECT_PATH_POSITION = new Position(5, 4);
    static final Position FIRST_DIAGONAL_PATH_POSITION = new Position(6, 3);
    static final Position SECOND_DIAGONAL_PATH_POSITION = new Position(6, 5);
    static final Position FIRST_DESTINATION = new Position(7, 2);
    static final Position SECOND_DESTINATION = new Position(7, 6);
    SangStrategy strategy = new SangStrategy();

    @DisplayName("동사남북 방향으로 1칸 이동 후 같은 방향의 대각선으로 2칸 이동할 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void canMove(Position destination) {
        boolean canMove = strategy.ableToMove(START_POSITION, destination, List.of(), List.of());
        assertThat(canMove).isTrue();
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
    void canNotMove(Position destination) {
        boolean canMove = strategy.ableToMove(START_POSITION, destination, List.of(), List.of());
        assertThat(canMove).isFalse();
    }

    static Stream<Arguments> canNotMove() {
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
        Piece alliesPiece = new Piece(PieceType.JOL, pathPosition);
        boolean canMove = strategy.ableToMove(START_POSITION, destination, List.of(), List.of(alliesPiece));
        assertThat(canMove).isFalse();
    }

    static Stream<Arguments> canNotMoveBecauseAlliesInPath() {
        return Stream.of(
                Arguments.of(DIRECT_PATH_POSITION, FIRST_DESTINATION),
                Arguments.of(DIRECT_PATH_POSITION, SECOND_DESTINATION),
                Arguments.of(FIRST_DIAGONAL_PATH_POSITION, FIRST_DESTINATION),
                Arguments.of(SECOND_DIAGONAL_PATH_POSITION, SECOND_DESTINATION)
        );
    }

    @DisplayName("아군 장기말이 목적지에 장애물로 있을 경우 이동이 불가능하다.")
    @Test
    void canNotMoveBecauseAlliesInDestination() {
        Piece alliesPiece = new Piece(PieceType.JOL, FIRST_DESTINATION);
        boolean canMove = strategy.ableToMove(START_POSITION, FIRST_DESTINATION, List.of(), List.of(alliesPiece));
        assertThat(canMove).isFalse();
    }

    @DisplayName("상대 장기말이 경로안에 장애물로 있을 경우 이동이 불가능하다.")
    @ParameterizedTest
    @MethodSource()
    void canNotMoveBecauseEnemyInPath(Position pathPosition, Position destination) {
        Piece enemyPiece = new Piece(PieceType.JOL, pathPosition);
        boolean canMove = strategy.ableToMove(START_POSITION, destination, List.of(enemyPiece), List.of());
        assertThat(canMove).isFalse();
    }

    static Stream<Arguments> canNotMoveBecauseEnemyInPath() {
        return Stream.of(
                Arguments.of(DIRECT_PATH_POSITION, FIRST_DESTINATION),
                Arguments.of(DIRECT_PATH_POSITION, SECOND_DESTINATION),
                Arguments.of(FIRST_DIAGONAL_PATH_POSITION, FIRST_DESTINATION),
                Arguments.of(SECOND_DIAGONAL_PATH_POSITION, SECOND_DESTINATION)
        );
    }

    @DisplayName("상대 장기말이 목적지에 있을 경우 이동이 가능하다.")
    @Test
    void canMoveWithEnemyInDestination() {
        Piece enemyPiece = new Piece(PieceType.JOL, FIRST_DESTINATION);
        boolean canMove = strategy.ableToMove(START_POSITION, FIRST_DESTINATION, List.of(enemyPiece), List.of());
        assertThat(canMove).isTrue();
    }
}