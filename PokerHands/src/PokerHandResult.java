import java.util.ArrayList;

public class PokerHandResult {
	public PokerHandResult(ArrayList<Card> hand) {
		assert(hand.size() == 5);
		
		this.Rank = PokerHandRank.HighCard;

		int matchedScore = 0;
		int unmatchedScore = 0;

		int countMatchingValues = 0;
		int maxMatchingValues = 1;
		int matchedSoFar = 1;
		Card card = hand.get(0);
		CardValue value = card.Value;

		// This needs to be greater than the value of an Ace.
		final int valueBase = 15;
		// This needs to be greater than the maximum score from 5 unmatched cards.
		final int maxUnmatchedScore = valueBase * valueBase * valueBase * valueBase * valueBase;
		// This needs to be the maximum score from 2 sets of matched cards.
		final int maxMatchedScore = valueBase * valueBase;
		// This should not overflow.
		assert(maxUnmatchedScore * maxMatchedScore > 0);
		
		// Enumerate through the cards, scoring the unmatched cards and matched cards.
		// Also, keep track of the number of matched values, as well as the highest
		// count of matched values.
		for (int i = 1; i <= hand.size(); i++) {
			if (i < hand.size()) {
				card = hand.get(i);
				if (card.Value == value) {
					matchedSoFar++;
					continue;
				}
			}
			
			// Compute the end of a value. Either there is no next card or it has a different value.
			int score = value.ordinal() + 1; // start at 1, instead of 0 
			if (matchedSoFar > 1) {
				// There is at least two occurrences of that value.
				countMatchingValues++;
				assert(countMatchingValues <= 2);
				
				if (maxMatchingValues < 2) {
					// This is the first set of multiple values so far.
					maxMatchingValues = matchedSoFar;
					matchedScore = score;
				} else if (matchedSoFar > maxMatchingValues) {
					// Number of occurrences is the largest so far.
					assert(matchedScore < valueBase);
					maxMatchingValues = matchedSoFar;
					matchedScore += score * valueBase;
				} else {
					// Number of occurrences is tied or fewer than the largest so far.
					matchedScore *= valueBase;
					matchedScore += score;
				}
			} else {
				// The value only occurred once.
				unmatchedScore *= valueBase;
				unmatchedScore += score;
			}
			matchedSoFar = 1;
			value = card.Value;
		}
		
		// Compute the secondary score.
		this.SecondaryScore = matchedScore * maxUnmatchedScore;
		this.SecondaryScore += unmatchedScore;
		
		// Compute rank based off of matched values.
		if (maxMatchingValues == 4) {
			this.Rank = PokerHandRank.FourOfAKind;
		} else if (maxMatchingValues == 3) {
			if (countMatchingValues > 1) {
				this.Rank = PokerHandRank.FullHouse;
			} else {
				this.Rank = PokerHandRank.ThreeOfAKind;
			}
		} else if (maxMatchingValues == 2) {
			if (countMatchingValues > 1) {
				this.Rank = PokerHandRank.TwoPairs;
			} else {
				this.Rank = PokerHandRank.Pair;
			}
		}
		
		// If at least one pair wasn't found, it's possible there is a straight,
		// flush, or both. If something had been found, this would not be possible.
		if (this.Rank == PokerHandRank.HighCard) {
			boolean isFlush  = isFlush(hand);
			boolean isStraight = isStraight(hand);
			if (isStraight) {
				if (hand.get(0).Value == CardValue.Ace &&
					hand.get(4).Value == CardValue.Two) {
					// If this is a straight with an ace as a low card,
					// adjust the score so ace is scored low rather than high.
					final int aceScore = CardValue.Ace.ordinal() + 1;
					final int aceHighScore = aceScore * valueBase * valueBase * valueBase * valueBase;
					// Remove the ace high score.
					this.SecondaryScore -= aceHighScore;
					// Shift the remaining four cards left one digit.
					this.SecondaryScore *= valueBase;
					// Add back the low ace (value is 0).
				}
			}
			if (isFlush && isStraight) {
				this.Rank = PokerHandRank.StraightFlush;
			} else if (isFlush) {
				this.Rank = PokerHandRank.Flush;
			} else if (isStraight) {
				this.Rank = PokerHandRank.Straight;
			}
		}
	}
	
	public static int compare(PokerHandResult r1, PokerHandResult r2) {
		// Compare the rank order first.
		int score1 = r1.Rank.ordinal();
		int score2 = r2.Rank.ordinal();		
		if (score1 != score2) {
			return score1 - score2;
		}
		
		// In the case of a tie, compare the secondary score.
		score1 = r1.SecondaryScore;
		score2 = r2.SecondaryScore;
		return score1 - score2;
	}

	
	private static boolean isFlush(ArrayList<Card> hand) {		
		Card card = hand.get(0);
		CardSuit suit = card.Suit;
		for (int i = 1; i < hand.size(); i++) {
			card = hand.get(i);
			if (card.Suit != suit) {
				return false;
			}
		}
		return true;
	}
	
	private static boolean isStraight(ArrayList<Card> hand) {
		assert(hand.size() == 5);

		Card card = hand.get(0);
		CardValue value = card.Value;

		for (int i = 1; i < hand.size(); i++) {
			card = hand.get(i);
			
			if (i == 1 && value == CardValue.Ace && card.Value == CardValue.Five) {
				value = card.Value;
				continue;
			}
			
			if (card.Value.ordinal() != value.ordinal() - 1) {
				return false;
			}

			value = card.Value;
		}
		
		return true;
	}

	// This is the primary method for comparing hands.
	public PokerHandRank Rank;
	// In the case of a tie with the Rank, the secondary score is used.
	public int SecondaryScore;
}
