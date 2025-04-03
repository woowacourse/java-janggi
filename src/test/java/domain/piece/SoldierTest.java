package domain;

import static fixtures.PositionFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Piece;
import domain.piece.Soldier;
import domain.piece.TeamType;
import domain.position.Position;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SoldierTest {

    static Stream<Arguments> canMoveSoldierWhenCho_Success() {
        return Stream.of(
                Arguments.of(A1),
                Arguments.of(B2),
                Arguments.of(C1)
        );
    }

    static Stream<Arguments> canMoveSoldierWhenCho_Fail() {
        return Stream.of(
                Arguments.of(B0),
                Arguments.of(C2),
                Arguments.of(C0),
                Arguments.of(A0),
                Arguments.of(A2)
        );
    }

    static Stream<Arguments> canMoveSoldierWhenHan_Success() {
        return Stream.of(
                Arguments.of(A1),
                Arguments.of(C1),
                Arguments.of(B0)
        );
    }

    static Stream<Arguments> canMoveSoldierWhenHan_Fail() {
        return Stream.of(
                Arguments.of(B2),
                Arguments.of(C2),
                Arguments.of(C0),
                Arguments.of(A0),
                Arguments.of(A2)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("주위 칸이 비어있을 때 정상적으로 이동할 수 있다")
    void canMoveSoldierWhenCho_Success(Position movePosition) {
        Position currentPosition = B1;
        Piece solider = new Soldier(TeamType.CHO);

        assertThatNoException()
                .isThrownBy(() -> solider.validateCanMove(TeamType.CHO,currentPosition, movePosition, Map.of()));
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("갈 수 없는 지역이면 예외가 발생한다.")
    void canMoveSoldierWhenCho_Fail(Position movePosition) {
        Position currentPosition = B1;
        Piece solider = new Soldier(TeamType.CHO);

        assertThatThrownBy(() -> solider.validateCanMove(TeamType.CHO,currentPosition, movePosition, Map.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("주위 칸이 비어있을 때 정상적으로 이동할 수 있다")
    void canMoveSoldierWhenHan_Success(Position movePosition) {
        Position currentPosition = B1;
        Piece solider = new Soldier(TeamType.HAN);

        assertThatNoException()
                .isThrownBy(() -> solider.validateCanMove(TeamType.HAN,currentPosition, movePosition, Map.of()));
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("주위 칸이 비어있을 때 정상적으로 이동할 수 있다")
    void canMoveSoldierWhenHan_Fail(Position movePosition) {
        Position currentPosition = B1;
        Piece solider = new Soldier(TeamType.HAN);

        assertThatThrownBy(() -> solider.validateCanMove(TeamType.HAN,currentPosition, movePosition, Map.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("도착 칸에 아군이 있으면 이동할 수 없다.")
    void canMoveSoldier2() {
        Position movePosition = B2;
        Position position = B2;
        Piece solider1 = new Soldier(TeamType.CHO);

        Position currentPosition = B1;
        Piece solider2 = new Soldier(TeamType.CHO);

        assertThatThrownBy(() -> solider2.validateCanMove(TeamType.CHO,currentPosition, movePosition, Map.of(position,solider1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("도착 칸에 적이 있으면 이동할 수 있다.")
    void canMoveSoldier3() {
        Position movePosition = B3;
        Position position = B2;
        Piece solider1 = new Soldier(TeamType.CHO);

        Position currentPosition = B3;
        Piece solider2 = new Soldier(TeamType.HAN);

        assertThatNoException()
                .isThrownBy(() -> solider1.validateCanMove(TeamType.CHO,position, movePosition, Map.of(currentPosition,solider2)));
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("궁 안에서 이동은 특별 이동이 가능하다.")
    void canMoveSoldier4(TeamType teamType,Position from, Position to){
        Piece solider1 = new Soldier(teamType);
        assertThatNoException()
                .isThrownBy(() -> solider1.validateCanMove(teamType,from, to, Map.of()));
    }

    private static Stream<Arguments> canMoveSoldier4(){
        return Stream.of(
                Arguments.of(TeamType.HAN,D2, E1),
                Arguments.of(TeamType.HAN,F2, E1),
                Arguments.of(TeamType.HAN,E1, D0),
                Arguments.of(TeamType.HAN,E1, F0),
                Arguments.of(TeamType.CHO,D7, E8),
                Arguments.of(TeamType.CHO,F7, E8),
                Arguments.of(TeamType.CHO,E8, D9),
                Arguments.of(TeamType.CHO,E8, F9)
        );
    }
}
