package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Board;
import domain.Position;
import domain.Team;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import javax.print.StreamPrintService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class RookTest {

    /**
     * 1. 도착 지점이 같은 열과 행이 아닌 경우 이동 불가
     * 2. 도착지에 같은 팀이 존재하는 경우 이동 불가
     * 3. 도착지랑 출발지 사이에 말이 하나라도 존재하면 이동 불가
     */

    /**
     * 1. 도착 지점이 같은 열이 아닌 경우 이동 불가
     */
    @Test
    void 도착_지점이_같은_열_혹은_행이_아닌_경우_이동_불가능하다() {
        // given
        Board board = new Board();
        Piece rook = new Rook(Team.CHO);

        // when
        Position from = Position.from(10, 1);
        Position to = Position.from(9, 2);

        // then
        assertThat(rook.canMove(from, to, board)).isEqualTo(false);
    }

    /**
     * 2. 도착지에 같은 팀이 존재하는 경우 이동 불가
     */
    @Test
    void 도착지에_같은_팀이_존재하는_경우_이동_불가능하다() {
        // given
        Piece rook = new Rook(Team.CHO);

        // when
        Position from = Position.from(10, 1);
        Position to = Position.from(10, 2);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Rook(Team.CHO));
        testPiece.put(to, new Pawn(Team.CHO));

        Board board = new Board(testPiece);

        // then
        assertThat(rook.canMove(from, to, board)).isEqualTo(false);
    }

    /**
     * 3. 도착지랑 출발지 사이에 말이 하나라도 존재하면 이동 불가
     */
    @Test
    void 도착지랑_출발지_사이에_말이_하나라도_존재하면_이동_불가능하다() {
        // given
        Piece rook = new Rook(Team.CHO);

        // when
        Position from = Position.from(10, 1);
        Position to = Position.from(10, 3);
        Position between = Position.from(10, 2);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Rook(Team.CHO));
        testPiece.put(between, new Pawn(Team.CHO));

        Board board = new Board(testPiece);

        // then
        assertThat(rook.canMove(from, to, board)).isEqualTo(false);
    }

    /**
     * 궁성 내 대각선 이동 테스트
     * 1. 궁성 내 이동 경로에 장애물이 없다면 대각선으로 이동 가능하다
     * 2. 장애물이 있다면 이동 불가능 하다.
     * 3. 도착지에 같은 팀 말이 있다면 이동 불가능 하다
     */

    @Test
    void 궁성_내_이동_경로에_장애물이_없다면_대각선으로_이동가능하다() {
        Piece rook = new Rook(Team.CHO);

        Position from = Position.from(8, 4);
        Position to = Position.from(10, 6);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Rook(Team.CHO));

        Board board = new Board(testPiece);

        assertThat(rook.canMove(from, to, board)).isEqualTo(true);
    }

    @Test
    void 궁성_내_이동_경로에_장애물이_있다면_대각선으로_이동_불가능하다() {
        Piece rook = new Rook(Team.CHO);

        Position from = Position.from(8, 4);
        Position between = Position.from(9, 5);
        Position to = Position.from(10, 6);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Rook(Team.CHO));
        testPiece.put(between, new Pawn(Team.CHO));

        Board board = new Board(testPiece);

        assertThat(rook.canMove(from, to, board)).isEqualTo(false);
    }

    @Test
    void 궁도착지에_같은_팀_말이_있다면_이동_불가능하다() {
        Piece rook = new Rook(Team.CHO);

        Position from = Position.from(8, 4);
        Position to = Position.from(10, 6);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Rook(Team.CHO));
        testPiece.put(to, new Pawn(Team.CHO));

        Board board = new Board(testPiece);

        assertThat(rook.canMove(from, to, board)).isEqualTo(false);
    }


    /**
     * 정상 테스트
     */

    @ParameterizedTest
    @MethodSource("validDirectionsPalacePositions")
    void 궁성_내에서_대각선과_상하좌우로_움직일_수_있다(Position to) {
        // given
        Piece rook = new Rook(Team.CHO);

        // when
        Position from = Position.from(10,4);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Rook(Team.CHO));

        Board board = new Board(testPiece);

        assertThat(rook.canMove(from, to, board)).isEqualTo(true);
    }

    static Stream<Position> validDirectionsPalacePositions() {
        return Stream.of(
                Position.from(10, 5),
                Position.from(9,4),
                Position.from(9,5)
        );
    }

    @ParameterizedTest
    @MethodSource("validDirectionsPositions")
    void 출발지와_도착지_사이에_기물이_없고_직선_움직임인_경우_차는_정상_이동한다(Position to) {
        // given
        Piece rook = new Rook(Team.CHO);

        // when
        Position from = Position.from(10, 1);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Rook(Team.CHO));

        Board board = new Board(testPiece);

        // then
        assertThat(rook.canMove(from, to, board)).isEqualTo(true);
    }

    static Stream<Position> validDirectionsPositions() {
        return Stream.of(
                Position.from(10, 3),
                Position.from(8, 1)
        );
    }
}
