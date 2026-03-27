package domain.activePiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import domain.Column;
import domain.Position;
import domain.Row;
import domain.piece.Piece;
import domain.piece.Team;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class GuardTest {

    @Test
    void 정상_범위_입력() {
        Piece guard = new Guard(Team.HAN);
        assertThat(guard.canMove(new Position(5,5), new Position(6,5))).isTrue();
    }
    @Test
    void 정상_범위가_아니면_거짓() {
        Piece guard = new Guard(Team.HAN);
        assertThat(guard.canMove(new Position(5,5), new Position(8,8))).isFalse();
    }
    @Test
    void 사_정상_경로_출력_한다() {
        ActivePiece guard = new Guard(Team.HAN);

        Position src = new Position(new Row(1), new Column(3));
        Position dest = new Position(new Row(2), new Column(3));
        List<Position> routes = new ArrayList<>(
                List.of(new Position(new Row(1), new Column(3)), new Position(new Row(2), new Column(3))));

        assertThat(guard.searchRoute(src,dest)).isEqualTo(routes);
    }

    @Test
    void 사_비정상_경로_출력_한다() {
        ActivePiece guard = new Guard(Team.HAN);

        Position src = new Position(new Row(1), new Column(3));
        Position dest = new Position(new Row(2), new Column(3));
        List<Position> routes = new ArrayList<>(
                List.of(new Position(new Row(1), new Column(3)), new Position(new Row(3), new Column(3))));

        assertThat(guard.searchRoute(src,dest)).isNotEqualTo(routes);
    }


}
