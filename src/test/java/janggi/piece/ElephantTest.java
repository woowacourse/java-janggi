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

class ElephantTest {

    @Test
    @DisplayName("2개의 상이 올바른 위치에 초기화 될 수 있다")
    void defaultsOf() {
        // given
        // when
        final List<Elephant> ChoElephants = Elephant.defaultsOf(Team.CHO);
        final List<Elephant> HanElephants = Elephant.defaultsOf(Team.HAN);

        // then
        assertAll(() -> {
            assertThat(ChoElephants.get(0).position).isEqualTo(Position.of(Team.decideRow(1, Team.CHO), 2));
            assertThat(ChoElephants.get(1).position).isEqualTo(Position.of(Team.decideRow(1, Team.CHO), 7));
            assertThat(HanElephants.get(0).position).isEqualTo(Position.of(Team.decideRow(1, Team.HAN), 2));
            assertThat(HanElephants.get(1).position).isEqualTo(Position.of(Team.decideRow(1, Team.HAN), 7));
        });
    }

    @Test
    @DisplayName("상은 수직/수평으로 1칸 이동 후, 진행 방향의 대각선으로 2칸 이동할 수 있다")
    void move() {
        // given
        final Position position = Position.of(5, 5);
        final Piece elephant = Elephant.of(position, Team.HAN);
        final Board board = Board.from(Pieces.empty().addAll(List.of(elephant)));

        final Position movedPosition = position.add(new Vector(2, 3));

        // when
        final Piece move = elephant.move(board, movedPosition);

        // then
        assertThat(move.getPosition()).isEqualTo(movedPosition);
    }

    @ParameterizedTest
    @CsvSource(value = {"2, 4", "4, 2", "3,3", "1,1"})
    @DisplayName("상은 규칙에 어긋나게 움직일 수 없다")
    void move(final int rowDirection, final int columnDirection) {
        // given
        final Position position = Position.of(5, 5);
        final Piece elephant = Elephant.of(position, Team.HAN);
        final Board board = Board.from(Pieces.empty().addAll(List.of(elephant)));

        final Position movedPosition = position.add(new Vector(rowDirection, columnDirection));

        // when
        // then
        assertThatThrownBy(() -> elephant.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }
}
