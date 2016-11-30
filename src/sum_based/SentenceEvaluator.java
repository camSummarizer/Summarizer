package sum_based;

import java.util.Map;

public class SentenceEvaluator {

	static long Score(String[] sentence,
			Map<String, Long> wordsFrequencies) {
		long score = 0;
		for (String word : sentence) {
			Long wordFrequency = wordsFrequencies.get(word);
			if (wordFrequency != null) {
				score += wordFrequency;
			}
		}
		return score;
	}

}
