package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.GameState;
import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.piece.multiplemovepiece.Cha;
import janggi.piece.onemovepiece.Byeong;
import janggi.piece.onemovepiece.Janggun;
import janggi.position.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @DisplayName("장기판은 기물과 위치를 가진다.")
    @Test
    void board() {
        //given
        final PieceInitializer pieceInitializer = new PieceInitializer();
        final List<Piece> pieces = pieceInitializer.generate();

        //when
        final Board board = new Board(pieces);

        //then
        assertThat(board.getJanggiBoard()).hasSize(32);
    }

    @DisplayName("선택한 위치에 기물이 존재하지 않는다면 예외를 던진다.")
    @Test
    void emptyPieceByPosition() {
        //given
        final List<Piece> pieces = List.of(
                new Byeong(Team.HAN, new Position(3, 2))
        );

        final Board board = new Board(pieces);

        final Position presentPosition = new Position(4, 2);

        //when //then
        assertThatThrownBy(() -> board.validateEmptyPieceBy(presentPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("이동시키려는 경로에 장애물이 존재한다면 예외를 던진다.")
    @Test
    void exceptionObstacle() {
        //given
        final List<Piece> pieces = List.of(
                new Cha(Team.HAN, new Position(4, 2)),
                new Byeong(Team.HAN, new Position(7, 2))
        );

        final Position presentPosition = new Position(4, 2);
        final Position futurePosition = new Position(8, 2);

        final Board board = new Board(pieces);

        //when //then
        assertThatThrownBy(() -> board.pieceMove(presentPosition, futurePosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("장기판의 기물을 옮길 수 있다.")
    @Test
    void pieceMove() {
        //given
        final List<Piece> pieces = List.of(
                new Byeong(Team.HAN, new Position(3, 2))
        );

        final Board board = new Board(pieces);

        final Position presentPosition = new Position(3, 2);
        final Position futurePosition = new Position(4, 2);

        //when
        board.pieceMove(presentPosition, futurePosition);

        //then
        final Piece actual = board.getJanggiBoard().get(futurePosition);
        assertThat(actual).isEqualTo(new Byeong(Team.HAN,
                new Position(4, 2)));
    }

    @DisplayName("왕이 죽으면 게임이 종료 상태가 된다.")
    @Test
    void jaggunDeadEndState() {
        //given
        final List<Piece> pieces = List.of(
                new Byeong(Team.HAN, new Position(3, 2)),
                new Janggun(Team.CHO, new Position(4, 2))
        );

        final Board board = new Board(pieces);

        final Position presentPosition = new Position(3, 2);
        final Position futurePosition = new Position(4, 2);

        //when
        final GameState gameState = board.pieceMove(presentPosition, futurePosition);

        //then
        assertThat(gameState).isEqualTo(GameState.END);
    }

    @DisplayName("왕이 죽지 않으면 게임이 진행 상태가 된다.")
    @Test
    void nonJaggunDeadEndState() {
        //given
        final List<Piece> pieces = List.of(
                new Byeong(Team.HAN, new Position(3, 2)),
                new Janggun(Team.CHO, new Position(5, 2))
        );

        final Board board = new Board(pieces);

        final Position presentPosition = new Position(3, 2);
        final Position futurePosition = new Position(4, 2);

        //when
        final GameState gameState = board.pieceMove(presentPosition, futurePosition);

        //then
        assertThat(gameState).isEqualTo(GameState.IN_PROGRESS);
    }

}
