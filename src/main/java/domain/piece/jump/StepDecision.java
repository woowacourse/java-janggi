package domain.piece.jump;

public record StepDecision(boolean shouldAdd, boolean shouldStop, boolean shouldJump) {

    public static StepDecision step() {
        return new StepDecision(true, false, false);
    }

    public static StepDecision skip() {
        return new StepDecision(false, false, false);
    }

    public static StepDecision stop() {
        return new StepDecision(false, true, false);
    }

    public static StepDecision jump() {
        return new StepDecision(false, false, true);
    }

    public static StepDecision capture() {
        return new StepDecision(true, true, false);
    }
}
