package exception.custom;


import exception.TeamErrorMessage;

public class InvalidTeamException extends GameException {
    public InvalidTeamException() {
        super(TeamErrorMessage.INVALID_TEAM_NAME.getMessage());
    }

    public InvalidTeamException(String message) {
        super(message);
    }
}
