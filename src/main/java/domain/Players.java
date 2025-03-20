package domain;

public class Players {
    private final Player choPlayer;
    private final Player hanPlayer;

    private Players(Player choPlayer, Player hanPlayer) {
        this.choPlayer = choPlayer;
        this.hanPlayer = hanPlayer;
    }

    public static Players createFrom(Usernames usernames, String startPlayerName) {
        validateHasName(usernames, startPlayerName);
        return new Players(new Player(startPlayerName, TeamType.CHO),
                new Player(usernames.getAnotherPlayerName(startPlayerName), TeamType.HAN));
    }

    private static void validateHasName(Usernames usernames, String startPlayerName) {
        if (isNameNotExist(usernames, startPlayerName)) {
            throw new IllegalArgumentException("존재하지 않은 이름입니다.");
        }
    }

    private static boolean isNameNotExist(Usernames usernames, String startPlayerName) {
        return !usernames.hasUsername(startPlayerName);
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
