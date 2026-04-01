package domain.player;

public record PlayerProfile(Name name, Team team) {
    public String nameValue() {
        return name.value();
    }

    public boolean hasName(String name) {
        return nameValue().equals(name);
    }
}
