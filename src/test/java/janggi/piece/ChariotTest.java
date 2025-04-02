package janggi.piece;

import janggi.board.Board;
import janggi.coordinate.Position;
import janggi.coordinate.Vector;
import janggi.player.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ChariotTest {

    @Test
    @DisplayName("2개의 차가 올바른 위치에 초기화 될 수 있다")
    void defaultsOf() {
        // given
        // when
        final List<Chariot> ChoChariots = Chariot.defaultsOf(Team.CHO);
        final List<Chariot> HanChariots = Chariot.defaultsOf(Team.HAN);

        // then
        assertAll(() -> {
            assertThat(ChoChariots.get(0).position).isEqualTo(Position.of(Team.decideRow(1, Team.CHO), 1));
            assertThat(ChoChariots.get(1).position).isEqualTo(Position.of(Team.decideRow(1, Team.CHO), 9));
            assertThat(HanChariots.get(0).position).isEqualTo(Position.of(Team.decideRow(1, Team.HAN), 1));
            assertThat(HanChariots.get(1).position).isEqualTo(Position.of(Team.decideRow(1, Team.HAN), 9));
        });
    }


    @ParameterizedTest
    @CsvSource(value = {"1,0", "2,0", "3,0", "4,0", "5,0", "0,1", "0,2", "0,3", "0,4"})
    @DisplayName("차는 수직/수평으로 보드판 내부를 자유롭게 이동할 수 있다")
    void move(final int rowDirection, final int columnDirection) {
        // given
        final Position position = Position.of(5, 5);
        final Piece chariot = Chariot.of(position, Team.HAN);
        final Board board = Board.from(Pieces.empty().addAll(List.of(chariot)));

        final Position movedPosition = position.add(new Vector(rowDirection, columnDirection));

        // when
        final Piece move = chariot.move(board, movedPosition);

        // then
        assertThat(move.getPosition()).isEqualTo(movedPosition);
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 1", "2, 2", "-1,-2", "-2,-3"})
    @DisplayName("차는 규칙에 어긋나게 움직일 수 없다")
    void cannotMoveToInvalidDirection(final int rowDirection, final int columnDirection) {
        // given
        final Position position = Position.of(5, 5);
        final Piece chariot = Chariot.of(position, Team.HAN);
        final Board board = Board.from(Pieces.empty().addAll(List.of(chariot)));

        final Position movedPosition = position.add(new Vector(rowDirection, columnDirection));

        // when
        // then
        assertThatThrownBy(() -> chariot.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }
}
