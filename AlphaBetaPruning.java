import java.util.List;

public class AlphaBetaPruning extends AdversarialSearch {

	public AlphaBetaPruning() {
		super();
	}

	public AlphaBetaPruning(int maximumDepth) {
		super(maximumDepth);
	}

	@Override
	Solution getNextMove(BoardState initialState, BoardGameAgent gameAgent, Player player) {
		expandedStateCount = 0;
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		nextMove = null;

		alphaBetaPruning(initialState, gameAgent, player, 0, alpha, beta);
		return new Solution(nextMove, expandedStateCount);
	}


	private double alphaBetaPruning(BoardState state, BoardGameAgent gameAgent, Player player, int depth, double alpha, double beta) {
		expandedStateCount++;
		if (depth == maximumDepth || state.isTerminal()) {
			return gameAgent.getUtility(state, player);
		}

		List<BoardState> nextStates = state.getSuccessors(player);
		if (player == Player.One) {
			double value = Integer.MIN_VALUE;
			for (BoardState nextState : nextStates) {
				value = Math.max(value, alphaBetaPruning(nextState, gameAgent, Player.Two, depth + 1, alpha, beta));
				alpha = Math.max(alpha, value);
				if (beta <= alpha) {
					break;
				}
				if (depth == 0) {
					nextMove = nextState;
				}
			}
			return value;
		} else {
			double value = Integer.MAX_VALUE;
			for (BoardState nextState : nextStates) {
				value = Math.min(value, alphaBetaPruning(nextState, gameAgent, Player.One, depth + 1, alpha, beta));
				beta = Math.min(beta, value);
				if (beta <= alpha) {
					break;
				}
			}
			return value;
		}
	}
}

