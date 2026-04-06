package game;

import domain.Position;
import domain.Team;
import domain.board.JanggiBoard;
import domain.piece.Piece;
import domain.piece.PieceType;

public class JanggiGame {

    private final JanggiBoard janggiBoard;
    private GameState gameState;

    public JanggiGame(JanggiBoard janggiBoard) {
        this.janggiBoard = janggiBoard;
        this.gameState = GameState.CHO_TURN;
    }

    public JanggiGame(JanggiBoard janggiBoard, Team team) {
        this.janggiBoard = janggiBoard;
        this.gameState = mapToGameState(team);
    }

    private GameState mapToGameState(Team team) {
        if (team == Team.HAN) {
            return GameState.HAN_TURN;
        }
        return GameState.CHO_TURN;
    }

    public void progress(Position currentPosition, Position targetPosition) {
        validateGameState();

        Piece movingPiece = janggiBoard.getPiece(currentPosition);
        validateTurn(movingPiece);

        Piece caughtPiece = janggiBoard.movePiece(currentPosition, targetPosition);
        PieceType pieceType = caughtPiece.getPieceType();

        this.gameState = gameState.nextTurn(pieceType == PieceType.KING);
    }

    public Team getWinner() {
        double teamChoScore = janggiBoard.calculateScore(Team.CHO);
        double teamHanScore = janggiBoard.calculateScore(Team.HAN);

        if (teamChoScore > teamHanScore) {
            return Team.CHO;
        }
        if (teamHanScore > teamChoScore) {
            return Team.HAN;
        }
        return Team.NONE;
    }

    public boolean isFinished() {
        return gameState == GameState.FINISHED;
    }

    public Team getCurrentTeam() {
        return gameState.getTeam();
    }

    private void validateGameState() {
        if (gameState == GameState.FINISHED) {
            throw new IllegalArgumentException("게임이 이미 종료되었습니다.");
        }
    }

    private void validateTurn(Piece piece) {
        if (!gameState.isTurnOf(piece.getTeam())) {
            throw new IllegalArgumentException("[ERROR] 현재 차례가 아닙니다.");
        }
    }

}
