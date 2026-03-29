package janggi.domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.team.TeamType;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CannonMoveRuleTest {


    @Nested
    @DisplayName("이동 가능한 목적지 계산 테스트")
    class Execute {

        @Test
        @DisplayName("이동 경로에 죽일 수 없는 기물이 있는 경우")
        void success() {
            // given
            Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
            positionPieceMap.put(Position.valueOf(6, 2), new Cannon(TeamType.BLUE));
            positionPieceMap.put(Position.valueOf(6, 4), new Soldier(TeamType.RED));
            positionPieceMap.put(Position.valueOf(6, 7), new Cannon(TeamType.RED));
            Board board = new Board(positionPieceMap);
            Position from = Position.valueOf(6, 7);
            MoveRule moveRuleOfCannon = new CannonMoveRule(Direction.LEFT);

            // when
            List<Position> actual = moveRuleOfCannon.execute(from, board);

            // then
            List<Position> expected = List.of(Position.valueOf(6, 3));
            assertThat(actual).isEqualTo(expected);
        }
    }
}
