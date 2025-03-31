package janggi.domain;

import static janggi.domain.BoardSetup.INNER_ELEPHANT_SETUP;
import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.piece.Chariot;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PiecesInitializer;
import janggi.domain.piece.direction.Position;
import janggi.domain.piece.direction.Route;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameTest {

    @DisplayName("보드에서 기물을 선택한다.")
    @Test
    void selectPieceTest() {

        // given
        final Game game = new Game(
                new Pieces(PiecesInitializer.initializePieces(INNER_ELEPHANT_SETUP, INNER_ELEPHANT_SETUP)),
                new Turn(BLUE));
        final Position position = new Position(0, 0);

        // when
        final Piece piece = game.selectPiece(position);

        // then
        assertThat(piece).isEqualTo(game.selectPiece(new Position(0, 0)));
    }

    @DisplayName("보드에서 기물을 선택 시, 내 팀의 기물이 없을 시 예외가 발생한다.")
    @Test
    void selectPieceThrowExceptionTest() {

        // given
        final Game game = new Game(
                new Pieces(PiecesInitializer.initializePieces(INNER_ELEPHANT_SETUP, INNER_ELEPHANT_SETUP)),
                new Turn(BLUE));
        final Position emptyPosition = new Position(5, 5);

        // when & then
        assertThatThrownBy(() -> game.selectPiece(emptyPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치에 우리팀 기물이 없습니다.");
    }

    @DisplayName("기물의 이동 가능한 경로를 찾는다.")
    @Test
    void findPossibleRoutesTest() {

        // given
        final Game game = new Game(
                new Pieces(PiecesInitializer.initializePieces(INNER_ELEPHANT_SETUP, INNER_ELEPHANT_SETUP)),
                new Turn(BLUE));
        final Piece chariot = new Chariot(new Position(0, 0), BLUE);

        // when
        final Set<Route> routes = game.findPossibleRoutes(chariot);

        // then
        assertAll(() -> {
            assertThat(routes).isNotEmpty();
            assertThat(routes.size()).isEqualTo(2);
            assertThat(routes).contains(
                    new Route(List.of(new Position(0, 1), new Position(0, 2))),
                    new Route(List.of(new Position(0, 1)))
            );
        });

    }

    @DisplayName("기물이 이동한다.")
    @Test
    void movePieceTest() {

        // given
        final Game game = new Game(
                new Pieces(PiecesInitializer.initializePieces(INNER_ELEPHANT_SETUP, INNER_ELEPHANT_SETUP)),
                new Turn(BLUE));
        final Piece chariot = game.selectPiece(new Position(0, 0));

        // when
        game.movePiece(new Position(0, 2), chariot);
        final Piece selectPiece = game.selectPiece(new Position(0, 2));

        // then
        assertThat(chariot).isEqualTo(selectPiece);
    }

    @DisplayName("턴을 교체한다.")
    @Test
    void changeTurnTest() {

        // given
        final Game game = new Game(
                new Pieces(PiecesInitializer.initializePieces(INNER_ELEPHANT_SETUP, INNER_ELEPHANT_SETUP)),
                Turn.initialize());

        // when
        game.changeTurn();
        final Turn nextTurn = game.getTurn();

        assertAll(() -> {
            assertThat(nextTurn.getTurn()).isEqualTo(RED);
        });
    }

    @DisplayName("남은 기물로 점수를 계산한다.")
    @Test
    void getScoreByTeamTest() {

        // given
        final Game game = new Game(
                new Pieces(PiecesInitializer.initializePieces(INNER_ELEPHANT_SETUP, INNER_ELEPHANT_SETUP)),
                new Turn(BLUE));

        // when
        final double redResult = game.getScoreByTeam(RED);
        final double blueResult = game.getScoreByTeam(BLUE);

        // then
        assertAll(() -> {
            assertThat(redResult).isEqualTo(72);
            assertThat(blueResult).isEqualTo(73.5);
        });
    }
}
