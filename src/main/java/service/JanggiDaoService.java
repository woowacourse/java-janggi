package service;

import dao.BoardDao;
import dao.TurnDao;
import domain.board.Point;
import domain.piece.Piece;
import domain.piece.Team;

import java.util.Map;

public class JanggiDaoService {

    private final TurnDao turnDao;
    private final BoardDao boardDao;

    public JanggiDaoService(TurnDao turnDao, BoardDao boardDao) {
        this.turnDao = turnDao;
        this.boardDao = boardDao;
    }

    public boolean hasSavedGame() {
        return boardDao.hasRecords();
    }

    public Map<Point, Piece> findBoard() {
        return boardDao.load();
    }

    public Team findTurn() {
        return turnDao.load();
    }

    public void saveAllData(Map<Point, Piece> board, Team turn) {
        removeAllData();
        turnDao.save(turn);
        for (Point point : board.keySet()) {
            boardDao.save(point, board.get(point));
        }
    }

    public void removeAllData() {
        turnDao.remove();
        boardDao.removeAll();
    }
}
