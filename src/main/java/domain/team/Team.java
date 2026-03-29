package domain.team;

public enum Team {

    CHO("초", "楚"),
    HAN("한", "漢"),
    ;

    private final String koreanTeamName;
    private final String chineseTeamName;

    Team(String koreanTeamName, String chineseTeamName) {
        this.koreanTeamName = koreanTeamName;
        this.chineseTeamName = chineseTeamName;
    }

    public Team nextTurn(){
        if(this == CHO){
            return HAN;
        }
        return CHO;
    }

    public String getKoreanTeamName(){
        return koreanTeamName;
    }

    public String getChineseTeamName(){
        return chineseTeamName;
    }

}
