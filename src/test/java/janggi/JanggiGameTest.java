package janggi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class JanggiGameTest {

    @DisplayName("초의 마 초기배치를 할 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void test1(AssignType assignType, List<Position> maPositions) {
        //given
        JanggiGame janggiGame = new JanggiGame(assignType, AssignType.LEFT_TOP);

        //when
        List<Piece> choWorldPieces = janggiGame.getChoPieces();

        List<Position> maPiecePositions = choWorldPieces.stream()
                .filter(piece -> piece.getPieceType() == PieceType.MA)
                .map(Piece::getPosition)
                .toList();

        //then
        assertThat(maPiecePositions).containsExactlyInAnyOrderElementsOf(maPositions);
    }

    private static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of(AssignType.IN_TOP, List.of(new Position(1, 0), new Position(7, 0))),
                Arguments.of(AssignType.LEFT_TOP, List.of(new Position(2, 0), new Position(7, 0))),
                Arguments.of(AssignType.OUT_TOP, List.of(new Position(2, 0), new Position(6, 0))),
                Arguments.of(AssignType.RIGHT_TOP, List.of(new Position(1, 0), new Position(6, 0)))
        );
    }

    @DisplayName("초의 상 초기배치를 할 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void test2(AssignType assignType, List<Position> sangPositions) {
        //given
        JanggiGame janggiGame = new JanggiGame(assignType, AssignType.LEFT_TOP);

        //when
        List<Piece> choWorldPieces = janggiGame.getChoPieces();

        List<Position> sangPiecePositions = choWorldPieces.stream()
                .filter(piece -> piece.getPieceType() == PieceType.SANG)
                .map(Piece::getPosition)
                .toList();

        //then
        assertThat(sangPiecePositions).containsExactlyInAnyOrderElementsOf(sangPositions);
    }

    private static Stream<Arguments> test2() {
        return Stream.of(
                Arguments.of(AssignType.IN_TOP, List.of(new Position(2, 0), new Position(6, 0))),
                Arguments.of(AssignType.LEFT_TOP, List.of(new Position(1, 0), new Position(6, 0))),
                Arguments.of(AssignType.OUT_TOP, List.of(new Position(1, 0), new Position(7, 0))),
                Arguments.of(AssignType.RIGHT_TOP, List.of(new Position(2, 0), new Position(7, 0)))
        );
    }

    @DisplayName("한의 마 초기배치를 할 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void test3(AssignType assignType, List<Position> maPositions) {
        //given
        JanggiGame janggiGame = new JanggiGame(AssignType.RIGHT_TOP, assignType);

        //when
        List<Piece> hanWorldPieces = janggiGame.getHanPieces();

        List<Position> maPiecePositions = hanWorldPieces.stream()
                .filter(piece -> piece.getPieceType() == PieceType.MA)
                .map(Piece::getPosition)
                .toList();

        //then
        assertThat(maPiecePositions).containsExactlyInAnyOrderElementsOf(maPositions);
    }

    private static Stream<Arguments> test3() {
        return Stream.of(
                Arguments.of(AssignType.IN_TOP, List.of(new Position(1, 9), new Position(7, 9))),
                Arguments.of(AssignType.LEFT_TOP, List.of(new Position(2, 9), new Position(7, 9))),
                Arguments.of(AssignType.OUT_TOP, List.of(new Position(2, 9), new Position(6, 9))),
                Arguments.of(AssignType.RIGHT_TOP, List.of(new Position(1, 9), new Position(6, 9)))
        );
    }

    @DisplayName("한의 상 초기배치를 할 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void test4(AssignType assignType, List<Position> sangPositions) {
        //given
        JanggiGame janggiGame = new JanggiGame(AssignType.RIGHT_TOP, assignType);

        //when
        List<Piece> hanWorldPieces = janggiGame.getHanPieces();

        List<Position> sangPiecePositions = hanWorldPieces.stream()
                .filter(piece -> piece.getPieceType() == PieceType.SANG)
                .map(Piece::getPosition)
                .toList();

        //then
        assertThat(sangPiecePositions).containsExactlyInAnyOrderElementsOf(sangPositions);
    }

    private static Stream<Arguments> test4() {
        return Stream.of(
                Arguments.of(AssignType.IN_TOP, List.of(new Position(2, 9), new Position(6, 9))),
                Arguments.of(AssignType.LEFT_TOP, List.of(new Position(1, 9), new Position(6, 9))),
                Arguments.of(AssignType.OUT_TOP, List.of(new Position(1, 9), new Position(7, 9))),
                Arguments.of(AssignType.RIGHT_TOP, List.of(new Position(2, 9), new Position(7, 9)))
        );
    }

    @DisplayName("초의 나머지 말을 초기배치를 할 수 있다.")
    @Test
    void test5() {
        //given
        JanggiGame janggiGame = new JanggiGame(AssignType.RIGHT_TOP, AssignType.RIGHT_TOP);

        //when
        List<Piece> hanWorldPieces = janggiGame.getHanPieces();

        getPositions(PieceType.CHA, List.of(new Position(0, 9), new Position(8, 9)), hanWorldPieces);
        getPositions(PieceType.GUNG, List.of(new Position(4, 8)), hanWorldPieces);
        getPositions(PieceType.SA, List.of(new Position(3, 9), new Position(5, 9)), hanWorldPieces);
        getPositions(PieceType.JOL,
                List.of(new Position(0, 6), new Position(2, 6), new Position(4, 6), new Position(6, 6),
                        new Position(8, 6)), hanWorldPieces);
        getPositions(PieceType.PO, List.of(new Position(1, 7), new Position(7, 7)), hanWorldPieces);
    }

    private void getPositions(PieceType pieceType, List<Position> positions, List<Piece> pieces) {
        List<Position> maPiecePositions = pieces.stream()
                .filter(piece -> piece.getPieceType() == pieceType)
                .map(Piece::getPosition)
                .toList();
        assertThat(maPiecePositions)
                .containsExactlyInAnyOrderElementsOf(positions);
    }

}
