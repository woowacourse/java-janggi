package service;

import domain.piece.Side;
import domain.players.Players;
import repository.PlayerRepository;

public class PlayerService {

    private final Players players;
    private final PlayerRepository playerRepository;

    public PlayerService(Players players, PlayerRepository playerRepository) {
        this.players = players;
        this.playerRepository = playerRepository;
    }


    public Side getWhoseTurn() {
        return players.getWhoseTurn();
    }

    public void switchTurn() {
        players.switchTurn();
    }
}
