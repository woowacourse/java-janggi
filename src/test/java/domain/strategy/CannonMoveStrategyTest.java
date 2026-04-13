package domain.strategy;

import domain.*;
import domain.vo.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

class CannonMoveStrategyTest {

    @Test
    @DisplayName("초나라 포의 이동 경로에 포를 제외한 기물이 1개 있으면 정상 이동한다.")
    void 초나라_포_정상_이동() {
        // given
        MoveStrategy strategy = new CannonMoveStrategy();
        Piece mover = Piece.of(Team.CHU, Type.CANNON);
        Piece jumpPiece = Piece.of(Team.CHU, Type.SOLDIER);

        Position from = Position.of(2, 1);
        Position to = Position.of(4, 1);

        // then
        Assertions.assertTrue(strategy.canMove(mover, from, to, Map.of(Position.of(3, 1), jumpPiece)));
    }

    @Test
    @DisplayName("한나라 포의 이동 경로에 다른 기물이 없으면 이동하지 못한다.")
    void 포의_이동_경로에_다른_기물이_없으면_이동_불가() {
        // given
        Piece cannon = Piece.of(Team.CHU, Type.CANNON);

        Position from = Position.of(2, 1);
        Position to = Position.of(5, 1);

        // then
        Assertions.assertFalse(cannon.canMovePiece(from, to, Map.of()));
    }

    @Test
    @DisplayName("초나라 포의 이동 경로에 기물이 둘 이상 있으면 이동하지 않는다.")
    void 초나라_포_이동_경로에_기물이_둘_이상_있으면_이동_불가() {
        // given
        MoveStrategy strategy = new CannonMoveStrategy();
        Piece mover = Piece.of(Team.CHU, Type.CANNON);
        Piece soldier1 = Piece.of(Team.CHU, Type.SOLDIER);
        Piece soldier2 = Piece.of(Team.CHU, Type.SOLDIER);

        Position from = Position.of(2, 1);
        Position to = Position.of(5, 1);

        // then
        Assertions.assertFalse(strategy.canMove(mover, from, to,
                Map.of(Position.of(3, 1), soldier1, Position.of(4, 1), soldier2)));
    }

    @Test
    @DisplayName("목적지에 같은 팀 기물이 있으면 이동하지 않는다.")
    void 포_목적지에_같은_팀_기물이_있으면_이동_불가() {
        // given
        MoveStrategy strategy = new CannonMoveStrategy();
        Piece mover = Piece.of(Team.CHU, Type.CANNON);
        Piece jumpPiece = Piece.of(Team.CHU, Type.SOLDIER);
        Piece target = Piece.of(Team.CHU, Type.SOLDIER);

        Position from = Position.of(2, 1);
        Position to = Position.of(5, 1);

        // then
        Assertions.assertFalse(strategy.canMove(mover, from, to,
                Map.of(Position.of(3, 1), jumpPiece, to, target)));
    }

    @Test
    @DisplayName("목적지에 포가 있으면 이동하지 않는다.")
    void 목적지에_포가_있으면_이동_불가() {
        // given
        MoveStrategy strategy = new CannonMoveStrategy();
        Piece mover = Piece.of(Team.CHU, Type.CANNON);
        Piece jumpPiece = Piece.of(Team.CHU, Type.SOLDIER);
        Piece targetCannon = Piece.of(Team.HAN, Type.CANNON);

        Position from = Position.of(2, 1);
        Position to = Position.of(5, 1);

        // then
        Assertions.assertFalse(strategy.canMove(mover, from, to,
                Map.of(Position.of(3, 1), jumpPiece, to, targetCannon)));
    }

    @Test
    @DisplayName("목적지에 다른 팀 기물이 있으면 이동한다.")
    void 포_목적지에_다른_팀_기물이_있으면_이동_가능() {
        // given
        MoveStrategy strategy = new CannonMoveStrategy();
        Piece mover = Piece.of(Team.CHU, Type.CANNON);
        Piece jumpPiece = Piece.of(Team.CHU, Type.SOLDIER);
        Piece target = Piece.of(Team.HAN, Type.SOLDIER);

        Position from = Position.of(2, 1);
        Position to = Position.of(5, 1);

        // then
        Assertions.assertTrue(strategy.canMove(mover, from, to,
                Map.of(Position.of(3, 1), jumpPiece, to, target)));
    }

    @Test
    @DisplayName("포의 이동 경로에 포가 존재하면 이동하지 못한다.")
    void 포의_이동_경로에_포가_있으면_이동_불가() {
        // given
        MoveStrategy strategy = new CannonMoveStrategy();
        Piece mover = Piece.of(Team.CHU, Type.CANNON);
        Piece pathCannon = Piece.of(Team.CHU, Type.CANNON);

        Position from = Position.of(2, 1);
        Position to = Position.of(5, 1);

        // then
        Assertions.assertFalse(strategy.canMove(mover, from, to,
                Map.of(Position.of(3, 1), pathCannon)));
    }

    @Test
    @DisplayName("궁성 안에서 포의 이동 가능한 대각선 이동 시 이동한다.")
    void 궁성_안_가능한_포의_대각선_이동() {
        // given
        Position from = Position.of(7, 3);
        Position to = Position.of(9, 5);

        Piece mover = Piece.of(Team.CHU, Type.CANNON);
        Piece general = Piece.of(Team.CHU, Type.GENERAL);

        // when // then
        Assertions.assertTrue(mover.canMovePiece(from, to, Map.of(Position.of(8, 4), general)));
    }
}
