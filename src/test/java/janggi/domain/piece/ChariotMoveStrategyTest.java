package janggi.domain.piece;

import janggi.domain.board.BoardDesignPolicy;
import janggi.domain.board.BoardDesignPolicyImpl;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


class ChariotMoveStrategyTest {

    @Test
    public void 차_기물의_이동가능한_위치_목록을_반환한다() {
        // given
        BoardDesignPolicy boardDesignPolicy = new FakeBoardDesignPolicy();
        Map<Position, Piece> board = boardDesignPolicy.initBoard();
        MoveStrategy moveStrategy = new ChariotMoveStrategy();
        Dynasty dynasty = Dynasty.CHO;
        Position from = Position.from(5, 5);
        board.put(from, new Piece(dynasty, moveStrategy));
        board.put(Position.from(3 ,5), new Piece(dynasty, moveStrategy));
        board.put(Position.from(5 ,1), new Piece(Dynasty.HAN, moveStrategy));


        // when
        List<Position> positions = moveStrategy.canMovePositions(board, from, dynasty);

        System.out.println(positions);
        // then
        Assertions.assertThat(positions)
                .containsExactlyInAnyOrder(
                        Position.from(5, 6),
                        Position.from(5, 7),
                        Position.from(5, 8),
                        Position.from(5, 9),
                        Position.from(4, 5),
                        Position.from(5, 1),
                        Position.from(5, 2),
                        Position.from(5, 3),
                        Position.from(5, 4),
                        Position.from(6, 5),
                        Position.from(7, 5),
                        Position.from(8, 5),
                        Position.from(9, 5),
                        Position.from(10, 5)
                );
    }

    static class FakeBoardDesignPolicy implements BoardDesignPolicy {

        @Override
        public Map<Position, Piece> initBoard() {
            return new HashMap<>(Map.of());
        }
    }

}
