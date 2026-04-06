package domain.activePiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Team;
import domain.piece.Piece;
import domain.piece.Guard;
import domain.piece.Piece;
import domain.position.Column;
import domain.position.Position;
import domain.position.Row;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class GuardTest {

    @Test
    void 정상_범위_입력() {
        Piece guard = new Guard(Team.HAN);
        assertThat(guard.canMove(new Position(5, 5), new Position(6, 5))).isTrue();
    }

    @Test
    void 정상_범위가_아니면_거짓() {
        Piece guard = new Guard(Team.HAN);
        assertThat(guard.canMove(new Position(5, 5), new Position(8, 8))).isFalse();
    }

    @Test
    void 사_정상_경로_출력_한다() {
        Piece guard = new Guard(Team.HAN);

        Position source = new Position(new Row(1), new Column(3));
        Position destination = new Position(new Row(2), new Column(3));
        List<Position> routes = new ArrayList<>();

        assertThat(guard.calculateRoute(source, destination)).isEqualTo(routes);
    }

}
