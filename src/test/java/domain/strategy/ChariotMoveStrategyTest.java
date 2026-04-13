package domain.strategy;

import static org.junit.jupiter.api.Assertions.*;

import domain.Piece;
import domain.Team;
import domain.Type;
import domain.vo.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

class ChariotMoveStrategyTest {

    Piece chariotPiece;
    Piece horsePiece;
    Piece elephantPiece;

    @BeforeEach
    void setUp() {
        chariotPiece = Piece.of(Team.CHU, Type.CHARIOT);
        horsePiece = Piece.of(Team.CHU, Type.HORSE);
        elephantPiece = Piece.of(Team.HAN, Type.ELEPHANT);
    }

    @Test
    @DisplayName("차의 직선 이동 경로에 기물이 없으면 직선 이동한다.")
    void 차_직선_이동() {
        // given
        Position from = Position.of(0, 0);
        Position to = Position.of(2, 0);

        // when // then
        assertTrue(chariotPiece.canMovePiece(from, to, Map.of()));
    }

    @Test
    @DisplayName("이동 목적지에 같은 팀 기물이 있으면 직선 이동하지 않는다.")
    void 차_목적지에_같은_팀_기물이_있으면_이동_불가() {
        // given
        Position from = Position.of(0, 0);
        Position to = Position.of(3, 0);

        // when // then
        assertFalse(chariotPiece.canMovePiece(from, to, Map.of(to, horsePiece)));
    }

    @Test
    @DisplayName("이동 목적지에 다른 팀 기물이 있으면 직선 이동한다.")
    void 차_목적지에_다른_팀_기물이_있으면_이동_가능() {
        // given
        Position from = Position.of(0, 0);
        Position to = Position.of(0, 3);

        // when // then
        assertTrue(chariotPiece.canMovePiece(from, to, Map.of(to, elephantPiece)));
    }

    @Test
    @DisplayName("궁성 안에서 가능한 대각선 이동 시 이동한다.")
    void 궁성_안_가능한_대각선_이동_가능() {
        // given
        Position from = Position.of(2, 3);
        Position to = Position.of(0, 5);

        // when // then
        assertTrue(chariotPiece.canMovePiece(from, to, Map.of()));
    }

    @Test
    @DisplayName("궁성 안에서 불가능한 대각선 이동 시 이동하지 않는다.")
    void 궁성_안_불가능한_대각선_이동은_불가() {
        // given
        Position from = Position.of(0, 4);
        Position to = Position.of(2, 6);

        // when // then
        assertFalse(chariotPiece.canMovePiece(from, to, Map.of()));
    }
}
