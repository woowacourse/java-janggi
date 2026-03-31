package domain.player;

import common.exception.JanggiException;

public final class Player {

    private final PlayerProfile playerProfile;

    public Player(Name name, Team team) {
        validateNotBlank(name.value());
        playerProfile = new PlayerProfile(name, team);
    }

    public boolean hasName(String name) {
        return playerProfile.hasName(name);
    }

    public Team getTeam() {
        return playerProfile.team();
    }

    public PlayerProfile getProfile() {
        return playerProfile;
    }

    private void validateNotBlank(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new JanggiException("플레이어의 이름은 빈 문자열일 수 없습니다.");
        }
    }
}
