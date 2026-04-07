package domain.strategy;

import static org.junit.jupiter.api.Assertions.*;

import domain.Piece;
import domain.Team;
import domain.Type;
import domain.vo.Position;
import java.util.HashMap;
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

        Position from = Position.of(0, 3);
        Position to = Position.of(1, 3);

        // when & then
        assertTrue(strategy.canMove(mover, from, to, Map.of()));
    }

    @Test
    @DisplayName("사의 목적지에 같은 팀 기물이 있으면 이동하지 않는다.")
    void 사_목적지에_같은_팀_기물이_있으면_이동_불가() {
        // given
        Piece guardPiece = Piece.of(Team.CHU, Type.GUARD);

        Position from = Position.of(0, 3);
        Position to = Position.of(0, 2);

        // when & then
        assertFalse(guardPiece.canMovePiece(from, to, Map.of(to, guardPiece)));
    }

    @Test
    @DisplayName("사의 목적지에 다른 팀 기물이 있으면 이동한다.")
    void 사_목적지에_다른_팀_기물이_있으면_정상_이동() {
        // given
        Piece guardPiece = Piece.of(Team.CHU, Type.GUARD);
        Piece soldierPiece = Piece.of(Team.HAN, Type.SOLDIER);

        Position from = Position.of(0, 3);
        Position to = Position.of(1, 3);

        // when & then
        assertTrue(guardPiece.canMovePiece(from, to, Map.of(to, soldierPiece)));
    }

    @Test
    @DisplayName("사가 궁성 외부 대각선으로 이동하면 예외를 발생한다.")
    void 사_궁성_밖_대각선_이동_불가() {
        // given
        Piece guardPiece = Piece.of(Team.CHU, Type.GUARD);

        Position from = Position.of(0, 3);
        Position to = Position.of(1, 2);

        // when & then
        assertFalse(guardPiece.canMovePiece(from, to, Map.of()));
    }

    @Test
    @DisplayName("사가 궁성 안에서 이동할 수 없는 곳으로 대각선 이동하려고 하면 이동하지 않는다.")
    void 사_궁성_안_이동_불가능한_대각선_이동_불가() {
        // given
        Piece guardPiece = Piece.of(Team.CHU, Type.GUARD);

        Position from = Position.of(1, 3);
        Position to = Position.of(2, 4);
        Map<Position, Piece> pieceOnPath = new HashMap<>();
        pieceOnPath.put(to, null);

        // when // then
        assertFalse(guardPiece.canMovePiece(from, to, pieceOnPath));
    }
}
