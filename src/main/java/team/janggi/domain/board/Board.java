package team.janggi.domain.board;

import java.util.Map;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.piece.Piece;

public class Board {
    private final BoardStatus boardStatus;
    private final BoardFactory boardFactory;

    public Board(BoardFactory boardFactory) {
        this(new LocalMemoryBoardStatus(), boardFactory);
    }

    public Board(BoardStatus boardStatus, BoardFactory boardFactory) {
        this.boardStatus = boardStatus;
        this.boardFactory = boardFactory;
    }

    public Board(Map<Position, Piece> boardPiece, BoardFactory boardFactory) {
        this(new LocalMemoryBoardStatus(boardPiece), boardFactory);
    }

    public void initBoard() {
        boardFactory.initBoardStatus(boardStatus);
    }

    public Map<Position, Piece> getStatus() {
        return boardStatus.getBoardStatus();
    }

    public void move(Team team, Position from, Position to) {
        boardStatus.movePiece(team, from, to);
    }

    public boolean isKingDisappeared() {
        return boardStatus.isKingDisappeared();
    }

    public double getScore(Team team) {
        return boardStatus.getScore(team);
    }
}
