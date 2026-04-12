package view;

public enum StartOption {
    LOAD,
    NEW;

    public static StartOption from(String input){
        if("1".equals(input)){
            return LOAD;
        }
        if("2".equals(input)){
            return NEW;
        }
        throw new IllegalArgumentException("[ERROR] 1또는 2만 입력할 수 있습니다.");
    }
}
