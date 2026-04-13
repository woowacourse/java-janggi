package janggi.domain.game;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.camp.CampType;
import janggi.dto.MoveResultDto;
import java.time.LocalDateTime;
import java.util.Map;

public class Game {

    private final GameRoom gameRoom;

    private final Board board;

    public Game(GameRoom gameRoom, Board board) {
        this.gameRoom = gameRoom;
        this.board = board;
    }

    public boolean isFinished() {
        return gameRoom.getGameStatus() != GameStatus.PLAYING;
    }

    public MoveResultDto move(Position source, Position destination) {
        MoveResultDto moveResultDto = board.movePiece(source, destination, gameRoom.getCurrentTurn());
        processAfterMove();
        return moveResultDto;
    }

    private void processAfterMove() {
        if (board.isGeneralKilled(gameRoom.getCurrentTurn().next())) {
            gameRoom.finish();
            return;
        }
        gameRoom.changeTurn(gameRoom.getCurrentTurn().next());
    }

    public long getGameRoomId() {
        return gameRoom.getGameRoomId();
    }

    public CampType getCurrentTurn() {
        return gameRoom.getCurrentTurn();
    }

    public GameStatus getGameStatus() {
        return gameRoom.getGameStatus();
    }

    public LocalDateTime getStartAt() {
        return gameRoom.getStartAt();
    }

    public LocalDateTime getEndAt() {
        return gameRoom.getEndAt();
    }

    public LocalDateTime getLastUpdatedAt() {
        return gameRoom.getLastUpdatedAt();
    }

    public Board getBoard() {
        return board;
    }

    public Map<Position, Piece> getPiecePositions() {
        return board.getBoard();
    }

    public Map<CampType, Double> getScoreBoard() {
        return board.getScoreBoard();
    }
}
