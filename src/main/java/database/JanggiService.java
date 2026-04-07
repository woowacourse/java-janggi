package database;

import database.connection.DBConnector;
import database.dao.BoardDao;
import database.dao.IntersectionDao;
import database.dto.BoardSummaryDto;
import domain.board.BoardSelectCommand;
import domain.board.DBIntersectionGenerator;
import domain.board.JanggiBoard;
import domain.intersection.Intersection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JanggiService {

    private final BoardDao boardDao;
    private final IntersectionDao intersectionDao;

    public JanggiService(BoardDao boardDao, IntersectionDao intersectionDao) {
        this.boardDao = boardDao;
        this.intersectionDao = intersectionDao;
    }

    public Long createBoard(JanggiBoard janggiBoard) {
        try (Connection connection = DBConnector.getConnection()) {
            Long saveId = boardDao.save(connection);
            intersectionDao.saveAllIntersection(connection, saveId, janggiBoard.getListIntersection());
            return saveId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<BoardSummaryDto> readExistPlayingBoard() {
        try (Connection connection = DBConnector.getConnection()) {
            return boardDao.readPlayingJanggiBoard(connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public JanggiBoard getExistBoard(BoardSelectCommand command) {
        try (Connection connection = DBConnector.getConnection()) {
            List<Intersection> intersections = intersectionDao.readIntersectionByBoardId(connection, command.select());
            return new JanggiBoard(new DBIntersectionGenerator(intersections));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
