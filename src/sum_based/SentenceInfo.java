package sum_based;

public class SentenceInfo {

	public final double score;
	public final int idx;
	public final String rawSentence;
	
	public SentenceInfo(double score, int idx, String rawSentence) {
		this.score = score;
		this.idx = idx;
		this.rawSentence = rawSentence;
	}
	
}
