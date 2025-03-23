package janggi.domain;

import static janggi.domain.BoardSetup.INNER_ELEPHANT_SETUP;
import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.piece.Chariot;
import janggi.domain.piece.Piece;
import janggi.domain.piece.direction.Position;
import janggi.domain.piece.direction.Route;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @DisplayName("보드에서 기물을 선택한다.")
    @Test
    void selectPieceTest() {

        // given
        final Board board = new Board(INNER_ELEPHANT_SETUP, INNER_ELEPHANT_SETUP);
        final Position position = new Position(0, 0);

        // when
        final Piece piece = board.selectPiece(position);

        // then
        assertThat(piece).isEqualTo(board.selectPiece(new Position(0, 0)));
    }

    @DisplayName("기물의 이동 가능한 경로를 찾는다.")
    @Test
    void findPossibleRoutesTest() {

        // given
        final Board board = new Board(INNER_ELEPHANT_SETUP, INNER_ELEPHANT_SETUP);
        final Piece chariot = new Chariot(new Position(0, 0), BLUE);

        // when
        final Set<Route> routes = board.findPossibleRoutes(chariot);

        // then
        assertThat(routes.size()).isEqualTo(2);
    }

    @DisplayName("기물이 이동한다.")
    @Test
    void movePieceTest() {

        // given
        final Board board = new Board(INNER_ELEPHANT_SETUP, INNER_ELEPHANT_SETUP);
        final Piece chariot = board.selectPiece(new Position(0, 0));

        // when
        board.movePiece(new Position(0, 2), chariot);
        final Piece selectPiece = board.selectPiece(new Position(0, 2));

        // then
        assertThat(chariot).isEqualTo(selectPiece);
    }

    @DisplayName("턴을 교체한다.")
    @Test
    void changeTurnTest() {

        // given
        final Board board = new Board(INNER_ELEPHANT_SETUP, INNER_ELEPHANT_SETUP);

        // when
        final Team currentTurn = board.getTurn();
        board.changeTurn();
        final Team nextTurn = board.getTurn();

        assertAll(() -> {
            assertThat(currentTurn).isEqualTo(BLUE);
            assertThat(nextTurn).isEqualTo(RED);
        });
    }
}
