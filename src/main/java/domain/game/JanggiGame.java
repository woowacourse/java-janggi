package domain.game;

import domain.Position;
import domain.Team;
import domain.board.JanggiBoard;
import domain.piece.Piece;

public class JanggiGame {

    private final JanggiBoard janggiBoard;
    private Team currentTeam;

    public JanggiGame(JanggiBoard janggiBoard) {
        this.janggiBoard = janggiBoard;
        this.currentTeam = Team.CHO;
    }

    public void progress(Position currentPosition, Position targetPosition) {
        Piece currentPiece = janggiBoard.getPiece(currentPosition);

        validateCorrectTurn(currentPiece);

        janggiBoard.movePiece(currentPosition, targetPosition);
        switchTeam();
    }

    private void validateCorrectTurn(Piece currentPiece) {
        if (currentPiece.getTeam() != currentTeam) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가합니다. " + currentTeam + "의 차례입니다.");
        }
    }

    private void switchTeam() {
        if (currentTeam == Team.CHO) {
            currentTeam = Team.HAN;
            return;
        }
        currentTeam = Team.CHO;
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }
}
