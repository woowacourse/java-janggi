package janggi.setting;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.fixture.ChoPiecePositionFixture;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.value.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PieceAssignTypeTest {

    @ParameterizedTest
    @DisplayName("초의 초기 장기말 배치를 할 수 있다.")
    @MethodSource
    void canAssignPieceInCho(PieceType pieceType, List<Position> piecePositions) {
        List<Piece> pieces = PieceAssignType.LEFT_SANG.makeAssign(CampType.CHO);
        List<Piece> pieceInPieceType = pieces.stream().filter(piece -> piece.checkPieceType(pieceType)).toList();

        assertThat(pieceInPieceType).extracting(Piece::getPosition)
                .containsExactlyInAnyOrderElementsOf(piecePositions);
    }

    static Stream<Arguments> canAssignPieceInCho() {
        return Stream.of(
                Arguments.of(PieceType.GUNG, ChoPiecePositionFixture.GUNG_POSITIONS),
                Arguments.of(PieceType.CHA, ChoPiecePositionFixture.CHA_POSITIONS),
                Arguments.of(PieceType.SA, ChoPiecePositionFixture.SA_POSITIONS),
                Arguments.of(PieceType.PO, ChoPiecePositionFixture.PO_POSITIONS),
                Arguments.of(PieceType.JOL, ChoPiecePositionFixture.JOL_POSITIONS)
        );
    }


    @ParameterizedTest
    @DisplayName("초기 배치 타입에 따라 초의 마 장기말의 초기 위치를 결정할 수 있다.")
    @MethodSource
    void canAssignMaByAssignTypeInCho(
            PieceAssignType assignType,
            List<Position> expectedMaPositions
    ) {
        List<Piece> pieces = assignType.makeAssign(CampType.CHO);

        List<Piece> actualMaPositions = pieces.stream()
                .filter(piece -> piece.checkPieceType(PieceType.MA))
                .toList();

        assertThat(actualMaPositions).extracting(Piece::getPosition)
                .containsExactlyInAnyOrderElementsOf(expectedMaPositions);
    }

    static Stream<Arguments> canAssignMaByAssignTypeInCho() {
        return Stream.of(
                Arguments.of(PieceAssignType.LEFT_SANG,
                        ChoPiecePositionFixture.MA_POSITIONS_WITH_LEFT_SANG),
                Arguments.of(PieceAssignType.RIGHT_SANG,
                        ChoPiecePositionFixture.MA_POSITIONS_WITH_RIGHT_SANG),
                Arguments.of(PieceAssignType.IN_SANG,
                        ChoPiecePositionFixture.MA_POSITIONS_WITH_IN_SANG),
                Arguments.of(PieceAssignType.OUT_SANG,
                        ChoPiecePositionFixture.MA_POSITIONS_WITH_OUT_SANG)
        );
    }

    @ParameterizedTest
    @DisplayName("초기 배치 타입에 따라 초의 상 장기말의 초기 위치를 결정할 수 있다.")
    @MethodSource
    void canAssignSangByAssignTypeInCho(
            PieceAssignType assignType,
            List<Position> expectedSangPositions
    ) {
        List<Piece> pieces = assignType.makeAssign(CampType.CHO);
        List<Piece> actualSangPositions = pieces.stream()
                .filter(piece -> piece.checkPieceType(PieceType.SANG))
                .toList();

        assertThat(actualSangPositions).extracting(Piece::getPosition)
                .containsExactlyInAnyOrderElementsOf(expectedSangPositions);
    }

    static Stream<Arguments> canAssignSangByAssignTypeInCho() {
        return Stream.of(
                Arguments.of(PieceAssignType.LEFT_SANG,
                        ChoPiecePositionFixture.SANG_POSITIONS_WITH_LEFT_SANG),
                Arguments.of(PieceAssignType.RIGHT_SANG,
                        ChoPiecePositionFixture.SANG_POSITIONS_WITH_RIGHT_SANG),
                Arguments.of(PieceAssignType.IN_SANG,
                        ChoPiecePositionFixture.SANG_POSITIONS_WITH_IN_SANG),
                Arguments.of(PieceAssignType.OUT_SANG,
                        ChoPiecePositionFixture.SANG_POSITIONS_WITH_OUT_SANG)
        );
    }

//    @Test
//    @DisplayName("한의 초기 장기말 배치를 할 수 있다.")
//    void canAssignPieceInHanCamp() {
//        List<Piece> pieces = PieceAssignType.LEFT_SANG.makeAssign(CampType.HAN);
//
//        List<Piece> gung = pieces.stream().filter(piece -> piece.checkPieceType(PieceType.GUNG)).toList();
//        List<Piece> cha = pieces.stream().filter(piece -> piece.checkPieceType(PieceType.GUNG)).toList();
//        List<Piece> sa = pieces.stream().filter(piece -> piece.checkPieceType(PieceType.GUNG)).toList();
//        List<Piece> po = pieces.stream().filter(piece -> piece.checkPieceType(PieceType.GUNG)).toList();
//        List<Piece> jol = pieces.stream().filter(piece -> piece.checkPieceType(PieceType.GUNG)).toList();
//
//        assertAll(
//                () -> assertThat(gung).extracting(Piece::getPosition)
//                        .containsExactlyInAnyOrderElementsOf(HanPiecePositionFixture.GUNG_POSITIONS_IN_HAN),
//                () -> assertThat(cha).extracting(Piece::getPosition)
//                        .containsExactlyInAnyOrderElementsOf(HanPiecePositionFixture.CHA_POSITIONS_IN_HAN),
//                () -> assertThat(sa).extracting(Piece::getPosition)
//                        .containsExactlyInAnyOrderElementsOf(HanPiecePositionFixture.SA_POSITIONS_IN_HAN),
//                () -> assertThat(po).extracting(Piece::getPosition)
//                        .containsExactlyInAnyOrderElementsOf(HanPiecePositionFixture.PO_POSITIONS_IN_HAN),
//                () -> assertThat(jol).extracting(Piece::getPosition)
//                        .containsExactlyInAnyOrderElementsOf(HanPiecePositionFixture.JOL_POSITIONS_IN_HAN)
//        );
//    }
}