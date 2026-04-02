package domain.strategy;

import domain.Piece;
import domain.Team;
import domain.Type;
import domain.vo.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

class GuardMoveStrategyTest {

    @Test
    @DisplayName("사의 목적지에 기물이 없으면 이동한다.")
    void 사_정상_이동() {
        // given
        MoveStrategy strategy = new GuardMoveStrategy();
        Piece mover = Piece.of(Team.CHU, Type.GUARD);

        Position position = Position.of(0, 3);
        Position targetPosition = Position.of(1, 3);

        // when & then
        Assertions.assertTrue(strategy.canMove(mover, position, targetPosition, Map.of()));
    }

    @Test
    @DisplayName("사의 목적지에 같은 팀 기물이 있으면 이동하지 않는다.")
    void 사_목적지에_같은_팀_기물이_있으면_이동_불가() {
        // given
        Piece guardPiece = Piece.of(Team.CHU, Type.GUARD);

        Position position = Position.of(0, 3);
        Position targetPosition = Position.of(0, 2);

        // when & then
        Assertions.assertFalse(guardPiece.canMovePiece(position, targetPosition, Map.of(targetPosition, guardPiece)));
    }

    @Test
    @DisplayName("사의 목적지에 다른 팀 기물이 있으면 이동한다.")
    void 사_목적지에_다른_팀_기물이_있으면_정상_이동() {
        // given
        Piece guardPiece = Piece.of(Team.CHU, Type.GUARD);
        Piece soldierPiece = Piece.of(Team.HAN, Type.SOLDIER);

        Position position = Position.of(0, 3);
        Position targetPosition = Position.of(1, 3);

        // when & then
        Assertions.assertTrue(guardPiece.canMovePiece(position, targetPosition, Map.of(targetPosition, soldierPiece)));
    }

    @Test
    @DisplayName("사가 궁성 외부 대각선으로 이동하면 예외를 발생한다.")
    void 사_궁성_밖_대각선_이동_불가() {
        // given
        Piece guardPiece = Piece.of(Team.CHU, Type.GUARD);

        Position position = Position.of(0, 3);
        Position targetPosition = Position.of(1, 2);

        // when & then
        Assertions.assertFalse(guardPiece.canMovePiece(position, targetPosition, Map.of()));
    }
}
