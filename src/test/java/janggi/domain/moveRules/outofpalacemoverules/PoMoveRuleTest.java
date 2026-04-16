package janggi.domain.moveRules.outofpalacemoverules;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.board.Board;
import janggi.domain.moveRules.MoveRule;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PoMoveRuleTest {

    @Test
    @DisplayName("포는 중간에 포가 아닌 기물(포다리)이 하나 있으면, 그 너머 공간으로 이동할 수 있다.")
    void 포는_장애물을_넘어_빈_공간으로_이동() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position start = new Position(5, 5);
        customBoard.put(start, new Piece(Team.CHO, PieceType.PO));
        customBoard.put(new Position(5, 6), new Piece(Team.CHO, PieceType.ZOL));
        MoveRule poMoveRule = new PoMoveRule();

        //when
        List<Position> availablePositions = poMoveRule.calculateAvailablePositions(start, Team.CHO, customBoard);

        //then
        assertThat(availablePositions).contains(
                new Position(5, 7),
                new Position(5, 8),
                new Position(5, 9),
                new Position(5, 10)
        );
    }

    @Test
    @DisplayName("포는 넘어가려는 목적지에 또 다른 포가 있으면, 포는 포를 포획할 수 없으므로 이동할 수 없다")
    void 포_목적지에_다른_포가_있으면_포획_및_이동_불가() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position position = new Position(5, 8);
        customBoard.put(position, new Piece(Team.CHO, PieceType.PO));
        customBoard.put(new Position(5, 6), new Piece(Team.HAN, PieceType.CHA));
        customBoard.put(new Position(5, 5), new Piece(Team.HAN, PieceType.PO));
        Board board = new Board(customBoard);

        //when & then
        assertThatThrownBy(() -> board.findAvailablePositions(position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동할 수 없는 좌표입니다.");
    }

    @Test
    @DisplayName("포는 장애물이 없으면 이동할 수 없다.")
    void 포는_장애물_없이_이동_불가() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position position = new Position(5, 5);
        Piece choPo = new Piece(Team.CHO, PieceType.PO);
        customBoard.put(position, choPo);
        MoveRule poMoveRule = new PoMoveRule();

        //when
        List<Position> availablePositions = poMoveRule.calculateAvailablePositions(position, Team.CHO, customBoard);

        //then
        assertThat(availablePositions).isEmpty();
    }
}
