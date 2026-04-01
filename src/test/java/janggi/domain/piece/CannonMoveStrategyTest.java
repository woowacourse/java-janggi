package janggi.domain.piece;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CannonMoveStrategyTest {

    @Test
    public void 포_기물의_이동가능한_위치_목록을_반환한다() {
        // given
        Map<Position, Piece> board = new HashMap<>();
        PieceType pieceType = PieceType.CANNON;
        Dynasty dynasty = Dynasty.CHO;
        Position from = Position.from(8, 6);

        // 1. 위쪽에 기물이 하나만 있을 때
        board.put(Position.from(5, 6), new Piece(dynasty, PieceType.CHARIOT));
        // 2. 오른쪽에 기물이 두개 있을 때 (도착 지점이 아군)
        board.put(Position.from(8, 7), new Piece(dynasty, PieceType.CHARIOT));
        board.put(Position.from(8, 9), new Piece(dynasty, PieceType.CHARIOT));
        // 3. 아래쪽에 기물이 두개 있을 때 (도착 지점이 적군)
        board.put(Position.from(9, 6), new Piece(dynasty, PieceType.CHARIOT));
        board.put(Position.from(10, 6), new Piece(Dynasty.HAN, PieceType.CHARIOT));
        // 4. 대각선 왼쪽 아래에 기물이 하나만 있을 때
        board.put(Position.from(9, 5), new Piece(dynasty, PieceType.CHARIOT));

        // when
        List<Position> positions = pieceType.moveStrategy()
                .findPlaceablePositions(BoardSnapshot.of(board), from, dynasty);

        // then
        Assertions.assertThat(positions)
                .containsExactlyInAnyOrder(
                        Position.from(4, 6),
                        Position.from(3, 6),
                        Position.from(2, 6),
                        Position.from(1, 6),
                        Position.from(8, 8),
                        Position.from(10, 6),
                        Position.from(10, 4)
                );
    }

    @Test
    public void 포_기물의_이동가능한_위치_목록을_반환한다_경로에_포가_있는_경우() {
        // given
        Map<Position, Piece> board = new HashMap<>();
        PieceType pieceType = PieceType.CANNON;
        Dynasty dynasty = Dynasty.CHO;
        Position from = Position.from(5, 5);

        // 1. 첫번째로 만난 기물이 포인 경우
        board.put(Position.from(3, 5), new Piece(dynasty, pieceType));
        // 2. 두번째로 만난 기물이 포인 경우 (도착 지점이 아군)
        board.put(Position.from(5, 7), new Piece(dynasty, PieceType.CHARIOT));
        board.put(Position.from(5, 9), new Piece(dynasty, pieceType));
        // 3. 두번째로 만난 기물이 포인 경우 (도착 지점이 적군)
        board.put(Position.from(7, 5), new Piece(dynasty, PieceType.CHARIOT));
        board.put(Position.from(9, 5), new Piece(Dynasty.HAN, pieceType));

        // when
        List<Position> positions = pieceType.moveStrategy()
                .findPlaceablePositions(BoardSnapshot.of(board), from, dynasty);

        // then
        Assertions.assertThat(positions)
                .containsExactlyInAnyOrder(
                        Position.from(5, 8),
                        Position.from(8, 5)
                );
    }

}
