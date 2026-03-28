package domain;

import dto.PieceInfo;
import java.util.List;

public class JanggiGame {

    private static final int MAX_ROW = 9;
    private static final int MIN_ROW = 0;
    private static final int MAX_COL = 8;
    private static final int MIN_COL = 0;

    private final Board board;
    private GameStatus gameStatus;

    public JanggiGame(Board board, GameStatus gameStatus) {
        this.board = board;
        this.gameStatus = gameStatus;
    }

    public void move(Position selectPosition, Position destination) {
        validateMove(selectPosition, destination);

        board.movePiece(selectPosition, destination);
        shiftGameStatus();
    }

    public boolean isGameFinished() {
        return this.gameStatus.isFinished();
    }

    public String gameStatus() {
        return this.gameStatus.description();
    }

    public PieceInfo findPieceInfoAt(Position selectPosition) {
        validateOutOfRange(selectPosition);

        Piece piece = board.findPieceAt(selectPosition);

        requireExists(piece);
        requireCorrectTurn(piece);

        return PieceInfo.from(piece);
    }

    public String currentPlayerTurn() {
        return gameStatus.description();
    }

    public List<PieceInfo> allFactors() {
        return board.allPieces().stream()
                .map(PieceInfo::from)
                .toList();
    }

    private void validateMove(Position select, Position destination) {
        validateOutOfRange(select);
        validateOutOfRange(destination);

        requireReachable(select, destination);
    }

    private void validateOutOfRange(Position position) {
        if (position.row() > MAX_ROW
                || position.row() < MIN_ROW
                || position.col() > MAX_COL
                || position.col() < MIN_COL) {
            throw new IllegalArgumentException("[ERROR] 장기판 범위를 벗어난 위치를 입력하셨습니다.");
        }
    }

    private void requireReachable(Position select, Position destination) {
        if (!board.isMoveable(select, destination)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치로는 이동할 수 없습니다.");
        }
    }

    private void shiftGameStatus() {
        gameStatus = gameStatus.changePlayerTurn();
        checkGameFinished();
    }

    private void checkGameFinished() {
        if(!board.hasGreenTeamGeneral()) {
            gameStatus = GameStatus.RED_TEAM_WIN;
        }

        if(!board.hasRedTeamGeneral()) {
            gameStatus = GameStatus.GREEN_TEAM_WIN;
        }
    }

    private void requireExists(Piece piece) {
        if(piece.isNoneTeam()) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에는 기물이 없습니다.");
        }
    }

    private void requireCorrectTurn(Piece piece) {
        if(gameStatus.equals(GameStatus.GREEN_PLAYER_TURN) && piece.isRedTeam()) {
            throw new IllegalArgumentException("[ERROR] 선택한 위치에는 아군 기물이 존재하지 않습니다.");
        }

        if(gameStatus.equals(GameStatus.RED_PLAYER_TURN) && piece.isGreenTeam()) {
            throw new IllegalArgumentException("[ERROR] 선택한 위치에는 아군 기물이 존재하지 않습니다.");
        }
    }
}
