package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class GuardTest {
    @Test
    @DisplayName("사 전진 테스트")
    void guardUpTest() {
        //given
        Position startPosition = new Position(10, 4);
        Position arrivedPosition = new Position(9, 4);
        Piece guard = new PalacePiece(Team.CHO, PieceType.GUARD);
        //when
        Position movedPosition = guard.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("사 후진 테스트")
    void guardDownTest() {
        //given
        Position startPosition = new Position(9, 4);
        Position arrivedPosition = new Position(10, 4);
        Piece guard = new PalacePiece(Team.CHO, PieceType.GUARD);
        //when
        Position movedPosition = guard.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("사 오른쪽 이동 테스트")
    void guardRightTest() {
        //given
        Position startPosition = new Position(10, 4);
        Position arrivedPosition = new Position(10, 5);
        Piece guard = new PalacePiece(Team.CHO, PieceType.GUARD);
        //when
        Position movedPosition = guard.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("사 왼쪽 이동 테스트")
    void guardLeftTest() {
        //given
        Position startPosition = new Position(10, 6);
        Position arrivedPosition = new Position(10, 5);
        Piece guard = new PalacePiece(Team.CHO, PieceType.GUARD);
        //when
        Position movedPosition = guard.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("사 오른쪽 위 이동 테스트")
    void guardRightUpTest() {
        //given
        Position startPosition = new Position(9,5);
        Position arrivedPosition = new Position(8, 6);
        Piece guard = new PalacePiece(Team.CHO, PieceType.GUARD);
        //when
        Position movedPosition = guard.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("사 오른쪽 아래 이동 테스트")
    void guardRightDownTest() {
        //given
        Position startPosition = new Position(9,5);
        Position arrivedPosition = new Position(10, 6);
        Piece guard = new PalacePiece(Team.CHO, PieceType.GUARD);
        //when
        Position movedPosition = guard.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("사 왼쪽 위 이동 테스트")
    void guardLeftUpTest() {
        //given
        Position startPosition = new Position(9,5);
        Position arrivedPosition = new Position(8, 4);
        Piece guard = new PalacePiece(Team.CHO, PieceType.GUARD);
        //when
        Position movedPosition = guard.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("사 왼쪽 아래 이동 테스트")
    void guardLeftDownTest() {
        //given
        Position startPosition = new Position(9,5);
        Position arrivedPosition = new Position(10, 4);
        Piece guard = new PalacePiece(Team.CHO, PieceType.GUARD);
        //when
        Position movedPosition = guard.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("사 장기판 밖으로 이동 시 예외 발생 테스트")
    void outOfBoardGuardTest() {
        //given
        Position startPosition = new Position(10,5);
        Position arrivedPosition = new Position(11, 5);
        Piece guard = new PalacePiece(Team.CHO, PieceType.GUARD);
        //when & then
        Assertions.assertThatThrownBy(() -> guard.move(startPosition, arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }
    @ParameterizedTest
    @MethodSource("makeOutOfPalacePosition")
    @DisplayName("사 궁성 밖으로 이동 시 예외 발생 테스트")
    void outOfPalaceExceptionTest(Position startPosition, Position arrivedPosition) {
        //given
        Piece guard = new PalacePiece(Team.CHO, PieceType.GUARD);
        //when & then
        Assertions.assertThatThrownBy(() -> guard.move(startPosition, arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> makeOutOfPalacePosition() {
        return Stream.of(
                Arguments.arguments(new Position(10, 4), new Position(10, 3)),
                Arguments.arguments(new Position(10, 4), new Position(9, 3)),

                Arguments.arguments(new Position(9, 4), new Position(10, 3)),
                Arguments.arguments(new Position(9, 4), new Position(9, 3)),
                Arguments.arguments(new Position(9, 4), new Position(8, 3)),

                Arguments.arguments(new Position(8, 4), new Position(9, 3)),
                Arguments.arguments(new Position(8, 4), new Position(8, 3)),
                Arguments.arguments(new Position(8, 4), new Position(7, 3)),
                Arguments.arguments(new Position(8, 4), new Position(7, 4)),
                Arguments.arguments(new Position(8, 4), new Position(7, 5)),

                Arguments.arguments(new Position(8, 5), new Position(7, 4)),
                Arguments.arguments(new Position(8, 5), new Position(7, 5)),
                Arguments.arguments(new Position(8, 5), new Position(7, 6)),

                Arguments.arguments(new Position(8, 6), new Position(7, 5)),
                Arguments.arguments(new Position(8, 6), new Position(7, 6)),
                Arguments.arguments(new Position(8, 6), new Position(7, 7)),
                Arguments.arguments(new Position(8, 6), new Position(8, 7)),
                Arguments.arguments(new Position(8, 6), new Position(9, 7)),

                Arguments.arguments(new Position(9, 6), new Position(8, 7)),
                Arguments.arguments(new Position(9, 6), new Position(9, 7)),
                Arguments.arguments(new Position(9, 6), new Position(10, 7)),

                Arguments.arguments(new Position(10, 6), new Position(10, 7)),
                Arguments.arguments(new Position(10, 6), new Position(9, 7)),

                Arguments.arguments(new Position(1, 4), new Position(1, 3)),
                Arguments.arguments(new Position(1, 4), new Position(2, 3)),

                Arguments.arguments(new Position(2, 4), new Position(1, 3)),
                Arguments.arguments(new Position(2, 4), new Position(2, 3)),
                Arguments.arguments(new Position(2, 4), new Position(3, 3)),

                Arguments.arguments(new Position(3, 4), new Position(2, 3)),
                Arguments.arguments(new Position(3, 4), new Position(3, 3)),
                Arguments.arguments(new Position(3, 4), new Position(4, 3)),
                Arguments.arguments(new Position(3, 4), new Position(4, 4)),
                Arguments.arguments(new Position(3, 4), new Position(4, 5)),

                Arguments.arguments(new Position(3, 5), new Position(4, 4)),
                Arguments.arguments(new Position(3, 5), new Position(4, 5)),
                Arguments.arguments(new Position(3, 5), new Position(4, 6)),

                Arguments.arguments(new Position(3, 6), new Position(4, 5)),
                Arguments.arguments(new Position(3, 6), new Position(4, 6)),
                Arguments.arguments(new Position(3, 6), new Position(4, 7)),
                Arguments.arguments(new Position(3, 6), new Position(3, 7)),
                Arguments.arguments(new Position(3, 6), new Position(2, 7)),

                Arguments.arguments(new Position(2, 6), new Position(3, 7)),
                Arguments.arguments(new Position(2, 6), new Position(2, 7)),
                Arguments.arguments(new Position(2, 6), new Position(1, 7)),

                Arguments.arguments(new Position(1, 6), new Position(1, 7)),
                Arguments.arguments(new Position(1, 6), new Position(2, 7))
        );
    }
}
