package database;

import database.connection.BoardIdContext;
import database.connection.DBConnector;
import database.dao.BoardDao;
import database.dao.IntersectionDao;
import database.dto.BoardSummaryDto;
import database.dto.GameResult;
import domain.board.BoardSelectCommand;
import domain.board.DBIntersectionGenerator;
import domain.board.JanggiBoard;
import domain.board.dto.Moved;
import domain.intersection.Intersection;
import domain.piece.Team;

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
            return boardDao.readPlayingJanggiBoardList(connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public JanggiBoard getExistBoard(BoardSelectCommand command) {
        try (Connection connection = DBConnector.getConnection()) {

            BoardSummaryDto boardSummaryDto = boardDao.readPlayingJanggiBoard(connection, command.select());
            List<Intersection> intersections = intersectionDao.readIntersectionByBoardId(connection, command.select());
            Team currentTurn = Team.valueOf(boardSummaryDto.currentTurn());

            return new JanggiBoard(new DBIntersectionGenerator(intersections), currentTurn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO 상태 패턴을 사용하여, currentTurn과 isFinished를 합치면 좋을 듯.
    public void updateTurn(Moved moved, Team currentTurn) {
        try (Connection connection = DBConnector.getConnection()) {

            Long boardId = BoardIdContext.getBoardId();

            boardDao.updateBoardTurn(connection, boardId, currentTurn);
            intersectionDao.update(connection, boardId, moved.destination());
            intersectionDao.update(connection, boardId, moved.origin());

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void updateBoardResult(GameResult gameResult) {
        try (Connection connection = DBConnector.getConnection()) {

            Long boardId = BoardIdContext.getBoardId();
            boardDao.updateBoardResult(connection, boardId, gameResult);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
