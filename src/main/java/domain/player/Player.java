package domain.player;

public final class Player {

    private final PlayerProfile playerProfile;

    public Player(Name name, Team team) {
        playerProfile = new PlayerProfile(name, team);
    }

    public String getName() {
        return playerProfile.name().value();
    }

    public Team getTeam() {
        return playerProfile.team();
    }
}
