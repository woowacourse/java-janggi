package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ElephantTest {

    private static final int DEFAULT_ROW = 5;
    private static final int DEFAULT_FILE = 5;
    private static final Side SIDE = Side.HAN;
    private static final Side OPPOSITE_SIDE = Side.CHO;
    private static final Soldier SAME_SIDE_PIECE = new Soldier(SIDE);
    private static final Soldier OPPOSITE_SIDE_PIECE = new Soldier(OPPOSITE_SIDE);
    private static final Intersection CURRENT_INTERSECTION = new Intersection(DEFAULT_ROW, DEFAULT_FILE);

    private static final Intersection LEFT_PATH_NODE = new Intersection(
            CURRENT_INTERSECTION.row() - 2,
            CURRENT_INTERSECTION.file() - 1
    );
    private static final Intersection RIGHT_PATH_NODE = new Intersection(
            CURRENT_INTERSECTION.row() - 2,
            CURRENT_INTERSECTION.file() + 1
    );
    private static final Intersection BOTH_PATH_NODE = new Intersection(
            CURRENT_INTERSECTION.row() - 1,
            CURRENT_INTERSECTION.file()
    );

    private static final Intersection LEFT_DESTINATION = new Intersection(
            CURRENT_INTERSECTION.row() - 3,
            CURRENT_INTERSECTION.file() - 2
    );
    private static final Intersection RIGHT_DESTINATION = new Intersection(
            CURRENT_INTERSECTION.row() - 3,
            CURRENT_INTERSECTION.file() + 2
    );

    @Nested
    class 이동할_경로에_기물이_있으면_이동할_수_없다 {

        @Test
        void 왼쪽_경로에_기물이_있으면_왼쪽_목적지로_이동할_수_없다() {
            // given
            Elephant elephant = new Elephant(SIDE);
            AlivePieces alivePieces = new AlivePieces(Map.of(LEFT_PATH_NODE, OPPOSITE_SIDE_PIECE));

            // when
            boolean canMove = elephant.canMove(CURRENT_INTERSECTION, LEFT_DESTINATION, alivePieces);

            // then
            assertThat(canMove).isFalse();
        }

        @Test
        void 오른쪽_경로에_기물이_있으면_오른쪽_목적지로_이동할_수_없다() {
            // given
            Elephant elephant = new Elephant(SIDE);
            AlivePieces alivePieces = new AlivePieces(Map.of(RIGHT_PATH_NODE, OPPOSITE_SIDE_PIECE));

            // when
            boolean canMove = elephant.canMove(CURRENT_INTERSECTION, RIGHT_DESTINATION, alivePieces);

            // then
            assertThat(canMove).isFalse();
        }

        @Test
        void 양쪽_경로에_기물이_있으면_양쪽_목적지로_이동할_수_없다() {
            // given
            Elephant elephant = new Elephant(SIDE);
            AlivePieces alivePieces = new AlivePieces(Map.of(BOTH_PATH_NODE, OPPOSITE_SIDE_PIECE));

            // when
            List<Intersection> movableIntersections = elephant.movableIntersections(CURRENT_INTERSECTION, alivePieces);

            // then
            assertThat(movableIntersections).doesNotContain(LEFT_DESTINATION, RIGHT_DESTINATION);
        }
    }

    @Test
    void 이동할_경로가_보드_범위_밖이라면_이동할_수_없다() {
        // given
        Elephant elephant = new Elephant(SIDE);

        Intersection borderlineIntersection = new Intersection(1, 1);
        AlivePieces emptyAlivePlaces = new AlivePieces(Map.of());

        // when
        List<Intersection> movableIntersection = elephant.movableIntersections(
                borderlineIntersection,
                emptyAlivePlaces
        );

        // then
        boolean movableOutOfBoard = movableIntersection.stream()
                .anyMatch(Intersection::isOutOfBoard);

        assertThat(movableOutOfBoard).isFalse();
    }

    @Test
    void 종착지가_보드_범위_밖이라면_이동할_수_없다() {
        // given
        Elephant elephant = new Elephant(SIDE);

        Intersection destinationOutOfBoard = new Intersection(3, 3);
        AlivePieces emptyAlivePlaces = new AlivePieces(Map.of());

        // when
        List<Intersection> movableIntersection = elephant.movableIntersections(destinationOutOfBoard, emptyAlivePlaces);

        // then
        boolean movableOutOfBoard = movableIntersection.stream()
                .anyMatch(Intersection::isOutOfBoard);

        assertThat(movableOutOfBoard).isFalse();
    }

    @Test
    void 종착지에_아군_기물이_있다면_이동할_수_없다() {
        // given
        Elephant elephant = new Elephant(SIDE);

        AlivePieces alivePieces = new AlivePieces(Map.of(
                LEFT_DESTINATION, SAME_SIDE_PIECE,
                RIGHT_DESTINATION, SAME_SIDE_PIECE
        ));

        // when
        List<Intersection> movableIntersections = elephant.movableIntersections(CURRENT_INTERSECTION, alivePieces);

        // then
        assertThat(movableIntersections).doesNotContain(LEFT_DESTINATION, RIGHT_DESTINATION);
    }

    @Test
    void 종착지에_상대_기물이_있다면_이동할_수_있다() {
        // given
        Elephant elephant = new Elephant(SIDE);

        AlivePieces alivePieces = new AlivePieces(Map.of(
                LEFT_DESTINATION, OPPOSITE_SIDE_PIECE,
                RIGHT_DESTINATION, OPPOSITE_SIDE_PIECE
        ));

        // when
        List<Intersection> movableIntersections = elephant.movableIntersections(CURRENT_INTERSECTION, alivePieces);

        // then
        assertThat(movableIntersections).contains(LEFT_DESTINATION, RIGHT_DESTINATION);
    }

    @Test
    void 종착지에_기물이_없다면_이동할_수_있다() {
        // given
        Elephant elephant = new Elephant(SIDE);

        AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

        // when
        List<Intersection> movableIntersections = elephant.movableIntersections(CURRENT_INTERSECTION, emptyAlivePieces);

        // then
        assertThat(movableIntersections).contains(LEFT_DESTINATION, RIGHT_DESTINATION);
    }
}
