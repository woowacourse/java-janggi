package janggi;

import fixture.PieceFixture;
import janggi.coordinate.Position;
import janggi.coordinate.Vector;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Pieces;
import janggi.piece.Soldier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    @ParameterizedTest
    @CsvSource(value = {"HAN, true", "CHO, false"})
    @DisplayName("대상 위치에 기물이 아군임을 확인할 수 있다")
    void isAlly(Team team, boolean expected) {
        // given
        Position position = Position.of(1, 1);
        Board board = Board.from(Pieces.empty().addAll(List.of(Soldier.of(position, Team.HAN))));

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
        Soldier soldier = Soldier.of(position, Team.HAN);
        Board board = Board.from(Pieces.empty().addAll(List.of(soldier)));

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
        Soldier soldier = Soldier.of(position, Team.HAN);
        Board board = Board.from(Pieces.empty().addAll(List.of(soldier)));

        // when
        // then
        assertThatThrownBy(() -> board.getPiece(Position.of(1, 2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기물이 존재하지 않는 지점입니다.");
    }

    @Test
    @DisplayName("올바른 위치로 기물을 움직일 수 있다")
    void canMovePiece() {
        // given
        Board board = Board.from(Pieces.empty().addAll(
                List.of(PieceFixture.createPiece(1, 1, PieceType.SOLDIER, Team.CHO))));

        Position departure = Position.of(1, 1);
        Position destination = Position.of(1, 2);

        boolean isExistsInDepartureBeforeMove = board.isExists(departure);
        boolean isExistsInDestinationBeforeMove = board.isExists(destination);

        // when
        board.movePiece(Player.from(Team.CHO), Position.of(1, 1), destination);

        // then
        boolean isExistsInDepartureAfterMove = board.isExists(departure);
        boolean isExistsInDestinationAfterMove = board.isExists(destination);

        assertThat(isExistsInDepartureBeforeMove).isTrue();
        assertThat(isExistsInDepartureAfterMove).isFalse();

        assertThat(isExistsInDestinationBeforeMove).isFalse();
        assertThat(isExistsInDestinationAfterMove).isTrue();
    }

    @Test
    @DisplayName("올바른 위치로 기물을 움직여서 상대의 기물을 잡는다면, 점수가 올라간다")
    void canAddScoreWhenCaptureEnemy() {
        // given
        Board board = Board.from(Pieces.empty().addAll(
                List.of(PieceFixture.createPiece(1, 1, PieceType.SOLDIER, Team.CHO),
                        PieceFixture.createPiece(1, 2, PieceType.SOLDIER, Team.HAN))));

        Position enemyPosition = Position.of(1, 2);

        // when
        Player me = Player.from(Team.CHO);
        Score scoreBeforeCapture = me.getScore();
        board.movePiece(me, Position.of(1, 1), enemyPosition);

        // then
        Score scoreAfterCapture = me.getScore();

        assertThat(scoreBeforeCapture).isEqualTo(new Score(0));
        assertThat(scoreAfterCapture).isEqualTo(Score.soldier());
    }

    @Test
    @DisplayName("상대방의 기물을 움직일 수 없다")
    void cannotMoveEnemyPiece() {
        // given
        Board board = Board.from(Pieces.empty().addAll(
                List.of(PieceFixture.createPiece(1, 1, PieceType.SOLDIER, Team.CHO),
                        PieceFixture.createPiece(1, 2, PieceType.SOLDIER, Team.HAN))));

        Position enemyPosition = Position.of(1, 2);

        // when
        Player cho = Player.from(Team.CHO);


        // then
        assertThatThrownBy(() -> board.movePiece(cho, enemyPosition, enemyPosition.add(new Vector(0, 1))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("자신의 기물만을 움직일 수 있습니다.");
    }
}

