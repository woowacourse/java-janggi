package board;

public enum SangSetupType {
    LEFT {
        @Override
        public SangSetup create() {
            return new LeftSangSetup();
        }
    },
    RIGHT {
        @Override
        public SangSetup create() {
            return new RightSangSetup();
        }
    },
    INNER {
        @Override
        public SangSetup create() {
            return new InnerSangSetup();
        }
    },
    OUTER {
        @Override
        public SangSetup create() {
            return new OuterSangSetup();
        }
    };

    public abstract SangSetup create();

    public static SangSetupType from(int number) {
        if (number == 1) {
            return LEFT;
        }
        if (number == 2) {
            return RIGHT;
        }
        if (number == 3) {
            return INNER;
        }
        if (number == 4) {
            return OUTER;
        }
        throw new IllegalArgumentException("잘못된 상차림 번호입니다.");
    }
}
