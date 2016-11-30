package sum_based;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public class SumBased {

	public static String[] splitSentences(String text) {
		return text.split("\\.|\\?|!");
	}
	
	/* Extracts the words from a text and applies normalization to the words,
	 * filtering out the ones that are closed words or contain non-alphabet symbols.
	 */
	public static Stream<String> extractWords(String text) {
		return Arrays.stream(splitSentences(text))
				.map(s -> Arrays.stream(StringUtils.split(text)))
				.flatMap(Function.identity())
				.map(word -> SummarizerTextUtils.normalizeWord(word))
				.filter(s -> SummarizerTextUtils.shouldKeepWord(s));
	}
	
	public static Map<String, Long> getWordFrequencies(String text) {
		return extractWords(text).collect(Collectors.groupingBy(e -> e, Collectors.counting()));
	}
	
	public static ArrayList<Pair<String, Long>> rankSentences(String text) {
		Map<String, Long> wordsFrequencies = getWordFrequencies(text);
		outputWordFrequencies(wordsFrequencies);
		ArrayList<Pair<String, Long>> sentenceScores = new ArrayList<>();
		String[] sentences = splitSentences(text);
		for (String sentence : sentences) {
			String[] sentenceWords = extractWords(sentence).toArray(size -> new String[size]);
			long score = SentenceEvaluator.Score(sentenceWords, wordsFrequencies);
			sentenceScores.add(Pair.of(sentence, score));
		}
		sentenceScores.sort((o1, o2) -> Long.compare(o2.getRight(), o1.getRight()));
		
		return sentenceScores;
	}
	
	public static void outputWordFrequencies(Map<String, Long> pairs) {
		for (Map.Entry<String, Long> pair : pairs.entrySet()) {
			System.out.println(pair.getKey() + " :  " + pair.getValue());
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); // .useDelimiter("//.|?|!|:");
		StringBuffer buf = new StringBuffer();
		while(sc.hasNextLine()) {
			buf.append(" " + sc.nextLine());
		}
		// System.out.println(buf.toString());
		ArrayList<Pair<String, Long>> rankedSentences = rankSentences(buf.toString());
		for (Pair<String, Long> s : rankedSentences) {
			System.out.println(s.getLeft() + " " + s.getRight());
		}
		sc.close();
	}
	
}
