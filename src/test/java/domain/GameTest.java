package domain;

import domain.board.*;
import domain.game.Game;
import domain.game.Status;
import domain.strategy.ChariotMoveStrategy;
import domain.strategy.GeneralMoveStrategy;
import domain.strategy.SoldierMoveStrategy;
import domain.vo.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class GameTest {

    @Test
    @DisplayName("게임 정상 생성")
    void shouldCreateGameCorrectly() {
        // given
        Board board = BoardFactory.setUp(Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT, Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT);

        // when
        Game game = Game.of(board);

        // then
        Assertions.assertEquals(game.getBoard(), board);
    }

    @Test
    @DisplayName("초나라 궁이 잡히면 한나라가 승리한다")
    void shouldEndGameWhenChuGeneralIsCaptured() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(1, 4), Piece.of(Team.CHU, Type.GENERAL, new GeneralMoveStrategy()));
        pieces.put(Position.of(2, 4), Piece.of(Team.HAN, Type.CHARIOT, new ChariotMoveStrategy()));
        Board board = Board.of(pieces);
        Game game = Game.loadGame(1L, board, Team.HAN, Status.PLAYING);

        // when
        game.tryToMove(Position.of(2, 4), Position.of(1, 4));

        // then
        Assertions.assertEquals(Status.HAN_WIN, game.getStatus());
    }

    @Test
    @DisplayName("한나라 궁이 잡히면 초나라가 승리한다")
    void shouldEndGameWhenHanGeneralIsCaptured() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(8, 4), Piece.of(Team.HAN, Type.GENERAL, new GeneralMoveStrategy()));
        pieces.put(Position.of(7, 4), Piece.of(Team.CHU, Type.CHARIOT, new ChariotMoveStrategy()));
        Board board = Board.of(pieces);
        Game game = Game.of(board);

        // when
        game.tryToMove(Position.of(7, 4), Position.of(8, 4));

        // then
        Assertions.assertEquals(Status.CHU_WIN, game.getStatus());
    }

    @Test
    @DisplayName("일반 기물을 잡아도 게임은 계속된다")
    void shouldContinueGameWhenNormalPieceIsCaptured() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(1, 4), Piece.of(Team.HAN, Type.SOLDIER, new SoldierMoveStrategy()));
        pieces.put(Position.of(2, 4), Piece.of(Team.CHU, Type.CHARIOT, new ChariotMoveStrategy()));
        Board board = Board.of(pieces);
        Game game = Game.of(board);

        // when
        game.tryToMove(Position.of(2, 4), Position.of(1, 4));

        // then
        Assertions.assertEquals(Status.PLAYING, game.getStatus());
    }

    @Test
    @DisplayName("게임이 끝나면 더 이상 이동할 수 없다")
    void shouldNotMoveAfterGameEnds() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(1, 4), Piece.of(Team.CHU, Type.GENERAL, new GeneralMoveStrategy()));
        pieces.put(Position.of(2, 4), Piece.of(Team.HAN, Type.CHARIOT, new ChariotMoveStrategy()));
        pieces.put(Position.of(3, 0), Piece.of(Team.CHU, Type.SOLDIER, new SoldierMoveStrategy()));
        Board board = Board.of(pieces);
        Game game = Game.of(board);

        // when
        game.lose("한");

        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            game.tryToMove(Position.of(2, 4), Position.of(1, 4));
        });
    }
}
