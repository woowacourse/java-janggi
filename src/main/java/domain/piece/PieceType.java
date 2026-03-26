package domain.piece;

public enum PieceType {
    GENERAL("궁", new GeneralMovingCondition()),
    CHARIOT("차", new ChariotMovingCondition()),
    CANON("포", new CanonMovingCondition()),
    HORSE("마", new HorseMovingCondition()),
    ELEPHANT("상", new ElephantMovingCondition()),
    COUNSELOR("사", new CounselorMovingCondition()),
    PAWN("병", new PawnMovingCondition());

    private final String name;
    private final MovingCondition movingCondition;

    PieceType(String name, MovingCondition movingCondition) {
        this.name = name;
        this.movingCondition = movingCondition;
    }

    public String getName() {
        return name;
    }

    public MovingCondition getMovingCondition() {
        return movingCondition;
    }
}
