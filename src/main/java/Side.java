import java.util.List;

public enum Side {
    CHO {
        @Override
        public int baseY() {
            return 0;
        }

        @Override
        public int generalY() {
            return 1;
        }

        @Override
        public int cannonY() {
            return 2;
        }

        @Override
        public int soldierY() {
            return 3;
        }

        @Override
        public List<Integer> formationX() {
            return List.of(1, 2, 6, 7);
        }
    },
    HAN {
        @Override
        public int baseY() {
            return 9;
        }

        @Override
        public int generalY() {
            return 8;
        }

        @Override
        public int cannonY() {
            return 7;
        }

        @Override
        public int soldierY() {
            return 6;
        }

        @Override
        public List<Integer> formationX() {
            return List.of(7, 6, 2, 1);
        }
    };

    public abstract int baseY();
    public abstract int generalY();
    public abstract int cannonY();
    public abstract int soldierY();
    public abstract List<Integer> formationX();

    public boolean isSameAs(Side other) {
        return this.equals(other);
    }
}
