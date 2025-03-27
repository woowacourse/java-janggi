package domain.game;

import domain.board.Board;
import domain.board.BoardLocation;
import domain.board.PieceExtractor;
import domain.board.PieceFinder;
import domain.piece.Piece;
import domain.piece.Team;

public class JanggiGame {
    private final Board board;
    private final Team team;

    public JanggiGame(Board board, Team team) {
        this.board = board;
        this.team = team;
    }

    public void process(BoardLocation current, BoardLocation destination) {
        Piece piece = board.getByLocationOrThrow(current);
        piece.validateEqualTeam(team);
        validateMovable(current, destination, piece);
        board.occupy(current, destination);
    }

    public Team opposite(Team team) {
        return team.opposite();
    }

    public Board getBoard() {
        return board;
    }

    public Team getTeam() {
        return team;
    }

    private void validateMovable(BoardLocation current, BoardLocation destination, Piece piece){
        PieceExtractor pieceExtractor = board::extractPathPiece;
        PieceFinder pieceFinder = board::findByLocation;
        piece.validateMovable(current, destination, pieceExtractor, pieceFinder);
    }
}
