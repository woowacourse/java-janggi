package janggi.domain;

import java.util.Objects;

public class Player {

    private final String nickname;
    private final Dynasty dynasty;

    public Player(String nickname, Dynasty dynasty) {
        validateNickname(nickname);
        this.nickname = nickname;
        this.dynasty = dynasty;
    }

    private void validateNickname(String nickname) {
        if (nickname.isEmpty() || nickname.length() > 6) {
            throw new IllegalArgumentException("닉네임은 1자 ~ 6자여야합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return Objects.equals(nickname, player.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nickname);
    }

    public String getNickname() {
        return nickname;
    }

    public Dynasty getDynasty() {
        return dynasty;
    }

    public boolean isSameDynasty(Dynasty dynasty) {
        return this.dynasty == dynasty;
    }
}
