package domain.move.rule;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import domain.piece.AlivePieces;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.move.Path;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@DisplayName("다른 기물을 뛰어 넘을 수 없는 규칙 테스트")
class BasicRuleTest {

    private BasicRule basicRule;

    @BeforeEach
    void setUp() {
        basicRule = new BasicRule();
    }

    @DisplayName("경유지에 기물이 있으면 해당 경로를 사용할 수 없다")
    @ParameterizedTest
    @EnumSource(value = Side.class, names = "NONE", mode = EnumSource.Mode.EXCLUDE)
    void 경유지에_기물이_있으면_해당_경로를_사용할_수_없다(Side sideOfBlockingPiece) {
        // given
        Side mySide = Side.CHO;
        Intersection destination = new Intersection(3, 3);
        Intersection blockedPassing = new Intersection(5, 3);

        List<Path> paths = List.of(
                createPath(destination, blockedPassing, new Intersection(4, 3))
        );

        AlivePieces alivePieces = createAlivePieces(blockedPassing, createDefaultPiece(sideOfBlockingPiece));

        // when
        List<Intersection> movableDestinations = basicRule
                .movableDestinations(mySide, paths, alivePieces);

        // then
        assertThat(movableDestinations).isEmpty();
    }

    @DisplayName("경유지에 기물이 없으면 해당 경로를 사용할 수 있다")
    @Nested
    class 경유지에_기물이_없으면_해당_경로를_사용할_수_있다 {

        @DisplayName("목적지에 있는 기물이 아군이면 이동할 수 없다")
        @ParameterizedTest
        @EnumSource(value = Side.class, names = "NONE", mode = EnumSource.Mode.EXCLUDE)
        void 목적지에_있는_기물이_아군이면_이동할_수_없다(Side mySide) {
            // given
            Intersection destination = new Intersection(3, 3);

            List<Path> paths = List.of(
                    createPath(destination, new Intersection(5, 3), new Intersection(4, 3))
            );

            AlivePieces alivePieces = createAlivePieces(destination, createDefaultPiece(mySide));

            // when
            List<Intersection> movableDestinations = basicRule
                    .movableDestinations(mySide, paths, alivePieces);

            // then
            assertThat(movableDestinations).isEmpty();
        }

        @DisplayName("목적지에 있는 기물이 적군이면 이동할 수 있다")
        @Test
        void 목적지에_있는_기물이_적군이면_이동할_수_있다() {
            // given
            Side mySide = Side.CHO;
            Side oppositeSide = Side.HAN;
            Intersection destination = new Intersection(3, 3);

            List<Path> paths = List.of(
                    createPath(destination, new Intersection(5, 3), new Intersection(4, 3))
            );

            AlivePieces alivePieces = createAlivePieces(destination, createDefaultPiece(oppositeSide));

            // when
            List<Intersection> movableDestinations = basicRule
                    .movableDestinations(mySide, paths, alivePieces);

            // then
            assertThat(movableDestinations).containsExactlyInAnyOrder(destination);
        }
    }

    private static Path createPath(Intersection destination, Intersection... passingIntersections) {
        return new Path(destination, List.of(passingIntersections));
    }

    private static AlivePieces createAlivePieces(Intersection intersection, Piece piece) {
        return new AlivePieces(Map.of(
                intersection, piece
        ));
    }

    private static Piece createDefaultPiece(Side side) {
        return Piece.of(PieceType.SOLDIER, side);
    }
}
