package janggi.domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.team.TeamType;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CannonMoveRuleTest {


    @Nested
    @DisplayName("이동 가능한 목적지 계산 테스트")
    class Execute {

        @Test
        @DisplayName("포는 포를 잡을 수 없다")
        void execute_1() {
            // given
            Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
            positionPieceMap.put(Position.valueOf(6, 2), new Cannon(TeamType.BLUE));
            positionPieceMap.put(Position.valueOf(6, 4), new Soldier(TeamType.RED));
            positionPieceMap.put(Position.valueOf(6, 7), new Cannon(TeamType.RED));
            Board board = new Board(positionPieceMap);
            Position from = Position.valueOf(6, 2);
            MoveRule moveRuleOfCannon = new CannonMoveRule(Direction.RIGHT);

            // when
            List<Position> actual = moveRuleOfCannon.execute(from, board);

            // then
            List<Position> expected = List.of(Position.valueOf(6, 5), Position.valueOf(6, 6));
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("포는 포를 제외한 기물을 잡을 수 있다")
        void execute_2() {
            // given
            Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
            positionPieceMap.put(Position.valueOf(6, 2), new Cannon(TeamType.BLUE));
            positionPieceMap.put(Position.valueOf(6, 4), new Soldier(TeamType.RED));
            positionPieceMap.put(Position.valueOf(6, 7), new Soldier(TeamType.RED));
            Board board = new Board(positionPieceMap);
            Position from = Position.valueOf(6, 2);
            MoveRule moveRuleOfCannon = new CannonMoveRule(Direction.RIGHT);

            // when
            List<Position> actual = moveRuleOfCannon.execute(from, board);

            // then
            List<Position> expected = List.of(Position.valueOf(6, 5), Position.valueOf(6, 6), Position.valueOf(6, 7));
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("포는 포를 뛰어넘을 수 없다")
        void execute_3() {
            // given
            Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
            positionPieceMap.put(Position.valueOf(6, 2), new Cannon(TeamType.BLUE));
            positionPieceMap.put(Position.valueOf(6, 4), new Cannon(TeamType.RED));
            positionPieceMap.put(Position.valueOf(6, 7), new Soldier(TeamType.RED));
            Board board = new Board(positionPieceMap);
            Position from = Position.valueOf(6, 2);
            MoveRule moveRuleOfCannon = new CannonMoveRule(Direction.RIGHT);

            // when
            List<Position> actual = moveRuleOfCannon.execute(from, board);

            // then
            List<Position> expected = List.of();
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("포는 뛰어넘을 수 있는 기물이 없다면 움직일 수 없다")
        void execute_4() {
            // given
            Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
            positionPieceMap.put(Position.valueOf(6, 2), new Cannon(TeamType.BLUE));
            Board board = new Board(positionPieceMap);
            Position from = Position.valueOf(6, 2);
            MoveRule moveRuleOfCannon = new CannonMoveRule(Direction.RIGHT);

            // when
            List<Position> actual = moveRuleOfCannon.execute(from, board);

            // then
            List<Position> expected = List.of();
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("포는 반드시 기물을 하나 뛰어넘은 후 장애물을 만나기 전까지 이동할 수 있다")
        void execute_5() {
            // given
            Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
            positionPieceMap.put(Position.valueOf(6, 2), new Cannon(TeamType.BLUE));
            positionPieceMap.put(Position.valueOf(6, 4), new Soldier(TeamType.RED));
            positionPieceMap.put(Position.valueOf(6, 7), new Soldier(TeamType.BLUE));
            Board board = new Board(positionPieceMap);
            Position from = Position.valueOf(6, 2);
            MoveRule moveRuleOfCannon = new CannonMoveRule(Direction.RIGHT);

            // when
            List<Position> actual = moveRuleOfCannon.execute(from, board);

            // then
            List<Position> expected = List.of(Position.valueOf(6, 5), Position.valueOf(6, 6));
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("포는 반드시 기물을 하나 뛰어넘은 후 장기판의 경계까지 이동할 수 있다")
        void execute_6() {
            // given
            Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
            positionPieceMap.put(Position.valueOf(6, 2), new Cannon(TeamType.BLUE));
            positionPieceMap.put(Position.valueOf(6, 4), new Soldier(TeamType.RED));
            Board board = new Board(positionPieceMap);
            Position from = Position.valueOf(6, 2);
            MoveRule moveRuleOfCannon = new CannonMoveRule(Direction.RIGHT);

            // when
            List<Position> actual = moveRuleOfCannon.execute(from, board);

            // then
            List<Position> expected = List.of(Position.valueOf(6, 5), Position.valueOf(6, 6), Position.valueOf(6, 7),
                    Position.valueOf(6, 8), Position.valueOf(6, 9));
            assertThat(actual).isEqualTo(expected);
        }
    }
}
