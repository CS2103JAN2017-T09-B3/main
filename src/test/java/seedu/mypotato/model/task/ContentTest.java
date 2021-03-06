package seedu.mypotato.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ContentTest {

    @Test
    public void isValidContent() {
        // invalid content
        //assertFalse(Content.isValidContent(" ")); // spaces only
        assertFalse(Content.isValidContent("#")); // only non-alphanumeric characters
        assertFalse(Content.isValidContent("meeting/")); // contains non-alphanumeric characters

        // valid content
        assertTrue(Content.isValidContent("")); // empty string
        assertTrue(Content.isValidContent("peter jack")); // alphabets only
        assertTrue(Content.isValidContent("12345")); // numbers only
        assertTrue(Content.isValidContent("peter the 2nd")); // alphanumeric characters
        assertTrue(Content.isValidContent("Capital Tan")); // with capital letters
        assertTrue(Content.isValidContent("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
