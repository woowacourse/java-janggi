package janggi.starategy;

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

class ChaStrategyTest {

    static final Position START_POSITION = new Position(4, 4);
    static final Position MIDDLE_POSITION = new Position(5, 4);
    static final Position DESTINATION_POSITION = new Position(6, 4);
    static final Position OVER_POSITION = new Position(7, 4);
    ChaStrategy chaStrategy = new ChaStrategy();


    @DisplayName("동서남북 방향으로 끝까지 이동이 가능하다.")
    @ParameterizedTest
    @MethodSource()
    void canMove(Position destination) {
        boolean canMove = chaStrategy.ableToMove(START_POSITION, destination, List.of(), List.of());
        assertThat(canMove).isTrue();
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


    @DisplayName("이동 불가능한 위치로는 이동이 불가능하다.")
    @ParameterizedTest
    @MethodSource()
    void canNotMove(Position destination) {
        boolean canMove = chaStrategy.ableToMove(START_POSITION, destination, List.of(), List.of());
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

    @DisplayName("아군 장기말이 장애물일 경우 장애물 이전 위치까지 이동이 가능하다.")
    @Test
    void canMoveToPreviousPositionWithAllies() {
        Piece alliesPiece = new Piece(PieceType.JOL, DESTINATION_POSITION);
        boolean canMove = chaStrategy.ableToMove(START_POSITION, MIDDLE_POSITION, List.of(), List.of(alliesPiece));
        assertThat(canMove).isTrue();
    }

    @DisplayName("아군 장기말이 장애물일 경우 장애물 위치를 포함해 너머로 이동이 불가능하다.")
    @Test
    void canNotMoveToOverPositionWithAllies() {
        Piece alliesPiece = new Piece(PieceType.JOL, DESTINATION_POSITION);
        assertAll(
                () -> assertThat(
                        chaStrategy.ableToMove(START_POSITION, DESTINATION_POSITION, List.of(), List.of(alliesPiece)))
                        .isFalse(),
                () -> assertThat(chaStrategy.ableToMove(START_POSITION, OVER_POSITION, List.of(), List.of(alliesPiece)))
                        .isFalse()
        );
    }

    @DisplayName("상대 장기말이 장애물일 경우 장애물 위치까지 이동이 가능하다.")
    @Test
    void canMoveToDestinationWithEnemy() {
        Piece enemyPiece = new Piece(PieceType.JOL, DESTINATION_POSITION);
        boolean canMove = chaStrategy.ableToMove(START_POSITION, MIDDLE_POSITION, List.of(enemyPiece), List.of());
        assertThat(canMove).isTrue();
    }

    @DisplayName("상대 장기말이 장애물일 경우 장애물 위치를 제외하고 너머로 이동이 불가능하다.")
    @Test
    void canMoveToOverPositionWith() {
        Piece enemyPiece = new Piece(PieceType.JOL, DESTINATION_POSITION);
        boolean canMove = chaStrategy.ableToMove(START_POSITION, OVER_POSITION, List.of(enemyPiece), List.of());
        assertThat(canMove).isFalse();
    }
}