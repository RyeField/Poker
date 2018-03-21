/**
 * This is a Rank enum which store all the possible rank value in a poker
 *
 * @author Jian Shi
 * @loginID: shij3
 * @email: shijianhzchina@gmail.com
 * @date: 9/21/2017 21:55
 *
 * The Rank enum help to form the poker cards, judge the input cards, classify
 * the rank of card in one player's hand (use default compareTo method) and
 * return the final result.
 */

public enum Rank {
    /**
     * List of constant name of this Rank enum with each of them have it's
     * field value to represent the input value of the rank in a card.
     */
    Two("2"), Three("3"), Four("4"), Five("5"),
    Six("6"), Seven("7"), Eight("8"), Nine("9"),
    Ten("T"), Jack("J"), Queen("Q"), King("K"), Ace("A");

    //define the field of the Rank enum
    private final String rank;

    /**
     * The constructor of the Rank enum, the field value rank must be supplied
     * to the constructor of the enum when having a field.
     *
     * @param rank - The field of each enum constant
     */
    Rank(String rank) {
        this.rank = rank;
    }

    //get the rank field value
    public String getRank() {
        return rank;
    }

    /**
     * Return the enum constant name of the given specific rank field value.
     *
     * @param rankVal - The field of a specific enum constant
     * @return   return the enum constant name of the given specific field value
     */
    public static String getName(String rankVal) {
        for (Rank rankValue : Rank.values()) {
            if (rankVal.equals(rankValue.getRank())) {
                return rankValue.name();
            }
        }
        return null;
    }

    /**
     * Return the request format of the rank value.
     * if the rank is smaller than 10 than just return field value
     * if the rank is equals 10 than just return "10"
     * if the rank is larger than 10 than just return constant name
     *
     * @return   return the request format of the rank value.
     */
    public String getResult() {
        if (Rank.valueOf(Rank.getName(this.getRank()))
                .compareTo(Rank.Ten) < 0) {
            return this.getRank();
        } else if (Rank.valueOf(Rank.getName(this.getRank()))
                .compareTo(Rank.Ten) == 0) {
            return "10";
        } else {
            return this.name();
        }
    }
}
