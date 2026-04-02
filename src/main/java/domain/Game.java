package domain;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.board.Side;
import domain.coordinate.Position;
import domain.piece.Piece;

import java.util.List;
import java.util.Map;

public class Game {

    private final Board board;
    private Side turn;

    public Game(BoardInitializer boardInitializer) {
        this.board = new Board(boardInitializer.initialize());
        this.turn = boardInitializer.getFirstTurnSide();
    }

    public void movePiece(Position start, Position destination) {
        board.movePiece(start, destination);
        changeTurn();
    }

    public Position getValidatedStartPosition(Position position) {
        position.validateRange();
        validateEnsureSameSidePiece(position);
        validateMovable(position);
        return position;
    }

    public List<Position> getPossibleMoves(Position start) {
        return board.calculatePossibleMoves(start);
    }

    public Position getEndPosition(int index, List<Position> possibleMoves) {
        return possibleMoves.get(index);
    }

    private void validateEnsureSameSidePiece(Position start) {
        if (board.isOpponentSide(start, turn)) {
            throw new IllegalArgumentException("\n아군 기물만 이동 가능합니다. 다시 입력해주세요.");
        }
    }

    private void validateMovable(Position position) {
        List<Position> possibleMoves = getPossibleMoves(position);

        if (possibleMoves.isEmpty()) {
            throw new IllegalArgumentException("\n해당 기물은 움직일 수 있는 좌표가 없습니다. 다른 기물을 선택해주세요.");
        }
    }

    private void changeTurn() {
        turn = turn.change();
    }

    public Side getTurn() {
        return turn;
    }

    public Map<Position, Piece> getBoard() {
        return board.getBoard();
    }
}
