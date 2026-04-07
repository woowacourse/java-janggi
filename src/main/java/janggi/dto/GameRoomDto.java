package janggi.dto;

import janggi.domain.JanggiGame;
import janggi.domain.board.Board;
import janggi.domain.piece.Team;

import java.util.Optional;

public class GameRoomDto {
    private final int gameId;
    private final JanggiGame game;

    public GameRoomDto(int gameId, JanggiGame game) {
        this.gameId = gameId;
        this.game = game;
    }

    public int getGameId() {
        return gameId;
    }

    public JanggiGame getGame() {
        return game;
    }

    public Board getBoard() {
        return game.getBoard();
    }

    public Optional<Team> getWinner() {
        return game.getWinner();
    }

    public Team getCurrentTeam() {
        return game.getCurrentTeam();
    }

    public int calculateScore(Team team) {
        return game.calculateScore(team);
    }
}
