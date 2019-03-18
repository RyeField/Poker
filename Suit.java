/**
 * This is a Suit enum which store all the possible suit value in a poker
 *
 * @author Jian Shi
 * @email: shijianhzchina@gmail.com
 * @date: 9/21/2017 22:01
 *
 * The Suit enum help to form the poker cards, judge the input cards, classify
 * the suit of card in one player's hand (use default compareTo method) and
 * return the final result.
 */
public enum Suit {
    /**
     * List of constant name of this Suit enum with each of them have it's
     * field value to represent the input value of the suit in a card.
     */
    Clubs("C"), Diamonds("D"), Hearts("H"), Spades("S");

    //define the field of the suit enum
    private final String suit;

    /**
     * The constructor of the Suit enum, the field value suit must be supplied
     * to the constructor of the enum when having a field.
     *
     * @param suit - The field of each enum constant
     */
    Suit(String suit) {
        this.suit = suit;
    }

    //get the suit field value
    public String getSuit() {
        return suit;
    }

    /**
     * Return the enum constant name of the given specific suit field value.
     *
     * @param suitVal - The field of a specific enum constant
     * @return   return the enum constant name of the given specific field value
     */
    public static String getName(String suitVal){
        for(Suit suitValue : Suit.values()){
            if(suitVal.equals(suitValue.getSuit())) {
                return suitValue.name();
            }
        }
        return null;
    }
}
