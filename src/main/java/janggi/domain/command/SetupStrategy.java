package janggi.domain.command;

import janggi.domain.setup.ElephantFormation;
import janggi.domain.setup.InnerElephantElephantFormation;
import janggi.domain.setup.LeftElephantElephantFormation;
import janggi.domain.setup.OuterElephantElephantFormation;
import janggi.domain.setup.RightElephantElephantFormation;
import java.util.function.Supplier;

public enum SetupStrategy {

    INNER_ELEPHANT(1, InnerElephantElephantFormation::new),
    OUTER_ELEPHANT(2, OuterElephantElephantFormation::new),
    LEFT_ELEPHANT(3, LeftElephantElephantFormation::new),
    RIGHT_ELEPHANT(4, RightElephantElephantFormation::new);

    private static final int MIN_SETUP_COMMAND = 1;
    private static final int MAX_SETUP_COMMAND = 4;

    private final int setupTypeNumber;
    private final Supplier<ElephantFormation> policySupplier;

    SetupStrategy(final int setupTypeNumber, final Supplier<ElephantFormation> policySupplier) {
        this.setupTypeNumber = setupTypeNumber;
        this.policySupplier = policySupplier;
    }

    public static SetupStrategy from(final int setupTypeNumber) {
        for (SetupStrategy setup : SetupStrategy.values()) {
            if (setup.setupTypeNumber == setupTypeNumber) {
                return setup;
            }
        }
        throw new IllegalArgumentException("입력은 " + MIN_SETUP_COMMAND + "에서 " + MAX_SETUP_COMMAND + "까지의 정수 값이어야 합니다.");
    }


    public ElephantFormation toPolicy() {
        return policySupplier.get();
    }
}
