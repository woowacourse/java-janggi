package janggi;

import janggi.piece.Piece;
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

}
