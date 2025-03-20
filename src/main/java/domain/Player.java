package domain;

public final class Player {

  private final Team team;

  public Player(final Team team) {
    this.team = team;
  }

  public Team getTeam() {
    return team;
  }

  public boolean isFirstAttack() {
    return team.isFirst();
  }
}
