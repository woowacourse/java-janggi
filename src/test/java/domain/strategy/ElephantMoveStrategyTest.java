package domain.strategy;

import domain.Piece;
import domain.Team;
import domain.Type;
import domain.vo.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

class ElephantMoveStrategyTest {

    @Test
    @DisplayName("초나라 상의 이동 경로에 다른 기물이 없으면 정상 이동한다.")
    void 초나라_상_정상_이동() {
        // given
        MoveStrategy strategy = new ElephantMoveStrategy();
        Piece mover = Piece.of(Team.CHU, Type.ELEPHANT);

        Position from = Position.of(0, 1);
        Position to = Position.of(3, 3);

        // then
        Assertions.assertTrue(strategy.canMove(mover, from, to, Map.of()));
    }

    @Test
    @DisplayName("한나라 상의 이동 경로에 다른 기물이 없으면 정상 이동한다.")
    void 한나라_상_정상_이동() {
        // given
        MoveStrategy strategy = new ElephantMoveStrategy();
        Piece mover = Piece.of(Team.HAN, Type.ELEPHANT);

        Position from = Position.of(9, 1);
        Position to = Position.of(6, 3);

        // then
        Assertions.assertTrue(strategy.canMove(mover, from, to, Map.of()));
    }

    @Test
    @DisplayName("초나라 상의 이동 경로에 기물이 있으면 이동하지 않는다.")
    void 초나라_상_이동_경로에_기물이_있으면_이동_불가() {
        // given
        MoveStrategy strategy = new ElephantMoveStrategy();
        Piece mover = Piece.of(Team.CHU, Type.ELEPHANT);
        Piece blockingPiece = Piece.of(Team.CHU, Type.HORSE);

        Position from = Position.of(0, 1);
        Position to = Position.of(2, 4);
        Position mid1 = Position.of(0, 2);

        // then
        Assertions.assertFalse(strategy.canMove(mover, from, to, Map.of(mid1, blockingPiece)));
    }

    @Test
    @DisplayName("목적지에 같은 팀 기물이 있으면 이동하지 않는다.")
    void 상_목적지에_같은_팀_기물이_있으면_이동_불가() {
        // given
        MoveStrategy strategy = new ElephantMoveStrategy();
        Piece mover = Piece.of(Team.CHU, Type.ELEPHANT);
        Piece target = Piece.of(Team.CHU, Type.SOLDIER);

        Position from = Position.of(0, 1);
        Position to = Position.of(3, 3);

        // then
        Assertions.assertFalse(strategy.canMove(mover, from, to, Map.of(to, target)));
    }

    @Test
    @DisplayName("목적지에 다른 팀 기물이 있으면 이동한다.")
    void 상_목적지에_다른_팀_기물이_있으면_이동_가능() {
        // given
        MoveStrategy strategy = new ElephantMoveStrategy();
        Piece mover = Piece.of(Team.CHU, Type.ELEPHANT);
        Piece target = Piece.of(Team.HAN, Type.SOLDIER);

        Position from = Position.of(3, 4);
        Position to = Position.of(6, 2);

        // then
        Assertions.assertTrue(strategy.canMove(mover, from, to, Map.of(to, target)));
    }
}
