package repository;

import domain.board.Board;
import domain.game.Game;
import repository.dto.GameDto;

import java.sql.Connection;
import java.util.List;

public interface GameDao {

    Game save(Connection con, Game game);

    void update(Connection con, Long gameId, String turnName, String status);

    List<GameDto> findAll(Connection con);

    Game findById(Connection con, Long gameId, Board board);
}
