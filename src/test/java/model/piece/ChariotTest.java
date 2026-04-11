package model.piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Board;
import model.Team;
import model.coordinate.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ChariotTest {

    @ParameterizedTest
    @MethodSource("model.fixture.PieceTestFixture#직선_이동_가능한_위치")
    void 차는_직선으로_이동할_수_있다(Position current, Position next) {
        // given
        Piece chariot = new Chariot(Team.HAN);
        // when
        boolean canMove = chariot.canMove(current, next);
        // then
        assertThat(canMove).isTrue();
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PieceTestFixture#직선_이동_불가능한_위치")
    void 차는_대각선으로_이동할_수_없다(Position current, Position next) {
        // given
        Piece chariot = new Chariot(Team.HAN);
        // when
        boolean canMove = chariot.canMove(current, next);
        // then
        assertThat(canMove).isFalse();
    }

    @Test
    void 차는_이동_경로의_중간_위치를_반환한다() {
        // given
        Piece chariot = new Chariot(Team.HAN);
        Position current = new Position(0, 0);
        Position next = new Position(0, 5);

        // when
        List<Position> path = chariot.extractPath(current, next);

        // then
        assertThat(path).containsExactly(
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3),
                new Position(0, 4)
        );
    }

    @Test
    void 차가_한_칸_이동하면_중간_경로가_없다() {
        // given
        Piece chariot = new Chariot(Team.HAN);
        Position current = new Position(3, 4);
        Position next = new Position(3, 5);

        // when
        List<Position> path = chariot.extractPath(current, next);

        // then
        assertThat(path).isEmpty();
    }

    @Test
    void 이동_경로에_기물이_있으면_멱이_적용된다() {
        // given
        Piece chariot = new Chariot(Team.HAN);
        Position current = new Position(0, 0);
        Position next = new Position(0, 5);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(current, chariot);
        pieces.put(new Position(0, 3), new Chariot(Team.CHO));
        Board board = new Board(pieces);

        // when
        List<Position> path = chariot.extractPath(current, next);

        // then
        assertThat(board.hasPieceAt(path)).isTrue();
    }

    @Test
    void 이동_경로에_기물이_없으면_멱이_적용되지_않는다() {
        // given
        Piece chariot = new Chariot(Team.HAN);
        Position current = new Position(0, 0);
        Position next = new Position(0, 5);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(current, chariot);
        Board board = new Board(pieces);

        // when
        List<Position> path = chariot.extractPath(current, next);

        // then
        assertThat(board.hasPieceAt(path)).isFalse();
    }

    @Test
    void 한_칸_이동은_경로가_없으므로_멱이_적용되지_않는다() {
        // given
        Piece chariot = new Chariot(Team.HAN);
        Position current = new Position(0, 0);
        Position next = new Position(0, 1);

        Board board = new Board(new HashMap<>());

        // when
        List<Position> path = chariot.extractPath(current, next);

        // then
        assertThat(board.hasPieceAt(path)).isFalse();
    }

}