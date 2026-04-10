package domain.movement;

import static domain.util.AssertUtils.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Intersection;
import domain.game.Side;
import domain.piece.AlivePieces;
import domain.piece.Piece;
import domain.piece.Soldier;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RouteTest {

    private static final Side SIDE = Side.HAN;
    private static final Side OPPOSITE_SIDE = Side.CHO;
    private static final Piece SAME_SIDE_PIECE = new Soldier(SIDE);
    private static final Piece OPPOSITE_SIDE_PIECE = new Soldier(OPPOSITE_SIDE);

    @Nested
    class 본인의_상태를_검증한다 {

        @Test
        void 경로가_없다면_예외를_던진다() {
            assertThatThrownBy(() -> new Route(List.of()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("경로에는 하나 이상의 좌표 정보가 필요합니다.");
        }

        @Test
        void 경로가_있다면_정상적으로_생성된다() {
            Intersection intersection = new Intersection(5, 5);

            assertThatNoException(() -> new Route(List.of(intersection)));
        }
    }

    @Nested
    class 목적지가_이용_가능한_상태인지를_판단한다 {

        private final Intersection destination = new Intersection(5, 5);

        @Test
        void 목적지에_아군_기물이_있다면_이용할_수_없다() {
            // given
            Route route = new Route(List.of(destination));

            AlivePieces alivePieces = new AlivePieces(Map.of(
                    destination, SAME_SIDE_PIECE
            ));

            // when
            boolean available = route.isDestinationAvailable(alivePieces, SIDE);

            // then
            assertThat(available).isFalse();
        }

        @Test
        void 목적지에_상대_기물이_있다면_이용할_수_있다() {
            // given
            Route route = new Route(List.of(destination));

            AlivePieces alivePieces = new AlivePieces(Map.of(
                    destination, OPPOSITE_SIDE_PIECE
            ));

            // when
            boolean available = route.isDestinationAvailable(alivePieces, SIDE);

            // then
            assertThat(available).isTrue();
        }

        @Test
        void 목적지가_비어_있다면_이용할_수_있다() {
            // given
            Route route = new Route(List.of(destination));

            AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

            // when
            boolean available = route.isDestinationAvailable(emptyAlivePieces, SIDE);

            // then
            assertThat(available).isTrue();
        }
    }

    @Nested
    class 경로를_통해_목적지로_이동할_수_있는지를_판단한다 {

        @Test
        void 경로에_기물이_있다면_이동할_수_없다() {
            // given
            Intersection path = new Intersection(5, 6);
            Intersection destination = new Intersection(5, 7);
            Route route = new Route(List.of(path, destination));

            Map<Intersection, Piece> pieces = Map.of(
                    path, OPPOSITE_SIDE_PIECE
            );
            AlivePieces alivePieces = new AlivePieces(pieces);

            // when
            boolean canReach = route.canReachDestinationThroughPath(alivePieces, SIDE);

            // then
            assertThat(canReach).isFalse();
        }

        @Test
        void 경로에_기물이_없다면_이동할_수_있다() {
            // given
            Intersection path = new Intersection(5, 6);
            Intersection destination = new Intersection(5, 7);
            Route route = new Route(List.of(path, destination));

            AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

            // when
            boolean canReach = route.canReachDestinationThroughPath(emptyAlivePieces, SIDE);

            // then
            assertThat(canReach).isTrue();
        }

        @Test
        void 이용할_수_없는_목적지라면_이동할_수_없다() {
            // given
            Intersection path = new Intersection(5, 6);
            Intersection destination = new Intersection(5, 7);
            Route route = new Route(List.of(path, destination));

            Map<Intersection, Piece> pieces = Map.of(
                    destination, SAME_SIDE_PIECE
            );
            AlivePieces alivePieces = new AlivePieces(pieces);

            // when
            boolean canReach = route.canReachDestinationThroughPath(alivePieces, SIDE);

            // then
            assertThat(canReach).isFalse();
        }
    }

    @Nested
    class 궁성만_포함하고_있는지를_반환한다 {

        @Test
        void 모든_좌표가_궁성이라면_true를_반환한다() {
            // given
            List<Intersection> palaceIntersections = List.of(
                    new Intersection(2, 5),
                    new Intersection(1, 4),
                    new Intersection(3, 6)
            );
            Route route = new Route(palaceIntersections);

            // when
            boolean containsOnlyPalace = route.containsOnlyPalace();

            // then
            assertThat(containsOnlyPalace).isTrue();
        }

        @Test
        void 궁성이_아닌_좌표가_포함되어_있다면_false를_반환한다() {
            // given
            Intersection notPalaceIntersection = new Intersection(5, 5);
            Intersection palaceIntersection = new Intersection(2, 5);

            Route route = new Route(List.of(
                    notPalaceIntersection,
                    palaceIntersection
            ));

            // when
            boolean containsOnlyPalace = route.containsOnlyPalace();

            // then
            assertThat(containsOnlyPalace).isFalse();
        }
    }

    @Test
    void 경로에_위치한_기물들을_반환한다() {
        // given
        Intersection firstNode = new Intersection(2, 2);
        Intersection secondNode = new Intersection(3, 3);
        Intersection destination = new Intersection(4, 4);

        AlivePieces alivePieces = new AlivePieces(Map.of(
                firstNode, SAME_SIDE_PIECE,
                secondNode, OPPOSITE_SIDE_PIECE
        ));

        // when
        Route route = new Route(List.of(firstNode, secondNode, destination));

        // then
        List<Piece> pieces = route.getPiecesOnPath(alivePieces);
        assertThat(pieces).containsExactly(SAME_SIDE_PIECE, OPPOSITE_SIDE_PIECE);
    }

    @Test
    void 목적지를_반환한다() {
        Intersection firstNode = new Intersection(4, 4);
        Intersection secondNode = new Intersection(4, 5);
        Intersection destination = new Intersection(4, 6);

        Route route = new Route(List.of(firstNode, secondNode, destination));

        Intersection actualDestination = route.getDestination();

        assertThat(actualDestination).isEqualTo(destination);
    }
}
