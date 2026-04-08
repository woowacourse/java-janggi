package janggi.model.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.Team;
import janggi.model.piece.Piece;
import janggi.model.piece.diagonalMove.Ma;
import janggi.model.piece.palace.Jang;
import janggi.model.piece.palace.Sa;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlayingBoardTest {

    Board board;

    @BeforeEach
    void beforeEach() {
        Map<Position, Piece> board = new HashMap<>();

        board.put(
                new Position(Row.SEVEN, Column.FIVE),
                new Ma(Team.CHO)
        );
        board.put(
                new Position(Row.EIGHT, Column.SEVEN),
                new Jang(Team.HAN)
        );
        board.put(
                new Position(Row.SIX, Column.SEVEN),
                new Ma(Team.CHO)
        );
        board.put(
                new Position(Row.SIX, Column.FIVE),
                new Ma(Team.HAN)
        );
        board.put(
                new Position(Row.SEVEN, Column.ONE),
                new Ma(Team.CHO)
        );
        board.put(
                new Position(Row.SIX, Column.ONE),
                new Ma(Team.HAN)
        );

        board.put(
                new Position(Row.ZERO, Column.FOUR),
                new Sa(Team.CHO)
        );
        board.put(
                new Position(Row.NINE, Column.FOUR),
                new Sa(Team.CHO)
        );

        this.board = PlayingBoard.of(board);
    }

    @DisplayName("from에 기물이 없으면 예외가 발생한다.")
    @Test
    void move_empty() {
        //given
        Position from = new Position(Row.ONE, Column.ONE);
        Position to = new Position(Row.THREE, Column.TWO);
        Team cho = Team.CHO;

        //when & then
        assertThatThrownBy(() -> board.move(cho, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치에 기물이 존재하지 않습니다.");
    }

    @DisplayName("from에 기물이 다른 팀이면 예외가 발생한다.")
    @Test
    void move_different_team() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.FIVE, Column.FOUR);
        Team han = Team.HAN;

        //when & then
        assertThatThrownBy(() -> board.move(han, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상대편 기물을 움직일 수 없습니다.");
    }

    @DisplayName("from에 있는 기물이 다른 팀이면 예외가 발생한다.")
    @Test
    void move_piece_on_path() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.FIVE, Column.FOUR);
        Team cho = Team.CHO;

        //when & then
        assertThatThrownBy(() -> board.move(cho, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로로 기물을 움직일 수 없습니다.");
    }

    @DisplayName("이동하는 기물은 to에 같은 팀 기물이 있으면 예외가 발생한다.")
    @Test
    void move_same_team_on_destination() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.SIX, Column.SEVEN);
        Team cho = Team.CHO;

        //when & then
        assertThatThrownBy(() -> board.move(cho, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로로 기물을 움직일 수 없습니다.");
    }

    @DisplayName("사/장은 to에 같은 팀 기물이 있으면 예외가 발생한다.")
    @Test
    void move_same_team_on_destination_sa_jang() {
        //given
        Position from = new Position(Row.ZERO, Column.FOUR);
        Position to = new Position(Row.NINE, Column.FOUR);
        Team cho = Team.CHO;

        //when & then
        assertThatThrownBy(() -> board.move(cho, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로로 기물을 움직일 수 없습니다.");
    }

    @DisplayName("to에 있는 기물을 제거하고 to로 이동한다.")
    @Test
    void move_success() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.EIGHT, Column.SEVEN);
        Team cho = Team.CHO;

        //when & then
        assertThatCode(() -> board.move(cho, from, to))
                .doesNotThrowAnyException();
    }

    @DisplayName("상대편 장을 잡으면 승리한다.")
    @Test
    void move_capture_jang() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.EIGHT, Column.SEVEN);
        Team cho = Team.CHO;

        //when
        Board moved = board.move(cho, from, to);

        //then
        assertThat(moved.isWinnerDetermined())
                .isTrue();
        assertThat(moved.winner())
                .isEqualTo(Team.CHO);
    }
}