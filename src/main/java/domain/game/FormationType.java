package domain.game;

import strategy.formation.InitialFormationStrategy;
import strategy.formation.InnerFormationStrategy;
import strategy.formation.LeftFormationStrategy;
import strategy.formation.OuterFormationStrategy;
import strategy.formation.RightFormationStrategy;

public enum FormationType {
    INNER {
        @Override
        public InitialFormationStrategy createStrategy() {
            return new InnerFormationStrategy();
        }
    },
    OUTER {
        @Override
        public InitialFormationStrategy createStrategy() {
            return new OuterFormationStrategy();
        }
    },
    LEFT {
        @Override
        public InitialFormationStrategy createStrategy() {
            return new LeftFormationStrategy();
        }
    },
    RIGHT {
        @Override
        public InitialFormationStrategy createStrategy() {
            return new RightFormationStrategy();
        }
    };

    public abstract InitialFormationStrategy createStrategy();
}
