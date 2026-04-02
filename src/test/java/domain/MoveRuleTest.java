package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.constant.Country;
import domain.constant.PieceType;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MoveRuleTest {

    @ParameterizedTest
    @MethodSource("validMovePositions")
    void 각_기물별_이동_가능_좌표_확인_테스트(PieceType pieceType, Position start, Position end) {
        Piece choPiece = new Piece(Country.CHO, pieceType);
        boolean canMove = pieceType.canMovePosition(start, end, choPiece);
        assertThat(canMove).isTrue();
    }

    private static Stream<Arguments> validMovePositions() {
        return Stream.of(
                // 마 이동 가능 좌표
                Arguments.of(PieceType.MA, Position.create(3, 3), Position.create(1, 2)),
                Arguments.of(PieceType.MA, Position.create(3, 3), Position.create(1, 4)),
                Arguments.of(PieceType.MA, Position.create(3, 3), Position.create(5, 2)),
                Arguments.of(PieceType.MA, Position.create(3, 3), Position.create(5, 4)),
                Arguments.of(PieceType.MA, Position.create(3, 3), Position.create(2, 1)),
                Arguments.of(PieceType.MA, Position.create(3, 3), Position.create(4, 1)),
                Arguments.of(PieceType.MA, Position.create(3, 3), Position.create(2, 5)),
                Arguments.of(PieceType.MA, Position.create(3, 3), Position.create(4, 5)),

                // 차 이동 가능 좌표
                Arguments.of(PieceType.CHA, Position.create(3, 3), Position.create(1, 3)),
                Arguments.of(PieceType.CHA, Position.create(3, 3), Position.create(9, 3)),
                Arguments.of(PieceType.CHA, Position.create(3, 3), Position.create(3, 9)),
                Arguments.of(PieceType.CHA, Position.create(3, 3), Position.create(3, 1)),

                // 상 이동 가능 좌표
                Arguments.of(PieceType.SANG, Position.create(5, 5), Position.create(2, 3)),
                Arguments.of(PieceType.SANG, Position.create(5, 5), Position.create(2, 7)),
                Arguments.of(PieceType.SANG, Position.create(5, 5), Position.create(8, 3)),
                Arguments.of(PieceType.SANG, Position.create(5, 5), Position.create(8, 7)),
                Arguments.of(PieceType.SANG, Position.create(5, 5), Position.create(3, 2)),
                Arguments.of(PieceType.SANG, Position.create(5, 5), Position.create(7, 2)),
                Arguments.of(PieceType.SANG, Position.create(5, 5), Position.create(3, 8)),
                Arguments.of(PieceType.SANG, Position.create(5, 5), Position.create(7, 8)),

                // 사 이동 가능 좌표
                Arguments.of(PieceType.SA, Position.create(3, 3), Position.create(2, 3)),
                Arguments.of(PieceType.SA, Position.create(3, 3), Position.create(4, 3)),
                Arguments.of(PieceType.SA, Position.create(3, 3), Position.create(3, 2)),
                Arguments.of(PieceType.SA, Position.create(3, 3), Position.create(3, 4)),

                // 장 이동 가능 좌표
                Arguments.of(PieceType.JANG, Position.create(3, 3), Position.create(2, 3)),
                Arguments.of(PieceType.JANG, Position.create(3, 3), Position.create(4, 3)),
                Arguments.of(PieceType.JANG, Position.create(3, 3), Position.create(3, 2)),
                Arguments.of(PieceType.JANG, Position.create(3, 3), Position.create(3, 4)),

                // 졸 이동 가능 좌표 (초나라)
                Arguments.of(PieceType.JOL, Position.create(3, 3), Position.create(4, 3)),
                Arguments.of(PieceType.JOL, Position.create(3, 3), Position.create(3, 2)),
                Arguments.of(PieceType.JOL, Position.create(3, 3), Position.create(3, 4))
        );
    }

    @ParameterizedTest
    @MethodSource("invalidMovePositions")
    void 각_기물별_이동_불가_좌표_확인_테스트(PieceType pieceType, Position start, Position end) {
        Piece choPiece = new Piece(Country.CHO, pieceType);
        boolean canMove = pieceType.canMovePosition(start, end, choPiece);
        assertThat(canMove).isFalse();
    }

    private static Stream<Arguments> invalidMovePositions() {
        return Stream.of(
                // 마 이동 불가 좌표
                Arguments.of(PieceType.MA, Position.create(3, 3), Position.create(2, 3)),
                Arguments.of(PieceType.MA, Position.create(3, 3), Position.create(4, 4)),
                Arguments.of(PieceType.MA, Position.create(3, 3), Position.create(1, 3)),

                // 차 이동 불가 좌표
                Arguments.of(PieceType.CHA, Position.create(3, 3), Position.create(4, 4)),
                Arguments.of(PieceType.CHA, Position.create(3, 3), Position.create(5, 6)),

                // 상 이동 불가 좌표
                Arguments.of(PieceType.SANG, Position.create(5, 5), Position.create(4, 5)),
                Arguments.of(PieceType.SANG, Position.create(5, 5), Position.create(3, 3)),
                Arguments.of(PieceType.SANG, Position.create(5, 5), Position.create(2, 4)),

                // 사 이동 불가 좌표
                Arguments.of(PieceType.SA, Position.create(3, 3), Position.create(1, 3)),
                Arguments.of(PieceType.SA, Position.create(3, 3), Position.create(5, 5)),

                // 장 이동 불가 좌표
                Arguments.of(PieceType.JANG, Position.create(3, 3), Position.create(1, 3)),
                Arguments.of(PieceType.JANG, Position.create(3, 3), Position.create(5, 5)),

                // 졸 이동 불가 좌표 (초나라 기준)
                Arguments.of(PieceType.JOL, Position.create(3, 3), Position.create(2, 3)),
                Arguments.of(PieceType.JOL, Position.create(3, 3), Position.create(4, 4)),
                Arguments.of(PieceType.JOL, Position.create(3, 3), Position.create(5, 3))
        );
    }

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
