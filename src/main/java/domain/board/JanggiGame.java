package domain.board;

import domain.ScoreCalculator;
import domain.Team;
import domain.dto.JanggiBoardDto;
import domain.piece.MoveablePiece;
import domain.piece.Piece;
import domain.position.Position;

public class JanggiGame {
    private final JanggiBoard janggiBoard;

    public JanggiGame(JanggiBoard janggiBoard) {
        this.janggiBoard = janggiBoard;
    }

    public void playTurn(Position from, Position to) {
        validateNotBlank(from);
        MoveablePiece currentPiece = (MoveablePiece) janggiBoard.getPiece(from);
        validateCurrentPiece(currentPiece);
        validateMoveable(currentPiece, from, to);

        janggiBoard.move(from, to, currentPiece);
    }

    public boolean isGameOver() {
        return janggiBoard.isGameOver();
    }

    public JanggiBoardDto getBoardDto() {
        return JanggiBoardDto.from(janggiBoard);
    }

    public Team getTurn() {
        return janggiBoard.getTurn();
    }

    public double calculateScore(Team team) {
        return new ScoreCalculator().calculateScore(JanggiBoardDto.from(janggiBoard), team);
    }

    private void validateNotBlank(Position from) {
        if (janggiBoard.getPiece(from).isBlank()) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에는 기물이 존재하지 않습니다.");
        }
    }

    private void validateCurrentPiece(Piece currentPiece) {
        if (currentPiece.isOtherTeam(janggiBoard.getTurn())) {
            throw new IllegalArgumentException("[ERROR] 상대방의 기물을 이동할 수 없습니다.");
        }
    }

    private void validateMoveable(MoveablePiece currentPiece, Position from, Position to) {
        if (!currentPiece.canMove(from, to, janggiBoard)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치로 이동할 수 없는 기물입니다.");
        }
    }
}
