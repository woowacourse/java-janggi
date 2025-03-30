package domain.board.factory;

import static domain.player.Team.CHO;
import static domain.player.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Board;
import domain.board.Point;
import domain.pieces.Piece;
import domain.pieces.PieceDefinition;
import domain.player.Player;
import dto.Choice;
import exceptions.JanggiGameRuleWarningException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public final class BoardFactoryTest {

    @Nested
    @DisplayName("보드를 생성할 때, 선택에 따라 로케이터를 변경한다")
    class TestSetupBoardByElephantLocator {

        @Test
        @DisplayName("바깥 차림을 선택한 경우")
        void test_setupOuterElephantLocator() {
            //given
            final Map<Player, Choice> given = new LinkedHashMap<>();
            given.put(new Player(1, HAN), new Choice(1));

            final Point pointLeft = new Point(HAN.getInitialRow(), 1);
            final Point pointRight = new Point(HAN.getInitialRow(), 7);
            final String expected = PieceDefinition.ELEPHANT.getNameForTeam(HAN);

            //when
            final Board board = BoardFactory.generateBoard(given);
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
            final Map<Player, Choice> given = new LinkedHashMap<>();
            given.put(new Player(1, HAN), new Choice(2));

            final Point pointLeft = new Point(HAN.getInitialRow(), 2);
            final Point pointRight = new Point(HAN.getInitialRow(), 6);
            final String expected = PieceDefinition.ELEPHANT.getNameForTeam(HAN);

            //when
            final Board board = BoardFactory.generateBoard(given);
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
            final Map<Player, Choice> given = new LinkedHashMap<>();
            given.put(new Player(1, HAN), new Choice(3));

            final Point pointLeft = new Point(HAN.getInitialRow(), 1);
            final Point pointRight = new Point(HAN.getInitialRow(), 6);
            final String expected = PieceDefinition.ELEPHANT.getNameForTeam(HAN);

            //when
            final Board board = BoardFactory.generateBoard(given);
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
            final Map<Player, Choice> given = new LinkedHashMap<>();
            given.put(new Player(1, HAN), new Choice(4));

            final Point pointLeft = new Point(HAN.getInitialRow(), 2);
            final Point pointRight = new Point(HAN.getInitialRow(), 7);
            final String expected = PieceDefinition.ELEPHANT.getNameForTeam(HAN);

            //when
            final Board board = BoardFactory.generateBoard(given);
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
            final Map<Player, Choice> given = new LinkedHashMap<>();
            given.put(new Player(1, HAN), new Choice(3));

            final Point pointLeftForHan = new Point(HAN.getInitialRow(), 1);
            final Point pointRightForHan = new Point(HAN.getInitialRow(), 6);
            final String expectedForHan = PieceDefinition.ELEPHANT.getNameForTeam(HAN);

            given.put(new Player(1, CHO), new Choice(4));

            final Point pointLeftForCho = new Point(CHO.getInitialRow(), 2);
            final Point pointRightForCho = new Point(CHO.getInitialRow(), 7);
            final String expectedForCho = PieceDefinition.ELEPHANT.getNameForTeam(CHO);

            //when
            final Board board = BoardFactory.generateBoard(given);
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
            final Map<Player, Choice> given = new LinkedHashMap<>();
            given.put(new Player(1, HAN), new Choice(Integer.MAX_VALUE));

            //when&then
            assertThatThrownBy(() -> BoardFactory.generateBoard(given))
                    .isInstanceOf(JanggiGameRuleWarningException.class);
        }
    }
}
