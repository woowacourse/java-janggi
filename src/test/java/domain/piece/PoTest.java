package domain.piece;

import domain.BoardStatus;
import domain.piece.strategy.ByeongMoveStrategy;
import domain.piece.strategy.SlidingMoveStrategy;
import domain.position.Position;
import java.util.HashMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PoTest {
    // 목적지와 포 사이에 기물이 1개 있는 경우 -> 가능
    // 목적지에 상대방 포가 있는 경우 -> 不
    // 목적지와 포 사이에 기물이 2개 있는 경우 -> 不
    // 목적지와 포 사이에 포가 있는 경우 -> 不
    // 목적지와 포 사이에 아무것도 없는 경 -> 不

    private static final Po TEST_PO = new Po(new SlidingMoveStrategy(), Team.CHO);

    @Test
    @DisplayName("목적지에 상대방 포가 있는 경우 예외가 발생해야 한다")
    void isMovable_fail_po_locates_destination() {
        //given
        Position start = Position.of(2, 2);
        Position destination = Position.of(2, 4);
        Position obstacle = Position.of(2, 4);

        HashMap<Position, Piece> testPieces = new HashMap<>();
        testPieces.put(start, TEST_PO);
        testPieces.put(obstacle, new Po(new SlidingMoveStrategy(), Team.CHO));

        BoardStatus testBoard = BoardStatus.from(testPieces);

        //when
        Assertions.assertThatThrownBy(() -> TEST_PO.check(testBoard, start, destination)).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Test
    @DisplayName("목적지와 포 사이에 포가 있는 경우 예외가 발생해야 한다")
    void isMovable_fail_has_between_po() {
        //given
        Position start = Position.of(2, 2);
        Position destination = Position.of(2, 7);
        Position obstacle = Position.of(2, 4);

        HashMap<Position, Piece> testPieces = new HashMap<>();
        testPieces.put(start, TEST_PO);
        testPieces.put(obstacle, new Po(new SlidingMoveStrategy(), Team.CHO));

        BoardStatus testBoard = BoardStatus.from(testPieces);

        //when
        Assertions.assertThatThrownBy(() -> TEST_PO.check(testBoard, start, destination)).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Test
    @DisplayName("목적지와 포 사이에 기물이 1개만 있는 경우 갈 수 있어야 한다")
    void isMovable_success_only_one_piece() {
        //given
        Position start = Position.of(2, 2);
        Position destination = Position.of(2, 4);

        Position obstaclePosition = Position.of(2, 3);

        HashMap<Position, Piece> testPieces = new HashMap<>();
        testPieces.put(start, TEST_PO);
        testPieces.put(obstaclePosition, new Byeong(new ByeongMoveStrategy(), Team.CHO));

        BoardStatus testBoard = BoardStatus.from(testPieces);

        //when
        Assertions.assertThatNoException().isThrownBy(() -> TEST_PO.check(testBoard, start, destination));
    }

    @Test
    @DisplayName("목적지와 포 사이에 기물이 2개 이상인 경우 예외가 발생해야 한다")
    void isMovable_fail_over_two_pieces() {
        //given
        Position start = Position.of(2, 2);
        Position destination = Position.of(2, 5);
        Position firstObstaclePosition = Position.of(2, 3);
        Position secondObstaclePosition = Position.of(2, 4);

        HashMap<Position, Piece> testPieces = new HashMap<>();
        testPieces.put(start, TEST_PO);
        testPieces.put(firstObstaclePosition, new Byeong(new ByeongMoveStrategy(), Team.CHO));
        testPieces.put(secondObstaclePosition, new Byeong(new ByeongMoveStrategy(), Team.CHO));

        BoardStatus testBoard = BoardStatus.from(testPieces);

        //when
        Assertions.assertThatThrownBy(() -> TEST_PO.check(testBoard, start, destination)).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Test
    @DisplayName("목적지와 포 사이에 아무것도 없는 경우 이동 불가")
    void isMovable_fail_clean_way() {
        //given
        Position start = Position.of(2, 2);
        Position destination = Position.of(2, 4);

        HashMap<Position, Piece> emptyBoard = new HashMap<>();
        emptyBoard.put(start, TEST_PO);

        BoardStatus testBoard = BoardStatus.from(emptyBoard);

        //when, then
        Assertions.assertThatThrownBy(() -> TEST_PO.check(testBoard, start, destination)).isInstanceOf(
                IllegalArgumentException.class);
    }

}