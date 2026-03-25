package domain.place;

public class Empty implements Place{

    @Override
    public boolean isEmpty() {
        return true;
    }


    @Override
    public String getFormat(){
        return "  ";
    }

}
