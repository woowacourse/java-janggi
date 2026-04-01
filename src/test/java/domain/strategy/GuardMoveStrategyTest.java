package domain.strategy;

import domain.Board;
import domain.BoardFactory;
import domain.Piece;
import domain.Team;
import domain.Type;
import domain.vo.Position;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GuardMoveStrategyTest {

    @Test
    @DisplayName("사의 목적지에 기물이 없으면 이동한다.")
    void 사_정상_이동() {
        // given
        MoveStrategy strategy = new GuardMoveStrategy();
        Board board = BoardFactory.setUp();

        // when
        Position position = Position.of(0, 3);
        Position targetPosition = Position.of(1, 3);

        // then
        Assertions.assertTrue(strategy.canMove(position, targetPosition, board));
    }

    @Test
    @DisplayName("사의 목적지에 같은 팀 기물이 있으면 이동하지 않는다.")
    void 사_목적지에_같은_팀_기물이_있으면_이동_불가() {
        // given
        MoveStrategy guardMoveStrategy = new GuardMoveStrategy();
        Map<Position, Piece> PositionPiecePair = new LinkedHashMap<>();
        PositionPiecePair.put(Position.of(0, 3), Piece.of(Team.CHU, Type.GUARD, guardMoveStrategy));
        PositionPiecePair.put(Position.of(0, 2), Piece.of(Team.CHU, Type.GUARD, guardMoveStrategy));
        Board board = Board.of(PositionPiecePair);

        // when
        Position position = Position.of(0, 3);
        Position targetPosition = Position.of(0, 2);

        // then
        Assertions.assertFalse(guardMoveStrategy.canMove(position, targetPosition, board));
    }


    @Test
    @DisplayName("사의 목적지에 다른 팀 기물이 있으면 이동한다.")
    void 사_목적지에_다른_팀_기물이_있으면_정상_이동() {
        // given
        MoveStrategy guardMoveStrategy = new GuardMoveStrategy();
        MoveStrategy soldierMoveStrategy = new SoldierMoveStrategy();
        Map<Position, Piece> PositionPiecePair = new LinkedHashMap<>();
        PositionPiecePair.put(Position.of(0, 3), Piece.of(Team.CHU, Type.GUARD, guardMoveStrategy));
        PositionPiecePair.put(Position.of(1, 3), Piece.of(Team.HAN, Type.SOLDIER, soldierMoveStrategy));
        Board board = Board.of(PositionPiecePair);

        // when
        Position position = Position.of(0, 3);
        Position targetPosition = Position.of(1, 3);

        // then
        Assertions.assertTrue(guardMoveStrategy.canMove(position, targetPosition, board));
    }
}
