package domain;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.coordinate.Position;
import domain.piece.Piece;
import dto.PossibleMovesDto;

import java.util.List;
import java.util.Map;

public class Game {

    private final Board board;
    private Side turn;

    public Game(BoardInitializer boardInitializer) {
        this.board = new Board(boardInitializer.initialize());
        this.turn = boardInitializer.getFirstTurnSide();
    }

    public Piece getPiece(Position position) {
        validateStartPosition(position);
        return board.getPiece(position);
    }

    public boolean isAvailableDestination(Position destination) {
        if (!board.isValidRange(destination)) {
            return false;
        }

        return isOpponentOrEmptyPiece(destination);
    }

    public boolean isOpponentPiece(Position position) {
        return isOpponentOrEmptyPiece(position) && !board.isEmpty(position);
    }

    public boolean isCannon(Position position) {
        return !board.isValidRange(position) || board.isCannon(position);
    }

    public boolean isNotEmpty(Position position) {
        return !board.isEmpty(position);
    }

    public void validateStartPosition(Position start) {
        board.validateRange(start);
        validateCurrentTurnPiece(start);
    }

    public List<Position> getPossibleMoves(Position startPosition) {
        Piece piece = getPiece(startPosition);
        List<Position> possibleMoves = piece.getPossibleMoves(this, startPosition);
        validateDoesNotMoves(possibleMoves);
        return possibleMoves;
    }

    public void movePiece(Position start, Position destination) {
        validateCurrentTurnPiece(start);
        board.move(start, destination);
        changeTurn();
    }

    public Position getDestination(int index, PossibleMovesDto possibleMovesDto) {
        return possibleMovesDto.getPossibleMoves().get(index - 1);
    }

    private void validateDoesNotMoves(List<Position> possibleMoves) {
        if (possibleMoves.isEmpty()) {
            throw new IllegalArgumentException("\n해당 기물은 움직일 수 있는 좌표가 없습니다. 다른 기물을 선택해주세요.\n");
        }
    }

    private boolean isFriendlyPiece(Position position) {
        return board.getPiece(position).isFriendly(turn);
    }

    private boolean isOpponentOrEmptyPiece(Position position) {
        return !isFriendlyPiece(position);
    }

    private void validateCurrentTurnPiece(Position start) {
        if (isOpponentOrEmptyPiece(start)) {
            throw new IllegalArgumentException("아군 기물만 이동 가능합니다. 다시 입력해주세요.");
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
