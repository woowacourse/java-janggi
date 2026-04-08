package janggi.model.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.Team;
import janggi.model.palace.PalaceFactory;
import janggi.model.palace.Palaces;
import janggi.model.piece.diagonalMove.Ma;
import janggi.model.piece.straightMove.Cha;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.PositionPath;
import janggi.model.position.absolute.Row;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MaTest {

    Palaces palaces = new PalaceFactory().create();

    @DisplayName("상하 또는 좌우로 한칸을 간 후에 같은 방향의 대각선으로 한칸 이동한다.")
    @Test
    void getLegalPath() {
        //given
        Byeong byeong = new Byeong(Team.CHO);

        Map<Position, Piece> board = Map.of(
                new Position(Row.SIX, Column.FIVE), byeong
        );

        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.FIVE, Column.FOUR);
        Ma ma = new Ma(Team.CHO);

        //when
        PositionPath path = ma.getLegalPath(from, to);

        //then
        assertThat(path.findPiecesOn(board))
                .containsExactly(byeong);
    }

    @DisplayName("행과 열의 거리가 각각 (1,2) 혹은 (2,1)이 아니면 예외가 발생한다.")
    @Test
    void getLegalPath_invalid() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.FOUR, Column.SIX);
        Ma ma = new Ma(Team.CHO);

        //when & then
        assertThatThrownBy(() -> ma.getLegalPath(from, to))
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

        Ma ma = new Ma(Team.CHO);

        //when & then
        assertThat(ma.canPassThrough(gimulsOnPath, gimulAtTo))
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
        Ma ma = new Ma(Team.CHO);

        //when & then
        assertThat(ma.canPassThrough(gimulsOnPath, gimulAtTo))
                .isFalse();
    }

    @DisplayName("같은 팀이면 true를 반환한다.")
    @Test
    void isSameTeam() {
        //given
        Ma ma = new Ma(Team.CHO);

        //when & then
        assertThat(ma.isSameTeam(Team.CHO)).isTrue();
        assertThat(ma.isSameTeam(Team.HAN)).isFalse();
    }


}