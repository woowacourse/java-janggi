package domain.game;

import domain.piece.Country;
import java.util.function.Consumer;

public class Turn {

    private Country current;

    public Turn(Country current) {
        this.current = current;
    }

    public <T> void take(T value, Consumer<T> consumer) {
        while (true) {
            try {
                consumer.accept(value);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void next() {
        current = current.convertCountry();
    }

    public String getCurrentName() {
        return current.getCountryName();
    }

    public Country getCountry() {
        return current;
    }
}
