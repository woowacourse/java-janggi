package janggi.service;

import static janggi.controller.JanggiGameController.FIRST_TURN;

import janggi.domain.board.Board;
import janggi.domain.board.InitialBoardGenerator;
import janggi.dao.BoardDao;
import janggi.dao.BoardVO;
import janggi.dao.PieceDao;
import janggi.db.TransactionManager;
import janggi.exception.DataAccessException;
import janggi.domain.piece.Piece;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BoardService {

    private final BoardDao boardDao;
    private final PieceDao pieceDao;
    private final TransactionManager transactionManager;
    private Long lastCreatedBoardId;

    public BoardService(BoardDao boardDao, PieceDao pieceDao, TransactionManager transactionManager) {
        this.boardDao = boardDao;
        this.pieceDao = pieceDao;
        this.transactionManager = transactionManager;
    }

    public Board createNewBoard() {
        return transactionManager.performTransaction(() -> {
            InitialBoardGenerator initialBoardGenerator = new InitialBoardGenerator();
            Board board = initialBoardGenerator.generate(FIRST_TURN);
            BoardVO boardVO = BoardVO.of(board, UUID.randomUUID().toString());
            Long boardId = boardDao.create(boardVO);
            pieceDao.saveAll(boardId, board.getCells());
            lastCreatedBoardId = boardId;
            return board;
        });
    }

    public Long getLastCreatedBoardId() {
        if (lastCreatedBoardId == null) {
            throw new DataAccessException("아직 생성된 board가 없습니다.");
        }
        return lastCreatedBoardId;
    }

    public void movePiecesWithTransaction(Board board, Movement movement, Long boardId) {
        transactionManager.performTransaction(() -> {
            board.move(movement);
            pieceDao.saveAll(boardId, board.getCells());
            boardDao.updateCurrentCamp(boardId, board.getCurrentCamp().name());
            return null;
        });
    }

    public Board getBoardRecordWithTransaction(Long boardId) {
        return transactionManager.performTransaction(() -> {
            BoardVO boardVO = boardDao.findById(boardId)
                    .orElseThrow(() -> new DataAccessException("해당 ID의 Board를 찾을 수 없습니다."));
            Map<Position, Piece> pieces = pieceDao.findAllByBoardId(boardId);
            return boardVO.toBoard(pieces);
        });
    }

    public List<Long> getAllBoardIds() {
        return transactionManager.performTransaction(boardDao::findAllBoardIds);
    }
}
