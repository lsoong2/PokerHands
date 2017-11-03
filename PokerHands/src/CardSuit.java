public enum CardSuit {
    Club,
    Heart,
    Diamond,
    Spade;
    
    @Override
    public String toString() {
    	switch(this) {
	    	case Club: return "C";
	    	case Heart: return "H";
	    	case Diamond: return "D";
	    	case Spade: return "S";
	    	default: throw new IllegalArgumentException();
    	}
    }
}
