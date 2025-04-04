package game;

import domain.Board;
import domain.chesspiece.ChessPiece;
import domain.position.ChessPosition;
import domain.score.Score;
import domain.type.ChessTeam;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Janggi {

    private final Board board;
    private Turn turn;

    public Janggi(final Board board, final Turn turn) {
        this.board = board;
        this.turn = turn;
    }

    public void processTurn(final ChessPosition from, final ChessPosition to) {
        final List<ChessPosition> availableDestinations = getAvailableDestinations(from);
        board.validateToPosition(availableDestinations, to);
        board.move(from, to);
        this.turn = turn.change();
    }

    public void validateFromPosition(final ChessPosition from) {
        board.validateExistPieceByPosition(from);
        board.validateCurrentTeam(from, turn.getTeam());
    }

    public List<ChessPosition> getAvailableDestinations(final ChessPosition from) {
        final ChessPiece chessPiece = board.getChessPieceByPosition(from);
        return board.getAvailableDestinations(from, chessPiece);
    }

    public Score calculateScoreByTeam(final ChessTeam team) {
        return board.calculateScoreByTeam(team);
    }

    public ChessTeam getCurrentTeam() {
        return turn.getTeam();
    }

    public Map<ChessPosition, ChessPiece> getChessPiecesMapView() {
        return board.getChessPieces()
                .stream()
                .collect(Collectors.toMap(
                        ChessPiece::getPosition,
                        chessPiece -> chessPiece,
                        (newValue, oldValue) -> newValue
                ));
    }

}
