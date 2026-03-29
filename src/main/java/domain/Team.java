package domain;

public enum Team {
    CHO("초"),
    HAN("한");

    private final String koreanName;

    Team(String koreanName){
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }
}
