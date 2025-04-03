package janggi.domain.camp;

public enum Camp {

    HAN("한") {
        @Override
        public Camp reverse() {
            return CHU;
        }
    },
    CHU("초") {
        @Override
        public Camp reverse() {
            return HAN;
        }
    },
    ;

    private final String name;

    Camp(String name) {
        this.name = name;
    }

    public abstract Camp reverse();

    public static Camp from(String name) {
        if (name.equals("한")) {
            return Camp.HAN;
        }
        return Camp.CHU;
    }

    public String getName() {
        return name;
    }
}
