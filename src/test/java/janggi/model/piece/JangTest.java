package janggi.model.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.Team;
import janggi.model.piece.palace.Jang;
import janggi.model.piece.straightMove.Cha;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.PositionPath;
import janggi.model.position.absolute.Row;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JangTest {

    Map<Position, Piece> emptyBoard = Map.of();

    @DisplayName("from이나 to가 궁성 밖에 위치하면 예외가 발생한다.")
    @Test
    void getLegalPath_invalid() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.SEVEN, Column.SEVEN);
        Jang jang = new Jang(Team.HAN);

        //when & then
        assertThatThrownBy(() -> jang.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("from과 to는 같은 궁성 안에 있어야 합니다.");
    }

    @DisplayName("궁성 안의 선을 따라 이동하지 않으면 예외가 발생한다.")
    @Test
    void getLegalPath_not_on_line() {
        //given
        Position from = new Position(Row.EIGHT, Column.FIVE);
        Position to = new Position(Row.NINE, Column.FOUR);
        Jang jang = new Jang(Team.HAN);

        //when & then
        assertThatThrownBy(() -> jang.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로로 이동할 수 없습니다.");
    }

    @DisplayName("궁성 안에서는 간선을 따라 이동한다.")
    @Test
    void getLegalPath_diagonal() {
        //given
        Position from = new Position(Row.NINE, Column.FIVE);
        Position to = new Position(Row.EIGHT, Column.FOUR);
        Jang jang = new Jang(Team.HAN);

        //when & then
        assertThat(jang.getLegalPath(
                        from,
                        to
                ).findPiecesOn(emptyBoard)
        ).isEmpty();
    }

    @DisplayName("남쪽으로 한칸 이동한다.")
    @Test
    void getLegalPath_moveSouth() {
        //given
        Position from = new Position(Row.NINE, Column.FIVE);
        Position to = new Position(Row.ZERO, Column.FIVE);
        Jang jang = new Jang(Team.HAN);

        Cha cha = new Cha(Team.CHO);

        //when
        PositionPath path = jang.getLegalPath(from, to);

        //then
        assertThat(path.findPiecesOn(emptyBoard))
                .isEmpty();
    }

    @DisplayName("북쪽으로 한칸 이동한다.")
    @Test
    void getLegalPath_moveNorth() {
        //given
        Position from = new Position(Row.NINE, Column.FIVE);
        Position to = new Position(Row.EIGHT, Column.FIVE);
        Jang jang = new Jang(Team.HAN);

        Cha cha = new Cha(Team.CHO);

        //when
        PositionPath path = jang.getLegalPath(from, to);

        //then
        assertThat(path.findPiecesOn(emptyBoard))
                .isEmpty();
    }

    @DisplayName("동쪽으로 한칸 이동한다.")
    @Test
    void getLegalPath_moveEast() {
        //given
        Position from = new Position(Row.NINE, Column.FIVE);
        Position to = new Position(Row.NINE, Column.SIX);
        Jang jang = new Jang(Team.HAN);

        Cha cha = new Cha(Team.CHO);

        //when
        PositionPath path = jang.getLegalPath(from, to);

        //then
        assertThat(path.findPiecesOn(emptyBoard))
                .isEmpty();
    }

    @DisplayName("서쪽으로 한칸 이동한다.")
    @Test
    void getLegalPath_moveWest() {
        //given
        Position from = new Position(Row.NINE, Column.FIVE);
        Position to = new Position(Row.ZERO, Column.FOUR);
        Jang jang = new Jang(Team.HAN);

        Cha cha = new Cha(Team.CHO);

        //when
        PositionPath path = jang.getLegalPath(from, to);

        //then
        assertThat(path.findPiecesOn(emptyBoard))
                .isEmpty();
    }

    @DisplayName("경로 상에 다른 기물이 존재하면 false를 반환한다.")
    @Test
    void canPassThrough() {
        //given
        List<Piece> gimulsOnPath = List.of(
                new Cha(Team.CHO)
        );
        Cha gimulAtTo = new Cha(Team.HAN);
        Jang jang = new Jang(Team.HAN);

        //when & then
        assertThat(jang.canPassThrough(gimulsOnPath, gimulAtTo))
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
        Jang jang = new Jang(Team.HAN);

        //when & then
        assertThat(jang.canPassThrough(gimulsOnPath, gimulAtTo))
                .isFalse();
    }
}