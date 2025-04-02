package janggi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.game.Board;
import janggi.domain.game.Game;
import janggi.domain.game.Team;
import janggi.domain.piece.Soldier;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import janggi.dto.GameDto;
import janggi.dto.PieceDto;
import janggi.fixture.FakeGameDao;
import janggi.fixture.FakePieceDao;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameServiceTest {

    private final FakeGameDao gameDao = new FakeGameDao(Map.of(1, new GameDto(1, "CHO", LocalDateTime.now())));
    private final FakePieceDao pieceDao = new FakePieceDao(
            Map.of(1, new ArrayList<>(
                    List.of(new PieceDto("GENERAL", "CHO", 3, 0),
                            new PieceDto("GENERAL", "HAN", 3, 7)))));
    private final GameService gameService = new GameService(gameDao, pieceDao);

    @DisplayName("게임 아이디로 저장된 게임 정보를 가져올 수 있다.")
    @Test
    void testLoadGameByGameId() {
        // given
        // when
        Game game = gameService.loadGameByGameId(1);
        // then
        assertAll(
                () -> assertThat(game.hasPieceAt(new Position(Column.THREE, Row.ZERO))).isTrue(),
                () -> assertThat(game.hasPieceAt(new Position(Column.THREE, Row.SEVEN))).isTrue()
        );
    }

    @DisplayName("모든 게임 목록을 불러올 수 있다.")
    @Test
    void testGetAllGames() {
        // given
        // when
        List<GameDto> allGames = gameService.getAllGames();
        // then
        assertThat(allGames).hasSize(1);
    }

    @DisplayName("게임 정보를 DB에 저장할 수 있다.")
    @Test
    void testSaveGame() {
        // given
        Board board = new Board(Map.of(new Position(Column.ZERO, Row.ZERO), new Soldier(Team.HAN)));
        Game game = new Game(Team.HAN, board);
        // when
        gameService.saveGame(game);
        // then
        assertThat(gameService.getAllGames()).hasSize(2);
    }

    @DisplayName("게임 아이디로 기존의 게임 정보를 수정할 수 있다.")
    @Test
    void testUpdateGame() {
        // given
        Board board = new Board(Map.of(new Position(Column.ZERO, Row.ZERO), new Soldier(Team.HAN)));
        Game game = new Game(Team.HAN, board);
        // when
        gameService.updateGame(1, game);
        // then
        Game actual = gameService.loadGameByGameId(1);
        assertThat(actual.getTurn()).isEqualTo(Team.HAN);
    }

    @DisplayName("게임 아이디로 기존의 게임 정보를 삭제할 수 있다.")
    @Test
    void testDeleteGame() {
        // given
        // when
        gameService.deleteGame(1);
        // then
        assertThat(gameService.getAllGames()).hasSize(0);
    }
}
