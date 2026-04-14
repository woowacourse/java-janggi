package janggi.domain.board;

import janggi.domain.piece.*;
import janggi.domain.vo.position.Position;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {
    private Board board;

    @Test
    void 특정_좌표의_기물을_찾기_테스트() {
        // given
        board = new Board(BoardInitializer.createBoard());
        Position position = new Position(1, 4);

        // when
        Piece piece = board.findPieceByPosition(position);

        // then
        assertThat(piece.getTeam()).isEqualTo(Team.HAN);
        assertThat(piece.pieceType()).isEqualTo(PieceType.KING);
    }

    @Test
    void 특정_좌표가_빈칸인지_확인_테스트() {
        // given
        board = new Board();
        Position position = new Position(5, 0);

        // when, then
        assertTrue(board.isEmptyPosition(position));
    }

    @Test
    void 정상_이동_테스트() {
        // given
        Position from = new Position(9, 0);
        Position to = new Position(7, 0);
        board = initializeBoardWith(from, new Tank(Team.CHO));

        // when
        board.move(from, to, Team.CHO);

        // then
        assertThat(board.findPieceByPosition(from).isEmpty()).isTrue();
        assertThat(board.findPieceByPosition(to).pieceType()).isEqualTo(PieceType.TANK);
    }

    @Test
    void 상대방_기물_조작시_예외가_발생한다() {
        // given
        Position from = new Position(0,0);
        Position to = new Position(1,0);
        Team turn = Team.CHO;
        Piece setupPiece = new Soldier(Team.HAN);

        board = initializeBoardWith(from, setupPiece);

        // when, then
        assertThatThrownBy(() -> board.move(from, to, turn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("자신 진영의 기물을");
    }

    @Test
    void 아군_기물_위치로_이동시_예외가_발생한다() {
        // given
        Position from = new Position(0,0);
        Position to = new Position(0,1);
        Team turn = Team.HAN;
        Piece setupPiece = new Soldier(Team.HAN);
        Piece targetPiece = new Tank(Team.HAN);

        board = initializeBoardWith(from, setupPiece, to, targetPiece);

        // when, then
        assertThatThrownBy(() -> board.move(from, to, turn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("도착지점에 플레이어님의 진영 기물이");
    }

    @Test
    void 기물이_없는_곳에서_시작시_예외가_발생한다() {
        // given
        Position from = new Position(0,0);
        Position to = new Position(0,1);
        Team turn = Team.HAN;

        board = new Board();

        // when, then
        assertThatThrownBy(() -> board.move(from, to, turn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("기물이 없습니다");
    }

    @Test
    void 이동_길목이_막힌_경우_예외가_발생한다() {
        // given
        Position from = new Position(0,0);
        Position path = new Position(2,1);
        Position to = new Position(3,2);
        Team turn = Team.HAN;
        Piece setupPiece = new Elephant(Team.HAN);
        Piece obstaclePiece = new Cannon(Team.HAN);

        board = initializeBoardWith(from, setupPiece, path, obstaclePiece);

        // when, then
        assertThatThrownBy(() -> board.move(from, to, turn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동 규칙에 맞지 않습니다");
    }

    public Board initializeBoardWith(Object... args) {
        Map<Position, Piece> map = new HashMap<>();

        for (int i = 0; i < args.length; i += 2) {
            map.put((Position) args[i], (Piece) args[i + 1]);
        }

        return new Board(map);
    }
}
