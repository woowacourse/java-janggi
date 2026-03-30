package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GuardMoveStrategyTest {

    private GuardMoveStrategy guardMoveStrategy = GuardMoveStrategy.getInstance();

    @Test
    @DisplayName("사 기물의 이동가능한 위치 목록을 반환한다")
    public void findMovablePositions_success() throws Exception {
        // given
        Position from = Position.from(1, 4);
        Dynasty dynasty = Dynasty.CHO;
        Map<Position, Piece> board = Map.of(
                from, new Piece(dynasty, guardMoveStrategy),
                Position.from(2, 5), new Piece(dynasty, GeneralMoveStrategy.getInstance())
        );

        // when
        List<Position> results = guardMoveStrategy.findMovablePositions(board, from, dynasty);

        // then
        assertThat(results)
                .containsExactlyInAnyOrder(
                        Position.from(1, 3), // 실제로는 궁성밖으로 못나가서 이 위치로는 못움직인다.
                        Position.from(1,5),
                        Position.from(2, 3), // 실제로는 궁성밖으로 못나가서 이 위치로는 못움직인다.
                        Position.from(2, 4)
                );
    }

}
