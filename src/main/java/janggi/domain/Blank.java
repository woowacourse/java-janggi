package janggi.domain;

public class Blank implements Space {

    @Override
    public boolean isBlank() {
        return true;
    }

    @Override
    public String displayValue() {
        return "●";
    }
}
