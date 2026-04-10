package domain;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.General;
import domain.piece.Team;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    void 유효한_기물_이동이라면_성공한다() {

        Board board = new Board(
                Map.of(new Position(0, 0), new Chariot(Team.CHO))
        );

        Game game = new Game(board);
        assertDoesNotThrow(
                () -> game.move(new Position(0, 0), new Position(5, 0))
        );

    }

    @Test
    void 기물이_없다면_실패한다() {
        Board board = new Board(
                Map.of(new Position(0, 0), new Chariot(Team.CHO))
        );
        Game game = new Game(board);
        assertThrows(IllegalStateException.class,
                () -> game.move(new Position(1, 0), new Position(5, 0)))
        ;
    }


    @Test
    void 차례가_아니라면_실패한다() {
        Board board = new Board(
                Map.of(new Position(0, 0), new Chariot(Team.HAN))
        );

        Game game = new Game(board);
        assertThrows(IllegalStateException.class,
                () -> game.validateMoveAblePiece(new Position(0, 0)));
    }


    @Test
    void 기물의_이동은_번갈아_진행된다() {
        Board board = new Board(Map.of(
                new Position(0, 0), new Chariot(Team.CHO),
                new Position(5, 0), new Chariot(Team.HAN)
        ));

        Game game = new Game(board);

        game.move(new Position(0, 0), new Position(4, 0));


        assertDoesNotThrow(
                () -> game.validateMoveAblePiece(new Position(5, 0))
        );
    }

    @Test
    void 기물의_이동턴이_아니면_진행될_수_없다() {
        Board board = new Board(Map.of(
                new Position(0, 0), new Chariot(Team.CHO),
                new Position(5, 0), new Chariot(Team.HAN)
        ));

        Game game = new Game(board);
        game.move(new Position(0, 0), new Position(4, 0));

        assertThrows(IllegalStateException.class,
                () -> game.validateMoveAblePiece(new Position(4, 0)));

    }

    @Test
    void 기물이_잡혔다면_그_기물은_없다() {
        Board board = new Board(Map.of(
                new Position(0, 0), new Chariot(Team.CHO),
                new Position(5, 0), new Chariot(Team.HAN)
        ));

        Game game = new Game(board);
        game.move(new Position(0, 0), new Position(5, 0));

        assertThrows(IllegalStateException.class,
                () -> game.validateMoveAblePiece(new Position(5, 0)));
    }

    @Test
    void 게임이_끝났는지_확인한다() {
        Board board = new Board(Map.of(
                new Position(0, 0), new Chariot(Team.HAN),
                new Position(1, 0), new Cannon(Team.HAN),
                new Position(4, 9), new General(Team.HAN)
        ));

        Game game = new Game(board);
        boolean gameEnd = game.isGameEnd();
        assertThat(gameEnd).isTrue();
    }

    @Test
    void 게임의_결과가_제대로_동작하는지_확인한다() {
        Board board = new Board(Map.of(
                new Position(0, 0), new Chariot(Team.HAN),
                new Position(1, 0), new Cannon(Team.HAN),
                new Position(4, 9), new General(Team.HAN)
        ));

        Game game = new Game(board);
        Team winnerTeam = game.getWinnerTeam();

        assertThat(winnerTeam).isEqualTo(Team.HAN);
    }

    @Test
    void 궁이_둘다_죽지_않았다면_결과를_받을_수_없다() {
        Board board = new Board(Map.of(
                new Position(0, 0), new Chariot(Team.HAN),
                new Position(1, 0), new Cannon(Team.HAN),
                new Position(4, 9), new General(Team.HAN),
                new Position(4, 0), new General(Team.CHO)

                ));

        Game game = new Game(board);
        assertThrows(IllegalStateException.class, game::getWinnerTeam);
    }


}
