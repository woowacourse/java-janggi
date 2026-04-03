package janggi.domain.game;

import janggi.domain.board.Board;
import janggi.domain.board.Destinations;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceMapper;
import java.util.Map;
import java.util.function.BiFunction;

public class GameManager {

    private final Players players;
    private final Board board;
    private Turn turn;

    public GameManager(Players players, Board board, Turn initiativeTurn) {
        this.players = players;
        this.board = board;
        this.turn = initiativeTurn;
    }

    public void switchTurn() {
        turn = turn.next();
    }

    public boolean isFinished() {
        return !board.isBothPalaceExist();
    }

    public Player currentPlayer() {
        return players.currentPlayer(turn);
    }

    public boolean isThereMoveablePiece(Position selectedPosition) {
        if (board.isPieceExist(selectedPosition)) {
            Piece selectedPiece = board.findPieceBy(selectedPosition);
            return players.isCurrentSidePiece(turn, selectedPiece) && board.isMoveablePiece(selectedPosition);
        }
        return false;
    }

    public Destinations findDestinations(Position selectedPosition) {
        return board.moveablePositions(selectedPosition);
    }

    public boolean isPieceExist(Position position) {
        return board.isPieceExist(position);
    }

    public <K, V> Map<K, V> exportBoardState(BiFunction<Integer, Integer, K> positionMapper,
                                             PieceMapper<V> pieceMapper) {
        return board.exportBoardState(positionMapper, pieceMapper);
    }

    public void movePiece(Position selected, Position target, Destinations destinations) {
        board.movePiece(selected, target, destinations);
    }
}
