package domain;

import dto.BoardDto;
import java.util.List;
import java.util.Map;

public class JanggiGame {

    private static final int MAX_ROW = 9;
    private static final int MIN_ROW = 0;
    private static final int MAX_COL = 8;
    private static final int MIN_COL = 0;

    private static final String OUT_OF_RANGE_JANGGI_BOARD = "[ERROR] 장기판 범위를 벗어났습니다.";
    private static final String CANNOT_MOVE_TO_SAME_POSITION = "[ERROR] 기물은 제자리 이동이 불가능합니다.";
    private static final String CAN_NOT_MOVE_DESTINATION = "[ERROR] 기물이 목적지에 도달할 수 없습니다.";
    private static final String NOT_SAME_TEAM_PIECE = "[ERROR] 본인의 기물이 아닙니다.";

    private final Board board;
    private GameStatus gameStatus;

    private JanggiGame(Board board, GameStatus gameStatus) {
        this.board = board;
        this.gameStatus = gameStatus;
    }

    public static JanggiGame of(Board board, GameStatus gameStatus) {
        return new JanggiGame(board, gameStatus);
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

    public List<Double> showTeamPieceScores() {
        return List.of(board.greenPiecesScore(), board.redPiecesScore());
    }

    public GameStatus gameStatus() {
        return gameStatus;
    }

    public BoardDto allFactors() {
        return new BoardDto(board.board());
    }

    public Board board() {
        return board;
    }

    private void validateMove(Position selectedPosition, Position destination) {
        validateBoardRange(destination);
        validateDifferentSelectedPositionAndDestination(selectedPosition, destination);
        validateMoveable(selectedPosition, destination);
    }

    private void validateBoardRange(Position position) {
        if (isOutOfRange(position)) {
            throw new IllegalArgumentException(OUT_OF_RANGE_JANGGI_BOARD);
        }
    }

    private static boolean isOutOfRange(Position position) {
        return (position.row() > MAX_ROW || position.row() < MIN_ROW) ||
                (position.col() > MAX_COL || position.col() < MIN_COL);
    }

    private void validateDifferentSelectedPositionAndDestination(Position selectedPosition, Position destination) {
        if (selectedPosition.equals(destination)) {
            throw new IllegalArgumentException(CANNOT_MOVE_TO_SAME_POSITION);
        }
    }

    private void validateMoveable(Position selectedPosition, Position destination) {
        if (!board.canMove(selectedPosition, destination)) {
            throw new IllegalArgumentException(CAN_NOT_MOVE_DESTINATION);
        }
    }

    private void validateOwnPieceExistsAt(Position selectedPosition) {
        boolean hasOwnPiece = currentTurnPieces().containsKey(selectedPosition);

        if (!hasOwnPiece) {
            throw new IllegalArgumentException(NOT_SAME_TEAM_PIECE);
        }
    }

    private Map<Position, Piece> currentTurnPieces() {
        if (gameStatus.equals(GameStatus.GREEN_PLAYER_TURN)) {
            return board.greenPieces();
        }

        return board.redPieces();
    }

}
