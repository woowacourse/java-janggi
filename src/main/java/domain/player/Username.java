package domain.player;

import java.util.Objects;

public class Username {

    private static final int MAX_USERNAME_LENGTH = 10;
    private final String name;

    public Username(String name) {
        this.name = name;
        validateNameLength();
    }

    private void validateNameLength() {
        if (name.length() > MAX_USERNAME_LENGTH) {
            throw new IllegalArgumentException(String.format("이름은 최대 %d자 까지 가능합니다.", MAX_USERNAME_LENGTH));
        }
    }

    public String getName(){
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Username username = (Username) o;
        return Objects.equals(name, username.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

