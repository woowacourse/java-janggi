package infrastructure.repository;

import application.persistence.BoardRepository;
import domain.Coordinate;
import domain.board.Board;
import domain.piece.Piece;
import infrastructure.dao.BoardDao;
import infrastructure.entity.BoardEntity;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardJdbcRepository implements BoardRepository {

    private final BoardDao boardDao;

    public BoardJdbcRepository(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    @Override
    public void saveAll(Board board) {
        List<BoardEntity> boardEntities = BoardEntity.from(board);
        boardDao.save(boardEntities);
    }

    @Override
    public Board findAll() {
        List<BoardEntity> boardEntities = boardDao.findAll();

        Map<Coordinate, Piece> pieces = boardEntities.stream()
                .map(BoardEntity::toDomain)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return new Board(pieces);
    }

    @Override
    public void deleteAll() {
        boardDao.deleteAll();
    }
}
