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

class StepRuleTest {

    private final StepRule stepRule = new StepRule();

    @Test
    @DisplayName("빈 칸으로 이동할 수 있다.")
    void moveToEmptyTest() {
        // given
        Position target = new Position(3, 4);
        List<Path> paths = List.of(new Path(List.of(target)));
        Map<Position, Piece> pieceMap = Map.of(target, EmptyPiece.getInstance());

        // when
        List<Position> result = stepRule.getPossiblePositions(Side.HAN, pieceMap, paths);

        // then
        assertThat(result).containsOnly(target);
    }

    @Test
    @DisplayName("적군 기물이 있는 위치로 이동할 수 있다.")
    void captureEnemyTest() {
        // given
        Position target = new Position(3, 4);
        List<Path> paths = List.of(new Path(List.of(target)));
        Map<Position, Piece> pieceMap = Map.of(target, new Pawn(Side.CHU));

        // when
        List<Position> result = stepRule.getPossiblePositions(Side.HAN, pieceMap, paths);

        // then
        assertThat(result).containsOnly(target);
    }

    @Test
    @DisplayName("아군 기물이 있는 위치로 이동할 수 없다.")
    void blockedByFriendlyTest() {
        // given
        Position target = new Position(3, 4);
        List<Path> paths = List.of(new Path(List.of(target)));
        Map<Position, Piece> pieceMap = Map.of(target, new Pawn(Side.HAN));

        // when
        List<Position> result = stepRule.getPossiblePositions(Side.HAN, pieceMap, paths);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("여러 경로 중 이동 가능한 위치만 반환한다.")
    void multiplePathsTest() {
        // given
        Position empty = new Position(3, 4);
        Position enemy = new Position(5, 4);
        Position friendly = new Position(4, 3);
        List<Path> paths = List.of(
                new Path(List.of(empty)),
                new Path(List.of(enemy)),
                new Path(List.of(friendly))
        );
        Map<Position, Piece> pieceMap = Map.of(
                empty, EmptyPiece.getInstance(),
                enemy, new Pawn(Side.CHU),
                friendly, new Pawn(Side.HAN)
        );

        // when
        List<Position> result = stepRule.getPossiblePositions(Side.HAN, pieceMap, paths);

        // then
        assertThat(result).containsOnly(empty, enemy);
    }
}
