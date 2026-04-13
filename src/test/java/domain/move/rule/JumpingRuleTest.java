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

@DisplayName("다른 기물을 뛰어 넘어야 하는 규칙 테스트")
class JumpingRuleTest {

    private JumpingRule jumpingRule;

    @BeforeEach
    void setUp() {
        jumpingRule = new JumpingRule();
    }

    @DisplayName("뛰어 넘을 기물이 없을 때의 검증")
    @Nested
    class 뛰어_넘을_기물이_없을_때의_검증 {

        @DisplayName("아예 존재하지 않는 경우")
        @ParameterizedTest(name = "경로 사용을 판단할 진영이 {0}")
        @EnumSource(value = Side.class, names = "NONE", mode = EnumSource.Mode.EXCLUDE)
        void 아예_존재하지_않는_경우(Side side) {
            // given
            Intersection destination = new Intersection(3, 3);

            List<Path> paths = List.of(
                    createPath(destination, new Intersection(5, 3), new Intersection(4, 3))
            );

            AlivePieces alivePieces = new AlivePieces(Map.of());

            // when
            List<Intersection> movableDestinations = jumpingRule
                    .movableDestinations(side, paths, alivePieces);

            // then
            assertThat(movableDestinations).isEmpty();
        }

        @DisplayName("포라서 뛰어 넘을 수 없는 경우")
        @ParameterizedTest(name = "포의 진영이 {0}일 때")
        @EnumSource(value = Side.class, names = "NONE", mode = EnumSource.Mode.EXCLUDE)
        void 포라서_뛰어_넘을_수_없는_경우(Side sideOfCannon) {
            // given
            Side mySide = Side.CHO;
            Intersection destination = new Intersection(3, 3);
            Intersection intersectionOfCannon = new Intersection(5, 3);
            List<Path> paths = List.of(
                    createPath(destination, intersectionOfCannon, new Intersection(4, 3))
            );

            AlivePieces alivePieces = createAlivePieces(intersectionOfCannon, createCannon(sideOfCannon));

            // when
            List<Intersection> movableDestinations = jumpingRule
                    .movableDestinations(mySide, paths, alivePieces);

            // then
            assertThat(movableDestinations).isEmpty();
        }
    }

    @DisplayName("뛰어 넘을 기물이 있을 때의 검증")
    @Nested
    class 뛰어_넘을_기물이_있을_때의_검증 {

        @DisplayName("이후 경유지에 기물이 있으면 해당 경로를 사용할 수 없다")
        @ParameterizedTest(name = "뛰어 넘을 기물과 경유지 기물의 진영이 {0}")
        @EnumSource(value = Side.class, names = "NONE", mode = EnumSource.Mode.EXCLUDE)
        void 이후_경유지에_기물이_있으면_해당_경로를_사용할_수_없다(Side side) {
            // given
            Side mySide = Side.CHO;
            Intersection destination = new Intersection(3, 3);
            Intersection blockedPassing = new Intersection(4, 3);
            Intersection screenPassing = new Intersection(5, 3);

            List<Path> paths = List.of(
                    createPath(destination, screenPassing, blockedPassing)
            );

            AlivePieces alivePieces = new AlivePieces(Map.of(
                    blockedPassing, createDefaultPiece(side),
                    screenPassing, createDefaultPiece(side)
            ));

            // when
            List<Intersection> movableDestinations = jumpingRule
                    .movableDestinations(mySide, paths, alivePieces);

            // then
            assertThat(movableDestinations).isEmpty();
        }

        @DisplayName("이후 경유지에 기물이 없으면 해당 경로를 사용할 수 있다")
        @ParameterizedTest(name = "뛰어 넘을 기물의 진영이 {0}")
        @EnumSource(value = Side.class, names = "NONE", mode = EnumSource.Mode.EXCLUDE)
        void 이후_경유지에_기물이_없으면_해당_경로를_사용할_수_있다(Side sideOfScreen) {
            // given
            Side mySide = Side.CHO;
            Intersection destination = new Intersection(3, 3);
            Intersection screenPassing = new Intersection(5, 3);

            List<Path> paths = List.of(
                    createPath(destination, screenPassing, new Intersection(4, 3))
            );

            AlivePieces alivePieces = new AlivePieces(Map.of(
                    screenPassing, createDefaultPiece(sideOfScreen)
            ));

            // when
            List<Intersection> movableDestinations = jumpingRule
                    .movableDestinations(mySide, paths, alivePieces);

            // then
            assertThat(movableDestinations).containsExactlyInAnyOrder(destination);
        }
    }

