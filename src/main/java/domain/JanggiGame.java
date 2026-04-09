package domain;

import dto.PieceInfo;
import dto.PieceSnapshot;
import exception.JanggiBusinessException;
import java.util.List;

public class JanggiGame {

    private final Board board;
    private GameStatus gameStatus;

    private JanggiGame(Board board) {
        this(board, GameStatus.GREEN_PLAYER_TURN);
    }

    public JanggiGame(Board board, GameStatus gameStatus) {
        this.board = board;
        this.gameStatus = gameStatus;
    }

    public static JanggiGame initGame(Board board) {
        return new JanggiGame(board);
    }

    public PieceInfo findPieceInfoAt(Position selectPosition) {
        validateOutOfRange(selectPosition);
        requirePieceExists(selectPosition);
        requireCorrectTurn(selectPosition);

        return PieceInfo.from(board.findPieceAt(selectPosition));
    }

    public void move(Position selectPosition, Position destination) {
        validateMove(selectPosition, destination);

        board.movePiece(selectPosition, destination);
        shiftGameStatus();
    }

    public boolean isFinished() {
        return this.gameStatus.isFinished();
    }

    public String gameStatus() {
        return this.gameStatus.description();
    }

    public String currentPlayerTurn() {
        return gameStatus.description();
    }

    public int currentPlayerPiecesPointSum() {
        if(gameStatus.equals(GameStatus.GREEN_PLAYER_TURN)) {
            return board.greenTeamPointSum();
        }
        return board.redTeamPointSum();
    }

    public List<PieceInfo> allFactors() {
        return board.allFactors().stream()
                .map(PieceInfo::from)
                .toList();
    }

    private void validateMove(Position select, Position destination) {
        validateOutOfRange(select);
        validateOutOfRange(destination);
    }

    private void shiftGameStatus() {
        gameStatus = gameStatus.changePlayerTurn();
        checkGameFinished();
    }

    private void checkGameFinished() {
        if (!board.hasGreenTeamGeneral()) {
            gameStatus = GameStatus.RED_TEAM_WIN;
        }

        if (!board.hasRedTeamGeneral()) {
            gameStatus = GameStatus.GREEN_TEAM_WIN;
        }
    }

    private void validateOutOfRange(Position position) {
        if (position.isOutOfBoard()) {
            throw new JanggiBusinessException("[ERROR] 장기판 범위를 벗어난 위치를 입력하셨습니다.");
        }
    }

    private void requirePieceExists(Position position) {
        if (board.isNone(position)) {
            throw new JanggiBusinessException("[ERROR] 해당 위치에는 기물이 없습니다.");
        }
    }

    private void requireCorrectTurn(Position position) {
        if (gameStatus.equals(GameStatus.GREEN_PLAYER_TURN) && board.isPieceRedTeamAt(position)) {
            throw new JanggiBusinessException("[ERROR] 선택한 위치에는 아군 기물이 존재하지 않습니다.");
        }

        if (gameStatus.equals(GameStatus.RED_PLAYER_TURN) && board.isPieceGreenTeamAt(position)) {
            throw new JanggiBusinessException("[ERROR] 선택한 위치에는 아군 기물이 존재하지 않습니다.");
        }
    }

    public List<PieceSnapshot> pieceSnapshots() {
        return board.allFactors().stream()
                .map(PieceSnapshot::of)
                .toList();
    }
}
