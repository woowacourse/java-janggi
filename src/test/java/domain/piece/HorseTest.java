package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HorseTest {

    private static final int DEFAULT_ROW = 5;
    private static final int DEFAULT_FILE = 5;
    private static final Side SIDE = Side.HAN;
    private static final Side OPPOSITE_SIDE = Side.CHO;
    private static final Soldier SAME_SIDE_PIECE = new Soldier(SIDE);
    private static final Soldier OPPOSITE_SIDE_PIECE = new Soldier(OPPOSITE_SIDE);
    private static final Intersection CURRENT_INTERSECTION = new Intersection(DEFAULT_ROW, DEFAULT_FILE);

    private static final Intersection LEFT_DESTINATION = new Intersection(
            CURRENT_INTERSECTION.getRow() - 2,
            CURRENT_INTERSECTION.getFile() - 1
    );
    private static final Intersection RIGHT_DESTINATION = new Intersection(
            CURRENT_INTERSECTION.getRow() - 2,
            CURRENT_INTERSECTION.getFile() + 1
    );

    @Test
    void 전진할_칸이_보드_범위_밖이라면_이동할_수_없다() {
        // given
        Horse horse = new Horse(SIDE);

        Intersection borderlineIntersection = new Intersection(1, 1);
        AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

        // when
        List<Intersection> movableIntersections = horse.movableIntersections(borderlineIntersection, emptyAlivePieces);

        // then
        boolean movableOutOfBoard = movableIntersections.stream()
                .anyMatch(Intersection::isOutOfBoard);

        assertThat(movableOutOfBoard).isFalse();
    }

    @ParameterizedTest
    @MethodSource("eachSidePieces")
    void 전진할_칸에_기물이_있으면_이동할_수_없다(Piece piece) {
        // given
        Horse horse = new Horse(SIDE);

        Intersection forwardIntersection = new Intersection(
                CURRENT_INTERSECTION.getRow() - 1,
                CURRENT_INTERSECTION.getFile()
        );
        AlivePieces alivePieces = new AlivePieces(Map.of(
                forwardIntersection, piece
        ));

        // when
        List<Intersection> movableIntersections = horse.movableIntersections(CURRENT_INTERSECTION, alivePieces);

        // then
        Assertions.assertThat(movableIntersections).doesNotContain(LEFT_DESTINATION, RIGHT_DESTINATION);
    }

    @Test
    void 종착지가_보드_범위_밖이라면_이동할_수_없다() {
        // given
        Horse horse = new Horse(SIDE);

        Intersection destinationOutOfBoard = new Intersection(2, 2);
        AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

        // when
        List<Intersection> movableIntersections = horse.movableIntersections(destinationOutOfBoard, emptyAlivePieces);

        // then
        boolean movableOutOfBoard = movableIntersections.stream()
                .anyMatch(Intersection::isOutOfBoard);

        assertThat(movableOutOfBoard).isFalse();
    }

    @Test
    void 종착지에_아군_기물이_있다면_이동할_수_없다() {
        // given
        Horse horse = new Horse(SIDE);

        AlivePieces alivePieces = new AlivePieces(Map.of(
                LEFT_DESTINATION, SAME_SIDE_PIECE,
                RIGHT_DESTINATION, SAME_SIDE_PIECE
        ));

        // when
        List<Intersection> movableIntersections = horse.movableIntersections(CURRENT_INTERSECTION, alivePieces);

        // then
        assertThat(movableIntersections).doesNotContain(LEFT_DESTINATION, RIGHT_DESTINATION);
    }

    @Test
    void 종착지에_상대_기물이_있다면_이동할_수_있다() {
        // given
        Horse horse = new Horse(SIDE);

        AlivePieces alivePieces = new AlivePieces(Map.of(
                LEFT_DESTINATION, OPPOSITE_SIDE_PIECE,
                RIGHT_DESTINATION, OPPOSITE_SIDE_PIECE
        ));

        // when
        List<Intersection> movableIntersections = horse.movableIntersections(CURRENT_INTERSECTION, alivePieces);

        // then
        assertThat(movableIntersections).contains(LEFT_DESTINATION, RIGHT_DESTINATION);
    }

    @Test
    void 종착지에_기물이_없다면_이동할_수_있다() {
        // given
        Horse horse = new Horse(SIDE);

        AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

        // when
        List<Intersection> movableIntersections = horse.movableIntersections(CURRENT_INTERSECTION, emptyAlivePieces);

        // then
        assertThat(movableIntersections).contains(LEFT_DESTINATION, RIGHT_DESTINATION);
    }

    private static Stream<Arguments> eachSidePieces() {
        return Stream.of(
                Arguments.of(SAME_SIDE_PIECE),
                Arguments.of(OPPOSITE_SIDE_PIECE)
        );
    }

    @Test
    void 본인의_점수를_반환한다() {
        // given
        Horse horse = new Horse(SIDE);
        double expected = 5;

        // when
        double actual = horse.getScore();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
