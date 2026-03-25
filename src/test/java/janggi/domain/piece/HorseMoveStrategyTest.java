package janggi.domain.piece;

import janggi.domain.board.BoardDesignPolicy;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import janggi.fixture.FakeBoardDesignPolicy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HorseMoveStrategyTest {

    @Test
    public void 말_기물의_이동가능한_위치_목록을_반환한다() {
        // given
        BoardDesignPolicy boardDesignPolicy = new FakeBoardDesignPolicy();
        Map<Position, Piece> board = boardDesignPolicy.initBoard();
        MoveStrategy moveStrategy = new HorseMoveStrategy();
        Dynasty dynasty = Dynasty.CHO;
        Position from = Position.from(5, 5);
        board.put(from, new Piece(dynasty, moveStrategy));
        board.put(Position.from(3, 4), new Piece(dynasty, moveStrategy));
        board.put(Position.from(5, 6), new Piece(Dynasty.HAN, moveStrategy));
        board.put(Position.from(7, 6), new Piece(Dynasty.HAN, moveStrategy));

        // when
        List<Position> positions = moveStrategy.canMovePositions(board, from, dynasty);

        System.out.println(positions);
        // then
        Assertions.assertThat(positions)
                .containsExactlyInAnyOrder(
                        Position.from(3, 6),
                        Position.from(4, 3),
                        Position.from(6, 3),
                        Position.from(7, 4),
                        Position.from(7, 6)
                );
    }

}
