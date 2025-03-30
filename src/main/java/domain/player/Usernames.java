package domain.player;

public class Usernames {

    private final Username playerAName;
    private final Username playerBName;

    public Usernames(Username playerAName, Username playerBName) {
        validateDuplicate(playerAName, playerBName);
        this.playerAName = playerAName;
        this.playerBName = playerBName;
    }

    public Username getAnotherPlayerName(Username username) {
        if (!hasUsername(username)) {
            throw new IllegalArgumentException("없는 이름입니다.");
        }
        if (playerAName.equals(username)) {
            return playerBName;
        }
        return playerAName;
    }

    private void validateDuplicate(Username playerAName, Username playerBName) {
        if (playerAName.equals(playerBName)) {
            throw new IllegalArgumentException("중복된 이름은 불가합니다.");
        }
    }

    public boolean hasUsername(Username username) {
        return playerAName.equals(username) || playerBName.equals(username);
    }

}
