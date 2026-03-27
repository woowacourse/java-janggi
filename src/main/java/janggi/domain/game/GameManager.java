package janggi.domain.game;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.dto.BoardDTO;
import janggi.dto.PieceDTO;
import janggi.dto.PositionDTO;
import java.util.List;
import java.util.Map;

public class GameManager {

    private final Turn turn;
    private final Players players;
    private final Board board;

    public GameManager(Turn initiativeTurn, Players players, Board board) {
        this.turn = initiativeTurn;
        this.players = players;
        this.board = board;
    }

    public void switchTurn() {
        turn.switchTurn();
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
            return players.isCurrentSidePiece(turn, selectedPiece);
        }
        return false;
    }

    public List<Position> findDestinations(Position selectedPosition) {
        return board.calculateDestinations(selectedPosition);
    }

    public boolean isPieceExist(Position position) {
        return board.isPieceExist(position);
    }

    public BoardDTO boardStatus() {
        Map<PositionDTO, PieceDTO> mappedState =
                board.exportBoardState(PositionDTO::new, PieceDTO::new);

        return new BoardDTO(mappedState);
    }

    public void movePiece(Position selected, Position target, List<Position> destinations) {
        board.movePiece(selected, target, destinations);
    }
}
