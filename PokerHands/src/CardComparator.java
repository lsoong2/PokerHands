import java.util.*;

public class CardComparator implements Comparator<Card> {
	public int compare(Card c1, Card c2) {
		// sorts from highest to lowest.
		if (c1.Value != c2.Value) {
			return c2.Value.ordinal() - c1.Value.ordinal();
		}
		if (c1.Suit != c2.Suit) {
			return c2.Suit.ordinal() - c1.Suit.ordinal();
		}
		return 0;
	}
}
