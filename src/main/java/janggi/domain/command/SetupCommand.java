package janggi.domain.command;

import janggi.domain.InnerElephantSetupPolicy;
import janggi.domain.LeftElephantSetupPolicy;
import janggi.domain.OuterElephantSetupPolicy;
import janggi.domain.RightElephantSetupPolicy;
import janggi.domain.SetupPolicy;
import java.util.Arrays;
import java.util.function.Supplier;

public enum SetupCommand {

    INNER_ELEPHANT(1, "안상 차림", InnerElephantSetupPolicy::new),
    OUTER_ELEPHANT(2, "바깥상 차림", OuterElephantSetupPolicy::new),
    LEFT_ELEPHANT(3, "왼상 차림", LeftElephantSetupPolicy::new),
    RIGHT_ELEPHANT(4, "오른상 차림", RightElephantSetupPolicy::new);

    private static final int MINUMUM_NUMBER = 1;
    private static final int MAXIMUM_NUMBER = 4;

    private final int number;
    private final String description;
    private final Supplier<SetupPolicy> policySupplier;

    SetupCommand(final int number, final String description,
        final Supplier<SetupPolicy> setupPolicySupplier) {
        this.number = number;
        this.description = description;
        this.policySupplier = setupPolicySupplier;
    }

    public static SetupCommand pick(final int number) {
        return Arrays.stream(values())
            .filter(setupCommand -> setupCommand.number == number)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format(
                "명령 번호는 %d에서 %d까지의 정수 값이어야 합니다.", MINUMUM_NUMBER, MAXIMUM_NUMBER)));
    }

    public SetupPolicy toPolicy() {
        return policySupplier.get();
    }

    public int getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }
}