    @DisplayName("목적지 기물 종류에 따른 검증")
    @Nested
    class 목적지_기물_종류에_따른_검증 {

        @DisplayName("포이면 이동할 수 없다")
        @ParameterizedTest(name = "포의 진영이 {0}")
        @EnumSource(value = Side.class, names = "NONE", mode = EnumSource.Mode.EXCLUDE)
        void 포이면_이동할_수_없다(Side sideOfCannon) {
            // given
            Side mySide = Side.CHO;
            Intersection destination = new Intersection(3, 3);
            Intersection screenPassing = new Intersection(5, 3);

            List<Path> paths = List.of(
                    createPath(destination, screenPassing, new Intersection(4, 3))
            );

            AlivePieces alivePieces = new AlivePieces(Map.of(
                    destination, createCannon(sideOfCannon),
                    screenPassing, createDefaultPiece(mySide)
            ));

            // when
            List<Intersection> movableDestinations = jumpingRule
                    .movableDestinations(mySide, paths, alivePieces);

            // then
            assertThat(movableDestinations).isEmpty();
        }

        @DisplayName("아군이면 이동할 수 없다")
        @ParameterizedTest(name = "아군 진영이 {0}")
        @EnumSource(value = Side.class, names = "NONE", mode = EnumSource.Mode.EXCLUDE)
        void 아군이면_이동할_수_없다(Side mySide) {
            // given
            Intersection destination = new Intersection(3, 3);
            Intersection screenPassing = new Intersection(5, 3);

            List<Path> paths = List.of(
                    createPath(destination, screenPassing, new Intersection(4, 3))
            );

            AlivePieces alivePieces = new AlivePieces(Map.of(
                    destination, createDefaultPiece(mySide),
                    screenPassing, createDefaultPiece(mySide)
            ));

            // when
            List<Intersection> movableDestinations = jumpingRule
                    .movableDestinations(mySide, paths, alivePieces);

            // then
            assertThat(movableDestinations).isEmpty();
        }

        @DisplayName("적군이면 이동할 수 있다")
        @Test
        void 적군이면_이동할_수_있다() {
            // given
            Side mySide = Side.CHO;
            Side oppositeSide = Side.HAN;
            Intersection destination = new Intersection(3, 3);
            Intersection screenPassing = new Intersection(5, 3);

            List<Path> paths = List.of(
                    createPath(destination, screenPassing, new Intersection(4, 3))
            );

            AlivePieces alivePieces = new AlivePieces(Map.of(
                    destination, createDefaultPiece(oppositeSide),
                    screenPassing, createDefaultPiece(mySide)
            ));

            // when
            List<Intersection> movableDestinations = jumpingRule
                    .movableDestinations(mySide, paths, alivePieces);

            // then
            assertThat(movableDestinations).containsExactlyInAnyOrder(destination);
        }

        @DisplayName("비어 있으면 이동할 수 있다")
        @ParameterizedTest(name = "내 진영이 {0}일 때")
        @EnumSource(value = Side.class, names = "NONE", mode = EnumSource.Mode.EXCLUDE)
        void 비어_있으면_이동할_수_있다(Side mySide) {
            // given
            Intersection destination = new Intersection(3, 3);
            Intersection screenPassing = new Intersection(5, 3);

            List<Path> paths = List.of(
                    createPath(destination, screenPassing, new Intersection(4, 3))
            );

            AlivePieces alivePieces = new AlivePieces(Map.of(
                    screenPassing, createDefaultPiece(mySide)
            ));

            // when
            List<Intersection> movableDestinations = jumpingRule
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

    private static Piece createCannon(Side side) {
        return Piece.of(PieceType.CANNON, side);
    }

    private static Piece createDefaultPiece(Side side) {
        return Piece.of(PieceType.SOLDIER, side);
    }
}
