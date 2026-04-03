package domain.strategy;

import domain.*;
import domain.vo.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

class GeneralMoveStrategyTest {

    MoveStrategy strategy;
    Piece mover;

    @BeforeEach
    void setUp() {
        strategy = new GeneralMoveStrategy();
        mover = Piece.of(Team.CHU, Type.GENERAL);
    }

    @Test
    @DisplayName("궁의 목적지에 기물이 없으면 이동한다.")
    void 궁_정상_이동() {
        // given
        Position from = Position.of(1, 4);
        Position to = Position.of(1, 5);

        // when & then
        Assertions.assertTrue(strategy.canMove(mover, from, to, Map.of()));
    }

    @Test
    @DisplayName("궁의 목적지에 같은 팀 기물이 있으면 이동하지 않는다.")
    void 궁_목적지에_같은_팀_기물이_있으면_이동_불가() {
        // given
        Position from = Position.of(1, 4);
        Position to = Position.of(1, 5);
        Piece target = Piece.of(Team.CHU, Type.SOLDIER);

        // when & then
        Assertions.assertFalse(strategy.canMove(mover, from, to, Map.of(to, target)));
    }

    @Test
    @DisplayName("궁의 목적지에 다른 팀 기물이 있으면 이동한다.")
    void 궁_목적지에_다른_팀_기물이_있으면_정상_이동() {
        // given
        Position from = Position.of(1, 4);
        Position to = Position.of(1, 5);
        Piece target = Piece.of(Team.HAN, Type.SOLDIER);

        // when & then
        Assertions.assertTrue(strategy.canMove(mover, from, to, Map.of(to, target)));
    }
}
