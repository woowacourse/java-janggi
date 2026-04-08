package domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Piece;
import domain.Position;
import domain.constant.Country;
import domain.constant.PieceType;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PoMoveRuleTest {
    @ParameterizedTest
    @MethodSource("validPoMovePosition")
    void 포_이동_가능_경우_확인_테스트(PieceType pieceType, Position start, Position end) {
        Piece choPo = new Piece(Country.CHO, pieceType);
        List<Piece> route = List.of(new Piece(Country.CHO, PieceType.SANG));
        PieceType endPiece = PieceType.MA;

        assertThat(pieceType.canMovePosition(start, end, choPo)).isTrue();
        assertThat(pieceType.isAvailableRoute(route, endPiece)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("validPoMovePosition")
    void 포_잡는_기물_포인_경우_이동_불가_확인_테스트(PieceType pieceType, Position start, Position end) {
        Piece choPo = new Piece(Country.CHO, pieceType);
        List<Piece> route = List.of(new Piece(Country.CHO, PieceType.SANG));
        PieceType endPiece = PieceType.PO;

        assertThat(pieceType.canMovePosition(start, end, choPo)).isTrue();
        assertThat(pieceType.isAvailableRoute(route, endPiece)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("validPoMovePosition")
    void 포_뛰어넘는_기물_포인_경우_이동_불가_확인_테스트(PieceType pieceType, Position start, Position end) {
        Piece choPo = new Piece(Country.CHO, pieceType);
        List<Piece> route = List.of(new Piece(Country.CHO, PieceType.PO));
        PieceType endPiece = PieceType.PO;

        assertThatThrownBy(() -> pieceType.isAvailableRoute(route, endPiece))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포를 넘을 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("validPoMovePosition")
    void 포_뛰어넘는_기물이_두개_이상인_경우_이동_불가_확인_테스트(PieceType pieceType, Position start, Position end) {
        Piece choPo = new Piece(Country.CHO, pieceType);
        List<Piece> route = List.of(new Piece(Country.CHO, PieceType.MA),
                new Piece(Country.CHO, PieceType.CHA));
        PieceType endPiece = PieceType.PO;

        assertThat(pieceType.isAvailableRoute(route, endPiece)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("validPoMovePosition")
    void 포_뛰어넘는_기물이_없는_경우_이동_불가_확인_테스트(PieceType pieceType, Position start, Position end) {
        Piece choPo = new Piece(Country.CHO, pieceType);
        List<Piece> route = List.of();
        PieceType endPiece = PieceType.PO;

        assertThat(pieceType.isAvailableRoute(route, endPiece)).isFalse();
    }

    private static Stream<Arguments> validPoMovePosition() {
        return Stream.of(
                Arguments.of(PieceType.PO, Position.create(5, 5), Position.create(1, 5)),
                Arguments.of(PieceType.PO, Position.create(5, 5), Position.create(9, 5)),
                Arguments.of(PieceType.PO, Position.create(5, 5), Position.create(5, 9)),
                Arguments.of(PieceType.PO, Position.create(5, 5), Position.create(5, 1))
        );
    }
}
