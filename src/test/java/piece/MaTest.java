package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static pieceProperty.PieceType.MA;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pieceProperty.Position;
import pieceProperty.Positions;

class MaTest {

    @DisplayName("마는 위치 정보를 가진다,")
    @Test
    void maBoardPosition() {
        //given
        Position position = new Position(4, 5);

        //when
        Ma ma = new Ma(position);

        //then
        assertThat(ma.getBoardPosition()).isEqualTo(new Position(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @ParameterizedTest
    @MethodSource("maNonCanMoveToPositionProvider")
    void nonCanMoveTo(Position position) {
        //given
        Ma ma = new Ma(new Position(5, 5));

        //when //then
        assertThatThrownBy(() -> ma.canMoveTo(position)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("자신의 위치를 기준으로 직선으로 한칸 + 대각선으로 한칸 이동할 수 있다면 true를 반환한다.")
    @ParameterizedTest
    @MethodSource("maCanMoveToPositionProvider")
    void canMoveTo(Position position) {
        //given
        Ma ma = new Ma(new Position(5, 5));

        //when
        boolean actual = ma.canMoveTo(position);

        //then
        assertThat(actual).isTrue();

    }

    @DisplayName("마은 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        Ma ma = new Ma(new Position(5, 5));
        Position futurePosition = new Position(3, 6);

        //when
        Positions actual = ma.makeRoute(futurePosition);

        //then
        assertThat(actual.getPositions()).containsExactly(new Position(4, 5), new Position(3, 6));
    }

    private static Stream<Arguments> maNonCanMoveToPositionProvider() {
        return Stream.of(Arguments.of(new Position(3, 5)), Arguments.of(new Position(3, 3)),
                Arguments.of(new Position(3, 7)), Arguments.of(new Position(5, 7)),
                Arguments.of(new Position(3, 7)), Arguments.of(new Position(7, 7)),
                Arguments.of(new Position(7, 5)), Arguments.of(new Position(7, 3)),
                Arguments.of(new Position(5, 3)));
    }

    private static Stream<Arguments> maCanMoveToPositionProvider() {
        return Stream.of(Arguments.of(new Position(3, 4)), Arguments.of(new Position(3, 6)),
                Arguments.of(new Position(4, 7)), Arguments.of(new Position(6, 7)),
                Arguments.of(new Position(7, 6)), Arguments.of(new Position(7, 4)),
                Arguments.of(new Position(6, 3)), Arguments.of(new Position(4, 3)));
    }

    @Test
    @DisplayName("자신의 타입 리턴 테스트")
    void pieceTypeTest() {
        Ma ma = new Ma(new Position(5, 5));

        assertThat(ma.getPieceType().equals(MA)).isTrue();
    }

    @Test
    @DisplayName("왕인지 물어보는 테스트")
    void isKingTest() {
        Ma ma = new Ma(new Position(5, 5));

        assertThat(ma.isKing()).isFalse();
    }

    @Test
    @DisplayName("포 판별 테스트")
    void isPoTest() {
        Ma ma = new Ma(new Position(5, 5));

        assertThat(ma.isPo()).isFalse();
    }

}
