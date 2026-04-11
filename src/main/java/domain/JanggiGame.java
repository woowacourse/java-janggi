package domain;

import db.BoardDao;
import db.PieceDao;
import domain.dto.JanggiBoardDto;
import domain.piece.MoveablePiece;
import domain.position.Position;

public class JanggiGame {
    private final JanggiBoard janggiBoard;
    private final BoardDao boardDao;
    private final PieceDao pieceDao;
    private final long boardId;

    public JanggiGame() {
        this.janggiBoard = new JanggiBoard(new JanggiBoardInitializer());
        this.boardDao = new BoardDao();
        this.pieceDao = new PieceDao();
        this.boardId = initGame();
    }

    private long initGame() {
        long id = boardDao.create(Team.CHO.name());
        pieceDao.saveAll(id, JanggiBoardDto.from(janggiBoard));
        return id;
    }

    public void playTurn(Position from, Position to) {
        if (janggiBoard.isBlank(from)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에는 기물이 존재하지 않습니다.");
        }
        MoveablePiece currentPiece = (MoveablePiece) janggiBoard.getPiece(from);
        if (currentPiece.getTeam() != janggiBoard.getTurn()) {
            throw new IllegalArgumentException("[ERROR] 상대방의 기물을 이동할 수 없습니다.");
        }
        if (!currentPiece.canMove(from, to, janggiBoard)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치로 이동할 수 없는 기물입니다.");
        }
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
}
