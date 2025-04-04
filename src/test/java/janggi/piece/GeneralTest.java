package janggi.piece;

import janggi.board.Board;
import janggi.coordinate.Position;
import janggi.coordinate.Vector;
import janggi.player.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class GeneralTest {

    @Test
    @DisplayName("궁은 올바른 위치에 초기화 될 수 있다")
    void defaultsOf() {
        // given
        // when
        final General choGeneral = General.defaultOf(Team.CHO);
        final General hanGeneral = General.defaultOf(Team.HAN);

        // then
        assertThat(choGeneral.position).isEqualTo(Position.of(Team.decideRow(2, Team.CHO), 5));
        assertThat(hanGeneral.position).isEqualTo(Position.of(Team.decideRow(2, Team.HAN), 5));
    }

    @Test
    @DisplayName("궁은 수직/수평으로 1칸 이동할 수 있다")
    void canMoveSingleStep() {
        // given
        final Piece general = General.defaultOf(Team.CHO);
        final Position position = general.getPosition();
        final Board board = Board.from(Pieces.empty().add(general));

        final Position movedPosition = position.add(new Vector(-1, 0));

        // when
        final Piece move = general.move(board, movedPosition);

        // then
        assertThat(move.getPosition()).isEqualTo(movedPosition);
    }

    @ParameterizedTest
    @CsvSource(value = {"0, 2", "-3, 0"})
    @DisplayName("궁은 2칸 이상 움직일 수 없다")
    void cannotMoveOverSingleStep(final int deltaRow, final int deltaColumn) {
        // given
        final Piece general = General.defaultOf(Team.CHO);
        final Position position = general.getPosition();
        final Board board = Board.from(Pieces.empty().add(general));

        final Position movedPosition = position.add(new Vector(deltaRow, deltaColumn));

        // when
        // then
        assertThatThrownBy(() -> general.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }

    @Test
    @DisplayName("궁은 궁성의 중앙에서 대각선으로 1칸 이동할 수 있다")
    void move() {
        // given
        final Piece general = General.defaultOf(Team.CHO);
        final Position position = general.getPosition();
        final Board board = Board.from(Pieces.empty().add(general));

        // 대각선 4방 이동
        final Position movedPosition1 = position.add(new Vector(1, 1));
        final Position movedPosition2 = position.add(new Vector(-1, 1));
        final Position movedPosition3 = position.add(new Vector(1, -1));
        final Position movedPosition4 = position.add(new Vector(-1, -1));

        // when
        final Piece movedPiece1 = general.move(board, movedPosition1);
        final Piece movedPiece2 = general.move(board, movedPosition2);
        final Piece movedPiece3 = general.move(board, movedPosition3);
        final Piece movedPiece4 = general.move(board, movedPosition4);

        // then
        assertAll(() -> {
            assertThat(movedPiece1.getPosition()).isEqualTo(movedPosition1);
            assertThat(movedPiece2.getPosition()).isEqualTo(movedPosition2);
            assertThat(movedPiece3.getPosition()).isEqualTo(movedPosition3);
            assertThat(movedPiece4.getPosition()).isEqualTo(movedPosition4);
        });
    }

    @Test
    @DisplayName("궁은 궁성을 나갈 수 없다")
    void cannotExitPalace() {
        // given
        final General general = General.of(Position.of(8, 5), Team.CHO);
        final Board board = Board.from(Pieces.empty().add(general));

        // when
        // then
        assertThatThrownBy(() -> general.move(board, Position.of(7, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 기물은 궁성 밖으로 움직일 수 없습니다");
    }
}
