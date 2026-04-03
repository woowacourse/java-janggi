package domain.piece;

import domain.Board;
import domain.Position;
import domain.Team;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class PawnTest {
    /**
     * 졸/병 규칙 : 앞, 양 옆 한 칸씩 이동 가능
     * 1. 도착 지점이 한칸 앞, 혹은 양옆인지 검증
     */

    /**
     * 이동 진로가 빈칸인 경우 1. 한칸 앞이 빈칸인 경우, 이동할 수 있다. 2. 한칸 오른쪽이 빈칸인 경우, 이동할 수 있다. 3. 한칸 왼쪽이 빈칸인 경우, 이동할 수 있다.
     */
    @ParameterizedTest
    @MethodSource("toProvider")
    void 도착_지점이_한칸거리이면서_양쪽_옆_혹은_앞인_경우_정상_테스트(Position to){
        // given
        Board board = new Board();
        Piece pawn = new Pawn(Team.CHO);

        // when
        Position from = Position.from(7, 3);

        // then
        assertThat(pawn.canMove(from, to, board)).isEqualTo(true);
    }

    static Stream<Position> toProvider() {
        return Stream.of(
                Position.from(6, 3),
                Position.from(7, 2),
                Position.from(7, 4)
        );
    }

    /**
     * 목적지에 같은 팀이 있는 경우
     * 1. 목적지가 한칸 앞인 경우이면서 목적지에 같은 팀이 있는 경우 이동할 수 없다.
     * 2. 목적지가 한칸 오른쪽인 경우이면서 목적지에 같은 팀이 있는 경우 이동할 수 없다.
     * 3. 목적지가 한칸 왼쪽인 경우이면서 목적지에 같은 팀이 있는 경우 이동할 수 없다.
     */

    @Test
    void 목적지가_한_칸_앞인_경우이면서_목적지에_같은_팀이_있는_경우_이동할_수_없다() {
        // given
        Piece pawn = new Pawn(Team.CHO);

        Position from = Position.from(7, 1);
        Position to = Position.from(6, 1);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Pawn(Team.CHO));
        testPiece.put(to, new Pawn(Team.CHO));

        Board board = new Board(testPiece);

        // when & then
        assertThat(pawn.canMove(from, to, board)).isEqualTo(false);
    }

    @ParameterizedTest
    @MethodSource("invalidDirectionProvider")
    void 목적지가_앞_또는_좌_우_한_칸이_아닌_경우_이동할_수_없다(Position to) {
        // given
        Board board = new Board();
        Piece pawn = new Pawn(Team.CHO);

        Position from = Position.from(7, 3);

        assertThat(pawn.canMove(from, to, board)).isEqualTo(false);
    }

    static Stream<Position> invalidDirectionProvider() {
        return Stream.of(
                Position.from(6, 4),
                Position.from(5, 3),
                Position.from(8, 3)
        );
    }

    @ParameterizedTest
    @MethodSource("validDirectionProvider")
    void 궁성내_뒤를_제외한_대각선_이동이_가능하다(Position to) {
        Piece pawn = new Pawn(Team.CHO);

        Position from = Position.from(9, 5);
        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Pawn(Team.CHO));

        Board board = new Board(testPiece);

        assertThat(pawn.canMove(from, to, board)).isEqualTo(true);
    }

    static Stream<Position> validDirectionProvider() {
        return Stream.of(
                Position.from(8, 4),
                Position.from(8, 6)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidDiagonalDirectionProvider")
    void 궁성내_뒤로_향하는_대각선_이동은_불가능하다(Position to) {
        Piece pawn = new Pawn(Team.CHO);

        Position from = Position.from(9, 5);
        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Pawn(Team.CHO));

        Board board = new Board(testPiece);

        assertThat(pawn.canMove(from, to, board)).isEqualTo(false);
    }

    static Stream<Position> invalidDiagonalDirectionProvider() {
        return Stream.of(
                Position.from(10, 4),
                Position.from(10, 6)
        );
    }
}
