package janggi.domain.command;

import janggi.domain.setup.InnerElephantSetupPolicy;
import janggi.domain.setup.LeftElephantSetupPolicy;
import janggi.domain.setup.OuterElephantSetupPolicy;
import janggi.domain.setup.RightElephantSetupPolicy;
import janggi.domain.setup.SetupPolicy;
import java.util.Arrays;
import java.util.function.Supplier;

public enum SetupCommand {

    INNER_ELEPHANT("안상 차림", InnerElephantSetupPolicy::new),
    OUTER_ELEPHANT("바깥상 차림", OuterElephantSetupPolicy::new),
    LEFT_ELEPHANT("왼상 차림", LeftElephantSetupPolicy::new),
    RIGHT_ELEPHANT("오른상 차림", RightElephantSetupPolicy::new);

    private final String description;
    private final Supplier<SetupPolicy> policySupplier;

    SetupCommand(final String description,
        final Supplier<SetupPolicy> setupPolicySupplier) {
        this.description = description;
        this.policySupplier = setupPolicySupplier;
    }

    public static SetupCommand pick(final int number) {
        return Arrays.stream(values())
            .filter(setupCommand -> setupCommand.ordinal() + 1 == number)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format(
                "명령 번호는 %d에서 %d까지의 정수 값이어야 합니다.", 1, values().length)));
    }

    public SetupPolicy toPolicy() {
        return policySupplier.get();
    }

    public String getDescription() {
        return description;
    }
}
