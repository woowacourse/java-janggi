package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static janggi.domain.piece.PieceType.CHARIOT;
import static janggi.domain.piece.PieceType.SOLDIER;


class
ChariotMoveStrategyTest {

    @Test
    @DisplayName("차 기물은 상하좌우 네 방향으로 움직일 수 있으며 기물을 뛰어넘을 수는 없다.")
    public void chariot_findMovablePositions_success1() {
        // given
        MoveStrategy moveStrategy = ChariotMoveStrategy.getInstance();

        Map<Position, Piece> board = new HashMap<>();
        Dynasty dynasty = Dynasty.CHO;
        Position from = Position.from(5, 5);
        board.put(from, new Piece(dynasty, CHARIOT));

        board.put(Position.from(3 ,5), new Piece(dynasty, SOLDIER));
        board.put(Position.from(5 ,3), new Piece(dynasty, SOLDIER));
        board.put(Position.from(5 ,7), new Piece(dynasty, SOLDIER));
        board.put(Position.from(7 ,5), new Piece(dynasty, SOLDIER));


        // when
        List<Position> positions = moveStrategy.findMovablePositions(board, from, dynasty);

        // then
        Assertions.assertThat(positions)
                .containsExactlyInAnyOrder(
                        Position.from(4, 5),
                        Position.from(5, 4),
                        Position.from(5, 6),
                        Position.from(6, 5)
                );
    }

    @Test
    @DisplayName("차 기물은 궁성 영역 안에서는 대각선으로 이동할 수 있다.")
    public void chariot_findMovablePositions_success2() {
        // given
        MoveStrategy moveStrategy = ChariotMoveStrategy.getInstance();

        Map<Position, Piece> board = new HashMap<>();
        Dynasty dynasty = Dynasty.CHO;
        Position from = Position.from(1, 4);
        board.put(from, new Piece(dynasty, CHARIOT));


        // when
        List<Position> positions = moveStrategy.findMovablePositions(board, from, dynasty);

        // then
        Assertions.assertThat(positions)
                .contains(
                        Position.from(2, 5),
                        Position.from(3, 6)
                );
    }

}
