package domain;

import domain.piece.Piece;
import dto.BoardDTO;
import java.util.List;
import message.ErrorMessage;

public class JanggiGame {

    private static final int MAX_ROW = 9;
    private static final int MIN_ROW = 0;
    private static final int MAX_COL = 8;
    private static final int MIN_COL = 0;

    private final Board board;
    private GameStatus gameStatus;

    public JanggiGame(Board board) {
        this.board = board;
        this.gameStatus = GameStatus.GREEN_PLAYER_TURN;
    }

    public void move(Position selectPosition, Position destination) {
        validateDestinationSelection(selectPosition, destination);
        board.movePiece(selectPosition, destination);
        gameStatus = gameStatus.changePlayerTurn();
    }

    public void validatePieceSelection(Position selectPosition) {
        validateOutOfRange(selectPosition);
        validateOwnPieceExistsAt(selectPosition);
    }

    private void validateDestinationSelection(Position selectPosition, Position destination) {
        validateOutOfRange(destination);
        if (!board.isMoveable(selectPosition, destination)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateOwnPieceExistsAt(Position selectPosition) {
        if (playerPiecesInfo().stream().map(Piece::position).noneMatch(position -> position.equals(selectPosition))) {
            throw new IllegalArgumentException(ErrorMessage.NOT_SAME_TEAM_PIECE.getMessage());
        }
    }

    private void validateOutOfRange(Position position) {
        if (position.row() > MAX_ROW || position.row() < MIN_ROW || position.col() > MAX_COL
                || position.col() < MIN_COL) {
            throw new IllegalArgumentException(ErrorMessage.OUT_OF_RANGE_JANGGI_BOARD.getMessage());
        }
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

    private List<Piece> playerPiecesInfo() {
        if (gameStatus.equals(GameStatus.GREEN_PLAYER_TURN)) {
            return board.greenPieces();
        }

        return board.redPieces();
    }

    public BoardDTO allFactors() {
        return new BoardDTO(board.getBoard());
    }

}
