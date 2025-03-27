package domain.game;

import domain.board.Board;
import domain.board.BoardLocation;
import domain.board.PieceExtractor;
import domain.board.PieceFinder;
import domain.piece.Piece;

public class JanggiGame {
    private final Board board;
    private final Turn turn;

    public JanggiGame(Board board, Turn turn) {
        this.board = board;
        this.turn = turn;
    }

    public void process(BoardLocation current, BoardLocation destination) {
        Piece piece = board.getByLocationOrThrow(current);
        piece.validateEqualTeam(turn.getTeam());
        validateMovable(current, destination, piece);
        board.occupy(current, destination);
        turn.opposite();
    }

    public Board getBoard() {
        return board;
    }

    public Turn getTurn() {
        return turn;
    }

    private void validateMovable(BoardLocation current, BoardLocation destination, Piece piece){
        PieceExtractor pieceExtractor = board::extractPathPiece;
        PieceFinder pieceFinder = board::findByLocation;
        piece.validateMovable(current, destination, pieceExtractor, pieceFinder);
    }
}
