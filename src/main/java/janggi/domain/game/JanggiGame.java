package janggi.domain.game;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.board.Turn;
import janggi.domain.piece.Name;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Team;

public class JanggiGame {
    private final Board board;
    private GameStatus gameStatus;
    private Turn turn;

    public JanggiGame(Board board) {
        this.board = board;
        this.gameStatus = GameStatus.PLAYING;
        this.turn = new Turn(Team.HAN);
    }

    public void move(Position from, Position to) {
        validateGamePlayingStatus();
        validatePieceExistsAt(from);

        Piece piece = board.findPiece(from);
        validateCurrentTurn(piece);
        Piece capturedPiece = board.move(from, to);

        updateGameStateAfterMove(capturedPiece);
    }

    public Board board() {
        return board;
    }

    public double calculateScore(Team team) {
        return board.calculateScore(team);
    }

    public Team currentTurnTeam() {
        return turn.team();
    }

    public boolean isPlaying() {
        return gameStatus == GameStatus.PLAYING;
    }

    private void updateGameStateAfterMove(Piece capturedPiece) {
        if (isCapturedGeneral(capturedPiece)) {
            finishGame();
            return;
        }
        turn = turn.changeTurn();
    }

    private void validateCurrentTurn(Piece piece) {
        if (!isCurrentTeamPiece(piece)) {
            throw new IllegalArgumentException("해당 기물은 현재 턴의 진영 기물이 아닙니다.");
        }
    }

    private boolean isCurrentTeamPiece(Piece piece) {
        return turn.isCurrentTeam(piece.getTeam());
    }

    private void validatePieceExistsAt(Position from) {
        if (!board.hasPieceAt(from)) {
            throw new IllegalArgumentException("해당 출발 위치에는 기물이 존재하지 않습니다");
        }
    }

    private void validateGamePlayingStatus() {
        if (gameStatus == GameStatus.FINISHED) {
            throw new IllegalArgumentException("이미 종료된 게임입니다.");
        }
    }

    private boolean isCapturedGeneral(Piece piece) {
        if (piece == null) {
            return false;
        }
        return piece.getName() == Name.GENERAL;
    }

    private void finishGame() {
        if (gameStatus == GameStatus.PLAYING) {
            gameStatus = GameStatus.FINISHED;
        }
    }
}
