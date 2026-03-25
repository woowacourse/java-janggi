package janggi.domain.piece;

import janggi.domain.board.BoardDesignPolicy;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import janggi.fixture.FakeBoardDesignPolicy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.dynasty.Dynasty.HAN;

class ElephantMoveStrategyTest {

    @Test
    public void 상_기물의_이동가능한_위치_목록을_반환한다() {
        // given
        BoardDesignPolicy boardDesignPolicy = new FakeBoardDesignPolicy();
        Map<Position, Piece> board = boardDesignPolicy.initBoard();
        MoveStrategy moveStrategy = new ElephantMoveStrategy();
        Dynasty dynasty = CHO;
        Position from = Position.from(5, 5);
        board.put(from, new Piece(dynasty, moveStrategy));

        // 1. 상화좌우에서 막히는 경우
        board.put(Position.from(5, 6), new Piece(HAN, moveStrategy));
        // 2. 첫번째 대각선 칸에서 막히는 경우
        board.put(Position.from(3, 4), new Piece(HAN, moveStrategy));
        // 3. 도착지점에 상대팀인 경우
        board.put(Position.from(3, 2), new Piece(HAN, moveStrategy));
        // 4. 도착지점에 우리팀인 경우
        board.put(Position.from(8, 7), new Piece(dynasty, moveStrategy));

        // when
        List<Position> positions = moveStrategy.canMovePositions(board, from, dynasty);

        // then
        Assertions.assertThat(positions)
                .containsExactlyInAnyOrder(
                        Position.from(2, 7),
                        Position.from(3, 2),
                        Position.from(7, 2),
                        Position.from(8, 3)
                );
    }


}
