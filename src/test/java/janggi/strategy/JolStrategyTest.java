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

class JolStrategyTest {

    static final Position START_POSITION = new Position(4, 4);
    static final Position DESTINATION_POSITION = new Position(5, 4);
    static final Position GUNSUNG_CENTER = new Position(4, 1);
    JolStrategy strategy = new JolStrategy();

    @DisplayName("궁성 밖에서는 앞과 양옆으로 1칸씩 이동 가능하다.")
    @ParameterizedTest
    @MethodSource()
    void canMoveOutGungSung(Position destination) {
        boolean canMove = strategy.ableToMove(START_POSITION, destination, List.of(), List.of());
        assertThat(canMove).isTrue();
    }

    static Stream<Arguments> canMoveOutGungSung() {
        return Stream.of(
                Arguments.of(new Position(START_POSITION.x() + 1, START_POSITION.y())),
                Arguments.of(new Position(START_POSITION.x() - 1, START_POSITION.y())),
                Arguments.of(new Position(START_POSITION.x(), START_POSITION.y() - 1))
        );
    }

    @DisplayName("궁성 안에서는 양옆, 앞, 전방 대각선으로 이동이 가능하다.")
    @ParameterizedTest
    @MethodSource()
    void canMoveInGungSung(Position destination) {
        boolean canMove = strategy.ableToMove(GUNSUNG_CENTER, destination, List.of(), List.of());
        assertThat(canMove).isTrue();
    }

    static Stream<Arguments> canMoveInGungSung() {
        return Stream.of(
                Arguments.of(new Position(GUNSUNG_CENTER.x() + 1, GUNSUNG_CENTER.y())),
                Arguments.of(new Position(GUNSUNG_CENTER.x() - 1, GUNSUNG_CENTER.y())),
                Arguments.of(new Position(GUNSUNG_CENTER.x(), GUNSUNG_CENTER.y() - 1)),
                Arguments.of(new Position(GUNSUNG_CENTER.x() + 1, GUNSUNG_CENTER.y() - 1)),
                Arguments.of(new Position(GUNSUNG_CENTER.x() - 1, GUNSUNG_CENTER.y() - 1))
        );
    }

    @DisplayName("궁성 안이든 밖이든 뒤로 이동이 불가능하다.")
    @ParameterizedTest
    @MethodSource()
    void canNotMoveToBackPosition(Position destination) {
        boolean canMove = strategy.ableToMove(START_POSITION, destination, List.of(), List.of());
        assertThat(canMove).isFalse();
    }

    static Stream<Arguments> canNotMoveToBackPosition() {
        return Stream.of(
                Arguments.of(new Position(START_POSITION.x(), START_POSITION.y() + 1)),
                Arguments.of(new Position(START_POSITION.x() + 1, START_POSITION.y() + 1)),
                Arguments.of(new Position(START_POSITION.x() - 1, START_POSITION.y() + 1)),
                Arguments.of(new Position(GUNSUNG_CENTER.x(), GUNSUNG_CENTER.y() + 1)),
                Arguments.of(new Position(GUNSUNG_CENTER.x() + 1, GUNSUNG_CENTER.y() + 1)),
                Arguments.of(new Position(GUNSUNG_CENTER.x() - 1, GUNSUNG_CENTER.y() + 1))
        );
    }

    @DisplayName("이동 불가능한 위치로는 이동이 불가능하다.")
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
                Arguments.of(new Position(START_POSITION.x(), START_POSITION.y() - 2))
        );
    }

    @DisplayName("아군 장기말이 장애물일 경우 해당 위치로 이동이 불가능하다.")
    @Test
    void canNotMoveToDestinationWithAllies() {
        Piece alliesPiece = new Piece(PieceType.JOL, DESTINATION_POSITION);
        boolean canMove = strategy.ableToMove(START_POSITION, DESTINATION_POSITION, List.of(), List.of(alliesPiece));
        assertThat(canMove).isFalse();
    }

    @DisplayName("상대 장기말이 장애물일 경우 해당 위치로 이동이 가능하다.")
    @Test
    void canMoveToDestinationWithEnemy() {
        Piece enemyPiece = new Piece(PieceType.JOL, DESTINATION_POSITION);
        boolean canMove = strategy.ableToMove(START_POSITION, DESTINATION_POSITION, List.of(enemyPiece), List.of());
        assertThat(canMove).isTrue();
    }
}