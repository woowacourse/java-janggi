package model.board;

import java.util.function.Supplier;

public enum TableSetting {
    LEFT(LeftTableSettingInitializer::new),
    RIGHT(RightTableSettingInitializer::new),
    INSIDE(InsideTableSettingInitializer::new),
    OUTSIDE(OutsideTableSettingInitializer::new),
    ;

    private final Supplier<Initializer> initializerSupplier;

    TableSetting(Supplier<Initializer> initializerSupplier) {
        this.initializerSupplier = initializerSupplier;
    }

    Initializer getInitializer() {
        return initializerSupplier.get();
    }
}
