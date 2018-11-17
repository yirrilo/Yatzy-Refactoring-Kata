import java.util.stream.IntStream;

public class Yatzy {

    private static final int POINTS_FOR_YATZY = 50;
	private static final int MAX_VALUE_OF_DICE = 6;
	private static final int NUMBER_OF_DICE = 5;
	protected int[] dices;

    public Yatzy(int... dices)
    {
        this.dices = dices;
    }
    
    public static int chance(int... dices)
    {
        int total = 0;
        for (int dice : dices){
        	total += dice;
        }
        return total;
    }

    public static int yatzy(int... dice)
    {
    	boolean yatzy = true;
        for (int i = 1; i < NUMBER_OF_DICE; i++){
            yatzy &= dice[i-1] == dice[i];
        }
        return yatzy ? POINTS_FOR_YATZY : 0;
    }

    public static int ones(int... dices) {
        return sumDicesOfValue(1, dices);
    }

    public static int twos(int... dices) {
        return sumDicesOfValue(2, dices);
    }

    public static int threes(int... dices) {
        return sumDicesOfValue(3, dices);
    }

    public int fours()
    {
        return sumDicesOfValue(4, dices);
    }

    public int fives()
    {
        return sumDicesOfValue(5, dices);
    }

    public int sixes()
    {
        return sumDicesOfValue(6, dices);
    }

	private static int sumDicesOfValue(int valueToKeep, int... dices) {
		int sum = 0;
        for (int at = 0; at != NUMBER_OF_DICE; at++) {
            sum += dices[at] == valueToKeep ? valueToKeep : 0 ;
        }
        return sum;
	}

    public static int score_pair(int... dices)
    {
        return xOfAKind(2, dices);
    }

	private static int[] whichDiceContainsAtLeast(int[] dices, int threshold, boolean strict) {
        int[] counts = analyseDices(dices);
		return IntStream.range(1, counts.length+1)
				.filter(x -> counts[x-1] >= threshold && !strict || counts[x-1] == threshold && strict)
				.toArray();
	}

	private static int[] analyseDices(int... dices) {
		int[] counts = new int[MAX_VALUE_OF_DICE];
        for (int die : dices)
            counts[die-1]++;
		return counts;
	}

    public static int two_pair(int... dices)
    {
        int[] compatibles = whichDiceContainsAtLeast(dices, 2, false);
        int retour = (compatibles.length<=1)?0:(compatibles[compatibles.length-1]+compatibles[compatibles.length-2])*2;
        return retour;
    }

    public static int four_of_a_kind(int... dices)
    {
        return xOfAKind(4, dices);
    }

    public static int three_of_a_kind(int... dices)
    {
        return xOfAKind(3, dices);
    }

	private static int xOfAKind( int threshold, int... dices) {
		int[] compatibles = whichDiceContainsAtLeast(dices, threshold, false);
        return compatibles.length < 1 ? 0 : compatibles[compatibles.length-1]*threshold;
	}

    public static int smallStraight(int... dices)
    {
    	int[] counts = analyseDices(dices);
    	return allSetToOne(counts, 0, 4) ? 15 : 0;
    }

	private static boolean allSetToOne(int[] counts, int minDiceValue, int maxDiceValue) {
		return IntStream.range(minDiceValue, maxDiceValue).allMatch(x -> counts[x] == 1);    		
	}

    public static int largeStraight(int... dices)
    {
    	int[] counts = analyseDices(dices);
    	return allSetToOne(counts, 1, 5) ? 20 : 0;
    }

    public static int fullHouse(int... dices)
    {
        int[] bigSide = whichDiceContainsAtLeast(dices, 3, true);
        int[] smallSide = whichDiceContainsAtLeast(dices, 2, true);
        return bigSide.length == 1 && smallSide.length == 1 ? bigSide[0] *3 + smallSide[0] * 2 :0;
    }
}
