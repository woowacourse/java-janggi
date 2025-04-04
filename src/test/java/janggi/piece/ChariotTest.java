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

public class ChariotTest {
    @Test
    @DisplayName("차 전진 테스트")
    void chariotUpTest() {
        //given
        Position startPosition = new Position(7, 3);
        Position arrivedPosition = new Position(3, 3);
        Piece chariot = new DefaultPiece(Team.CHO, PieceType.CHARIOT);
        //when
        Position movedPosition = chariot.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("차 후진 테스트")
    void chariotDownTest() {
        //given
        Position startPosition = new Position(3, 3);
        Position arrivedPosition = new Position(7, 3);
        Piece chariot = new DefaultPiece(Team.CHO, PieceType.CHARIOT);
        //when
        Position movedPosition = chariot.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("차 오른쪽 이동 테스트")
    void chariotRightTest() {
        //given
        Position startPosition = new Position(3, 5);
        Position arrivedPosition = new Position(3, 9);
        Piece chariot = new DefaultPiece(Team.CHO, PieceType.CHARIOT);
        //when
        Position movedPosition = chariot.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @Test
    @DisplayName("차 왼쪽 이동 테스트")
    void chariotLeftTest() {
        //given
        Position startPosition = new Position(3, 5);
        Position arrivedPosition = new Position(3, 1);
        Piece chariot = new DefaultPiece(Team.CHO, PieceType.CHARIOT);
        //when
        Position movedPosition = chariot.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    @ParameterizedTest
    @MethodSource("makePositionInPalaceForLeftUpCrossTest")
    @DisplayName("차 궁성 내 좌측 상단 대각선 이동 테스트")
    void moveLeftUpCrossInPalaceTest(Position startPosition, Position arrivedPosition) {
        //given
        Piece chariot = new DefaultPiece(Team.CHO, PieceType.CHARIOT);
        //when
        Position movedPosition = chariot.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    static Stream<Arguments> makePositionInPalaceForLeftUpCrossTest() {
        return Stream.of(
                Arguments.arguments(new Position(10, 6), new Position(8,4)),
                Arguments.arguments(new Position(9, 5), new Position(8,4))
        );
    }

    @ParameterizedTest
    @MethodSource("makeExceptionPositionInPalaceForLeftUpCrossTest")
    @DisplayName("차 궁성 내 좌측 상단 대각선 이동 예외 테스트")
    void moveLeftUpCrossInPalaceExceptionTest(Position startPosition, Position arrivedPosition) {
        //given
        Piece chariot = new DefaultPiece(Team.CHO, PieceType.CHARIOT);
        //when & then
        Assertions.assertThatThrownBy(() -> chariot.move(startPosition, arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> makeExceptionPositionInPalaceForLeftUpCrossTest() {
        return Stream.of(
                Arguments.arguments(new Position(10, 4), new Position(9,3)),
                Arguments.arguments(new Position(10, 6), new Position(7,3))
        );
    }

    @ParameterizedTest
    @MethodSource("makePositionInPalaceForLeftDownCrossTest")
    @DisplayName("차 궁성 내 좌측 하단 대각선 이동 테스트")
    void moveLeftDownCrossInPalaceTest(Position startPosition, Position arrivedPosition) {
        //given
        Piece chariot = new DefaultPiece(Team.CHO, PieceType.CHARIOT);
        //when
        Position movedPosition = chariot.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    static Stream<Arguments> makePositionInPalaceForLeftDownCrossTest() {
        return Stream.of(
                Arguments.arguments(new Position(9,5), new Position(10, 6)),
                Arguments.arguments(new Position(8,4), new Position(10, 6))
        );
    }

    @ParameterizedTest
    @MethodSource("makeExceptionPositionInPalaceForLeftDownCrossTest")
    @DisplayName("차 궁성 내 좌측 하단 대각선 이동 예외 테스트")
    void moveLeftDownCrossInPalaceExceptionTest(Position startPosition, Position arrivedPosition) {
        //given
        Piece chariot = new DefaultPiece(Team.CHO, PieceType.CHARIOT);
        //when & then
        Assertions.assertThatThrownBy(() -> chariot.move(startPosition, arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> makeExceptionPositionInPalaceForLeftDownCrossTest() {
        return Stream.of(
                Arguments.arguments(new Position(7, 3), new Position(10,6)),
                Arguments.arguments(new Position(9, 4), new Position(10,5))
        );
    }

    // todo 여기부터 우측
    @ParameterizedTest
    @MethodSource("makePositionInPalaceForRightUpCrossTest")
    @DisplayName("차 궁성 내 우측 상단 대각선 이동 테스트")
    void moveRightUpCrossInPalaceTest(Position startPosition, Position arrivedPosition) {
        //given
        Piece chariot = new DefaultPiece(Team.CHO, PieceType.CHARIOT);
        //when
        Position movedPosition = chariot.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    static Stream<Arguments> makePositionInPalaceForRightUpCrossTest() {
        return Stream.of(
                Arguments.arguments(new Position(10, 4), new Position(8,6)),
                Arguments.arguments(new Position(9, 5), new Position(8,6))
        );
    }

    @ParameterizedTest
    @MethodSource("makeExceptionPositionInPalaceForRightUpCrossTest")
    @DisplayName("차 궁성 내 우측 상단 대각선 이동 예외 테스트")
    void moveRightUpCrossInPalaceExceptionTest(Position startPosition, Position arrivedPosition) {
        //given
        Piece chariot = new DefaultPiece(Team.CHO, PieceType.CHARIOT);
        //when & then
        Assertions.assertThatThrownBy(() -> chariot.move(startPosition, arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> makeExceptionPositionInPalaceForRightUpCrossTest() {
        return Stream.of(
                Arguments.arguments(new Position(10, 4), new Position(7,7)),
                Arguments.arguments(new Position(9,5), new Position(7,7))
        );
    }

    @ParameterizedTest
    @MethodSource("makePositionInPalaceForRightDownCrossTest")
    @DisplayName("차 궁성 내 우측 하단 대각선 이동 테스트")
    void moveRightDownCrossInPalaceTest(Position startPosition, Position arrivedPosition) {
        //given
        Piece chariot = new DefaultPiece(Team.CHO, PieceType.CHARIOT);
        //when
        Position movedPosition = chariot.move(startPosition, arrivedPosition);
        //then
        Assertions.assertThat(movedPosition).isEqualTo(arrivedPosition);
    }

    static Stream<Arguments> makePositionInPalaceForRightDownCrossTest() {
        return Stream.of(
                Arguments.arguments(new Position(8, 4), new Position(9,5)),
                Arguments.arguments(new Position(8, 4), new Position(10,6)),
                Arguments.arguments(new Position(9, 5), new Position(10,6))
        );
    }

    @ParameterizedTest
    @MethodSource("makeExceptionPositionInPalaceForRightDownCrossTest")
    @DisplayName("차 궁성 내 우측 하단 대각선 이동 예외 테스트")
    void moveRightDownCrossInPalaceExceptionTest(Position startPosition, Position arrivedPosition) {
        //given
        Piece chariot = new DefaultPiece(Team.CHO, PieceType.CHARIOT);
        //when & then
        Assertions.assertThatThrownBy(() -> chariot.move(startPosition, arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> makeExceptionPositionInPalaceForRightDownCrossTest() {
        return Stream.of(
                Arguments.arguments(new Position(7, 3), new Position(9,5)),
                Arguments.arguments(new Position(8, 5), new Position(9,6))
        );
    }

    @Test
    @DisplayName("차가 장기판 범위 밖 좌표로 이동할 경우 예외 발생")
    void outOfBoardTest() {
        //given
        Position startPosition = new Position(3, 5);
        Position arrivedPosition = new Position(3, 0);
        Piece chariot = new DefaultPiece(Team.CHO, PieceType.CHARIOT);
        //when & then
        Assertions.assertThatThrownBy(() -> chariot.move(startPosition, arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

}
