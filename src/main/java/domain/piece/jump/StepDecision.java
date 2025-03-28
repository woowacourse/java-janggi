package domain.piece.jump;

public record StepDecision(boolean shouldAdd, boolean shouldStop, boolean shouldJump) {

    public static StepDecision of(boolean add, boolean stop, boolean jump) {
        return new StepDecision(add, stop, jump);
    }

    public static StepDecision addStep() {
        return of(true, false, false);
    }

    public static StepDecision skip() {
        return of(false, false, false);
    }

    public static StepDecision stop() {
        return of(false, true, false);
    }
}
