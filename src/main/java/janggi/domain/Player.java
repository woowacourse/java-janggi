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

    public boolean isSameDynasty(Dynasty dynasty) {
        return this.dynasty == dynasty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Player player)) {
            return false;
        }
        return Objects.equals(nickname, player.nickname) && dynasty == player.dynasty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, dynasty);
    }

    public String getNickname() {
        return nickname;
    }

    public Dynasty getDynasty() {
        return dynasty;
    }
}
