package janggi.domain.command;

import janggi.domain.setup.ElephantFormation;
import janggi.domain.setup.InnerElephantElephantFormation;
import janggi.domain.setup.LeftElephantElephantFormation;
import janggi.domain.setup.OuterElephantElephantFormation;
import janggi.domain.setup.RightElephantElephantFormation;
import java.util.function.Supplier;

public enum SetupCommand {

    INNER_ELEPHANT(InnerElephantElephantFormation::new),
    OUTER_ELEPHANT(OuterElephantElephantFormation::new),
    LEFT_ELEPHANT(LeftElephantElephantFormation::new),
    RIGHT_ELEPHANT(RightElephantElephantFormation::new);
    
    private final Supplier<ElephantFormation> policySupplier;

    SetupCommand(final Supplier<ElephantFormation> setupPolicySupplier) {
        this.policySupplier = setupPolicySupplier;
    }

    public ElephantFormation toPolicy() {
        return policySupplier.get();
    }
}
