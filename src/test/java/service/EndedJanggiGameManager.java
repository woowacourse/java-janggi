package service;

import domain.board.Formation;
import domain.manager.JanggiGameManager;
import domain.player.Player;
import domain.player.PlayerProfile;
import domain.player.Team;
import domain.position.Position;
import domain.rule.BigJangDrawRule;
import domain.rule.DrawGameWinnerRule;
import domain.rule.GameResultEngine;
import domain.rule.NormalGameWinnerRule;

import java.util.List;

public class EndedJanggiGameManager extends JanggiGameManager {
    private final Player winnerPlayer;
    private final PlayerProfile winnerProfile;

    public EndedJanggiGameManager(Player choPlayer, Player hanPlayer, PlayerProfile winnerProfile) {
        super(
                choPlayer,
                hanPlayer,
                Formation.from(1),
                Formation.from(1),
                new GameResultEngine(
                        List.of(new BigJangDrawRule()),
                        List.of(new NormalGameWinnerRule(), new DrawGameWinnerRule())
                )
        );
        this.winnerProfile = winnerProfile;
        this.winnerPlayer = winnerProfile.team() == Team.CHO ? choPlayer : hanPlayer;
    }

    @Override
    public void validateSource(Position source) {
    }

    @Override
    public void move(Position source, Position destination) {
        endGame();
    }

    @Override
    public Player getCurrentPlayer() {
        return winnerPlayer;
    }

    @Override
    public PlayerProfile calculateFinalScore() {
        return winnerProfile;
    }
}
