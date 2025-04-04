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

class GuardTest {

    @Test
    @DisplayName("2개의 사가 올바른 위치에 초기화 될 수 있다")
    void defaultsOf() {
        // given
        // when
        final List<Guard> ChoGuards = Guard.defaultsOf(Team.CHO);
        final List<Guard> HanGuards = Guard.defaultsOf(Team.HAN);

        // then
        assertAll(() -> {
            assertThat(ChoGuards.get(0).position).isEqualTo(Position.of(Team.decideRow(1, Team.CHO), 4));
            assertThat(ChoGuards.get(1).position).isEqualTo(Position.of(Team.decideRow(1, Team.CHO), 6));
            assertThat(HanGuards.get(0).position).isEqualTo(Position.of(Team.decideRow(1, Team.HAN), 4));
            assertThat(HanGuards.get(1).position).isEqualTo(Position.of(Team.decideRow(1, Team.HAN), 6));
        });
    }

    @Test
    @DisplayName("사는 수직/수평으로 1칸 이동할 수 있다")
    void move() {
        // given
        final Piece guard = Guard.defaultsOf(Team.CHO).getFirst();

        final Position position = guard.getPosition();
        final Board board = Board.from(Pieces.empty().add(guard));

        final Position movedPosition = position.add(new Vector(-1, 0));

        // when
        final Piece move = guard.move(board, movedPosition);

        // then
        assertThat(move.getPosition()).isEqualTo(movedPosition);
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 1", "2, 0"})
    @DisplayName("사는 2칸 이상 움직일 수 없다")
    void move(final int rowDirection, final int columnDirection) {
        // given
        final Position position = Position.of(5, 5);
        final Piece guard = Guard.of(position, Team.HAN);
        final Board board = Board.from(Pieces.empty().add(guard));

        final Position movedPosition = position.add(new Vector(rowDirection, columnDirection));

        // when
        // then
        assertThatThrownBy(() -> guard.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }

    @Test
    @DisplayName("사는 궁성을 나갈 수 없다")
    void cannotExitPalace() {
        // given
        final Guard guard = Guard.of(Position.of(8, 5), Team.CHO);
        final Board board = Board.from(Pieces.empty().add(guard));

        // when
        // then
        assertThatThrownBy(() -> guard.move(board, Position.of(7, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 기물은 궁성 밖으로 움직일 수 없습니다");
    }
}
