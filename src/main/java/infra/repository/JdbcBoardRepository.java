package infra.repository;

import controller.dto.CurrentBoardStatus;
import domain.Team;
import infra.dao.BoardDao;
import infra.dao.TurnDao;
import java.util.List;

public class JdbcBoardRepository implements BoardRepository {
    private final BoardDao boardDao;
    private final TurnDao turnDao;

    public JdbcBoardRepository(BoardDao boardDao, TurnDao turnDao) {
        this.boardDao = boardDao;
        this.turnDao = turnDao;
    }

    @Override
    public void save(List<CurrentBoardStatus> boardStatuses, Team currentTurn) {
        deleteAll();
        boardDao.save(boardStatuses);
        turnDao.save(currentTurn);
    }

    @Override
    public List<CurrentBoardStatus> readBoard() {
        return boardDao.findAll();
    }

    @Override
    public Team readTurn() {
        return turnDao.findAll();
    }

    @Override
    public void deleteAll() {
        boardDao.deleteAll();
        turnDao.deleteAll();
    }
}
