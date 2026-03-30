package domain;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.board.Side;
import domain.coordinate.Position;
import domain.piece.Piece;
import dto.PossibleMovesDto;

import java.util.List;

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

    public Piece getPiece(Position position) {
        return board.getPiece(position);
    }

    public Position getValidatedStartPosition(Position position) {
        board.validateStartPosition(position, turn);
        validateMovable(position);
        return position;
    }

    public List<Position> getPossibleMoves(Position start) {
        Piece piece = getPiece(start);
        return piece.getPossibleMoves(this, start);
    }

    public Position getEndPosition(int index, PossibleMovesDto possibleMovesDto) {
        return possibleMovesDto.getPossibleMoves().get(index - 1);
    }

    private void validateMovable(Position position) {
        List<Position> possibleMoves = getPossibleMoves(position);

        if (possibleMoves.isEmpty()) {
            throw new IllegalArgumentException("\n해당 기물은 움직일 수 있는 좌표가 없습니다. 다른 기물을 선택해주세요.\n");
        }
    }

    private void changeTurn() {
        turn = turn.change();
    }

    public Side getTurn() {
        return turn;
    }

    public Board getBoard() {
        return board;
    }
}
