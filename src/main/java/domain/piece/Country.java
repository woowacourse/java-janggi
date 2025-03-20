package domain.piece;

public enum Country {
    CHO,
    HAN;

    public static Country convertTurn(Country country){
        if(country == Country.HAN){
            return Country.CHO;
        }
        return Country.HAN;
    }
}
