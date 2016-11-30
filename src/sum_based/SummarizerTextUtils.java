package sum_based;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

public class SummarizerTextUtils {

	private static Set<String> CLOSED_WORDS = new TreeSet<String>(Arrays.asList(new String[]{
			"of", "to", "and", "I", "she", "he", "from", "a", "at", "by",
			"on", "an", "the", "how", "into", "what", "that", "they", "if",
			"i", "but", "about", "would", "you", "yourself", "her", 
	}));
	
	/* Returns whether the word should be included in the document. */
	public static boolean shouldKeepWord(String word) {
		return !CLOSED_WORDS.contains(word) && StringUtils.isAlpha(word);
	}
	
	public static String normalizeWord(String word) {
		return StringUtils.lowerCase(word);
	}
	
}
