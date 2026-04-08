package domain.game;

import constant.BoardSpec;
import domain.board.Board;
import domain.piece.Piece;
import domain.piece.Side;
import domain.vo.Position;
import java.util.List;
import java.util.Map;

public class Game {

    private final Board board;
    private final Turn turn;

    public Game(Board board) {
        this.board = board;
        this.turn = new Turn(BoardSpec.DEFAULT_STARTING_SIDE);
    }

    public void move(Position sourcePosition, Position targetPosition) {
        validateMovement(sourcePosition, targetPosition);
        board.movePiece(sourcePosition, targetPosition);
        if (!isGameEnd()) {
            turn.next();
        }
    }

    public boolean isGameEnd() {
        return board.hasKing(Side.CHO) || board.hasKing(Side.HAN);
    }

    public Side getCurrentTurn() {
        return turn.current();
    }

    public Map<Position, Piece> getBoard() {
        return board.getBoard();
    }

    public double calculateTotalScore(Side side) {
        return board.calculateTotalScore(side);
    }

    private void validateMovement(Position sourcePosition, Position targetPosition) {
        Piece sourcePiece = board.getPiece(sourcePosition);
        Piece targetPiece = board.getPiece(targetPosition);
        sourcePiece.validateMovement(turn.current(), targetPiece);
        List<Position> route = sourcePiece.findRoute(sourcePosition, targetPosition);
        List<Piece> pieces = board.findPiecesOnRoute(route, targetPosition);
        sourcePiece.checkRoute(pieces);
    }
}
