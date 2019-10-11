
import robot.MonteCarloAlgorithm;
import java.awt.Color;
import java.awt.Point;
import java.util.List;

public class Robot extends MonteCarloAlgorithm<Snapshot, Move, Team> {
    
    public Robot(double secondsPerTurn) {
        super(secondsPerTurn, 500);
    }
    
    @Override
    protected List<Move> getLegalMoves(Snapshot s) {
        return Move.legalMoves(s);
    }

    @Override
    protected Snapshot applyMove(Snapshot from, Move m) {
        return from;
    }

    @Override
    protected Team getWinningTeam(Snapshot from) {
        return from.winner;
    }

    @Override
    protected Team getLastTeamToMove(Snapshot from) {
        return from.history.get(from.history.size()-1).currentPlayer;
    }
    
    @Override
    protected MoveRecord<Move, Snapshot>
    heuristicChoice(List<MoveRecord<Move, Snapshot>> moves)
    {
        int bTip = 0;
        int mTip = 22;
        int yTip = 110;
        int rTip = 120;
        int gTip = 109;
        int cTip = 21;
        
        int bestM = 0;
        double bestD = 0;
        double d;
        Point tip = new Point();
        
        int size = moves.size();
        assert size > 0;
        if (size == 1) {
            return moves.get(0);
        }
        
        for(int i = 0; i < moves.size(); i++){
            if(moves.get(i).state.currentPlayer.c == Color.blue)
                tip = moves.get(i).state.marbles.get(rTip);
            else if(moves.get(i).state.currentPlayer.c == Color.magenta)
                tip = moves.get(i).state.marbles.get(gTip);
            else if(moves.get(i).state.currentPlayer.c == Color.yellow)
                tip = moves.get(i).state.marbles.get(cTip);
            else if(moves.get(i).state.currentPlayer.c == Color.red)
                tip = moves.get(i).state.marbles.get(bTip);
            else if(moves.get(i).state.currentPlayer.c == Color.green)
                tip = moves.get(i).state.marbles.get(mTip);
            else if(moves.get(i).state.currentPlayer.c == Color.cyan)
                tip = moves.get(i).state.marbles.get(yTip);
            
            Point p = moves.get(i).state.marbles.get(moves.get(i).move.newPosition);
            
            d = Math.sqrt(Math.pow(tip.x-p.x, 2) + Math.pow(tip.y-p.y, 2));
            
            if(bestD - d >= 0 || i == 0){
                bestD = d;
                bestM = i;
            }
        }
        
        return moves.get(bestM);
    }
}
