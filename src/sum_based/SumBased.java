package sum_based;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

public class SumBased {

	private static final int MIN_SENTENCE_LENGTH = 4;
	private static final int FIRST_TEN = 10;
	
	public static String[] splitSentences(String text) {
		return text.split("\\.|\\?|!");
	}
	
	/** 
	 * Extracts the words from a text and applies normalization to the words,
	 * filtering out the ones that are closed words or contain non-alphabet symbols.
	 */
	public static Stream<String> extractWordsAndFilter(String text) {
		return extractWordsWithoutFiltering(text)
				.filter(s -> SummarizerTextUtils.shouldKeepWord(s));
	}
	
	public static Stream<String> extractWordsWithoutFiltering(String text) {
		return Arrays.stream(splitSentences(text))
				.map(s -> Arrays.stream(StringUtils.split(s)))
				.flatMap(Function.identity())
				.map(word -> SummarizerTextUtils.normalizeWord(word));
	}
	
	/**
	 * Returns the word frequency for each word in the text.
	 */
	public static Map<String, Long> getWordFrequencies(String text) {
		return extractWordsAndFilter(text).collect(Collectors.groupingBy(e -> e, Collectors.counting()));
	}
	
	/**
	 * Ranks the sentences based on the sentence score function.
	 */
	public static ArrayList<SentenceInfo> rankSentences(String text, 
			SentenceEvaluatorFunction evaluator) {
		Map<String, Long> wordsFrequencies = getWordFrequencies(text);
		// outputWordFrequencies(wordsFrequencies);
		ArrayList<SentenceInfo> sentenceScores = new ArrayList<>();
		String[] sentences = splitSentences(text);
		for (int idx = 0; idx < sentences.length; idx++) {
			String sentence = sentences[idx];
			String[] sentenceWords = extractWordsWithoutFiltering(sentence).toArray(size -> new String[size]);
			if ( sentenceWords.length >= MIN_SENTENCE_LENGTH) {
				double score = evaluator.evaluateSentenceBasedOnFrequencies(sentenceWords, wordsFrequencies);
				sentenceScores.add(new SentenceInfo(score, idx, sentence));
			}
		}
		sentenceScores.sort((o1, o2) -> Double.compare(o2.score, o1.score));
		
		return sentenceScores;
	}
	
	public static void outputWordFrequencies(Map<String, Long> pairs) {
		for (Map.Entry<String, Long> pair : pairs.entrySet()) {
			System.out.println(pair.getKey() + " :  " + pair.getValue());
		}
	}
	
	public static void main(String[] args) throws IOException{
		Scanner sc = new Scanner(new File(args[0])); // .useDelimiter("//.|?|!|:");
		StringBuffer buf = new StringBuffer();
		while(sc.hasNextLine()) {
			buf.append(" " + sc.nextLine());
		}
		// System.out.println(buf.toString());
		List<SentenceInfo> rankedSentences = rankSentences(buf.toString(), SentenceEvaluator::averageScoreWithoutClosed).subList(0, FIRST_TEN + 1);
		rankedSentences.sort((s1, s2) ->  Integer.compare(s1.idx, s2.idx));
		Map<String, Long> wordsFrequencies = getWordFrequencies(buf.toString());
		for (SentenceInfo s : rankedSentences) {
			String[] sentence = extractWordsWithoutFiltering(s.rawSentence).toArray(size -> new String[size]);
			int sentenceLength = sentence.length;
			//SentenceEvaluator.outputSentence(sentence, wordsFrequencies);
			System.out.println("(" + s.idx + ")" + s.rawSentence + " " + s.score + " (" + sentenceLength + ")");
		}
		sc.close();
	}
	
}
