package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.factory.BoardFactory;
import domain.pieces.Cannon;
import domain.pieces.Chariot;
import domain.pieces.Piece;
import domain.pieces.Soldier;
import domain.player.Team;
import exceptions.JanggiGameRuleWarningException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import vo.Choice;

public final class BoardTest {

    @Nested
    @DisplayName("기물을 이동할 때")
    class TestMakeMovementsThrowException {

        @Test
        @DisplayName("해당 경로로 이동할 수 없을 경우, 예외를 던진다")
        void test_throwExceptionWhenPieceIsNotMovable() {
            // given
            final EnumMap<Team, Choice> given = new EnumMap<>(Team.class);
            given.put(Team.CHO, new Choice(1));
            given.put(Team.HAN, new Choice(1));
            final Board board = BoardFactory.generateBoard(given);
            Point startPoint = new Point(0, 0);
            Point arrivalpoint = new Point(5, 0);

            // when
            assertThatThrownBy(() -> board.canMovePiece(startPoint, arrivalpoint, Team.CHO))
                    .isInstanceOf(JanggiGameRuleWarningException.class)
                    .hasMessageContaining("해당 경로로 이동할 수 없습니다.");
        }

        @Test
        @DisplayName("도착점이 이동할 수 없는 위치일 경우, 예외를 던진다")
        void test_throwExceptionWhenPieceIsNotAbleToArrive() {
            // given
            final EnumMap<Team, Choice> given = new EnumMap<>(Team.class);
            given.put(Team.CHO, new Choice(1));
            given.put(Team.HAN, new Choice(1));
            final Board board = BoardFactory.generateBoard(given);

            Point startPoint = new Point(0, 0);
            Point arrivalpoint = new Point(1, 1);

            // when
            assertThatThrownBy(() -> board.canMovePiece(startPoint, arrivalpoint, Team.HAN))
                    .isInstanceOf(JanggiGameRuleWarningException.class)
                    .hasMessageContaining("아군 기물만 움직일 수 있습니다.");
        }

        @Test
        @DisplayName("이동할 기물이 존재하지 않을 경우, 예외를 던진다")
        void test_NoPieceOnStartPoint() {
            // given
            final EnumMap<Team, Choice> given = new EnumMap<>(Team.class);
            given.put(Team.CHO, new Choice(1));
            given.put(Team.HAN, new Choice(1));
            final Board board = BoardFactory.generateBoard(given);
            Point startPoint = new Point(1, 0);
            Point arrivalpoint = new Point(1, 1);

            // when & then
            assertThatThrownBy(() -> board.canMovePiece(startPoint, arrivalpoint, Team.HAN))
                    .isInstanceOf(JanggiGameRuleWarningException.class)
                    .hasMessageContaining("출발점에 이동할 기물이 없습니다.");
        }
    }

    @Nested
    @DisplayName("궁궐 내부에서의 이동일 경우, 이동 검증 방식이 변경된다.")
    class TestCanDifferentMoveInPalace {
        @Test
        @DisplayName("포의 경우")
        void test_forCannon() {
            //given
            final Point centerOfPalace = new Point(1, 4);
            final Map<Point, Piece> locations = new HashMap<>();
            final Team team = Team.CHO;
            locations.put(centerOfPalace, new Chariot(team));

            final Point start = new Point(0, 3);
            final Point arrival = new Point(2, 5);
            final Point arrivalOutOfPalace = new Point(3, 6);
            locations.put(start, new Cannon(team));
            final Board board = new Board(locations);

            //when&then
            assertThat(board.canMovePiece(start, arrival, team)).isTrue();
            assertThatThrownBy(() -> board.canMovePiece(start, arrivalOutOfPalace, team))
                    .isInstanceOf(JanggiGameRuleWarningException.class)
                    .hasMessageContaining("해당 기물이 도착할 수 없는 위치입니다.");

        }

        @Test
        @DisplayName("차의 경우")
        void test_forChariot() {
            //given
            final Map<Point, Piece> locations = new HashMap<>();
            final Team team = Team.CHO;
            final Point start = new Point(0, 3);
            final Point arrival = new Point(2, 5);
            final Point arrivalOutOfPalace = new Point(3, 6);
            locations.put(start, new Chariot(team));
            final Board board = new Board(locations);

            //when&then
            assertThat(board.canMovePiece(start, arrival, team)).isTrue();
            assertThatThrownBy(() -> board.canMovePiece(start, arrivalOutOfPalace, team))
                    .isInstanceOf(JanggiGameRuleWarningException.class)
                    .hasMessageContaining("해당 기물이 도착할 수 없는 위치입니다.");
        }

        @Test
        @DisplayName("졸의 경우")
        void test_forSoldier() {
            //given
            final Map<Point, Piece> locations = new HashMap<>();
            final Team team = Team.CHO;
            final Point start = new Point(0, 3);
            final Point arrival = new Point(1, 4);
            final Point arrivalOutOfPalace = new Point(1, 2);
            locations.put(start, new Soldier(team));
            final Board board = new Board(locations);

            //when&then
            assertThat(board.canMovePiece(start, arrival, team)).isTrue();
            assertThatThrownBy(() -> board.canMovePiece(start, arrivalOutOfPalace, team))
                    .isInstanceOf(JanggiGameRuleWarningException.class)
                    .hasMessageContaining("해당 기물이 도착할 수 없는 위치입니다.");
        }
    }
}
