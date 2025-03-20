package domain;

public class Usernames {
    private final String playerAName;
    private final String playerBName;

    public Usernames(String playerAName, String playerBName) {
        validateDuplicate(playerAName, playerBName);
        this.playerAName = playerAName;
        this.playerBName = playerBName;
    }

    private void validateDuplicate(String playerAName, String playerBName) {
        if (playerAName.equals(playerBName)) {
            throw new IllegalArgumentException("중복된 이름은 불가합니다.");
        }
    }

    public boolean hasUsername(String username) {
        return playerAName.equals(username) || playerBName.equals(username);
    }

    public String getAnotherPlayerName(String username) {
        if(!hasUsername(username)){
            throw new IllegalArgumentException("없는 이름입니다.");
        }
        if (playerAName.equals(username)) {
            return playerBName;
        }
        return playerAName;
    }

}
