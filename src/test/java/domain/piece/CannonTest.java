package domain.piece;

import static fixtures.PositionFixture.*;
import static org.assertj.core.api.Assertions.*;

import domain.position.Position;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CannonTest {
    static Stream<Arguments> canMoveCannon_Success() {
        return Stream.of(
                Arguments.of(D5, D4),
                Arguments.of(D6, D5),
                Arguments.of(D7, D6),
                Arguments.of(D8, D7),
                Arguments.of(D1, D2),
                Arguments.of(D0, D1),
                Arguments.of(B3, C3),
                Arguments.of(A3, C3),
                Arguments.of(F3, E3),
                Arguments.of(G3, F3),
                Arguments.of(H3, G3),
                Arguments.of(I3, F3)
        );
    }

    static Stream<Arguments> canMoveCannon_Fail() {
        return Stream.of(
                Arguments.of(D4, D5),
                Arguments.of(D2, D4),
                Arguments.of(C3, C2),
                Arguments.of(E3, D5),
                Arguments.of(E4,G3),
                Arguments.of(F5, H3)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("포 이동 성공 테스트")
    void canMoveCannon_Success(Position movePosition, Position otherPosition) {
        Position startPosition = D3;
        Piece cannon = new Cannon(TeamType.CHO);

        Piece other = new King(TeamType.HAN);

        assertThatNoException()
                .isThrownBy(()->cannon.validateCanMove(TeamType.CHO,startPosition,movePosition, Map.of(otherPosition,other)));
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("포 이동 실패 테스트")
    void canMoveCannon_Fail(Position movePosition, Position otherPosition) {
        Position startPosition = D3;
        Piece cannon = new Cannon(TeamType.CHO);

        Piece other = new King(TeamType.HAN);

        assertThatThrownBy(()->cannon.validateCanMove(TeamType.CHO,startPosition,movePosition, Map.of(otherPosition,other)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("이동 경로에 포가 있으면 예외를 반환한다")
    void canMoveCannon2() {
        Position startPosition = C2;
        Position expectedPosition = C4;
        Piece cannon = new Cannon(TeamType.HAN);

        Position otherPosition = Position.of(3, 2);
        Piece otherCannon = new Cannon(TeamType.HAN);

        assertThatThrownBy(()->cannon.validateCanMove(TeamType.HAN,startPosition,expectedPosition, Map.of(otherPosition,otherCannon)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("도착 지점에 아군이 있으면 예외를 반환한다")
    void canMoveCannon3() {
        Position startPosition = C2;
        Position expectedPosition = C4;
        Piece cannon = new Cannon(TeamType.HAN);

        Position jumpPosition = C3;
        Piece jump = new Soldier(TeamType.CHO);

        Position otherPosition = C4;
        Piece soldier = new Soldier(TeamType.HAN);

        assertThatThrownBy(()->cannon.validateCanMove(TeamType.HAN,startPosition,expectedPosition, Map.of(otherPosition,soldier,jumpPosition,jump)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("도착 지점에 적이 있으면 true를 반환한다")
    void canMoveCannon4() {
        Position startPosition = C2;
        Position expectedPosition = C4;
        Piece cannon = new Cannon(TeamType.HAN);

        Position jumpPosition = C3;
        Piece jump = new Soldier(TeamType.CHO);

        Position otherPosition = C4;
        Piece soldier = new Soldier(TeamType.CHO);

        assertThatNoException()
                .isThrownBy(()->cannon.validateCanMove(TeamType.HAN,startPosition,expectedPosition, Map.of(otherPosition,soldier,jumpPosition,jump)));
    }

    @Test
    @DisplayName("도착 지점에 포가 있으면 예외를 반환한다")
    void canMoveCannon5() {
        Position startPosition = C2;
        Position expectedPosition = C4;
        Piece cannon = new Cannon(TeamType.HAN);

        Position jumpPosition = C3;
        Piece jump = new Soldier(TeamType.CHO);

        Position otherPosition = C4;
        Piece otherCannon = new Cannon(TeamType.CHO);

        assertThatThrownBy(()->cannon.validateCanMove(TeamType.HAN,startPosition,expectedPosition, Map.of(otherPosition,otherCannon,jumpPosition,jump)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");

    }

    @Test
    @DisplayName("도착 지점까지 기물이 2개 이상이면 예외를 반환한다")
    void canMoveCannon6() {
        Position startPosition = C2;
        Position expectedPosition = C6;
        Piece cannon = new Cannon(TeamType.HAN);

        Position otherPosition1 = C4;
        Piece other1 = new Horse(TeamType.CHO);

        Position otherPosition2 = C5;
        Piece other2 = new Horse(TeamType.CHO);

        assertThatThrownBy(()->cannon.validateCanMove(TeamType.HAN,startPosition,expectedPosition, Map.of(otherPosition1,other1,otherPosition2,other2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("궁안에서는 특수하게 움직일 수 있다.")
    void canMoveCannonWhenPalace(Position from,Position to,Position jump){
        Piece cannon = new Cannon(TeamType.HAN);

        Piece jumpPiece = new Soldier(TeamType.CHO);

        Piece soldier = new Soldier(TeamType.CHO);

        assertThatNoException()
                .isThrownBy(()->cannon.validateCanMove(TeamType.HAN,from,to, Map.of(to,soldier,jump,jumpPiece)));
    }

    private static Stream<Arguments> canMoveCannonWhenPalace(){
        return Stream.of(
                Arguments.of(D9,F7,E8),
                Arguments.of(F7,D9,E8),
                Arguments.of(F9,D7,E8),
                Arguments.of(D7,F9,E8),
                Arguments.of(D0,F2,E1),
                Arguments.of(F2,D0,E1),
                Arguments.of(D2,F0,E1),
                Arguments.of(F0,D2,E1)
        );
    }
}
