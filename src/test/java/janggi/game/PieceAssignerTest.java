package janggi.game;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.data.fixture.ChoPiecePositionFixture;
import janggi.data.fixture.HanPiecePositionFixture;
import janggi.game.PieceAssigner;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.rule.CampType;
import janggi.rule.PieceAssignType;
import janggi.value.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PieceAssignerTest {

    @ParameterizedTest
    @DisplayName("초의 초기배치를 할 수 있다.")
    @MethodSource()
    void canAssignChoPiece(PieceType pieceType, List<Position> expectedPositions) {
        PieceAssigner pieceAssigner = new PieceAssigner();

        List<Piece> pieces = pieceAssigner.assignPieces(CampType.CHO, PieceAssignType.LEFT_SANG);

        List<Piece> piecesByType = pieces.stream().filter(piece -> piece.checkPieceType(pieceType)).toList();
        assertThat(piecesByType).extracting(Piece::getPosition)
                .containsExactlyInAnyOrderElementsOf(expectedPositions);
    }

    static Stream<Arguments> canAssignChoPiece() {
        return Stream.of(
                Arguments.of(PieceType.GUNG, ChoPiecePositionFixture.GUNG_POSITIONS),
                Arguments.of(PieceType.CHA, ChoPiecePositionFixture.CHA_POSITIONS),
                Arguments.of(PieceType.SA, ChoPiecePositionFixture.SA_POSITIONS),
                Arguments.of(PieceType.PO, ChoPiecePositionFixture.PO_POSITIONS),
                Arguments.of(PieceType.MA, ChoPiecePositionFixture.MA_POSITIONS_WITH_LEFT_SANG),
                Arguments.of(PieceType.SANG, ChoPiecePositionFixture.SANG_POSITIONS_WITH_LEFT_SANG),
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
        PieceAssigner pieceAssigner = new PieceAssigner();

        List<Piece> pieces = pieceAssigner.assignPieces(CampType.CHO, assignType);

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
        PieceAssigner pieceAssigner = new PieceAssigner();

        List<Piece> pieces = pieceAssigner.assignPieces(CampType.CHO, assignType);

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

    @ParameterizedTest
    @DisplayName("한의 초기배치를 할 수 있다.")
    @MethodSource()
    void canAssignHanPiece(PieceType pieceType, List<Position> expectedPositions) {
        PieceAssigner pieceAssigner = new PieceAssigner();

        List<Piece> pieces = pieceAssigner.assignPieces(CampType.HAN, PieceAssignType.LEFT_SANG);

        List<Piece> piecesByType = pieces.stream().filter(piece -> piece.checkPieceType(pieceType)).toList();
        assertThat(piecesByType).extracting(Piece::getPosition)
                .containsExactlyInAnyOrderElementsOf(expectedPositions);
    }

    static Stream<Arguments> canAssignHanPiece() {
        return Stream.of(
                Arguments.of(PieceType.GUNG, HanPiecePositionFixture.GUNG_POSITIONS),
                Arguments.of(PieceType.CHA, HanPiecePositionFixture.CHA_POSITIONS),
                Arguments.of(PieceType.SA, HanPiecePositionFixture.SA_POSITIONS),
                Arguments.of(PieceType.PO, HanPiecePositionFixture.PO_POSITIONS),
                Arguments.of(PieceType.MA, HanPiecePositionFixture.MA_POSITIONS_WITH_LEFT_SANG),
                Arguments.of(PieceType.SANG, HanPiecePositionFixture.SANG_POSITIONS_WITH_LEFT_SANG),
                Arguments.of(PieceType.BYUNG, HanPiecePositionFixture.BYUNG_POSITIONS)
        );
    }

    @ParameterizedTest
    @DisplayName("초기 배치 타입에 따라 한의 마 장기말의 초기 위치를 결정할 수 있다.")
    @MethodSource
    void canAssignMaByAssignTypeInHan(PieceAssignType assignType, List<Position> expectedMaPositions) {
        PieceAssigner pieceAssigner = new PieceAssigner();

        List<Piece> pieces = pieceAssigner.assignPieces(CampType.HAN, assignType);

        List<Piece> actualMaPositions = pieces.stream()
                .filter(piece -> piece.checkPieceType(PieceType.MA))
                .toList();
        assertThat(actualMaPositions).extracting(Piece::getPosition)
                .containsExactlyInAnyOrderElementsOf(expectedMaPositions);
    }

    static Stream<Arguments> canAssignMaByAssignTypeInHan() {
        return Stream.of(
                Arguments.of(PieceAssignType.LEFT_SANG,
                        HanPiecePositionFixture.MA_POSITIONS_WITH_LEFT_SANG),
                Arguments.of(PieceAssignType.RIGHT_SANG,
                        HanPiecePositionFixture.MA_POSITIONS_WITH_RIGHT_SANG),
                Arguments.of(PieceAssignType.IN_SANG,
                        HanPiecePositionFixture.MA_POSITIONS_WITH_IN_SANG),
                Arguments.of(PieceAssignType.OUT_SANG,
                        HanPiecePositionFixture.MA_POSITIONS_WITH_OUT_SANG)
        );
    }

    @ParameterizedTest
    @DisplayName("초기 배치 타입에 따라 한의 상 장기말의 초기 위치를 결정할 수 있다.")
    @MethodSource
    void canAssignSangByAssignTypeInHan(PieceAssignType assignType, List<Position> expectedSangPositions) {
        PieceAssigner pieceAssigner = new PieceAssigner();

        List<Piece> pieces = pieceAssigner.assignPieces(CampType.HAN, assignType);

        List<Piece> actualMaPositions = pieces.stream()
                .filter(piece -> piece.checkPieceType(PieceType.SANG))
                .toList();
        assertThat(actualMaPositions).extracting(Piece::getPosition)
                .containsExactlyInAnyOrderElementsOf(expectedSangPositions);
    }

    static Stream<Arguments> canAssignSangByAssignTypeInHan() {
        return Stream.of(
                Arguments.of(PieceAssignType.LEFT_SANG,
                        HanPiecePositionFixture.SANG_POSITIONS_WITH_LEFT_SANG),
                Arguments.of(PieceAssignType.RIGHT_SANG,
                        HanPiecePositionFixture.SANG_POSITIONS_WITH_RIGHT_SANG),
                Arguments.of(PieceAssignType.IN_SANG,
                        HanPiecePositionFixture.SANG_POSITIONS_WITH_IN_SANG),
                Arguments.of(PieceAssignType.OUT_SANG,
                        HanPiecePositionFixture.SANG_POSITIONS_WITH_OUT_SANG)
        );
    }
}