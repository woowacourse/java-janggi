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
        Map<Position, Piece> PositionPiecePair = new LinkedHashMap<>();
        Piece guardPiece = Piece.of(Team.CHU, Type.GUARD);
        PositionPiecePair.put(Position.of(0, 3), guardPiece);
        PositionPiecePair.put(Position.of(0, 2), guardPiece);
        Board board = Board.of(PositionPiecePair);

        Position position = Position.of(0, 3);
        Position targetPosition = Position.of(0, 2);

        // when
        // then
        Assertions.assertFalse(guardPiece.canMovePiece(position, targetPosition, board));
    }


    @Test
    @DisplayName("사의 목적지에 다른 팀 기물이 있으면 이동한다.")
    void 사_목적지에_다른_팀_기물이_있으면_정상_이동() {
        // given
        Map<Position, Piece> positionPiecePair = new LinkedHashMap<>();
        Piece guardPiece = Piece.of(Team.CHU, Type.GUARD);
        positionPiecePair.put(Position.of(0, 3), guardPiece);
        Piece soldierPiece = Piece.of(Team.HAN, Type.SOLDIER);
        positionPiecePair.put(Position.of(1, 3), soldierPiece);
        Board board = Board.of(positionPiecePair);

        Position position = Position.of(0, 3);
        Position targetPosition = Position.of(1, 3);

        // when
        // then
        Assertions.assertTrue(guardPiece.canMovePiece(position, targetPosition, board));
    }

    @Test
    @DisplayName("사가 궁성 외부 대각선으로 이동하면 예외를 발생한다.")
    void 사_궁성_밖_대각선_이동_불가() {
        // given
        Map<Position, Piece> positionPiecePair = new LinkedHashMap<>();
        Piece guardPiece = Piece.of(Team.CHU, Type.GUARD);
        positionPiecePair.put(Position.of(0, 3), guardPiece);
        Board board = Board.of(positionPiecePair);

        Position position = Position.of(0, 3);
        Position targetPosition = Position.of(1, 2);

        // when
        // then
        Assertions.assertFalse(guardPiece.canMovePiece(position, targetPosition, board));
    }
}
