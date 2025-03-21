package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.value.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PiecesTest {

    private static final Position STANDARD = new Position(4, 8);

    @DisplayName("장기말을 이동시킬 수 있다.")
    @Test
    void test1() {
        //given
        Pieces pieces = new Pieces(List.of(new Gung(STANDARD)));

        Position destination = new Position(5, 8);

        //when
        pieces.movePiece(List.of(), STANDARD, destination);

        Piece gung = pieces.getPieces().getFirst();
        assertThat(gung.getPosition()).isEqualTo(destination);
    }

    @DisplayName("이동시킬 장기말을 찾는 좌표가 범위를 벗어난 경우 예외를 발생시킨다.")
    @ParameterizedTest
    @MethodSource()
    void test2(Position invalidPosition) {
        //given
        Pieces pieces = new Pieces(List.of(new Gung(STANDARD)));

        //when & then
        assertThatThrownBy(() -> pieces.movePiece(List.of(), invalidPosition, new Position(5, 8)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] x좌표는 0~8, y좌표는 0~9 사이로 입력해주세요.");
    }

    static Stream<Arguments> test2() {
        return Stream.of(
                Arguments.of(new Position(-1, 0)),
                Arguments.of(new Position(9, 0)),
                Arguments.of(new Position(0, -1)),
                Arguments.of(new Position(0, 10))
        );
    }

    @DisplayName("목적지 좌표가 번위를 벗어난 경우 예외를 발생시킨다.")
    @ParameterizedTest
    @MethodSource()
    void test3(Position invalidPosition) {
        //given
        Pieces pieces = new Pieces(List.of(new Gung(STANDARD)));

        //when & then
        assertThatThrownBy(() -> pieces.movePiece(List.of(), STANDARD, invalidPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] x좌표는 0~8, y좌표는 0~9 사이로 입력해주세요.");
    }

    static Stream<Arguments> test3() {
        return Stream.of(
                Arguments.of(new Position(-1, 0)),
                Arguments.of(new Position(9, 0)),
                Arguments.of(new Position(0, -1)),
                Arguments.of(new Position(0, 10))
        );
    }

    @DisplayName("이동시킬 좌표에 장기말이 존재하지 않는 경우 예외를 발생시킨다.")
    @Test
    void test4() {
        //given
        Pieces pieces = new Pieces(List.of(new Gung(STANDARD)));

        Position invalidPosition = new Position(5, 8);

        assertThatThrownBy(() -> pieces.movePiece(List.of(), invalidPosition, new Position(6, 8)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 위치에 이동할 말이 존재하지 않습니다.");
    }

    @DisplayName("장기말이 목적지로 이동할 수 없는 경우 예외를 발생시킨다.")
    @Test
    void test5() {
        Pieces pieces = new Pieces(List.of(new Gung(STANDARD)));
        Position destination = new Position(7, 8);

        assertThatThrownBy(() -> pieces.movePiece(List.of(), STANDARD, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }
}