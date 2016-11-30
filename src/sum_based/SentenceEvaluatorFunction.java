package sum_based;

import java.util.Map;

public interface SentenceEvaluatorFunction {

	public double evaluateSentenceBasedOnFrequencies(String[] sentence,
			Map<String, Long> wordsFrequencies);
	
}
