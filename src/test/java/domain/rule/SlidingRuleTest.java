package domain.rule;

import domain.Side;
import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.piece.EmptyPiece;
import domain.piece.Pawn;
import domain.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SlidingRuleTest {

    private final SlidingRule slidingRule = new SlidingRule();

    @Test
    @DisplayName("빈 경로를 따라 끝까지 이동할 수 있다.")
    void slideAlongEmptyPathTest() {
        // given
        Position pos1 = new Position(3, 4);
        Position pos2 = new Position(2, 4);
        Position pos3 = new Position(1, 4);
        List<Path> paths = List.of(new Path(List.of(pos1, pos2, pos3)));
        Map<Position, Piece> pieceMap = Map.of(
                pos1, EmptyPiece.getInstance(),
                pos2, EmptyPiece.getInstance(),
                pos3, EmptyPiece.getInstance()
        );

        // when
        List<Position> result = slidingRule.getPossiblePositions(Side.HAN, pieceMap, paths);

        // then
        assertThat(result).containsOnly(pos1, pos2, pos3);
    }

    @Test
    @DisplayName("적군 기물을 만나면 잡고 멈춘다.")
    void captureAndStopTest() {
        // given
        Position empty = new Position(3, 4);
        Position enemy = new Position(2, 4);
        Position beyond = new Position(1, 4);
        List<Path> paths = List.of(new Path(List.of(empty, enemy, beyond)));
        Map<Position, Piece> pieceMap = Map.of(
                empty, EmptyPiece.getInstance(),
                enemy, new Pawn(Side.CHU),
                beyond, EmptyPiece.getInstance()
        );

        // when
        List<Position> result = slidingRule.getPossiblePositions(Side.HAN, pieceMap, paths);

        // then
        assertThat(result).containsOnly(empty, enemy);
        assertThat(result).doesNotContain(beyond);
    }

    @Test
    @DisplayName("아군 기물을 만나면 직전에 멈춘다.")
    void blockedByFriendlyTest() {
        // given
        Position empty = new Position(3, 4);
        Position friendly = new Position(2, 4);
        Position beyond = new Position(1, 4);
        List<Path> paths = List.of(new Path(List.of(empty, friendly, beyond)));
        Map<Position, Piece> pieceMap = Map.of(
                empty, EmptyPiece.getInstance(),
                friendly, new Pawn(Side.HAN),
                beyond, EmptyPiece.getInstance()
        );

        // when
        List<Position> result = slidingRule.getPossiblePositions(Side.HAN, pieceMap, paths);

        // then
        assertThat(result).containsOnly(empty);
    }

    @Test
    @DisplayName("여러 방향의 경로에서 각각 독립적으로 슬라이딩한다.")
    void multipleDirectionPathsTest() {
        // given
        Position up1 = new Position(3, 4);
        Position up2 = new Position(2, 4);
        Position right1 = new Position(4, 5);
        List<Path> paths = List.of(
                new Path(List.of(up1, up2)),
                new Path(List.of(right1))
        );
        Map<Position, Piece> pieceMap = Map.of(
                up1, EmptyPiece.getInstance(),
                up2, new Pawn(Side.CHU),
                right1, EmptyPiece.getInstance()
        );

        // when
        List<Position> result = slidingRule.getPossiblePositions(Side.HAN, pieceMap, paths);

        // then
        assertThat(result).containsOnly(up1, up2, right1);
    }
}
