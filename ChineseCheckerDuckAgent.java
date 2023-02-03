import java.util.List;

public class ChineseCheckerDuckAgent implements BoardGameAgent{
    final int maximumExpansionCount;

    public ChineseCheckerDuckAgent(int maximumExpansionCount) {
        this.maximumExpansionCount = maximumExpansionCount;
    }

    @Override
    public int estimateDepth(BoardState boardState, Player player) {
        int depth = 1;

        while (estimateExpansionCount(boardState, depth, player) < maximumExpansionCount){    //depth starting from 1 and increasing until expansionCount exceeds...
            depth++;                                                                         //..the maxExpansionCount value for that depth
        }

        return depth-1;         //returns the previous depth value, the last one before the limit
    }

    @Override
    public long estimateExpansionCount(BoardState boardState, int m, Player player) {
        if (m == 0)
            return 1;

        ChineseCheckerState chineseCheckerState = (ChineseCheckerState) boardState;
        long numOfExpansions = 0;
        int branchingFactor;
        List<BoardState> successors = chineseCheckerState.getSuccessors(player);   //get successors of the given state

        branchingFactor = successors.size();              //estimate a branching factor

        for (int i = 0; i <= m; i++) {
            numOfExpansions += Math.pow(branchingFactor, i);    //sum the number states for each depth
        }

        return numOfExpansions;
    }

    @Override
    public double getUtility(BoardState boardState, Player player) {
        ChineseCheckerState chineseCheckerState = (ChineseCheckerState) boardState;
        double utility = 0;

        for (int i = 0; i < chineseCheckerState.boardSize; i++) {
            for (int j = 0; j < chineseCheckerState.boardSize; j++) {
                if (chineseCheckerState.isInOpponentArea(player, i, j)){
                    continue;
                }

                Point2D point = new Point2D(i, j);
                if(boardState.get(i, j) == player){
                    utility -= getDistanceToTarget(boardState, point, player);
                }
                else if (boardState.get(i, j) == player.getOpponent()) {
                    utility += getDistanceToTarget(boardState, point, player.getOpponent());
                }
            }
        }


        return utility;
    }

    private int getDistanceToTarget(BoardState boardState, Point2D point, Player player){
        ChineseCheckerState state = (ChineseCheckerState) boardState;
        int boardSize = ((ChineseCheckerState)boardState).boardSize;
        int s = (boardSize - 1) / 2, minimumDistance = Integer.MAX_VALUE;

        if (boardSize % 2 == 0) {
            for (int y=0; y<s; y++) {
                for (int x=0; x<s; x++) {
                    if (player == Player.One){
                        Point2D areaPoint = new Point2D(x, y);
                        if (Math.abs(point.x - areaPoint.x) + Math.abs(point.y - areaPoint.y) < minimumDistance){
                            minimumDistance = (int)(Math.abs(point.x - areaPoint.x) + Math.abs(point.y - areaPoint.y));
                        }
                    }
                    else if (player == Player.Two){
                        Point2D areaPoint = new Point2D(boardSize-x-1, boardSize-y-1);
                        if (Math.abs(point.x - areaPoint.x) + Math.abs(point.y - areaPoint.y) < minimumDistance){
                            minimumDistance = (int)(Math.abs(point.x - areaPoint.x) + Math.abs(point.y - areaPoint.y));
                        }
                    }
                }
            }
        }
        else
        {
            for (int y=0; y<=s; y++) {
                for (int x=0; x<=y; x++) {
                    if (player == Player.One){
                        Point2D areaPoint = new Point2D(x, s-y);
                        if (Math.abs(point.x - areaPoint.x) + Math.abs(point.y - areaPoint.y) < minimumDistance){
                            minimumDistance = (int)(Math.abs(point.x - areaPoint.x) + Math.abs(point.y - areaPoint.y));
                        }
                    }
                    else if (player == Player.Two){
                        Point2D areaPoint = new Point2D(boardSize-x-1, boardSize-(s-y)-1);
                        if (Math.abs(point.x - areaPoint.x) + Math.abs(point.y - areaPoint.y) < minimumDistance){
                            minimumDistance = (int)(Math.abs(point.x - areaPoint.x) + Math.abs(point.y - areaPoint.y));
                        }
                    }
                }
            }
        }

        return minimumDistance;
    }


    @Override
    public String toString() {
        return "Duck Agent ";
    }
}
