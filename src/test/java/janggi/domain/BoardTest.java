package janggi.domain;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import java.util.List;
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

    @ParameterizedTest
    @CsvSource(value = {"RED, true", "GREEN, false"})
    @DisplayName("대상 위치에 기물이 아군임을 확인할 수 있다")
    void isAlly(Team team, boolean expected) {
        // given
        Position position = Position.of(1, 1);
        Board board = Board.initialize(List.of(new Soldier(position, Team.RED)));

        // when
        boolean actual = board.isAlly(position, team);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("대상 위치에 기물이 아군임을 확인할 수 있다")
    void getPiece() {
        // given
        Position position = Position.of(1, 1);
        Soldier soldier = new Soldier(position, Team.RED);
        Board board = Board.initialize(List.of(soldier));

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
        Soldier soldier = new Soldier(position, Team.RED);
        Board board = Board.initialize(List.of(soldier));

        // when
        // then
        assertThatThrownBy(() -> board.getPiece(Position.of(1, 2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("장기말이 존재하지 않는 지점입니다.");
    }

    private static Stream<Arguments> provideBoardWithEnemyPiece() {
        Position position = Position.of(1, 1);
        return Stream.of(
                Arguments.of(Board.initialize(List.of(new Soldier(position, Team.RED),
                        new Chariot(position.adjust(1, 0), Team.GREEN))), new Score(13)),
                Arguments.of(Board.initialize(List.of(new Soldier(position, Team.RED),
                        new Cannon(position.adjust(1, 0), Team.GREEN))), new Score(7)),
                Arguments.of(Board.initialize(List.of(new Soldier(position, Team.RED),
                        new Horse(position.adjust(1, 0), Team.GREEN))), new Score(5)),
                Arguments.of(Board.initialize(List.of(new Soldier(position, Team.RED),
                        new Elephant(position.adjust(1, 0), Team.GREEN))), new Score(3)),
                Arguments.of(Board.initialize(List.of(new Soldier(position, Team.RED),
                        new Soldier(position.adjust(1, 0), Team.GREEN))), new Score(2)),
                Arguments.of(Board.initialize(List.of(new Guard(position, Team.RED),
                        new Soldier(position.adjust(1, 0), Team.GREEN))), new Score(2))
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"GREEN,GREEN_WIN", "RED,RED_WIN"})
    @DisplayName("두 팀 중 궁이 잡은 팀이 승리한다")
    void checkGeneralDiedWhenOtherRemain(Team team, GameStatus expected) {
        //given
        Board board = Board.initialize(List.of(new General(Position.of(1, 1), team)));

        //when
        GameStatus actual = board.checkGeneralDied();

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("두 팀의 궁이 모두 살아있으면 게임을 진행한다")
    void checkGeneralDiedWhenAllAlive() {
        //given
        Board board = Board.initialize(
                List.of(new General(Position.of(1, 1), Team.RED),
                        new General(Position.of(1, 2), Team.GREEN)));

        //when
        GameStatus actual = board.checkGeneralDied();

        //then
        assertThat(actual).isEqualTo(GameStatus.CONTINUE);
    }

    @Test
    @DisplayName("두 팀의 기물 중, 궁만 살아있으면 무승부 처리한다")
    void checkGeneralDiedWhenRemainOnlyGeneral() {
        //given
        Board board = Board.initialize(
                List.of(new General(Position.of(1, 1), Team.RED),
                        new General(Position.of(1, 2), Team.GREEN)));

        //when
        GameStatus actual = board.checkRemainOnlyGeneral();

        //then
        assertThat(actual).isEqualTo(GameStatus.DRAW);
    }

    @Test
    @DisplayName("두 팀의 기물 중, 궁 이외의 기물이 하나라도 살아있으면 게임을 진행한다")
    void checkGeneralDiedWhenRemainNotOnlyGeneral() {
        //given
        Board board = Board.initialize(
                List.of(new General(Position.of(1, 1), Team.RED),
                        new General(Position.of(1, 2), Team.GREEN),
                        new Soldier(Position.of(2, 2), Team.GREEN)));

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
        Board board = Board.initialize(
                List.of(new General(Position.of(1, 1), Team.RED),
                        new General(Position.of(1, 2), Team.GREEN),
                        new Soldier(Position.of(2, 2), Team.GREEN)));

        //then
        Assertions.assertThatThrownBy(() -> board.movePiece(redPlayer, Position.of(1, 2), Position.of(1, 3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("적의 기물을 선택할 수 없습니다.");
    }
    
    @ParameterizedTest
    @MethodSource("provideBoardWithEnemyPiece")
    @DisplayName("적의 기물을 잡으면 이에 맞는 점수를 얻는다")
    void addScoreWhenCatchPiece(Board board, Score expected) {
        //given
        Player player = new Player("flint", Team.RED);
        Position departure = Position.of(1, 1);
        Position destination = Position.of(2, 1);

        //when
        board.movePiece(player, departure, destination);

        //then
        assertThat(player.getScore()).isEqualTo(expected);
    }
}
