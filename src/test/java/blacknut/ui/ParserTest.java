package blacknut.ui;

import blacknut.ui.BlacknutExceptions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void testParseCommand() {
        Parser parser = new Parser();
        assertEquals("deadline", parser.parseCommand("deadline submit report /by 2024-09-30 12:00"));
        assertEquals("event", parser.parseCommand("event team meeting /from 2024-09-01 /to 2024-09-02"));
    }

    @Test
    public void testParseIndex_validInput() throws InvalidTaskNumberException {
        Parser parser = new Parser();
        assertEquals(2, parser.parseIndex("delete 3"));
    }

    @Test
    public void testParseDescription_validInput() throws EmptyDescriptionException {
        Parser parser = new Parser();
        assertEquals("submit report", parser.parseDescription("deadline submit report", "deadline"));
    }

    @Test
    public void testParseDescription_emptyInput() {
        Parser parser = new Parser();
        assertThrows(EmptyDescriptionException.class, () -> parser.parseDescription("deadline ", "deadline"));
    }

    @Test
    public void testParseDeadline_validInput() throws IncorrectFormatException {
        Parser parser = new Parser();
        String[] result = parser.parseDeadline("deadline submit report /by 2024-09-30 12:00");
        assertArrayEquals(new String[]{"submit report", "2024-09-30 12:00"}, result);
    }

    @Test
    public void testParseEvent_validInput() throws IncorrectFormatException {
        Parser parser = new Parser();
        String[] result = parser.parseEvent("event team meeting /from 2024-09-01 /to 2024-09-02");
        assertArrayEquals(new String[]{"team meeting", "2024-09-01", "2024-09-02"}, result);
    }

    @Test
    public void testParseKeyword() {
        Parser parser = new Parser();
        assertEquals("report", parser.parseKeyword("find report"));
    }
}
