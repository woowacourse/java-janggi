package domain.board;

import db.BoardDao;
import db.PieceDao;
import domain.ScoreCalculator;
import domain.Team;
import domain.dto.JanggiBoardDto;
import domain.piece.MoveablePiece;
import domain.piece.Piece;
import domain.position.Position;

public class JanggiGame {
    private final JanggiBoard janggiBoard;
    private final BoardDao boardDao;
    private final PieceDao pieceDao;
    private final long boardId;

    public JanggiGame() {
        this.boardDao = new BoardDao();
        this.pieceDao = new PieceDao();
        this.janggiBoard = new JanggiBoard(new JanggiBoardInitializer());
        this.boardId = initGame();
    }

    public JanggiGame(long boardId) {
        this.boardDao = new BoardDao();
        this.pieceDao = new PieceDao();
        this.boardId = boardId;
        JanggiBoardDto boardDto = pieceDao.findAll(boardId);
        Team turn = Team.valueOf(boardDao.findTurn(boardId));
        this.janggiBoard = new JanggiBoard(new JanggiBoardLoader(boardDto), turn);
    }
    private long initGame() {
        long id = boardDao.create();
        pieceDao.saveAll(id, JanggiBoardDto.from(janggiBoard));
        return id;
    }

    public void playTurn(Position from, Position to) {
        validateNotBlank(from);
        MoveablePiece currentPiece = (MoveablePiece) janggiBoard.getPiece(from);
        validateCurrentPiece(currentPiece);
        validateMoveable(currentPiece, from, to);
        janggiBoard.move(from, to, currentPiece);
        pieceDao.move(boardId, from.row(), from.col(), to.row(), to.col());
        boardDao.updateTurn(boardId, janggiBoard.getTurn().name());
    }

    public boolean isGameOver() {
        if (janggiBoard.isGameOver()) {
            boardDao.finish(boardId);
            return true;
        }
        return false;
    }

    public JanggiBoardDto getBoardDto() {
        return JanggiBoardDto.from(janggiBoard);
    }

    public double calculateScore(Team team) {
        return new ScoreCalculator().calculateScore(JanggiBoardDto.from(janggiBoard), team);
    }

    private void validateNotBlank(Position from) {
        if (janggiBoard.isBlank(from)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에는 기물이 존재하지 않습니다.");
        }
    }

    private void validateCurrentPiece(Piece currentPiece) {
        if (currentPiece.getTeam() != janggiBoard.getTurn()) {
            throw new IllegalArgumentException("[ERROR] 상대방의 기물을 이동할 수 없습니다.");
        }
    }

    private void validateMoveable(MoveablePiece currentPiece, Position from, Position to) {
        if (!currentPiece.canMove(from, to, janggiBoard)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치로 이동할 수 없는 기물입니다.");
        }
    }
}
