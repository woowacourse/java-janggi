package janggi.domain;

public enum Side {

    HAN("한") {
        @Override
        public Side switchSide() {
            return CHO;
        }
    },
    CHO("초") {
        @Override
        public Side switchSide() {
            return HAN;
        }
    },
    NONE("없음") {
        @Override
        public Side switchSide() {
            return NONE;
        }
    },
    ;

    private final String koreanNameFormat;

    Side(String koreanNameFormat) {
        this.koreanNameFormat = koreanNameFormat;
    }

    public abstract Side switchSide();

    public String getNameFormat() {
        return koreanNameFormat;
    }
}
