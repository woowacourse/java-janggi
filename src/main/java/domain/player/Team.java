package domain.player;

public enum Team {
    CHO {
        @Override
        public Team opponent() {
            return HAN;
        }
    },
    HAN {
        @Override
        public Team opponent() {
            return CHO;
        }
    };

    public abstract Team opponent();
}
