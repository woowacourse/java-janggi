package janggi.domain.movestorage;

import janggi.domain.BoardState;
import janggi.domain.Column;
import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Row;
import janggi.domain.Team;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PoMoveStorageTest {

    private static class FakeBoard implements BoardState {
        @Override
        public boolean hasPieceAt(Position position) {
            return false;
        }

        @Override
        public Piece getPieceAt(Position position) {
            return null;
        }
    }

    private static class ObstaclFakeBoard implements BoardState {
        private final Map<Position, Piece> obstacles;

        public ObstaclFakeBoard(Map<Position, Piece> obstacles) {
            this.obstacles = new HashMap<>(obstacles);
        }

        @Override
        public boolean hasPieceAt(Position position) {
            return obstacles.containsKey(position);
        }

        @Override
        public Piece getPieceAt(Position position) {
            return obstacles.get(position);
        }
    }

    @Test
    void 출발지점과_도착지점이_X축은_같고_Y축은_도착지점이_더_높고_중간에_포가_아닌_기물이_한_개_있고_도착지점_기물이_포가_아니다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(0), Column.of(3));

        Position obstaclePosition = Position.of(Row.of(0), Column.of(1));
        Piece obstaclePiece = new Piece(new JolMoveStorage(), Team.HAN, 2,  "卒");

        BoardState boardState = new ObstaclFakeBoard(Map.of(obstaclePosition, obstaclePiece));

        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 출발지점과_도착지점이_X축은_같고_Y축은_도착지점이_더_낮고_중간에_포가_아닌_기물이_한_개_있고_도착지점_기물이_포가_아니다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(0), Column.of(3));
        Position to = Position.of(Row.of(0), Column.of(0));

        Position obstaclePosition = Position.of(Row.of(0), Column.of(1));
        Piece obstaclePiece = new Piece(new JolMoveStorage(), Team.HAN, 2,  "卒");

        BoardState boardState = new ObstaclFakeBoard(Map.of(obstaclePosition, obstaclePiece));

        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 출발지점과_도착지점이_Y축은_같고_X축은_도착지점이_더_높고_중간에_포가_아닌_기물이_한_개_있고_도착지점_기물이_포가_아니다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(3), Column.of(0));

        Position obstaclePosition = Position.of(Row.of(2), Column.of(0));
        Piece obstaclePiece = new Piece(new JolMoveStorage(), Team.HAN, 2,  "卒");

        BoardState boardState = new ObstaclFakeBoard(Map.of(obstaclePosition, obstaclePiece));

        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 출발지점과_도착지점이_Y축은_같고_X축은_도착지점이_더_낮고_중간에_포가_아닌_기물이_한_개_있고_도착지점_기물이_포가_아니다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(3), Column.of(0));
        Position to = Position.of(Row.of(0), Column.of(0));

        Position obstaclePosition = Position.of(Row.of(2), Column.of(0));
        Piece obstaclePiece = new Piece(new JolMoveStorage(), Team.HAN, 2,  "卒");

        BoardState boardState = new ObstaclFakeBoard(Map.of(obstaclePosition, obstaclePiece));

        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 포가_아예_갈_수_없는_행마면_예외가_발생한다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(1), Column.of(3));

        Position obstaclePosition = Position.of(Row.of(0), Column.of(1));
        Piece obstaclePiece = new Piece(new JolMoveStorage(), Team.HAN, 2,  "卒");

        BoardState boardState = new ObstaclFakeBoard(Map.of(obstaclePosition, obstaclePiece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_X축은_같고_Y축은_도착지점이_더_높고_중간에_기물이_없으면_예외가_발생한다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(0), Column.of(3));

        BoardState boardState = new FakeBoard();
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_X축은_같고_Y축은_도착지점이_더_낮고_중간에_기물이_없으면_예외가_발생한다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(0), Column.of(3));
        Position to = Position.of(Row.of(0), Column.of(0));

        BoardState boardState = new FakeBoard();
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_Y축은_같고_X축은_도착지점이_더_높고_중간에_기물이_없으면_예외가_발생한다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(3), Column.of(0));

        BoardState boardState = new FakeBoard();
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_Y축은_같고_X축은_도착지점이_더_낮고_중간에_기물이_없으면_예외가_발생한다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(3), Column.of(0));
        Position to = Position.of(Row.of(0), Column.of(0));

        BoardState boardState = new FakeBoard();
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_X축은_같고_Y축은_도착지점이_더_높고_도착지점_기물이_포면_예외가_발생한다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(0), Column.of(3));

        Position obstaclePosition = Position.of(Row.of(0), Column.of(3));
        Piece obstaclePiece = new Piece(new PoMoveStorage(), Team.HAN, 7,  "包");

        BoardState boardState = new ObstaclFakeBoard(Map.of(obstaclePosition, obstaclePiece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_X축은_같고_Y축은_도착지점이_더_낮고_도착지점_기물이_포면_예외가_발생한다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(0), Column.of(3));
        Position to = Position.of(Row.of(0), Column.of(0));

        Position obstaclePosition = Position.of(Row.of(0), Column.of(0));
        Piece obstaclePiece = new Piece(new PoMoveStorage(), Team.HAN, 7,  "包");

        BoardState boardState = new ObstaclFakeBoard(Map.of(obstaclePosition, obstaclePiece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_Y축은_같고_X축은_도착지점이_더_높고_도착지점_기물이_포면_예외가_발생한다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(3), Column.of(0));

        Position obstaclePosition = Position.of(Row.of(3), Column.of(0));
        Piece obstaclePiece = new Piece(new PoMoveStorage(), Team.HAN, 7,  "包");

        BoardState boardState = new ObstaclFakeBoard(Map.of(obstaclePosition, obstaclePiece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_Y축은_같고_X축은_도착지점이_더_낮고_도착지점_기물이_포면_예외가_발생한다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(3), Column.of(0));
        Position to = Position.of(Row.of(0), Column.of(0));

        Position obstaclePosition = Position.of(Row.of(0), Column.of(0));
        Piece obstaclePiece = new Piece(new PoMoveStorage(), Team.HAN, 7,  "包");

        BoardState boardState = new ObstaclFakeBoard(Map.of(obstaclePosition, obstaclePiece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_X축은_같고_Y축은_도착지점이_더_높고_중간_기물이_포면_예외가_발생한다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(0), Column.of(3));

        Position obstaclePosition = Position.of(Row.of(0), Column.of(2));
        Piece obstaclePiece = new Piece(new PoMoveStorage(), Team.HAN, 7,  "包");

        BoardState boardState = new ObstaclFakeBoard(Map.of(obstaclePosition, obstaclePiece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_X축은_같고_Y축은_도착지점이_더_낮고_중간_기물이_포면_예외가_발생한다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(0), Column.of(3));
        Position to = Position.of(Row.of(0), Column.of(0));

        Position obstaclePosition = Position.of(Row.of(0), Column.of(2));
        Piece obstaclePiece = new Piece(new PoMoveStorage(), Team.HAN, 7,  "包");

        BoardState boardState = new ObstaclFakeBoard(Map.of(obstaclePosition, obstaclePiece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_Y축은_같고_X축은_도착지점이_더_높고_중간_기물이_포면_예외가_발생한다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(3), Column.of(0));

        Position obstaclePosition = Position.of(Row.of(2), Column.of(0));
        Piece obstaclePiece = new Piece(new PoMoveStorage(), Team.HAN, 7,  "包");

        BoardState boardState = new ObstaclFakeBoard(Map.of(obstaclePosition, obstaclePiece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_Y축은_같고_X축은_도착지점이_더_낮고_중간_기물이_포면_예외가_발생한다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(3), Column.of(0));
        Position to = Position.of(Row.of(0), Column.of(0));

        Position obstaclePosition = Position.of(Row.of(2), Column.of(0));
        Piece obstaclePiece = new Piece(new PoMoveStorage(), Team.HAN, 7,  "包");

        BoardState boardState = new ObstaclFakeBoard(Map.of(obstaclePosition, obstaclePiece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_X축은_같고_Y축은_도착지점이_더_높고_중간에_포가_아닌_기물이_한_개_있고_도착지점_기물이_포면_예외가_발생한다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(0), Column.of(3));

        Position pathObstaclePosition = Position.of(Row.of(0), Column.of(2));
        Piece pathObstaclePiece = new Piece(new JolMoveStorage(), Team.HAN, 2,  "卒");

        Position targetPosition = Position.of(Row.of(0), Column.of(3));
        Piece targetPiece = new Piece(new PoMoveStorage(), Team.HAN, 7,  "包");

        Map<Position, Piece> initialPieces = Map.of(
                pathObstaclePosition, pathObstaclePiece,
                targetPosition, targetPiece
        );

        BoardState boardState = new ObstaclFakeBoard(initialPieces);

        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_X축은_같고_Y축은_도착지점이_더_낮고_중간에_포가_아닌_기물이_한_개_있고_도착지점_기물이_포면_예외가_발생한다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(0), Column.of(3));
        Position to = Position.of(Row.of(0), Column.of(0));

        Position pathObstaclePosition = Position.of(Row.of(0), Column.of(2));
        Piece pathObstaclePiece = new Piece(new JolMoveStorage(), Team.HAN, 2,  "卒");

        Position targetPosition = Position.of(Row.of(0), Column.of(0));
        Piece targetPiece = new Piece(new PoMoveStorage(), Team.HAN, 7,  "包");

        Map<Position, Piece> initialPieces = Map.of(
                pathObstaclePosition, pathObstaclePiece,
                targetPosition, targetPiece
        );

        BoardState boardState = new ObstaclFakeBoard(initialPieces);

        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_Y축은_같고_X축은_도착지점이_더_높고_중간에_포가_아닌_기물이_한_개_있고_도착지점_기물이_포면_예외가_발생한다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(3), Column.of(0));

        Position pathObstaclePosition = Position.of(Row.of(2), Column.of(0));
        Piece pathObstaclePiece = new Piece(new JolMoveStorage(), Team.HAN, 2,  "卒");

        Position targetPosition = Position.of(Row.of(3), Column.of(0));
        Piece targetPiece = new Piece(new PoMoveStorage(), Team.HAN, 7,  "包");

        Map<Position, Piece> initialPieces = Map.of(
                pathObstaclePosition, pathObstaclePiece,
                targetPosition, targetPiece
        );

        BoardState boardState = new ObstaclFakeBoard(initialPieces);

        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_Y축은_같고_X축은_도착지점이_더_s낮고_중간에_포가_아닌_기물이_한_개_있고_도착지점_기물이_포면_예외가_발생한다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(3), Column.of(0));
        Position to = Position.of(Row.of(0), Column.of(0));

        Position pathObstaclePosition = Position.of(Row.of(2), Column.of(0));
        Piece pathObstaclePiece = new Piece(new JolMoveStorage(), Team.HAN, 2,  "卒");

        Position targetPosition = Position.of(Row.of(0), Column.of(0));
        Piece targetPiece = new Piece(new PoMoveStorage(), Team.HAN, 7,  "包");

        Map<Position, Piece> initialPieces = Map.of(
                pathObstaclePosition, pathObstaclePiece,
                targetPosition, targetPiece
        );

        BoardState boardState = new ObstaclFakeBoard(initialPieces);

        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_X축은_같고_Y축은_도착지점이_더_높고_중간에_기물이_2개_있으면_예외가_발생한다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(0), Column.of(3));

        Position pathObstaclePosition1 = Position.of(Row.of(0), Column.of(1));
        Piece pathObstaclePiece1 = new Piece(new JolMoveStorage(), Team.HAN, 2,  "卒");

        Position pathObstaclePosition2 = Position.of(Row.of(0), Column.of(2));
        Piece pathObstaclePiece2 = new Piece(new MaMoveStorage(), Team.HAN, 5,  "馬");

        Map<Position, Piece> initialPieces = Map.of(
                pathObstaclePosition1, pathObstaclePiece1,
                pathObstaclePosition2, pathObstaclePiece2
        );

        BoardState boardState = new ObstaclFakeBoard(initialPieces);

        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_X축은_같고_Y축은_도착지점이_더_낮고_중간에_기물이_2개_있으면_예외가_발생한다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(0), Column.of(3));
        Position to = Position.of(Row.of(0), Column.of(0));

        Position pathObstaclePosition1 = Position.of(Row.of(0), Column.of(1));
        Piece pathObstaclePiece1 = new Piece(new JolMoveStorage(), Team.HAN, 2,  "卒");

        Position pathObstaclePosition2 = Position.of(Row.of(0), Column.of(2));
        Piece pathObstaclePiece2 = new Piece(new MaMoveStorage(), Team.HAN, 5,  "馬");

        Map<Position, Piece> initialPieces = Map.of(
                pathObstaclePosition1, pathObstaclePiece1,
                pathObstaclePosition2, pathObstaclePiece2
        );

        BoardState boardState = new ObstaclFakeBoard(initialPieces);

        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_Y축은_같고_X축은_도착지점이_더_높고_중간에_기물이_2개_있으면_예외가_발생한다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(3), Column.of(0));

        Position pathObstaclePosition1 = Position.of(Row.of(1), Column.of(0));
        Piece pathObstaclePiece1 = new Piece(new JolMoveStorage(), Team.HAN, 2,  "卒");

        Position pathObstaclePosition2 = Position.of(Row.of(2), Column.of(0));
        Piece pathObstaclePiece2 = new Piece(new MaMoveStorage(), Team.HAN, 5,  "馬");

        Map<Position, Piece> initialPieces = Map.of(
                pathObstaclePosition1, pathObstaclePiece1,
                pathObstaclePosition2, pathObstaclePiece2
        );

        BoardState boardState = new ObstaclFakeBoard(initialPieces);

        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_Y축은_같고_X축은_도착지점이_더_낮고_중간에_기물이_2개_있으면_예외가_발생한다() {
        // given
        MoveStorage moveStorage = new PoMoveStorage();

        Position from = Position.of(Row.of(3), Column.of(0));
        Position to = Position.of(Row.of(0), Column.of(0));

        Position pathObstaclePosition1 = Position.of(Row.of(1), Column.of(0));
        Piece pathObstaclePiece1 = new Piece(new JolMoveStorage(), Team.HAN, 2,  "卒");

        Position pathObstaclePosition2 = Position.of(Row.of(2), Column.of(0));
        Piece pathObstaclePiece2 = new Piece(new MaMoveStorage(), Team.HAN, 5,  "馬");

        Map<Position, Piece> initialPieces = Map.of(
                pathObstaclePosition1, pathObstaclePiece1,
                pathObstaclePosition2, pathObstaclePiece2
        );

        BoardState boardState = new ObstaclFakeBoard(initialPieces);

        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }
}
