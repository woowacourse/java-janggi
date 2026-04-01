package domain;

import dto.BoardDto;
import java.util.Map;
import message.ErrorMessage;

public class JanggiGame {

    private static final int MAX_ROW = 9;
    private static final int MIN_ROW = 0;
    private static final int MAX_COL = 8;
    private static final int MIN_COL = 0;

    private final Board board;
    private GameStatus gameStatus;

    private JanggiGame(Board board) {
        this.board = board;
        this.gameStatus = GameStatus.GREEN_PLAYER_TURN;
    }

    public static JanggiGame of(Board board) {
        return new JanggiGame(board);
    }

    public void move(Position selectedPosition, Position destination) {
        validateMove(selectedPosition, destination);
        board.movePiece(selectedPosition, destination);
        gameStatus = gameStatus.changePlayerTurn();
    }

    public void validatePieceSelection(Position selectedPosition) {
        validateBoardRange(selectedPosition);
        validateOwnPieceExistsAt(selectedPosition);
    }

    public void checkGameFinished() {
        if (!board.hasGreenTeamGeneral()) {
            gameStatus = GameStatus.RED_TEAM_WIN;
        }

        if (!board.hasRedTeamGeneral()) {
            gameStatus = GameStatus.GREEN_TEAM_WIN;
        }
    }

    public boolean isGameFinished() {
        return this.gameStatus.isFinished();
    }

    public String gameStatus() {
        return this.gameStatus.description();
    }

    public BoardDto allFactors() {
        return new BoardDto(board.board());
    }

    private void validateMove(Position selectedPosition, Position destination) {
        validateBoardRange(destination);
        validateMoveable(selectedPosition, destination);
    }

    private void validateBoardRange(Position position) {
        if (isOutOfRange(position)) {
            throw new IllegalArgumentException(ErrorMessage.OUT_OF_RANGE_JANGGI_BOARD.getMessage());
        }
    }

    private static boolean isOutOfRange(Position position) {
        return (position.row() > MAX_ROW || position.row() < MIN_ROW) ||
                (position.col() > MAX_COL || position.col() < MIN_COL);
    }

    private void validateMoveable(Position selectedPosition, Position destination) {
        if (!board.canMove(selectedPosition, destination)) {
            throw new IllegalArgumentException(ErrorMessage.CAN_NOT_MOVE_DESTINATION.getMessage());
        }
    }

    private void validateOwnPieceExistsAt(Position selectedPosition) {
        boolean hasOwnPiece = currentTurnPieces().containsKey(selectedPosition);

        if (!hasOwnPiece) {
            throw new IllegalArgumentException(ErrorMessage.NOT_SAME_TEAM_PIECE.getMessage());
        }
    }

    private Map<Position, Piece> currentTurnPieces() {
        if (gameStatus.equals(GameStatus.GREEN_PLAYER_TURN)) {
            return board.greenPieces();
        }

        return board.redPieces();
    }

}
