import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

public class Practice {

    public String formatForLog(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(date);
    }

    public String formatForBirthday(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(date);
    }

    @Test
    void 뭔가() {
        LocalDate date = LocalDate.of(2025, 12, 10);
        System.out.println(formatForLog(date));
    }
}
