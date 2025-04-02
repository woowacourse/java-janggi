package janggi.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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

class MaStrategyTest {

    static final Position START_POSITION = new Position(4, 4);
    static final Position PATH_POSITION = new Position(5, 4);
    static final Position FIRST_DESTINATION = new Position(6, 3);
    static final Position SECOND_DESTINATION = new Position(6, 3);
    MaStrategy maStrategy = new MaStrategy();

    @DisplayName("동사남북 방향으로 1칸이동 후 대각선을 1칸이동할 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void canMove(Position destination) {
        boolean canMove = maStrategy.ableToMove(START_POSITION, destination, List.of(), List.of());
        assertThat(canMove).isTrue();
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

    @DisplayName("이동 불가능한 위치로는 이동이 불가능하다.")
    @ParameterizedTest
    @MethodSource()
    void canNotMove(Position destination) {
        boolean canMove = maStrategy.ableToMove(START_POSITION, destination, List.of(), List.of());
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

    @DisplayName("아군 장기말이 경로상에 장애물로 있을 경우 이동이 불가능하다.")
    @Test
    void canNotMoveBecauseAlliesInPath() {
        Piece alliesPiece = new Piece(PieceType.JOL, PATH_POSITION);
        boolean canMoveToFirstDestination =
                maStrategy.ableToMove(START_POSITION, FIRST_DESTINATION, List.of(), List.of(alliesPiece));
        boolean canMoveToSecondDestination =
                maStrategy.ableToMove(START_POSITION, SECOND_DESTINATION, List.of(), List.of(alliesPiece));

        assertAll(
                () -> assertThat(canMoveToFirstDestination).isFalse(),
                () -> assertThat(canMoveToSecondDestination).isFalse());
    }

    @DisplayName("아군 장기말이 목적지에 장애물로 있을 경우 이동이 불가능하다.")
    @Test
    void canNotMoveBecauseAlliesInDestination() {
        Piece alliesPiece = new Piece(PieceType.JOL, FIRST_DESTINATION);
        boolean canMove = maStrategy.ableToMove(START_POSITION, FIRST_DESTINATION, List.of(), List.of(alliesPiece));
        assertThat(canMove).isFalse();
    }

    @DisplayName("상대 장기말이 경로에 장애물로 있을 경우 이동이 불가능하다.")
    @Test
    void canNotMoveBecauseEnemyInPath() {
        Piece enemyPiece = new Piece(PieceType.JOL, PATH_POSITION);
        boolean canMoveToFirstDestination =
                maStrategy.ableToMove(START_POSITION, FIRST_DESTINATION, List.of(enemyPiece), List.of());
        boolean canMoveToSecondDestination =
                maStrategy.ableToMove(START_POSITION, SECOND_DESTINATION, List.of(enemyPiece), List.of());

        assertAll(
                () -> assertThat(canMoveToFirstDestination).isFalse(),
                () -> assertThat(canMoveToSecondDestination).isFalse());
    }

    @DisplayName("상대 장기말이 목적지에 있을 경우 이동이 가능하다.")
    @Test
    void canMoveWithEnemyInDestination() {
        Piece enemyPiece = new Piece(PieceType.JOL, FIRST_DESTINATION);
        boolean canMove = maStrategy.ableToMove(START_POSITION, FIRST_DESTINATION, List.of(enemyPiece), List.of());
        assertThat(canMove).isTrue();
    }
}