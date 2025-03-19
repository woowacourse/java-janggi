import static org.assertj.core.api.Assertions.assertThat;

import janggi.Board;
import janggi.Janggi;
import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Position;
import janggi.piece.PositionSide;
import janggi.piece.Soldier;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class JanggiTest {
    private Janggi janggi;
    private Board board;

    static Stream<Arguments> getHorseElephant() {
        return Stream.of(
                Arguments.arguments(
                        PositionSide.LEFT,
                        PositionSide.LEFT,
                        PositionSide.LEFT,
                        PositionSide.LEFT,
                        List.of(
                                new Position(10, 2),
                                new Position(10, 7),
                                new Position(1, 2),
                                new Position(1, 7)
                        )
                )
        );
    }

    @BeforeEach
    void setUp() {
        board = new Board(new HashMap<>());
        janggi = new Janggi(board);
        janggi.initializeBoard(
                PositionSide.LEFT, PositionSide.LEFT, PositionSide.RIGHT, PositionSide.RIGHT
        );
    }

    @DisplayName("보드를 초기화하면 청궁이 95, 홍궁이 25에 배치된다.")
    @Test
    void initializeGeneral() {
        Assertions.assertAll(
                () -> assertThat(board.getPiece(new Position(9, 5))).isInstanceOf(General.class),
                () -> assertThat(board.getPiece(new Position(2, 5))).isInstanceOf(General.class)
        );
    }

    @DisplayName("보드를 초기화하면 청차가 01, 09, 홍차가 11, 19에 배치된다.")
    @Test
    void initializeChariot() {
        Assertions.assertAll(
                () -> assertThat(board.getPiece(new Position(10, 1))).isInstanceOf(Chariot.class),
                () -> assertThat(board.getPiece(new Position(10, 9))).isInstanceOf(Chariot.class),
                () -> assertThat(board.getPiece(new Position(1, 1))).isInstanceOf(Chariot.class),
                () -> assertThat(board.getPiece(new Position(1, 9))).isInstanceOf(Chariot.class)
        );
    }

    @DisplayName("보드를 초기화하면 청포가 82, 88, 홍포가 32, 38에 배치된다.")
    @Test
    void initializeCannon() {
        Assertions.assertAll(
                () -> assertThat(board.getPiece(new Position(8, 2))).isInstanceOf(Cannon.class),
                () -> assertThat(board.getPiece(new Position(8, 8))).isInstanceOf(Cannon.class),
                () -> assertThat(board.getPiece(new Position(3, 2))).isInstanceOf(Cannon.class),
                () -> assertThat(board.getPiece(new Position(3, 8))).isInstanceOf(Cannon.class)
        );
    }

    @DisplayName("보드를 초기화하면 청사가 04, 06, 홍사가 14, 16에 배치된다.")
    @Test
    void initializeGuard() {
        Assertions.assertAll(
                () -> assertThat(board.getPiece(new Position(10, 4))).isInstanceOf(Guard.class),
                () -> assertThat(board.getPiece(new Position(10, 6))).isInstanceOf(Guard.class),
                () -> assertThat(board.getPiece(new Position(1, 4))).isInstanceOf(Guard.class),
                () -> assertThat(board.getPiece(new Position(1, 6))).isInstanceOf(Guard.class)
        );
    }

    @DisplayName("보드를 초기화하면 청졸이 71, 73, 75, 77, 79, 홍졸이 41, 43, 45, 47, 49에 배치된다.")
    @Test
    void initializeSoldier() {
        Assertions.assertAll(
                () -> assertThat(board.getPiece(new Position(7, 1))).isInstanceOf(Soldier.class),
                () -> assertThat(board.getPiece(new Position(7, 3))).isInstanceOf(Soldier.class),
                () -> assertThat(board.getPiece(new Position(7, 5))).isInstanceOf(Soldier.class),
                () -> assertThat(board.getPiece(new Position(7, 7))).isInstanceOf(Soldier.class),
                () -> assertThat(board.getPiece(new Position(7, 9))).isInstanceOf(Soldier.class),

                () -> assertThat(board.getPiece(new Position(4, 1))).isInstanceOf(Soldier.class),
                () -> assertThat(board.getPiece(new Position(4, 3))).isInstanceOf(Soldier.class),
                () -> assertThat(board.getPiece(new Position(4, 5))).isInstanceOf(Soldier.class),
                () -> assertThat(board.getPiece(new Position(4, 7))).isInstanceOf(Soldier.class),
                () -> assertThat(board.getPiece(new Position(4, 9))).isInstanceOf(Soldier.class)
        );
    }

    @DisplayName("보드를 초기화하면 입력 값에 따라 마를 배치한다.")
    @MethodSource("getHorseElephant")
    @ParameterizedTest
    void initializeHorse(
            PositionSide blueLeftHorsePosition,
            PositionSide blueRightHorsePosition,
            PositionSide redLeftHorsePosition,
            PositionSide redRightHorsePosition,
            List<Position> expectedPosition
    ) {
        board = new Board(new HashMap<>());
        janggi = new Janggi(board);
        janggi.initializeBoard(
                blueLeftHorsePosition,
                blueRightHorsePosition,
                redLeftHorsePosition,
                redRightHorsePosition
        );

        expectedPosition.forEach(position -> assertThat(board.getPiece(position)).isInstanceOf(Horse.class));
    }
}
