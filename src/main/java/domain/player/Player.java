package domain.player;

public record Player(Name name, Team team) {

    public static Player of(String name, Team team) {
        return new Player(new Name(name), team);
    }

    public String getNameValue() {
        return name.value();
    }
}
