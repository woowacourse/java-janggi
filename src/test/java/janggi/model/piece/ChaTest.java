package janggi.model.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.Team;
import janggi.model.palace.PalaceFactory;
import janggi.model.palace.Palaces;
import janggi.model.piece.straightMove.Cha;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.PositionPath;
import janggi.model.position.absolute.Row;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChaTest {

    Palaces palaces = new PalaceFactory().create();

    @DisplayName("같은 행이나 열에 위치해있지 않으면 예외가 발생한다.")
    @Test
    void getLegalPath_invalidPath() {
        //given
        Position from = new Position(Row.SIX, Column.THREE);
        Position to = new Position(Row.TWO, Column.FIVE);
        Cha cha = new Cha(Team.CHO);

        //when & then
        assertThatThrownBy(() -> cha.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("직선 관계에 위치해 있지 않습니다.");
    }

    @DisplayName("from과 to가 같으면 예외가 발생한다.")
    @Test
    void getLegalPath_not_move() {
        //given
        Position from = new Position(Row.SIX, Column.THREE);
        Position to = new Position(Row.SIX, Column.THREE);
        Cha cha = new Cha(Team.CHO);

        //when & then
        assertThatThrownBy(() -> cha.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("직선 관계에 위치해 있지 않습니다.");
    }

    @DisplayName("from과 to가 서로다른 팀의 궁성에 있으면 예외가 발생한다.")
    @Test
    void getLegalPath_different_palace() {
        //given
        Position from = new Position(Row.SIX, Column.THREE);
        Position to = new Position(Row.TWO, Column.FIVE);
        Cha cha = new Cha(Team.CHO);

        //when & then
        assertThatThrownBy(() -> cha.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("직선 관계에 위치해 있지 않습니다.");
    }

    @DisplayName("같은 행이면 이동할 수 있다.")
    @Test
    void getLegalPath_sameRow() {
        //given
        Position from = new Position(Row.SIX, Column.THREE);
        Position to = new Position(Row.SIX, Column.FIVE);
        Cha cha = new Cha(Team.CHO);

        Byeong byeong = new Byeong(Team.CHO);

        Map<Position, Piece> board = Map.of(
                new Position(Row.SIX, Column.FOUR), byeong
        );

        //when
        PositionPath path = cha.getLegalPath(from, to);

        //then
        assertThat(path.findPiecesOn(board))
                .containsExactly(byeong);
    }

    @DisplayName("같은 열이면 이동할 수 있다.")
    @Test
    void getLegalPath_sameColumn() {
        //given
        Position from = new Position(Row.SIX, Column.THREE);
        Position to = new Position(Row.NINE, Column.THREE);
        Cha cha = new Cha(Team.CHO);

        Byeong byeong = new Byeong(Team.CHO);

        Map<Position, Piece> board = Map.of(
                new Position(Row.SEVEN, Column.THREE), byeong
        );

        //when
        PositionPath path = cha.getLegalPath(from, to);

        //then
        assertThat(path.findPiecesOn(board))
                .containsExactly(byeong);
    }

    @DisplayName("from과 to가 같은 궁성 안에 있으면, 간선을 따라 이동할 수 있다.")
    @Test
    void getLegalPath_samePalace() {
        //given
        Position from = new Position(Row.ZERO, Column.FOUR);
        Position to = new Position(Row.EIGHT, Column.SIX);
        Cha cha = new Cha(Team.CHO);

        Byeong byeong = new Byeong(Team.CHO);

        Map<Position, Piece> board = Map.of(
                new Position(Row.NINE, Column.FIVE), byeong
        );

        //when
        PositionPath path = cha.getLegalPath(from, to);

        //then
        assertThat(path.findPiecesOn(board))
                .containsExactly(byeong);
    }

    @DisplayName("궁성 안에서 간선을 따라 이동하지 않으면 예외가 발생한다.")
    @Test
    void getLegalPath_not_on_line() {
        //given
        Position from = new Position(Row.ZERO, Column.FIVE);
        Position to = new Position(Row.NINE, Column.FOUR);
        Cha cha = new Cha(Team.CHO);

        //when & then
        assertThatThrownBy(() -> cha.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로로 이동할 수 없습니다.");
    }

    @DisplayName("경로 상에 다른 기물이 존재하면 false를 반환한다.")
    @Test
    void canPassThrough() {
        //given
        List<Piece> gimulsOnPath = List.of(
                new Cha(Team.CHO)
        );
        Cha gimulAtTo = new Cha(Team.HAN);
        Cha cha = new Cha(Team.CHO);

        //when & then
        assertThat(cha.canPassThrough(gimulsOnPath, gimulAtTo))
                .isFalse();
    }

    @DisplayName("to에 있는 기물이 같은 팀이면 false를 반환한다.")
    @Test
    void canPassThrough_sameTeam() {
        //given
        List<Piece> gimulsOnPath = List.of(
                new Cha(Team.CHO)
        );
        Cha gimulAtTo = new Cha(Team.CHO);
        Cha cha = new Cha(Team.CHO);

        //when & then
        assertThat(cha.canPassThrough(gimulsOnPath, gimulAtTo))
                .isFalse();
    }
}