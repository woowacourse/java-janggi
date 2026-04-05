package domain.player;

public record PlayerProfile(Name name, Team team) {
    public boolean hasName(String nameValue) {
        return name.value().equals(nameValue);
    }
}
