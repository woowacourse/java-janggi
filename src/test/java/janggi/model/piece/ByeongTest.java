package janggi.model.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.Team;
import janggi.model.position.PositionPath;
import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.Row;
import janggi.model.piece.straightMove.Cha;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ByeongTest {
    @DisplayName("초나라일때, 북쪽으로 한칸 이동한다.")
    @Test
    void getLegalPath_moveNorth() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.SIX, Column.FIVE);
        Byeong byeong = new Byeong(Team.CHO);

        Cha cha = new Cha(Team.CHO);

        Map<Position, Piece> board = new HashMap<>(Map.of(
                new Position(Row.SIX, Column.FOUR), cha
        ));

        //when
        PositionPath path = byeong.getLegalPath(from, to);

        //then
        assertThat(path.findPiecesOn(board))
                .isEmpty();
    }

    @DisplayName("한나라일때, 남쪽으로 한칸 이동한다.")
    @Test
    void getLegalPath_moveSouth() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.EIGHT, Column.FIVE);
        Byeong byeong = new Byeong(Team.HAN);

        Cha cha = new Cha(Team.CHO);

        Map<Position, Piece> board = new HashMap<>(Map.of(
                new Position(Row.SIX, Column.FOUR), cha
        ));

        //when
        PositionPath path = byeong.getLegalPath(from, to);

        //then
        assertThat(path.findPiecesOn(board))
                .isEmpty();
    }

    @DisplayName("동쪽으로 한칸 이동한다.")
    @Test
    void getLegalPath_moveEast() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.SEVEN, Column.SIX);
        Byeong byeong = new Byeong(Team.CHO);

        Cha cha = new Cha(Team.CHO);

        Map<Position, Piece> board = new HashMap<>(Map.of(
                new Position(Row.SIX, Column.FOUR), cha
        ));

        //when
        PositionPath path = byeong.getLegalPath(from, to);

        //then
        assertThat(path.findPiecesOn(board))
                .isEmpty();
    }

    @DisplayName("서쪽으로 한칸 이동한다.")
    @Test
    void getLegalPath_moveWest() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.SEVEN, Column.FOUR);
        Byeong byeong = new Byeong(Team.CHO);

        Cha cha = new Cha(Team.CHO);

        Map<Position, Piece> board = new HashMap<>(Map.of(
                new Position(Row.SIX, Column.FOUR), cha
        ));

        //when
        PositionPath path = byeong.getLegalPath(from, to);

        //then
        assertThat(path.findPiecesOn(board))
                .isEmpty();
    }

    @DisplayName("초나라일때 남쪽으로 움직이면 예외가 발생한다.")
    @Test
    void getLegalPath_invalid_cho() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.EIGHT, Column.FIVE);
        Byeong byeong = new Byeong(Team.CHO);

        //when & then
        assertThatThrownBy(() -> byeong.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }

    @DisplayName("한나라일때 북쪽으로 움직이면 예외가 발생한다.")
    @Test
    void getLegalPath_invalid_han() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.SIX, Column.FIVE);
        Byeong byeong = new Byeong(Team.HAN);

        //when & then
        assertThatThrownBy(() -> byeong.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }


    @DisplayName("경로 상에 다른 기물이 존재하면 false를 반환한다.")
    @Test
    void canPassThrough() {
        //given
        List<Piece> gimulsOnPath = List.of(
                new Cha(Team.CHO)
        );
        Cha gimulAtTo = new Cha(Team.HAN);
        Byeong byeong = new Byeong(Team.CHO);

        //when & then
        assertThat(byeong.canPassThrough(gimulsOnPath, gimulAtTo))
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
        Byeong byeong = new Byeong(Team.CHO);

        //when & then
        assertThat(byeong.canPassThrough(gimulsOnPath, gimulAtTo))
                .isFalse();
    }
}