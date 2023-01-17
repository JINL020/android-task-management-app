package at.ac.univie.se2_team_0308.utils.import_tasks;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.List;

public class RetrieveMatchTest extends TestCase {
    @Test
    public void retrieveMatch_withDotallTrue_shouldReturnAllMatches() {
        String regex = "\\d+";
        String text = "The numbers are 42, 56 and 78.";
        boolean dotall = true;

        List<String> matches = MatchRetriever.retrieveMatch(regex, text, dotall);

        assertEquals(3, matches.size());
        assertEquals("42", matches.get(0));
        assertEquals("56", matches.get(1));
        assertEquals("78", matches.get(2));
    }

    @Test
    public void retrieveMatch_withDotallFalse_shouldReturnAllMatches() {
        String regex = "\\d+";
        String text = "The numbers are 42, 56 and 78.";
        boolean dotall = false;

        List<String> matches = MatchRetriever.retrieveMatch(regex, text, dotall);

        assertEquals(3, matches.size());
        assertEquals("42", matches.get(0));
        assertEquals("56", matches.get(1));
        assertEquals("78", matches.get(2));
    }

    @Test
    public void retrieveMatch_withInvalidRegex_shouldReturnEmptyList() {
        String regex = "[a-z";
        String text = "The numbers are 42, 56 and 78.";
        boolean dotall = false;

        List<String> matches = MatchRetriever.retrieveMatch(regex, text, dotall);

        assertEquals(0, matches.size());
    }


}