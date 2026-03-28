package domain;

import domain.piece.Piece;
import java.util.List;
import java.util.Map;

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

    public void validatePieceSelection(Position selectPosition) {
        validateOutOfRange(selectPosition);
        validateOwnPieceExistsAt(selectPosition);
    }

    public void move(Position selectPosition, Position destination) {
        validateDestinationSelection(selectPosition, destination);
        board.movePiece(selectPosition, destination);
        gameStatus = gameStatus.changePlayerTurn();
    }

    public void checkGameFinished() {
        if(!board.hasGreenTeamGeneral()) {
            gameStatus = GameStatus.RED_TEAM_WIN;
        }

        if(!board.hasRedTeamGeneral()) {
            gameStatus = GameStatus.GREEN_TEAM_WIN;
        }
    }

    public boolean isGameFinished() {
        return this.gameStatus.isFinished();
    }

    public String gameStatus() {
        return this.gameStatus.description();
    }

    private void validateDestinationSelection(Position selectPosition, Position destination) {
        validateOutOfRange(destination);
        if (!board.isMoveable(selectPosition, destination)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateOutOfRange(Position targetPosition) {
        if (targetPosition.row() > MAX_ROW || targetPosition.row() < MIN_ROW || targetPosition.col() > MAX_COL
                || targetPosition.col() < MIN_COL) {
            throw new IllegalArgumentException();
        }
    }

    private void validateOwnPieceExistsAt(Position selectPosition) {
        if (playerPiecesInfo().stream().map(Piece::position).noneMatch(position -> position.equals(selectPosition))) {
            throw new IllegalArgumentException();
        }
    }

    public List<Piece> playerPiecesInfo() {
        if (gameStatus.equals(GameStatus.GREEN_PLAYER_TURN)) {
            return board.greenPieces();
        }

        return board.redPieces();
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board.getBoard());
    }
}
