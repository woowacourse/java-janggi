package domain;

import domain.piece.Piece;
import dto.PieceInfo;
import java.util.List;

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
        validateOutOfRange(selectPosition);
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
        validateOwnPieceExistsAt(selectPosition);
        if (!board.isMoveable(selectPosition, destination)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치로는 이동할 수 없습니다.");
        }
    }

    private void validateOutOfRange(Position targetPosition) {
        if (targetPosition.row() > MAX_ROW
                || targetPosition.row() < MIN_ROW
                || targetPosition.col() > MAX_COL
                || targetPosition.col() < MIN_COL) {
            throw new IllegalArgumentException("[ERROR] 장기판 범위를 벗어난 위치를 입력하셨습니다.");
        }
    }

    private void validateOwnPieceExistsAt(Position selectPosition) {
        if (currentPlayerPiecesInfo().stream().map(Piece::position).noneMatch(position -> position.equals(selectPosition))) {
            throw new IllegalArgumentException("[ERROR] 선택한 위치에는 아군 기물이 존재합니다.");
        }
    }

    public List<Piece> currentPlayerPiecesInfo() {
        if (gameStatus.equals(GameStatus.GREEN_PLAYER_TURN)) {
            return board.greenPieces();
        }
        return board.redPieces();
    }

    public List<PieceInfo> allFactors() {
        return board.allPieces().stream().map(PieceInfo::from).toList();
    }

    public PieceInfo findPieceInfoAt(Position selectPosition) {
        Piece piece = board.findPieceAt(selectPosition);
        requirePlayerPiece(piece);
        requireCorrectTurn(piece);
        return PieceInfo.from(board.findPieceAt(selectPosition));
    }

    private void requireCorrectTurn(Piece piece) {
        if(gameStatus.equals(GameStatus.GREEN_PLAYER_TURN) && piece.isRedTeam()) {
            throw new IllegalArgumentException("[ERROR] 선택한 위치에는 아군 기물이 존재하지 않습니다.");
        }

        if(gameStatus.equals(GameStatus.RED_PLAYER_TURN) && piece.isGreenTeam()) {
            throw new IllegalArgumentException("[ERROR] 선택한 위치에는 아군 기물이 존재하지 않습니다.");
        }
    }

    private void requirePlayerPiece(Piece piece) {
        if(piece.isNoneTeam()) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에는 기물이 없습니다.");
        }
    }

    public String currentPlayerTurn() {
        return gameStatus.description();
    }
}
