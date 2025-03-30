package application.persistence;

import domain.board.Board;

public interface BoardRepository {

    void saveAll(Board board);

    Board findAll();

    void deleteAll();
}
