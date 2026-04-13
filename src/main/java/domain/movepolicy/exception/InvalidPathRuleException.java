package domain.movepolicy.exception;

import domain.exception.JanggiException;

public class InvalidPathRuleException extends JanggiException {

    public InvalidPathRuleException(MovePolicyErrorMessage errorMessage) {
        super(errorMessage.message());
    }
}
