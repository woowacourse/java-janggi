package database.service;

import database.context.BoardIdContext;
import database.transaction.TransactionExecutor;
import database.dao.BoardDao;
import database.dao.IntersectionDao;
import database.dto.BoardSummaryDto;
import database.dto.GameResult;
import database.mapper.JanggiBoardMapper;
import domain.board.BoardSelectCommand;
import domain.board.DBIntersectionGenerator;
import domain.board.JanggiBoard;
import database.dto.Moved;
import domain.intersection.Intersection;
import domain.piece.Team;

import java.util.List;

public class JanggiService {

    private final BoardDao boardDao;
    private final JanggiBoardMapper mapper;
    private final TransactionExecutor executor;
    private final IntersectionDao intersectionDao;

    public JanggiService(BoardDao boardDao, JanggiBoardMapper mapper, TransactionExecutor executor, IntersectionDao intersectionDao) {
        this.boardDao = boardDao;
        this.mapper = mapper;
        this.executor = executor;
        this.intersectionDao = intersectionDao;
    }

    public Long createBoard(JanggiBoard janggiBoard) {
        return executor.execute(() -> {
            Long saveId = boardDao.save();
            intersectionDao.saveAll(saveId, mapper.toIntersectionDtoList(janggiBoard.getListIntersection()));
            return saveId;
        });
    }

    public List<BoardSummaryDto> readExistPlayingBoard() {
        return executor.execute(boardDao::readAllNotFinished);
    }

    public JanggiBoard getExistBoard(BoardSelectCommand command) {
        return executor.execute(()->{
            BoardSummaryDto boardSummaryDto = boardDao.readPlayingById(command.select())
                    .orElseThrow(() -> new IllegalArgumentException("해당 ID의 진행 중인 게임을 찾을 수 없습니다."));
            List<Intersection> intersections = intersectionDao.readByBoardId(command.select());
            Team currentTurn = Team.valueOf(boardSummaryDto.currentTurn());
            return new JanggiBoard(new DBIntersectionGenerator(intersections), currentTurn);
        });
    }

    // TODO 상태 패턴을 사용하여, currentTurn과 isFinished를 합치면 좋을 듯.
    public void updateTurn(Moved moved, Team currentTurn) {
        executor.execute(() -> {
            Long boardId = BoardIdContext.getBoardId();
            boardDao.updateTurn(boardId, currentTurn);
            intersectionDao.update(boardId, mapper.toIntersectionDto(moved.destination()));
            intersectionDao.update(boardId, mapper.toIntersectionDto(moved.origin()));
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

}
