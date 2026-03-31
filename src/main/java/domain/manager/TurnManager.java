package domain.manager;

import common.exception.JanggiException;
import domain.piece.Piece;
import domain.player.Player;
import domain.player.Team;

public class TurnManager {

    private Player currentPlayer;
    private Player standbyPlayer;
    private boolean isGameRunning;

    public TurnManager(Player choPlayer, Player hanPlayer) {
        this.currentPlayer = choPlayer;
        this.standbyPlayer = hanPlayer;
        this.isGameRunning = true;
    }

    public void validateTurn(Team team) {
        if (currentPlayer.getTeam() != team) {
            throw new JanggiException("다른 팀의 기물입니다. 현재 차례: %s".formatted(currentPlayer.getTeam()));
        }
    }

    public void switchTurn() {
        Player temp = currentPlayer;
        currentPlayer = standbyPlayer;
        standbyPlayer = temp;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public void endGame() {
        isGameRunning = false;
    }

}
