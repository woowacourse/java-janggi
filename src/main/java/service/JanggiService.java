package service;

import domain.board.context.BoardIdContext;
import database.transaction.TransactionExecutor;
import database.dao.BoardDao;
import database.dao.IntersectionDao;
import database.dto.BoardSummaryDto;
import database.dto.GameResult;
import domain.board.generator.DBIntersectionGenerator;
import domain.board.JanggiBoard;
import service.dto.Moved;
import domain.board.exception.BoardException;
import domain.intersection.Intersection;
import domain.piece.Team;

import java.util.List;

import static domain.board.exception.BoardError.BOARD_NOT_FOUND;

public class JanggiService {

    private final BoardDao boardDao;
    private final TransactionExecutor executor;
    private final IntersectionDao intersectionDao;

    public JanggiService(BoardDao boardDao, TransactionExecutor executor, IntersectionDao intersectionDao) {
        this.boardDao = boardDao;
        this.executor = executor;
        this.intersectionDao = intersectionDao;
    }

    public Long createBoard(JanggiBoard janggiBoard) {
        return executor.execute(() -> {
            Long saveId = boardDao.save();
            intersectionDao.saveAll(saveId, janggiBoard.getListIntersection());
            return saveId;
        });
    }

    public List<BoardSummaryDto> readExistPlayingBoard() {
        return executor.execute(boardDao::readAllNotFinished);
    }

    public JanggiBoard getExistBoard(Long boardId) {
        return executor.execute(() -> {
            BoardSummaryDto boardSummaryDto = readBoardSummaryDto(boardId);
            List<Intersection> intersections = intersectionDao.selectIntersections(boardId);
            Team currentTurn = Team.valueOf(boardSummaryDto.currentTurn());
            return new JanggiBoard(new DBIntersectionGenerator(intersections), currentTurn);
        });
    }

    public void updateTurn(Moved moved) {
        executor.execute(() -> {
            Long boardId = BoardIdContext.getBoardId();
            validateBoardIsExist(boardId);
            boardDao.updateTurn(boardId, moved.currentTurn());
            intersectionDao.update(boardId, moved.destination());
            intersectionDao.update(boardId, moved.origin());
            return null;
        });
    }

    public void updateBoardResult(GameResult gameResult) {
        executor.execute(() -> {
            Long boardId = BoardIdContext.getBoardId();
            boardDao.updateResult(boardId, gameResult);
            return null;
        });
    }

    private BoardSummaryDto readBoardSummaryDto(Long boardId) {
        return boardDao.readPlayingById(boardId)
                .orElseThrow(() -> new BoardException(BOARD_NOT_FOUND.getMessage()));
    }

    private void validateBoardIsExist(Long boardId) {
        if (!boardDao.existsById(boardId)) {
            throw new BoardException(BOARD_NOT_FOUND.getMessage());
        }
    }

}
