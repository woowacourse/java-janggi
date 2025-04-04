package janggi;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.JanggiBoard;
import janggi.fixture.PiecePositionFixture;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.setting.AssignType;
import janggi.domain.setting.CampType;
import janggi.domain.value.JanggiPosition;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class JanggiBoardTest {

    @DisplayName("초의 마 초기배치를 할 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void test1(AssignType assignType, List<JanggiPosition> maJanggiPositions) {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(assignType, AssignType.LEFT_SANG);

        //when
        List<Piece> choWorldPieces = janggiBoard.getChoPieces();

        List<JanggiPosition> maPieceJanggiPositions = choWorldPieces.stream()
                .filter(piece -> piece.getPieceType() == PieceType.MA)
                .map(Piece::getPosition)
                .toList();

        //then
        assertThat(maPieceJanggiPositions).containsExactlyInAnyOrderElementsOf(maJanggiPositions);
    }

    private static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of(AssignType.IN_SANG, List.of(new JanggiPosition(1, 9), new JanggiPosition(7, 9))),
                Arguments.of(AssignType.LEFT_SANG, List.of(new JanggiPosition(2, 9), new JanggiPosition(7, 9))),
                Arguments.of(AssignType.OUT_SANG, List.of(new JanggiPosition(2, 9), new JanggiPosition(6, 9))),
                Arguments.of(AssignType.RIGHT_SANG, List.of(new JanggiPosition(1, 9), new JanggiPosition(6, 9)))
        );
    }

    @DisplayName("초의 상 초기배치를 할 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void test2(AssignType assignType, List<JanggiPosition> sangJanggiPositions) {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(assignType, AssignType.LEFT_SANG);

        //when
        List<Piece> choWorldPieces = janggiBoard.getChoPieces();

        List<JanggiPosition> sangPieceJanggiPositions = choWorldPieces.stream()
                .filter(piece -> piece.getPieceType() == PieceType.SANG)
                .map(Piece::getPosition)
                .toList();

        //then
        assertThat(sangPieceJanggiPositions).containsExactlyInAnyOrderElementsOf(sangJanggiPositions);
    }

    private static Stream<Arguments> test2() {
        return Stream.of(
                Arguments.of(AssignType.IN_SANG, List.of(new JanggiPosition(2, 9), new JanggiPosition(6, 9))),
                Arguments.of(AssignType.LEFT_SANG, List.of(new JanggiPosition(1, 9), new JanggiPosition(6, 9))),
                Arguments.of(AssignType.OUT_SANG, List.of(new JanggiPosition(1, 9), new JanggiPosition(7, 9))),
                Arguments.of(AssignType.RIGHT_SANG, List.of(new JanggiPosition(2, 9), new JanggiPosition(7, 9)))
        );
    }

    @DisplayName("한의 마 초기배치를 할 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void test3(AssignType assignType, List<JanggiPosition> maJanggiPositions) {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(AssignType.RIGHT_SANG, assignType);

        //when
        List<Piece> hanWorldPieces = janggiBoard.getHanPieces();

        List<JanggiPosition> maPieceJanggiPositions = hanWorldPieces.stream()
                .filter(piece -> piece.getPieceType() == PieceType.MA)
                .map(Piece::getPosition)
                .toList();

        //then
        assertThat(maPieceJanggiPositions).containsExactlyInAnyOrderElementsOf(maJanggiPositions);
    }

    private static Stream<Arguments> test3() {
        return Stream.of(
                Arguments.of(AssignType.IN_SANG, List.of(new JanggiPosition(1, 0), new JanggiPosition(7, 0))),
                Arguments.of(AssignType.LEFT_SANG, List.of(new JanggiPosition(2, 0), new JanggiPosition(7, 0))),
                Arguments.of(AssignType.OUT_SANG, List.of(new JanggiPosition(2, 0), new JanggiPosition(6, 0))),
                Arguments.of(AssignType.RIGHT_SANG, List.of(new JanggiPosition(1, 0), new JanggiPosition(6, 0)))
        );
    }

    @DisplayName("한의 상 초기배치를 할 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void test4(AssignType assignType, List<JanggiPosition> sangJanggiPositions) {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(AssignType.RIGHT_SANG, assignType);

        //when
        List<Piece> hanWorldPieces = janggiBoard.getHanPieces();

        List<JanggiPosition> sangPieceJanggiPositions = hanWorldPieces.stream()
                .filter(piece -> piece.getPieceType() == PieceType.SANG)
                .map(Piece::getPosition)
                .toList();

        //then
        assertThat(sangPieceJanggiPositions).containsExactlyInAnyOrderElementsOf(sangJanggiPositions);
    }

    private static Stream<Arguments> test4() {
        return Stream.of(
                Arguments.of(AssignType.IN_SANG, List.of(new JanggiPosition(2, 0), new JanggiPosition(6, 0))),
                Arguments.of(AssignType.LEFT_SANG, List.of(new JanggiPosition(1, 0), new JanggiPosition(6, 0))),
                Arguments.of(AssignType.OUT_SANG, List.of(new JanggiPosition(1, 0), new JanggiPosition(7, 0))),
                Arguments.of(AssignType.RIGHT_SANG, List.of(new JanggiPosition(2, 0), new JanggiPosition(7, 0)))
        );
    }

    @DisplayName("초의 나머지 말을 초기배치를 할 수 있다.")
    @Test
    void test5() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(AssignType.RIGHT_SANG, AssignType.RIGHT_SANG);

        //when
        List<Piece> choWorldPieces = janggiBoard.getChoPieces();

        getPositions(PieceType.CHA, PiecePositionFixture.CHA_JANGGI_POSITIONS, choWorldPieces);
        getPositions(PieceType.GUNG, PiecePositionFixture.GUNG_JANGGI_POSITIONS, choWorldPieces);
        getPositions(PieceType.SA, PiecePositionFixture.SA_JANGGI_POSITIONS, choWorldPieces);
        getPositions(PieceType.JOL, PiecePositionFixture.JOL_JANGGI_POSITIONS, choWorldPieces);
        getPositions(PieceType.PO, PiecePositionFixture.PO_JANGGI_POSITIONS, choWorldPieces);
    }

    private void getPositions(PieceType pieceType, List<JanggiPosition> janggiPositions, List<Piece> pieces) {
        List<JanggiPosition> maPieceJanggiPositions = pieces.stream()
                .filter(piece -> piece.getPieceType() == pieceType)
                .map(Piece::getPosition)
                .toList();
        assertThat(maPieceJanggiPositions)
                .containsExactlyInAnyOrderElementsOf(janggiPositions);
    }

    @DisplayName("초나라 장기말 움직임 확인")
    @Test
    void test6() {
        //given
        JanggiPosition targetJanggiPosition = new JanggiPosition(4,8);
        JanggiPosition destination = new JanggiPosition(5,8);

        JanggiBoard janggiBoard = new JanggiBoard(AssignType.IN_SANG, AssignType.IN_SANG);
        List<Piece> choPieces = janggiBoard.getChoPieces();
        //when
        janggiBoard.startTurn(targetJanggiPosition, destination, CampType.CHO);
        List<Piece> pieces = choPieces.stream().filter(piece -> piece.getPieceType() == PieceType.GUNG).toList();
        Piece gung = pieces.getFirst();

        //then
        Assertions.assertThat(gung.getPosition()).isEqualTo(destination);
    }

    @DisplayName("한나라 장기말 움직임 확인")
    @Test
    void test7() {
        //given
        JanggiPosition targetJanggiPosition = new JanggiPosition(4,1);
        JanggiPosition destination = new JanggiPosition(5,1);

        JanggiBoard janggiBoard = new JanggiBoard(AssignType.IN_SANG, AssignType.IN_SANG);
        List<Piece> hanPieces = janggiBoard.getHanPieces();
        //when
        janggiBoard.startTurn(targetJanggiPosition, destination, CampType.HAN);
        List<Piece> pieces = hanPieces.stream().filter(piece -> piece.getPieceType() == PieceType.GUNG).toList();
        Piece gung = pieces.getFirst();

        //then
        Assertions.assertThat(gung.getPosition()).isEqualTo(destination);
    }

    @DisplayName("최종 스코어 확인 - 초나라")
    @Test
    void test8() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(AssignType.IN_SANG, AssignType.IN_SANG);

        //when
        int choScore = janggiBoard.requestChoTotalScore();

        //then
        Assertions.assertThat(choScore).isEqualTo(72);
    }

    @DisplayName("최종 스코어 확인 - 한나라")
    @Test
    void test9() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(AssignType.IN_SANG, AssignType.IN_SANG);

        //when
        double hanScore = janggiBoard.requestHanTotalScore();

        //then
        Assertions.assertThat(hanScore).isEqualTo(73.5);
    }

    @DisplayName("초나라 왕 죽었는지 확인")
    @Test
    void test10() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(AssignType.IN_SANG, AssignType.IN_SANG);

        //when
        boolean isDeadChoGung = janggiBoard.isChoCampCollapse();

        //then
        Assertions.assertThat(isDeadChoGung).isFalse();
    }

    @DisplayName("한나라 왕 죽었는지 확인")
    @Test
    void test11() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(AssignType.IN_SANG, AssignType.IN_SANG);

        //when
        boolean isDeadHanGung = janggiBoard.isHanCampCollapse();

        //then
        Assertions.assertThat(isDeadHanGung).isFalse();
    }
}
