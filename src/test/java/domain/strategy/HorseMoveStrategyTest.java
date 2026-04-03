package domain.strategy;

import domain.Piece;
import domain.Team;
import domain.Type;
import domain.vo.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

class HorseMoveStrategyTest {

    @Test
    @DisplayName("초나라 마의 이동 경로에 다른 기물이 없으면 정상 이동한다.")
    void 초나라_마_정상_이동() {
        // given
        MoveStrategy strategy = new HorseMoveStrategy();
        Piece mover = Piece.of(Team.CHU, Type.HORSE);

        Position from = Position.of(0, 2);
        Position to = Position.of(2, 3);

        // then
        Assertions.assertTrue(strategy.canMove(mover, from, to, Map.of()));
    }

    @Test
    @DisplayName("한나라 마의 이동 경로에 다른 기물이 없으면 정상 이동한다.")
    void 한나라_마_정상_이동() {
        // given
        MoveStrategy strategy = new HorseMoveStrategy();
        Piece mover = Piece.of(Team.HAN, Type.HORSE);

        Position from = Position.of(9, 2);
        Position to = Position.of(7, 3);

        // then
        Assertions.assertTrue(strategy.canMove(mover, from, to, Map.of()));
    }

    @Test
    @DisplayName("초나라 마의 이동 경로에 기물이 있으면 이동하지 않는다.")
    void 초나라_마_이동_경로에_기물이_있으면_이동_불가() {
        // given
        MoveStrategy strategy = new HorseMoveStrategy();
        Piece mover = Piece.of(Team.CHU, Type.HORSE);
        Piece blockingPiece = Piece.of(Team.CHU, Type.SOLDIER);

        Position from = Position.of(0, 2);
        Position to = Position.of(2, 3);
        Position mid = Position.of(1, 2);

        // then
        Assertions.assertFalse(strategy.canMove(mover, from, to, Map.of(mid, blockingPiece)));
    }

    @Test
    @DisplayName("목적지에 같은 팀 기물이 있으면 이동하지 않는다.")
    void 마_목적지에_같은_팀_기물이_있으면_이동_불가() {
        // given
        MoveStrategy strategy = new HorseMoveStrategy();
        Piece mover = Piece.of(Team.CHU, Type.HORSE);
        Piece target = Piece.of(Team.CHU, Type.CANNON);

        Position from = Position.of(0, 2);
        Position to = Position.of(2, 1);

        // then
        Assertions.assertFalse(strategy.canMove(mover, from, to, Map.of(to, target)));
    }
}