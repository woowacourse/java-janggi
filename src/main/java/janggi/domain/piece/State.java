package janggi.domain.piece;

public interface State {

    public boolean isEmpty();

    public boolean isSameTeam(Team team);

    public String displayName();

}
