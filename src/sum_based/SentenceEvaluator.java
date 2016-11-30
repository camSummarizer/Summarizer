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
	
	static double averageScore(String[] sentence,
			Map<String, Long> wordsFrequencies) {
		double score = 0;
		for (String word : sentence) {
			Long wordFrequency = wordsFrequencies.get(word);
			if (wordFrequency != null) {
				score += wordFrequency;
			}
		}
		return score / (double) sentence.length;
	}
	
	static double averageScoreWithoutClosed(String[] sentence,
			Map<String, Long> wordsFrequencies) {
		double score = 0;
		int count = 0;
		for (String word : sentence) {
			Long wordFrequency = wordsFrequencies.get(word);
			if (wordFrequency != null) {
				score += wordFrequency;
				count++;
			}
		}
		return score / (double) (count * 0.3 + 0.7 * sentence.length);
	}
	
	/*public static complexEvaluator(String[] sentence,
			Map<String, Long> wordsFrequencies) {
		
	}*/
	
	static void outputSentence(String[] sentence,
			Map<String, Long> wordsFrequencies) {
		for (String word : sentence) {
			Long wordFrequency = wordsFrequencies.get(word);
			long out;
			if (wordFrequency == null) {
				out = 0;
			} else {
				out = wordFrequency;
			}
			System.out.print(word + "(" + out + ") ");
		}
		System.out.println();
	}

}
