package domain.board.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Team;
import domain.board.Board;
import domain.board.Point;
import domain.pieces.Piece;
import domain.pieces.PieceNames;
import execptions.JanggiArgumentException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardFactoryTest {

  @Nested
  @DisplayName("보드를 생성할 때, 선택에 따라 로케이터를 변경한다")
  class TestSetupBoardByElephantLocator {

    @Test
    @DisplayName("바깥 차림을 선택한 경우")
    void test_setupOuterElephantLocator() {
      //given
      final Map<Team, Integer> setupsByTeam = new HashMap<>();
      final Team team = Team.HAN;
      setupsByTeam.put(team, 1);
      final Point pointLeft = new Point(team.getInitialRow(), 1);
      final Point pointRight = new Point(team.getInitialRow(), 7);
      final String expected = PieceNames.ELEPHANT.getNameForTeam(team);

      //when
      final Board board = BoardFactory.generateBoard(setupsByTeam);
      final Map<Point, Piece> locations = board.getLocations();

      //then
      Assertions.assertAll(
          () -> assertThat(locations.get(pointLeft).getName()).isEqualTo(expected),
          () -> assertThat(locations.get(pointRight).getName()).isEqualTo(expected)
      );
    }

    @Test
    @DisplayName("안상 차림을 선택한 경우")
    void test_setupInnerElephantLocator() {
      //given
      final Map<Team, Integer> setupsByTeam = new HashMap<>();
      final Team team = Team.HAN;
      setupsByTeam.put(team, 2);
      final Point pointLeft = new Point(team.getInitialRow(), 2);
      final Point pointRight = new Point(team.getInitialRow(), 6);
      final String expected = PieceNames.ELEPHANT.getNameForTeam(team);

      //when
      final Board board = BoardFactory.generateBoard(setupsByTeam);
      final Map<Point, Piece> locations = board.getLocations();

      //then
      Assertions.assertAll(
          () -> assertThat(locations.get(pointLeft).getName()).isEqualTo(expected),
          () -> assertThat(locations.get(pointRight).getName()).isEqualTo(expected)
      );
    }

    @Test
    @DisplayName("왼상 차림을 선택한 경우")
    void test_setupLeftElephantLocator() {
      //given
      final Map<Team, Integer> setupsByTeam = new HashMap<>();
      final Team team = Team.HAN;
      setupsByTeam.put(team, 3);
      final Point pointLeft = new Point(team.getInitialRow(), 1);
      final Point pointRight = new Point(team.getInitialRow(), 6);
      final String expected = PieceNames.ELEPHANT.getNameForTeam(team);

      //when
      final Board board = BoardFactory.generateBoard(setupsByTeam);
      final Map<Point, Piece> locations = board.getLocations();

      //then
      Assertions.assertAll(
          () -> assertThat(locations.get(pointLeft).getName()).isEqualTo(expected),
          () -> assertThat(locations.get(pointRight).getName()).isEqualTo(expected)
      );
    }

    @Test
    @DisplayName("오른상 차림을 선택한 경우")
    void test_setupRightElephantLocator() {
      //given
      final Map<Team, Integer> setupsByTeam = new HashMap<>();
      final Team team = Team.HAN;
      setupsByTeam.put(team, 4);
      final Point pointLeft = new Point(team.getInitialRow(), 2);
      final Point pointRight = new Point(team.getInitialRow(), 7);
      final String expected = PieceNames.ELEPHANT.getNameForTeam(team);

      //when
      final Board board = BoardFactory.generateBoard(setupsByTeam);
      final Map<Point, Piece> locations = board.getLocations();

      //then
      Assertions.assertAll(
          () -> assertThat(locations.get(pointLeft).getName()).isEqualTo(expected),
          () -> assertThat(locations.get(pointRight).getName()).isEqualTo(expected)
      );
    }

    @Test
    @DisplayName("팀에 따라 다르게 선택한 경우")
    void test_setupLocatorForEachTeam() {
      //given
      final Map<Team, Integer> setupsByTeam = new HashMap<>();
      final Team han = Team.HAN;
      setupsByTeam.put(han, 1);
      final Point pointLeftForHan = new Point(han.getInitialRow(), 1);
      final Point pointRightForHan = new Point(han.getInitialRow(), 7);
      final String expectedForHan = PieceNames.ELEPHANT.getNameForTeam(han);

      final Team cho = Team.CHO;
      setupsByTeam.put(cho, 4);
      final Point pointLeftForCho = new Point(cho.getInitialRow(), 2);
      final Point pointRightForCho = new Point(cho.getInitialRow(), 7);
      final String expectedForCho = PieceNames.ELEPHANT.getNameForTeam(cho);

      //when
      final Board board = BoardFactory.generateBoard(setupsByTeam);
      final Map<Point, Piece> locations = board.getLocations();

      //then
      Assertions.assertAll(
          () -> assertThat(locations.get(pointLeftForHan).getName()).isEqualTo(expectedForHan),
          () -> assertThat(locations.get(pointRightForHan).getName()).isEqualTo(expectedForHan),
          () -> assertThat(locations.get(pointLeftForCho).getName()).isEqualTo(expectedForCho),
          () -> assertThat(locations.get(pointRightForCho).getName()).isEqualTo(expectedForCho)
      );
    }

    @Test
    @DisplayName("등록되지 않은 차림을 선택한 경우")
    void error_setupInvalidElephantLocator() {
      //given
      final Map<Team, Integer> setupsByTeam = new HashMap<>();
      final Team team = Team.HAN;
      setupsByTeam.put(team, Integer.MAX_VALUE);

      //when&then
      assertThatThrownBy(() -> BoardFactory.generateBoard(setupsByTeam))
          .isInstanceOf(JanggiArgumentException.class);
    }
  }
}
