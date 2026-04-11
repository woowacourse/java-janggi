package janggi.domain.moveRules.palaceMoveRules;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Palace;
import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.moveRules.MoveRule;
import janggi.domain.moveRules.palaceMoveRule.PoPalaceMoveRule;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PoPalaceMoveRuleTest {

    @Test
    @DisplayName("포는 궁성영역 모서리에서 중앙에 포가 아닌 기물이 있으면 대각선으로 이동할 수 있다")
    void 포_궁성에서의_이동_성공() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position poCurrentPosition = new Position(4, 10);
        Team hanTeam = Team.HAN;
        Piece po = new Piece(hanTeam, PieceType.PO);
        customBoard.put(poCurrentPosition, po);
        customBoard.put(Palace.CHO_PALACE_CENTER, new Piece(Team.CHO, PieceType.ZOL));
        MoveRule poPalaceMoveRule = new PoPalaceMoveRule();

        //when
        List<Position> availablePosition = poPalaceMoveRule.calculateAvailablePositions(
                poCurrentPosition,
                hanTeam,
                customBoard
        );

        //then
        assertThat(availablePosition).contains(new Position(6, 8));
    }

    @Test
    @DisplayName("포는 궁성에서 중앙에 기물이 없으면 궁성이동을 할 수 없다")
    void 포_궁성에서_중앙기물없으면_실패() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position poCurrentPosition = new Position(4, 10);
        Team hanTeam = Team.HAN;
        Piece po = new Piece(hanTeam, PieceType.PO);
        customBoard.put(poCurrentPosition, po);
        MoveRule poPalaceMoveRule = new PoPalaceMoveRule();

        //when
        List<Position> availablePosition = poPalaceMoveRule.calculateAvailablePositions(
                poCurrentPosition,
                hanTeam,
                customBoard
        );

        //then
        assertThat(availablePosition).isEmpty();
    }

    @Test
    @DisplayName("포는 궁성에서 중앙에 포가 있으면 궁성이동을 할 수 없다")
    void 포_궁성에서_중앙기물이_포이면_실패() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position poCurrentPosition = new Position(4, 10);
        Team hanTeam = Team.HAN;
        Piece po = new Piece(hanTeam, PieceType.PO);
        customBoard.put(poCurrentPosition, po);
        customBoard.put(Palace.CHO_PALACE_CENTER, new Piece(Team.CHO, PieceType.PO));
        MoveRule poPalaceMoveRule = new PoPalaceMoveRule();

        //when
        List<Position> availablePosition = poPalaceMoveRule.calculateAvailablePositions(
                poCurrentPosition,
                hanTeam,
                customBoard
        );

        //then
        assertThat(availablePosition).isEmpty();
    }

    @Test
    @DisplayName("포는 궁성에서 목적지에 아군기물이 있으면 궁성이동을 할 수 없다")
    void 포_궁성에서_목적지가_아군기물이면_이동_실패() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position poCurrentPosition = new Position(4, 10);
        Team hanTeam = Team.HAN;
        Piece po = new Piece(hanTeam, PieceType.PO);
        customBoard.put(poCurrentPosition, po);
        customBoard.put(new Position(6, 8), new Piece(Team.HAN, PieceType.ZOL));
        MoveRule poPalaceMoveRule = new PoPalaceMoveRule();

        //when
        List<Position> availablePosition = poPalaceMoveRule.calculateAvailablePositions(
                poCurrentPosition,
                hanTeam,
                customBoard
        );

        //then
        assertThat(availablePosition).isEmpty();
    }

    @Test
    @DisplayName("포는 궁성에서 목적지에 포가 있으면 궁성이동을 할 수 없다")
    void 포_궁성에서_목적지가_포이면_이동_실패() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position poCurrentPosition = new Position(4, 10);
        Team hanTeam = Team.HAN;
        Piece po = new Piece(hanTeam, PieceType.PO);
        customBoard.put(poCurrentPosition, po);
        customBoard.put(new Position(6, 8), new Piece(Team.CHO, PieceType.PO));
        MoveRule poPalaceMoveRule = new PoPalaceMoveRule();

        //when
        List<Position> availablePosition = poPalaceMoveRule.calculateAvailablePositions(
                poCurrentPosition,
                hanTeam,
                customBoard
        );

        //then
        assertThat(availablePosition).isEmpty();
    }
}
