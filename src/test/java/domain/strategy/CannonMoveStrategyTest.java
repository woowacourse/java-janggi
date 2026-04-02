package domain.strategy;

import domain.*;
import domain.vo.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class CannonMoveStrategyTest {

    @Test
    @DisplayName("초나라 포의 이동 경로에 포를 제외한 기물이 1개 있으면 정상 이동한다.")
    void 초나라_포_정상_이동() {
        // given
        MoveStrategy strategy = new CannonMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(2, 1), Piece.of(Team.CHU, Type.CANNON));
        boardMapper.put(Position.of(3, 1), Piece.of(Team.CHU, Type.SOLDIER));

        Board board = BoardFactory.of(boardMapper);

        // when
        Position from = Position.of(2, 1);
        Position to = Position.of(4, 1);

        // then
        Assertions.assertTrue(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("한나라 포의 이동 경로에 다른 기물이 없으면 이동하지 못한다.")
    void 포의_이동_경로에_다른_기물이_없으면_이동_불가() {
        // given
        Map<Position, Piece> boardMapper = new HashMap<>();
        Piece cannon = Piece.of(Team.CHU, Type.CANNON);
        boardMapper.put(Position.of(2, 1), cannon);

        Board board = BoardFactory.of(boardMapper);

        // when
        Position from = Position.of(2, 1);
        Position to = Position.of(5, 1);

        // then
        Assertions.assertFalse(cannon.canMovePiece(from, to, board));
    }

    @Test
    @DisplayName("초나라 포의 이동 경로에 기물이 둘 이상 있으면 이동하지 않는다.")
    void 초나라_포_이동_경로에_기물이_둘_이상_있으면_이동_불가() {
        // given
        MoveStrategy strategy = new CannonMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(2, 1), Piece.of(Team.CHU, Type.CANNON));
        boardMapper.put(Position.of(3, 1), Piece.of(Team.CHU, Type.SOLDIER));
        boardMapper.put(Position.of(4, 1), Piece.of(Team.CHU, Type.SOLDIER));

        Board board = BoardFactory.of(boardMapper);

        // when
        Position from = Position.of(2, 1);
        Position to = Position.of(5, 1);

        // then
        Assertions.assertFalse(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("목적지에 같은 팀 기물이 있으면 이동하지 않는다.")
    void 포_목적지에_같은_팀_기물이_있으면_이동_불가() {
        // given
        MoveStrategy strategy = new CannonMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(2, 1), Piece.of(Team.CHU, Type.CANNON));
        boardMapper.put(Position.of(3, 1), Piece.of(Team.CHU, Type.SOLDIER));
        boardMapper.put(Position.of(5, 1), Piece.of(Team.CHU, Type.SOLDIER));

        Board board = BoardFactory.of(boardMapper);

        // when
        Position from = Position.of(2, 1);
        Position to = Position.of(5, 1);

        // then
        Assertions.assertFalse(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("목적지에 포가 있으면 이동하지 않는다.")
    void 목적지에_포가_있으면_이동_불가() {
        // given
        MoveStrategy strategy = new CannonMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(2, 1), Piece.of(Team.CHU, Type.CANNON));
        boardMapper.put(Position.of(3, 1), Piece.of(Team.CHU, Type.SOLDIER));
        boardMapper.put(Position.of(5, 1), Piece.of(Team.HAN, Type.CANNON));

        Board board = BoardFactory.of(boardMapper);

        // when
        Position from = Position.of(2, 1);
        Position to = Position.of(5, 1);

        // then
        Assertions.assertFalse(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("목적지에 다른 팀 기물이 있으면 이동한다.")
    void 포_목적지에_다른_팀_기물이_있으면_이동_가능() {
        // given
        MoveStrategy strategy = new CannonMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(2, 1), Piece.of(Team.CHU, Type.CANNON));
        boardMapper.put(Position.of(3, 1), Piece.of(Team.CHU, Type.SOLDIER));
        boardMapper.put(Position.of(5, 1), Piece.of(Team.HAN, Type.SOLDIER));

        Board board = BoardFactory.of(boardMapper);

        // when
        Position from = Position.of(2, 1);
        Position to = Position.of(5, 1);

        // then
        Assertions.assertTrue(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("포의 이동 경로에 포가 존재하면 이동하지 못한다.")
    void 포의_이동_경로에_포가_있으면_이동_불가() {
        // given
        MoveStrategy strategy = new CannonMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(2, 1), Piece.of(Team.CHU, Type.CANNON));
        boardMapper.put(Position.of(3, 1), Piece.of(Team.CHU, Type.CANNON));
        boardMapper.put(Position.of(5, 1), Piece.of(Team.HAN, Type.SOLDIER));

        Board board = BoardFactory.of(boardMapper);

        // when
        Position from = Position.of(2, 1);
        Position to = Position.of(5, 1);

        // then
        Assertions.assertFalse(strategy.canMove(from, to, board));
    }
}
