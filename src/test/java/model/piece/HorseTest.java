package model.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Board;
import model.Janggi;
import model.Team;
import model.coordinate.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class HorseTest {

    @ParameterizedTest
    @MethodSource("model.fixture.PieceTestFixture#마_이동_가능한_위치")
    void 마는_한칸_직진_후_대각선으로_이동할_수_있다(Position current, Position next) {
        // given
        Piece horse = new Horse(Team.HAN);
        // when
        boolean canMove = horse.canMove(current, next);
        // then
        assertThat(canMove).isTrue();
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PieceTestFixture#마_이동_불가능한_위치")
    void 마는_이동_규칙에_맞지_않으면_이동할_수_없다(Position current, Position next) {
        // given
        Piece horse = new Horse(Team.HAN);
        // when
        boolean canMove = horse.canMove(current, next);
        // then
        assertThat(canMove).isFalse();
    }

    @Test
    void 마의_직선_경로에_기물이_있으면_멱이_적용된다() {
        // given
        Piece horse = new Horse(Team.HAN);
        Position current = new Position(5, 4);
        Position next = new Position(3, 5);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(current, horse);
        pieces.put(new Position(4, 4), new Soldier(Team.CHO));
        Board board = new Board(pieces);

        // when
        List<Position> path = horse.extractPath(current, next);

        // then
        assertThat(board.hasPieceAt(path)).isTrue();
    }

    @Test
    void 마의_경로에_기물이_있으면_이동할_수_없다() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(5, 4), new Horse(Team.CHO));
        pieces.put(new Position(4, 4), new Soldier(Team.CHO));
        Board board = new Board(pieces);
        Janggi janggi = new Janggi(board);

        // when & then
        assertThatThrownBy(() -> janggi.move(new Position(5, 4), new Position(3, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 마의_경로에_기물이_없으면_멱이_적용되지_않는다() {
        // given
        Piece horse = new Horse(Team.HAN);
        Position current = new Position(5, 4);
        Position next = new Position(3, 5);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(current, horse);
        Board board = new Board(pieces);

        // when
        List<Position> path = horse.extractPath(current, next);

        // then
        assertThat(board.hasPieceAt(path)).isFalse();
    }
}