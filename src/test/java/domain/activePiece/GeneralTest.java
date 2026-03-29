package domain.activePiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Team;
import domain.piece.ActivePiece;
import domain.piece.General;
import domain.piece.Piece;
import domain.position.Column;
import domain.position.Position;
import domain.position.Row;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class GeneralTest {

    @Test
    void 정상_범위_입력() {
        Piece general = new General(Team.HAN);
        assertThat(general.canMove(new Position(5, 5), new Position(5, 6))).isTrue();
    }

    @Test
    void 정상_범위가_아니면_거짓() {
        Piece general = new General(Team.HAN);
        assertThat(general.canMove(new Position(5, 5), new Position(6, 6))).isFalse();
    }

    @Test
    void 궁_정상_경로_출력_한다() {
        ActivePiece general = new General(Team.HAN);

        Position src = new Position(new Row(1), new Column(3));
        Position dest = new Position(new Row(2), new Column(3));
        List<Position> routes = new ArrayList<>();

        assertThat(general.searchRoute(src, dest)).isEqualTo(routes);
    }

    @Test
    void 궁_비정상_경로_출력_한다() {
        ActivePiece general = new General(Team.HAN);

        Position src = new Position(new Row(1), new Column(3));
        Position dest = new Position(new Row(2), new Column(3));
        List<Position> routes = new ArrayList<>(
                List.of(new Position(new Row(3), new Column(3))));

        assertThat(general.searchRoute(src, dest)).isNotEqualTo(routes);
    }
}
