package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.dto.GameDto;
import janggi.dto.PieceDtos;
import janggi.game.Game;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameDaoTest {
    private Game createdGame = new Game();

    @AfterEach
    void cleanUp() {
        //TODO 데이터 정리
    }

    @Test
    @DisplayName("데이터베이스 연결을 확인한다.")
    public void connection() {
        try (final var connection = JangiDatabase.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("하나의 게임 투플을 추가한다.")
    void createGameTuple() {
        GameDao.createGame(createdGame);
    }

    @Test
    @DisplayName("가장 최근에 만들어진 게임 투플을 조회한다.")
    void findGameLastCreated() {
        GameDao beforeGame = GameDao.createGame(createdGame);

        GameDto sameGame = GameDao.findLastCreated();
        assertThat(beforeGame.getId()).isEqualTo(sameGame.id());
    }

    @Test
    @DisplayName("조회된 게임 데이터에 맞게 게임 객체를 생성한다.")
    void createGameObjectFromTuple() {
        GameDao.createGame(createdGame);
        GameDto createdGame = GameDao.findLastCreated();
        PieceDtos pieceDtos = PieceDao.findPiecesBy(createdGame);

        GameDao recreatedGame = GameDao.recreateGameFrom(pieceDtos, createdGame);

        assertThat(recreatedGame.getId()).isEqualTo(createdGame.id());
    }

    @Test
    @DisplayName("게임의 턴을 수정한다.")
    void updateGameTurn() {
        GameDao gameDao = GameDao.createGame(createdGame);
        createdGame.reverseTurn();

        GameDao.updateTurn(gameDao.getGame());
        //TODO : 검증가능?
    }

    @Test
    @DisplayName("게임 객체를 삭제한다.")
    void deleteGame() {
        GameDao gameDao = GameDao.createGame(createdGame);

        GameDao.deleteGame(gameDao.getGame());

        assertThatThrownBy(() -> GameDao.deleteGame(gameDao.getGame()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("게임이 삭제되지 않았습니다.");
    }
}