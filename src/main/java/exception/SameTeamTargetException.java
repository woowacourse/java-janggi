package exception;

public class SameTeamTargetException extends IllegalArgumentException {
    public SameTeamTargetException() {
        super(ErrorMessage.SAME_TEAM_TARGET.getMessage());
    }
}
