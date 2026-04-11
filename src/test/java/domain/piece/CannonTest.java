package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Board;
import domain.Position;
import domain.Team;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CannonTest {
    /**
     * 1. 도착 지점이 같은 열과 행이 아닌 경우 이동 불가
     * 2. 도착지에 같은 팀이 존재하는 경우 이동 불가
     * 3. 도착지랑 출발지 사이에 오직 하나의 말이 존재하지 않는 경우 이동 불가
     * 4. 도착지와 출발지 사이의 말 하나가 포인 경우 이동 불가
     * 5. 도착지에 상대팀 말이 존재하면서, 해당 말이 포인 경우 이동 불가
     * 6. 이외는 이동 가능
     */

    /**
     * 1. 도착 지점이 같은 열이 아닌 경우 이동 불가
     */
    @Test
    void 도착_지점이_같은_열_혹은_행이_아닌_경우_이동_불가능하다() {
        // given
        Board board = new Board();
        Piece cannon = new Cannon(Team.CHO);

        // when
        Position from = Position.from(5, 1);
        Position to = Position.from(4, 2);

        // then
        assertThat(cannon.canMove(from, to, board)).isEqualTo(false);
    }

    /**
     * 2. 도착지에 같은 팀이 존재하는 경우 이동 불가
     */
    @Test
    void 도착지에_같은_팀이_존재하는_경우_이동_불가능_하다() {

        Position from = Position.from(5, 1);
        Position to = Position.from(5, 3);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Cannon(Team.CHO));
        testPiece.put(to, new Pawn(Team.CHO));

        Board board = new Board(testPiece);

        Piece cannon = new Cannon(Team.CHO);

        // then
        assertThat(cannon.canMove(from, to, board)).isEqualTo(false);
    }

    /**
     * 3. 도착지랑 출발지 사이에 오직 하나의 말이 존재하지 않는 경우 이동 불가
     */
    @Test
    void 도착지랑_출발지_사이에_오직_하나의_말이_존재하지_않는_경우_이동_불가능하다() {
        Position from = Position.from(5, 1);
        Position to = Position.from(5, 4);
        Position betweenFirst = Position.from(5, 2);
        Position betweenSecond = Position.from(5, 3);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Cannon(Team.CHO));
        testPiece.put(betweenFirst, new Pawn(Team.CHO));
        testPiece.put(betweenSecond, new Pawn(Team.CHO));

        Board board = new Board(testPiece);
        Piece cannon = new Cannon(Team.CHO);

        // then
        assertThat(cannon.canMove(from, to, board)).isEqualTo(false);
    }

    @Test
    void 중간에_말이_없는_경우_이동_불가능하다() {
        Piece cannon = new Cannon(Team.CHO);

        Position from = Position.from(5, 1);
        Position to = Position.from(5, 4);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, cannon);

        Board board = new Board(testPiece);

        assertThat(cannon.canMove(from, to, board)).isEqualTo(false);
    }

    /**
     * 4. 도착지와 출발지 사이의 말 하나가 포인 경우 이동 불가
     */
    @Test
    void 도착지와_출발지_사이의_말_하나가_포인_경우_이동_불가() {
        // given
        Piece cannon = new Cannon(Team.CHO);

        // when
        Position from = Position.from(5, 1);
        Position to = Position.from(5, 4);
        Position between = Position.from(5, 2);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Cannon(Team.CHO));
        testPiece.put(between, new Cannon(Team.CHO));

        Board board = new Board(testPiece);

        // then
        assertThat(cannon.canMove(from, to, board)).isEqualTo(false);
    }

    /**
     * 정상 테스트
     */
    @Test
    void 도착지에_포가_아닌_상대팀_말이_존재하면서_중간에_포가_아닌_오직_하나의_말만_존재하는_경우_이동_가능하다() {
        // given
        Piece cannon = new Cannon(Team.CHO);

        // when
        Position from = Position.from(5, 1);
        Position to = Position.from(5, 4);
        Position between = Position.from(5, 2);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Cannon(Team.CHO));
        testPiece.put(between, new Pawn(Team.CHO));
        testPiece.put(to, new Pawn(Team.HAN));

        Board board = new Board(testPiece);

        // then
        assertThat(cannon.canMove(from, to, board)).isEqualTo(true);
    }

    /**
     * 5. 도착지에 상대팀 말이 존재하면서, 해당 말이 포인 경우 이동 불가
     */
    @Test
    void 도착지에_상대팀_말이_존재하면서_해당_말이_포인_경우_이동_불가() {
        // given
        Piece cannon = new Cannon(Team.CHO);

        // when
        Position from = Position.from(5, 1);
        Position to = Position.from(5, 4);
        Position between = Position.from(5, 2);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Cannon(Team.CHO));
        testPiece.put(between, new Pawn(Team.CHO));
        testPiece.put(to, new Cannon(Team.HAN));

        Board board = new Board(testPiece);

        // then
        assertThat(cannon.canMove(from, to, board)).isEqualTo(false);
    }

    @Test
    void 도착지가_빈칸이면서_중간에_말이_존재하지_않는_경우_궁성_내_간선_이동이_불가능하다() {
        Piece cannon = new Cannon(Team.CHO);

        Position from = Position.from(8, 4);
        Position to = Position.from(10, 6);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Cannon(Team.CHO));

        Board board = new Board(testPiece);

        assertThat(cannon.canMove(from, to, board)).isEqualTo(false);
    }

    @Test
    void 도착지에_같은팀_기물이_있으면_궁성_내_간선_이동이_불가능하다() {
        Piece cannon = new Cannon(Team.CHO);

        Position from = Position.from(8, 4);
        Position to = Position.from(10, 6);
        Position between = Position.from(9, 5);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Cannon(Team.CHO));
        testPiece.put(between, new Pawn(Team.CHO));
        testPiece.put(to, new Pawn(Team.CHO));

        Board board = new Board(testPiece);

        assertThat(cannon.canMove(from, to, board)).isEqualTo(false);
    }

    @Test
    void 도착지가_빈칸이면서_중간에_포가_아닌_오직_하나의_말만_존재하는_경우_이동_가능하다() {
        // given
        Piece cannon = new Cannon(Team.CHO);

        // when
        Position from = Position.from(5, 1);
        Position to = Position.from(5, 4);
        Position between = Position.from(5, 2);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Cannon(Team.CHO));
        testPiece.put(between, new Pawn(Team.CHO));

        Board board = new Board(testPiece);

        // then
        assertThat(cannon.canMove(from, to, board)).isEqualTo(true);
    }

    @Test
    void 도착지가_빈칸이면서_중간에_포가_아닌_오직_하나의_말만_존재하는_경우_궁성_내_간선_이동가능하다() {
        Piece cannon = new Cannon(Team.CHO);

        Position from = Position.from(8, 4);
        Position to = Position.from(10, 6);
        Position between = Position.from(9, 5);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Cannon(Team.CHO));
        testPiece.put(between, new Pawn(Team.CHO));

        Board board = new Board(testPiece);

        assertThat(cannon.canMove(from, to, board)).isEqualTo(true);
    }
}
