package view;

public enum AnswerType {
    YES,
    NO,
    NONE;

    public boolean isPositive() {
        return this == YES;
    }

    public boolean isInvalid() {
        return this == NONE;
    }
}
