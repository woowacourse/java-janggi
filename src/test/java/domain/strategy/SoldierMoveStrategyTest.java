package domain.strategy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.*;
import domain.vo.Position;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

class SoldierMoveStrategyTest {

    @Test
    @DisplayName("졸의 목적지에 기물이 없으면 이동한다.")
    void 졸_정상_이동() {
        // given
        Piece soldierPiece = Piece.of(Team.CHU, Type.SOLDIER);

        Position position = Position.of(3, 4);
        Position targetPosition = Position.of(4, 4);

        // then
        assertTrue(soldierPiece.canMovePiece(position, targetPosition, Map.of()));
    }

    @Test
    @DisplayName("졸의 목적지에 같은 팀 기물이 있으면 이동하지 않는다.")
    void 졸_목적지에_같은_팀_기물이_있으면_이동_불가() {
        // given
        Piece soldierPiece = Piece.of(Team.CHU, Type.SOLDIER);
        Piece sameTeamPiece = Piece.of(Team.CHU, Type.SOLDIER);

        Position position = Position.of(3, 4);
        Position targetPosition = Position.of(4, 4);

        // then
        assertFalse(
                soldierPiece.canMovePiece(position, targetPosition, Map.of(targetPosition, sameTeamPiece)));
    }

    @Test
    @DisplayName("졸의 목적지에 다른 팀 기물이 있으면 이동한다.")
    void 졸_목적지에_다른_팀_기물이_있으면_정상_이동() {
        // given
        Piece chuSoldierPiece = Piece.of(Team.CHU, Type.SOLDIER);
        Piece hanSoldierPiece = Piece.of(Team.HAN, Type.SOLDIER);

        Position position = Position.of(3, 4);
        Position targetPosition = Position.of(4, 4);

        // then
        assertTrue(
                chuSoldierPiece.canMovePiece(position, targetPosition, Map.of(targetPosition, hanSoldierPiece)));
    }

    @Test
    @DisplayName("초나라 졸의 이동 경로가 후퇴이면 이동할 수 없다.")
    void 초나라_졸의_이동_경로가_후퇴이면_이동_불가() {
        // given
        Piece chuSoldierPiece = Piece.of(Team.CHU, Type.SOLDIER);

        Position position = Position.of(3, 4);
        Position targetPosition = Position.of(2, 4);

        // then
        assertFalse(chuSoldierPiece.canMovePiece(position, targetPosition, Map.of()));
    }

    @Test
    @DisplayName("한나라 졸의 이동 경로가 후퇴이면 이동할 수 없다.")
    void 한나라_졸의_이동_경로가_후퇴이면_이동_불가() {
        // given
        Piece hanSoldierPiece = Piece.of(Team.HAN, Type.SOLDIER);

        // when
        Position position = Position.of(6, 4);
        Position targetPosition = Position.of(7, 4);

        // then
        assertFalse(hanSoldierPiece.canMovePiece(position, targetPosition, Map.of()));
    }

    @Test
    @DisplayName("한나라 졸의 이동 경로가 궁성 중앙(1, 4)에서 대각선 전진방향이면 이동한다.")
    void 한나라_졸의_이동_경로가_궁성_중앙에서_대각선_전진방향이면_이동한다() {
        // given
        Piece hanSoldierPiece = Piece.of(Team.HAN, Type.SOLDIER);
        Position from = Position.of(1, 4);
        Position to = Position.of(0, 3);
        HashMap<Position, Piece> piecesOnPath = new HashMap<>();
        piecesOnPath.put(to, null);

        //when // then
        assertTrue(hanSoldierPiece.canMovePiece(from, to, piecesOnPath));
    }

    @Test
    @DisplayName("한나라 졸의 이동 경로가 궁성 밖에서 대각선 전진이면 이동할 수 없다.")
    void 한나라_졸의_이동_경로가_궁성_밖에서_대각선_전진이면_이동할_수_없다() {
        // given
        Piece hanSoldierPiece = Piece.of(Team.HAN, Type.SOLDIER);
        Position from = Position.of(6, 4);
        Position to = Position.of(5, 3);
        HashMap<Position, Piece> piecesOnPath = new HashMap<>();
        piecesOnPath.put(to, null);

        // when // then
        assertFalse(hanSoldierPiece.canMovePiece(from, to, piecesOnPath));
    }

    @Test
    @DisplayName("초나라 졸의 궁성 안에서 궁성 밖 대각선 전진이면 이동할 수 없다.")
    void 초나라_졸의_궁성_안에서_궁성_밖_대각선_전진이면_이동할_수_없다() {
        // given
        Piece chuSoldierPiece = Piece.of(Team.CHU, Type.SOLDIER);
        Position from = Position.of(8, 3);
        Position to = Position.of(9, 2);
        HashMap<Position, Piece> piecesOnPath = new HashMap<>();
        piecesOnPath.put(to, null);

        // when // then
        assertFalse(chuSoldierPiece.canMovePiece(from, to, piecesOnPath));
    }
}
