package janggi.domain;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import java.util.Map;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    @Test
    @DisplayName("Board에 Piece를 기반으로 초기화할 수 있다")
    void getPiece() {
        // given
        Position position = Position.of(1, 1);
        Soldier soldier = new Soldier(Team.RED);
        Board board = new Board(Map.of(position, soldier));

        // when
        Piece piece = board.getPiece(position);

        // then
        assertThat(piece).isEqualTo(soldier);
    }

    @Test
    @DisplayName("대상 위치에 기물이 없다면 예외를 던질 수 있다")
    void throwExceptionWhenNotExists() {
        // given
        Position position = Position.of(1, 1);
        Soldier soldier = new Soldier(Team.RED);
        Board board = new Board(Map.of(position, soldier));

        // when
        // then
        assertThatThrownBy(() -> board.getPiece(Position.of(1, 2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("장기말이 존재하지 않는 지점입니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"GREEN,GREEN_WIN", "RED,RED_WIN"})
    @DisplayName("두 팀 중 궁이 잡은 팀이 승리한다")
    void checkGeneralDiedWhenOtherRemain(Team team, GameStatus expected) {
        //given
        Board board = new Board(Map.of(Position.of(1, 1), new General(team)));

        //when
        GameStatus actual = board.checkGeneralDied();

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("두 팀의 기물 중, 궁만 살아있으면 무승부 처리한다")
    void checkGeneralDiedWhenRemainOnlyGeneral() {
        //given
        Board board = new Board(Map.of(Position.of(1, 1), new General(Team.RED),
                Position.of(1, 2), new General(Team.GREEN)));

        //when
        GameStatus actual = board.checkRemainOnlyGeneral();

        //then
        assertThat(actual).isEqualTo(GameStatus.DRAW);
    }

    @Test
    @DisplayName("두 팀의 기물 중, 궁 이외의 기물이 하나라도 살아있으면 게임을 진행한다")
    void checkGeneralDiedWhenRemainNotOnlyGeneral() {
        //given
        Board board = new Board(Map.of(Position.of(1, 1), new General(Team.RED),
                Position.of(1, 2), new General(Team.GREEN),
                Position.of(2, 2), new Soldier(Team.GREEN)));

        //when
        GameStatus actual = board.checkRemainOnlyGeneral();

        //then
        assertThat(actual).isEqualTo(GameStatus.CONTINUE);
    }

    @Test
    @DisplayName("적의 기물을 선택하여 이동할 수 없다")
    void cannotMoveEnemyPiece() {
        //given
        Player redPlayer = new Player("flint", Team.RED);

        //when
        Board board = new Board(Map.of(Position.of(1, 1), new General(Team.RED),
                Position.of(1, 2), new General(Team.GREEN),
                Position.of(2, 2), new Soldier(Team.GREEN)));

        //then
        Assertions.assertThatThrownBy(() -> board.movePiece(redPlayer, Position.of(1, 2), Position.of(1, 3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("적의 기물을 선택할 수 없습니다.");
    }

    private static Stream<Arguments> provideAllyPiece() {
        Position position = Position.of(5, 5);
        Team team = Team.RED;
        return Stream.of(
                Arguments.of(position, new Guard(team), position.adjust(1, 0), new Soldier(team), 1, 0),
                Arguments.of(position, new General(team), position.adjust(1, 0), new Soldier(team), 1, 0),
                Arguments.of(position, new Soldier(team), position.adjust(1, 0), new Soldier(team), 1, 0),
                Arguments.of(position, new Horse(team), position.adjust(1, 2), new Soldier(team), 1, 2),
                Arguments.of(position, new Elephant(team), position.adjust(2, 3), new Soldier(team), 2, 3),
                Arguments.of(position, new Chariot(team), position.adjust(3, 0), new Soldier(team), 3, 0));
    }

    @ParameterizedTest
    @MethodSource("provideAllyPiece")
    @DisplayName("모든 기물은 목적지에 아군이 존재할 경우 이동할 수 없다 - 포 제외 케이스")
    void cannotMoveWhenExistAllyPieceInDestination(Position position,
                                                   Piece piece,
                                                   Position allyPosition,
                                                   Piece allyPiece,
                                                   int rowDirection,
                                                   int columnDirection) {
        // given
        Player player = new Player("flint", Team.RED);
        Board board = new Board(Map.of(position, piece, allyPosition, allyPiece));

        Position movedPosition = position.adjust(rowDirection, columnDirection);

        // when
        // then
        assertThatThrownBy(
                () -> board.movePiece(player, position, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("목적지에 아군이 존재합니다.");
    }

    @Test
    @DisplayName("모든 기물은 목적지에 아군이 존재할 경우 이동할 수 없다 - 포 케이스")
    void cannotMoveWhenExistAllyPieceInDestination() {
        // given
        Team team = Team.RED;
        Player player = new Player("flint", team);
        Position position = Position.of(1, 1);
        Piece piece = new Cannon(team);
        Position otherPosition = Position.of(2, 1);
        Piece otherPiece = new Soldier(team);
        Position allyPosition = Position.of(3, 1);
        Piece allyPiece = new Soldier(team);
        Board board = new Board(Map.of(position, piece, otherPosition, otherPiece, allyPosition, allyPiece));

        Position movedPosition = position.adjust(2, 0);

        // when
        // then
        assertThatThrownBy(() -> board.movePiece(player, position, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("목적지에 아군이 존재합니다.");
    }

    @Test
    @DisplayName("같은 위치를 입력하면 예외를 던진다")
    void throwExceptionWhenSamePosition() {
        // given
        Team team = Team.RED;
        Player player = new Player("flint", team);
        Position position = Position.of(5, 5);
        Piece piece = new Soldier(team);
        Board board = new Board(Map.of(position, piece));

        // when
        // then
        assertThatThrownBy(() -> board.movePiece(player, position, position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("현재 위치와 이동할 위치와 같은 위치입니다");
    }
}
