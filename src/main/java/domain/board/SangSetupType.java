package domain.board;

import domain.board.exception.BoardErrorMessage;
import domain.board.exception.InvalidSangSetupException;
import java.util.Arrays;

public enum SangSetupType {
    LEFT(1) {
        @Override
        public SangSetup create() {
            return new LeftSangSetup();
        }
    },
    RIGHT(2) {
        @Override
        public SangSetup create() {
            return new RightSangSetup();
        }
    },
    INNER(3) {
        @Override
        public SangSetup create() {
            return new InnerSangSetup();
        }
    },
    OUTER(4) {
        @Override
        public SangSetup create() {
            return new OuterSangSetup();
        }
    };

    private final int number;

    SangSetupType(int number) {
        this.number = number;
    }

    public abstract SangSetup create();

    public static SangSetupType from(int number) {
        return Arrays.stream(values())
                .filter(sangSetupType -> sangSetupType.number == number)
                .findFirst()
                .orElseThrow(() -> new InvalidSangSetupException(BoardErrorMessage.INVALID_SANG_SETUP));
    }
}
