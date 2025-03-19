package domain;

public class Players {
    private final Player choPlayer;
    private final Player hanPlayer;

    public Players(Player choPlayer, Player hanPlayer) {
        this.choPlayer = choPlayer;
        this.hanPlayer = hanPlayer;
    }

    public String getChoPlayerName() {
        return choPlayer.getName();
    }

    public String getHanPlayerName() {
        return hanPlayer.getName();
    }

    public Player getTeamPlayer(TeamType teamType) {
        if (choPlayer.isSameTeam(teamType)) {
            return choPlayer;
        }
        return hanPlayer;
    }
}
