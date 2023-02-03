import java.util.Random;

// I.Q. scores for idiot: below 25 

public class ChineseCheckerIdiotAgent implements BoardGameAgent {
	private static Random rand = new Random(System.currentTimeMillis());

	@Override
	public int estimateDepth(BoardState boardState, Player player) 
	{
		return 1;
	}
	
	@Override
	public long estimateExpansionCount(BoardState boardState, int m, Player player)
	{
		return 1;
	}
	
	@Override
	public double getUtility(BoardState boardState, Player player)
	{
		// idiot agent has a random utility function
		return rand.nextDouble(1000.0);
	}
	
	@Override
	public String toString()
	{
		return "Idiot Agent   IQ < 25";
	}

}
