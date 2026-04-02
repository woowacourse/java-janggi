package domain.strategy;

import domain.Board;
import domain.BoardFactory;
import domain.Piece;
import domain.Team;
import domain.Type;
import domain.vo.Position;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotMoveStrategyTest {

    Piece chariotPiece;
    Board board;

    @BeforeEach
    void setUp() {
        chariotPiece = Piece.of(Team.CHU, Type.CHARIOT);
        Piece horsePiece = Piece.of(Team.CHU, Type.HORSE);
        Piece elephantPiece = Piece.of(Team.HAN, Type.ELEPHANT);
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(0, 0), chariotPiece);
        boardMapper.put(Position.of(3, 0), horsePiece);
        boardMapper.put(Position.of(0, 3), elephantPiece);
        board = BoardFactory.of(boardMapper);
    }

    @Test
    @DisplayName("차의 직선 이동 경로에 기물이 없으면 직선 이동한다.")
    void 차_직선_이동() {
        // given
        // when
        Position from = Position.of(0, 0);
        Position to = Position.of(2, 0);

        // then
        Assertions.assertTrue(chariotPiece.canMovePiece(from, to, board));
    }

    @Test
    @DisplayName("이동 목적지에 같은 팀 기물이 있으면 직선 이동하지 않는다.")
    void 차_목적지에_같은_팀_기물이_있으면_이동_불가() {
        // given
        // when
        Position from = Position.of(0, 0);
        Position to = Position.of(3, 0);

        // then
        Assertions.assertFalse(chariotPiece.canMovePiece(from, to, board));
    }

    @Test
    @DisplayName("이동 목적지에 다른 팀 기물이 있으면 직선 이동한다.")
    void 차_목적지에_다른_팀_기물이_있으면_이동_가능() {
        // given
        // when
        Position from = Position.of(0, 0);
        Position to = Position.of(0, 3);

        // then
        Assertions.assertTrue(chariotPiece.canMovePiece(from, to, board));
    }
}
