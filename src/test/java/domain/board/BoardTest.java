package domain.board;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.Team;
import domain.board.factory.BoardFactory;
import domain.pieces.Piece;
import domain.pieces.Soldier;
import execptions.JanggiGameRuleWarningException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public final class BoardTest {

  @Nested
  @DisplayName("보드가 생성될 때")
  class TestMakeBoard {

    @Test
    @DisplayName("보드는 9x10 크기로 구성되어야 한다")
    void test_generateBoard() {
      //given
      Map<Point, Piece> locations = new HashMap<>();
      locations.put(new Point(3, 1), new Soldier(Team.CHO));

      // when & then
      assertThatThrownBy(() -> new Board(locations))
          .isInstanceOf(JanggiGameRuleWarningException.class);
    }

    @Test
    @DisplayName("보드는 0,0부터 9,8까지 포함된다")
    void test_locationRange() {
      //given
      final EnumMap<Team, Integer> setups = new EnumMap<>(Team.class);
      setups.put(Team.HAN, 1);
      setups.put(Team.CHO, 1);
      final Board board = BoardFactory.generateBoard(setups);

      //when
      final Map<Point, Piece> locations = board.getLocations();

      // then
      assertAll(
          () -> assertThat(locations.keySet()).contains(new Point(0, 0)),
          () -> assertThat(locations.keySet()).contains(new Point(9, 8)),
          () -> assertThat(locations.keySet()).doesNotContain(new Point(10, 9))
      );
    }
  }

  @Nested
  @DisplayName("기물을 이동할 때")
  class TestMakeMovementsThrowException {

    @Test
    @DisplayName("해당 경로로 이동할 수 없으면 예외를 던진다")
    void test_throwExceptionWhenPieceIsNotMovable() {
      // given
      final EnumMap<Team, Integer> setups = new EnumMap<>(Team.class);
      setups.put(Team.HAN, 1);
      setups.put(Team.CHO, 1);
      final Board board = BoardFactory.generateBoard(setups);
      Point startPoint = new Point(0, 0);
      Point arrivalpoint = new Point(5, 0);

      // when
      assertThatThrownBy(() -> board.movePiece(startPoint, arrivalpoint, Team.CHO))
          .isInstanceOf(JanggiGameRuleWarningException.class)
          .hasMessageContaining("해당 경로로 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("해당 위치가 이동할 수 없는 위치면 예외를 던진다")
    void test_throwExceptionWhenPieceIsNotAbleToArrive() {
      // given
      final EnumMap<Team, Integer> setups = new EnumMap<>(Team.class);
      setups.put(Team.HAN, 1);
      setups.put(Team.CHO, 1);
      final Board board = BoardFactory.generateBoard(setups);

      Point startPoint = new Point(0, 0);
      Point arrivalpoint = new Point(1, 1);

      // when
      assertThatThrownBy(() -> board.movePiece(startPoint, arrivalpoint, Team.HAN))
          .isInstanceOf(JanggiGameRuleWarningException.class)
          .hasMessageContaining("아군 기물만 움직일 수 있습니다.");
    }

    @Test
    @DisplayName("이동할 기물이 존재하지 않으면 예외를 던진다")
    void test_NoPieceOnStartPoint() {
      // given
      final EnumMap<Team, Integer> setups = new EnumMap<>(Team.class);
      setups.put(Team.HAN, 1);
      setups.put(Team.CHO, 1);
      final Board board = BoardFactory.generateBoard(setups);
      Point startPoint = new Point(1, 0);
      Point arrivalpoint = new Point(1, 1);

      // when & then
      assertThatThrownBy(() -> board.movePiece(startPoint, arrivalpoint, Team.HAN))
          .isInstanceOf(JanggiGameRuleWarningException.class)
          .hasMessageContaining("출발점에 이동할 기물이 없습니다.");
    }
  }

}
