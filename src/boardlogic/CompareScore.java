package boardlogic;

import java.util.Comparator;

/**
 * A class that implements the comparator method for two moves under consideration.
 * @see Engine
 */
public class CompareScore implements Comparator<Move> {
    /**
     * Compares two moves under consideration and returns the move with a higher assigned value.
     * @param o1 The first move under consideration.
     * @param o2 The second move under consideration.
     * @return 1 if the first move has a higher score, -1 if the second move has a higher score,
     *         0 if the two move's scores are the same.
     */
    @Override
    public int compare(Move o1, Move o2) {
        if(o1.getScore() > o2.getScore()){
            return 1;
        }
        if(o1.getScore() == o2.getScore()){
            return 0;
        }
        return 1;
    }
}
