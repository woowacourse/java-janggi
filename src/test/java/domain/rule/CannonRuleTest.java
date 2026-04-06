package domain.rule;

import domain.Side;
import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.piece.Cannon;
import domain.piece.EmptyPiece;
import domain.piece.Pawn;
import domain.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CannonRuleTest {

    private final CannonRule cannonRule = new CannonRule();

    @Test
    @DisplayName("1개의 기물을 뛰어넘은 후 빈 칸으로 이동할 수 있다.")
    void jumpOverAndLandTest() {
        // given
        Position pivot = new Position(3, 4);
        Position landing1 = new Position(2, 4);
        Position landing2 = new Position(1, 4);
        List<Path> paths = List.of(new Path(List.of(pivot, landing1, landing2)));
        Map<Position, Piece> pieceMap = Map.of(
                pivot, new Pawn(Side.CHU),
                landing1, EmptyPiece.getInstance(),
                landing2, EmptyPiece.getInstance()
        );

        // when
        List<Position> result = cannonRule.getPossiblePositions(Side.HAN, pieceMap, paths);

        // then
        assertThat(result).containsOnly(landing1, landing2);
    }

    @Test
    @DisplayName("피벗 기물이 포이면 이동할 수 없다.")
    void cannotJumpOverCannonTest() {
        // given
        Position pivot = new Position(3, 4);
        Position landing = new Position(2, 4);
        List<Path> paths = List.of(new Path(List.of(pivot, landing)));
        Map<Position, Piece> pieceMap = Map.of(
                pivot, new Cannon(Side.CHU),
                landing, EmptyPiece.getInstance()
        );

        // when
        List<Position> result = cannonRule.getPossiblePositions(Side.HAN, pieceMap, paths);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("피벗을 넘은 후 적군 기물을 잡을 수 있다.")
    void captureAfterJumpTest() {
        // given
        Position pivot = new Position(3, 4);
        Position enemy = new Position(2, 4);
        List<Path> paths = List.of(new Path(List.of(pivot, enemy)));
        Map<Position, Piece> pieceMap = Map.of(
                pivot, new Pawn(Side.HAN),
                enemy, new Pawn(Side.CHU)
        );

        // when
        List<Position> result = cannonRule.getPossiblePositions(Side.HAN, pieceMap, paths);

        // then
        assertThat(result).containsOnly(enemy);
    }

    @Test
    @DisplayName("피벗을 넘은 후 아군 기물을 만나면 멈춘다.")
    void blockedByFriendlyAfterJumpTest() {
        // given
        Position pivot = new Position(3, 4);
        Position friendly = new Position(2, 4);
        List<Path> paths = List.of(new Path(List.of(pivot, friendly)));
        Map<Position, Piece> pieceMap = Map.of(
                pivot, new Pawn(Side.CHU),
                friendly, new Pawn(Side.HAN)
        );

        // when
        List<Position> result = cannonRule.getPossiblePositions(Side.HAN, pieceMap, paths);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("피벗을 넘은 후 포를 만나면 멈춘다.")
    void blockedByCannonAfterJumpTest() {
        // given
        Position pivot = new Position(3, 4);
        Position cannon = new Position(2, 4);
        Position beyond = new Position(1, 4);
        List<Path> paths = List.of(new Path(List.of(pivot, cannon, beyond)));
        Map<Position, Piece> pieceMap = Map.of(
                pivot, new Pawn(Side.CHU),
                cannon, new Cannon(Side.CHU),
                beyond, EmptyPiece.getInstance()
        );

        // when
        List<Position> result = cannonRule.getPossiblePositions(Side.HAN, pieceMap, paths);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("경로에 기물이 없으면 이동할 수 없다.")
    void noPivotTest() {
        // given
        Position pos1 = new Position(3, 4);
        Position pos2 = new Position(2, 4);
        List<Path> paths = List.of(new Path(List.of(pos1, pos2)));
        Map<Position, Piece> pieceMap = Map.of(
                pos1, EmptyPiece.getInstance(),
                pos2, EmptyPiece.getInstance()
        );

        // when
        List<Position> result = cannonRule.getPossiblePositions(Side.HAN, pieceMap, paths);

        // then
        assertThat(result).isEmpty();
    }
}
