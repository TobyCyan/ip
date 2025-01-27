package tasks;

import org.junit.jupiter.api.Test;

import java.time.DateTimeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TimedTaskTest {

    @Test
    public void toFormattedDateTimeOutputString_validDate_success() {

    }

    @Test
    public void toFormattedDateTimeOutputString_invalidDate_throwsException() {
        try {

        } catch (DateTimeException e) {

        }
    }

    @Test
    public void convertDateTimeFormat_validDate_success() {

    }

    @Test
    public void convertDateTimeFormat_invalidDate_throwsException() {
        try {
            String validDateTimeFormat = "";
            String invalidDateTime = "";
            assertEquals(validDateTimeFormat, TimedTask.convertDateTimeFormat(invalidDateTime));
            fail();
        } catch (DateTimeException ignored) {

        }
    }

}
