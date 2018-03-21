import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * This is a Player class which represent one player in the poker game to
 * characterise the player's card.
 *
 * @author Jian Shi
 * @loginID: shij3
 * @email: shijianhzchina@gmail.com
 * @date: 9/22/2017 00:45
 *
 * The Player class is representing the player in the poker game, It handle
 * the task of characterising the player's hand card type, it also have the
 * method compareTo to decide the winner between two players.
 */
public class Player {

    //Define the Number of card in one hand is 5
    private static final int MAX_HAND_NUM = 5;

    //Define the DRAW, WIN and LOSE to 0, 1 and -1 respectively
    private static final int DRAW = 0;
    private static final int WIN = 1;
    private static final int LOSE = -1;

    //Define different priority for different categories
    //with 1 for highest priority and 9 for lowest priority
    private static final int PRIORITY_SF = 1;
    private static final int PRIORITY_FK = 2;
    private static final int PRIORITY_FH = 3;
    private static final int PRIORITY_F = 4;
    private static final int PRIORITY_S = 5;
    private static final int PRIORITY_TK = 6;
    private static final int PRIORITY_TP = 7;
    private static final int PRIORITY_OP = 8;
    private static final int PRIORITY_HC = 9;

    //Define 2 arraylist to store rank and suit in
    private ArrayList<Rank> ranks = new ArrayList<>(MAX_HAND_NUM);
    private ArrayList<Suit> suits = new ArrayList<>(MAX_HAND_NUM);
    //Define 2 HashSet to store unique rank and suit in
    private HashSet<Rank> distinctRank = new HashSet<>();
    private HashSet<Suit> distinctSuit = new HashSet<>();

    //Define a array to store the sorted unique rank
    private Rank[] sortedDistinctRank;

    //Define 2 result rank for using in description return
    private Rank resultRank1, resultRank2;
    //Define player's priority
    private int priority;

    /**
     * The constructor of the Player class, take the input card as the
     * parameter. spilt the input String card into 2 parts rank and suit
     * using arraylist to store them, sort them wait for using in judge part
     * and compare part.
     *
     * @param hand - The input card array
     */
    public Player(ArrayList<String> hand) {
        for (String card : hand) {
            Rank rank = Rank.valueOf(Rank.getName(card.substring(0, 1)));
            Suit suit = Suit.valueOf(Suit.getName(card.substring(1, 2)));
            this.ranks.add(rank);
            this.distinctRank.add(rank);
            this.suits.add(suit);
            this.distinctSuit.add(suit);
            this.sortedDistinctRank = new Rank[this.distinctRank.size()];
            this.sortedDistinctRank
                    = this.distinctRank.toArray(sortedDistinctRank);
            Arrays.sort(this.sortedDistinctRank);
        }
    }


    /**
     * Make a judgement of player's hand cards from the sequence of high
     * priority to low priority
     *
     * @return   return the description of the player's hand cards
     */
    private String description() {
        if (isStraightFlush()) {
            return resultRank1.getResult() + "-high straight flush";
        } else if (isFourofaKind()) {
            return "Four " + resultRank1.getResult() + "s";
        } else if (isFullHouse()) {
            return resultRank1.getResult() + "s full of "
                    + resultRank2.getResult() + "s";
        } else if (isFlush()) {
            return resultRank1.getResult() + "-high flush";
        } else if (isStraight()) {
            return resultRank1.getResult() + "-high straight";
        } else if (isThreeofaKind()) {
            return "Three " + resultRank1.getResult() + "s";
        } else if (isTwoPair()) {
            return resultRank1.getResult() + "s over "
                    + resultRank2.getResult() + "s";
        } else if (isOnePair()) {
            return "Pair of " + resultRank1.getResult() + "s";
        } else if (isHighCard()) {
            return resultRank1.getResult() + "-high";
        }
        return null;
    }

    /**
     * This method to check whether the player's hand card is Straight Flush,
     * if true, then set the player's priority
     *
     * @return true - if the player's hand card is Straight Flush
     */
    private boolean isStraightFlush() {
        //if the hand card is Straight Flush
        //then the number of unique rank is 5 and the number of unique suit is 1
        if (distinctRank.size() == 5 && distinctSuit.size() == 1) {
            if (sortedDistinctRank[distinctRank.size() - 1]
                    .compareTo(sortedDistinctRank[0]) == MAX_HAND_NUM - 1) {
                resultRank1 = sortedDistinctRank[distinctRank.size() - 1];
                priority = PRIORITY_SF;
                return true;
            }
        }
        return false;
    }

    /**
     * This method to check whether the player's hand card is Four of a Kind,
     * if true, then set the player's priority
     *
     * @return true - if the player's hand card is Four of a Kind
     */
    private boolean isFourofaKind() {
        boolean result = false;
        //if the hand card is Four of a Kind then the number of unique rank is 2
        if (distinctRank.size() == 2) {
            result = nKind(distinctRank.size(), 4);
        }
        priority = PRIORITY_FK;
        return result;
    }

    /**
     * This method to check whether the player's hand card is Full House,
     * if true, then set the player's priority
     *
     * @return true - if the player's hand card is Full House
     */
    private boolean isFullHouse() {
        //if the hand card is Full House then the number of unique rank is 2
        if (distinctRank.size() == 2 && !this.isFourofaKind()) {
            int[] count = new int[distinctRank.size()];
            for (Rank rank : ranks) {
                for (int i = 0; i < distinctRank.size(); i++) {
                    if (rank.compareTo(sortedDistinctRank[i]) == 0) {
                        count[i]++;
                    }
                }
            }
            //find the card rank that repeat for 3 times and 2 times
            for (int i = 0; i < distinctRank.size(); i++) {
                if (count[i] == 3) {
                    resultRank1 = sortedDistinctRank[i];
                }
                if (count[i] == 2) {
                    resultRank2 = sortedDistinctRank[i];
                }
            }
            priority = PRIORITY_FH;
            return true;
        }
        return false;
    }

    /**
     * This method to check whether the player's hand card is Flush,
     * if true, then set the player's priority
     *
     * @return true - if the player's hand card is Flush
     */
    private boolean isFlush() {
        //if the hand card is Flush then the number of unique suit is 1
        if (distinctSuit.size() == 1) {
            priority = PRIORITY_F;
            resultRank1 = sortedDistinctRank[sortedDistinctRank.length - 1];
            return true;
        }
        return false;
    }

    /**
     * This method to check whether the player's hand card is Straight,
     * if true, then set the player's priority
     *
     * @return true - if the player's hand card is Straight
     */
    private boolean isStraight() {
        //if the hand card is Straight then the number of unique rank is 5
        if (distinctRank.size() == 5) {
            //(last element - first element) in sorted arrylist should be 4
            if (sortedDistinctRank[distinctRank.size() - 1]
                    .compareTo(sortedDistinctRank[0]) == MAX_HAND_NUM - 1) {
                resultRank1 = sortedDistinctRank[distinctRank.size() - 1];
                priority = PRIORITY_S;
                return true;
            }
        }
        return false;
    }

    /**
     * This method to check whether the player's hand card is Three of a kind,
     * if true, then set the player's priority
     *
     * @return true - if the player's hand card is Three of a kind
     */
    private boolean isThreeofaKind() {
        boolean result = false;
        //if the card is Three of a kind then the number of unique rank is 3
        if (distinctRank.size() == 3) {
            result = nKind(distinctRank.size(), 3);
        }
        priority = PRIORITY_TK;
        return result;
    }

    /**
     * This method to check whether the player's hand card is Two Pair,
     * if true, then set the player's priority
     *
     * @return true - if the player's hand card is Two Pair
     */
    private boolean isTwoPair() {
        //if the card is Two Pair then the number of unique rank is 3
        if (distinctRank.size() == 3 && !this.isThreeofaKind()) {
            ArrayList<Integer> countPosition = new ArrayList<>();
            int[] count = new int[distinctRank.size()];
            for (Rank rank : ranks) {
                for (int i = 0; i < distinctRank.size(); i++) {
                    if (rank.compareTo(sortedDistinctRank[i]) == 0) {
                        count[i]++;
                    }
                }
            }
            //find the card rank that repeat for 2 times
            for (int i = 0; i < distinctRank.size(); i++) {
                if (count[i] == 2) {
                    countPosition.add(i);
                }
            }
            priority = PRIORITY_TP;
            //the larger Rank number should be in the latter position in the
            //sorted arraylist
            resultRank2 = sortedDistinctRank[countPosition.get(0)];
            resultRank1 = sortedDistinctRank[countPosition.get(1)];
            return true;
        }
        return false;
    }

    /**
     * This method to check whether the player's hand card is One Pair,
     * if true, then set the player's priority
     *
     * @return true - if the player's hand card is One Pair
     */
    private boolean isOnePair() {
        boolean result = false;
        //if the card is One Pair then the number of unique rank is 4
        if (distinctRank.size() == 4) {
            result = nKind(distinctRank.size(), 2);
        }
        priority = PRIORITY_OP;
        return result;
    }

    /**
     * This method to check whether the player's hand card is High Card,
     * if true, then set the player's priority
     *
     * @return true - if the player's hand card is High Card
     */
    private boolean isHighCard() {
        //if the card is High Card then the number of unique rank is 5
        //and the rank can not be a sequence
        if (distinctRank.size() == 5) {
            if (sortedDistinctRank[distinctRank.size() - 1]
                    .compareTo(sortedDistinctRank[0]) > MAX_HAND_NUM - 1) {
                resultRank1 = sortedDistinctRank[distinctRank.size() - 1];
                priority = PRIORITY_HC;
                return true;
            }
        }
        return false;
    }

    /**
     * This method is used to check whether a card have a multiple times of
     * same rank.
     * For example:
     * FourofaKind have the countNum '4'
     * ThreeofaKind have the countNum '3'
     * OnePair have the countNum '2'
     *
     * @return true - if the hand card exist such a repeat pattern
     */
    private boolean nKind(int distinctRankSize, int countNum) {
        int[] count = new int[distinctRankSize];
        //count the num of unique rank number's repeat time.
        for (Rank rank : ranks) {
            for (int i = 0; i < distinctRankSize; i++) {
                if (rank.compareTo(sortedDistinctRank[i]) == 0) {
                    count[i]++;
                }
            }
        }
        //find the repeat rank number
        for (int i = 0; i < distinctRankSize; i++) {
            if (count[i] == countNum) {
                resultRank1 = sortedDistinctRank[i];
                return true;
            }
        }
        return false;
    }

    /**
     * This method is used to return the player's priority.
     *
     * @return the player's priority
     */
    public int getPriority() {
        return priority;
    }


    /**
     * This method is used to compare two player and decide the who is the
     * winner.
     *
     * @param that - The compare player
     * @return   return the result of comparison
     */
    public int compareTo(Player that) {
        if ((this.isTwoPair() || this.isFullHouse())
                && !this.isStraightFlush() && !this.isFourofaKind()
                && !this.isFlush() && !this.isStraight()
                && !this.isThreeofaKind() && !this.isOnePair()
                && !this.isHighCard()) {
            /**
             * Situation 1 Compare those who are TwoPair or FullHouse
             * They have 2 rank result need to be consider
             */
            Rank thisRankOne
                    = Rank.valueOf(Rank.getName(this.resultRank1.getRank()));
            Rank thatRankOne
                    = Rank.valueOf(Rank.getName(that.resultRank1.getRank()));
            Rank thisRankTwo
                    = Rank.valueOf(Rank.getName(this.resultRank2.getRank()));
            Rank thatRankTwo
                    = Rank.valueOf(Rank.getName(that.resultRank2.getRank()));
            /**
             * Situation 1.1 Compare first rank result
             */
            if (thisRankOne.compareTo(thatRankOne) < 0) {
                return LOSE;
            } else if (thisRankOne.compareTo(thatRankOne) > 0) {
                return WIN;
            } else {
                /**
                 * Situation 1.2 Compare second rank result
                 * if first rank is equal
                 */
                if (thisRankTwo.compareTo(thatRankTwo) < 0) {
                    return LOSE;
                } else if (thisRankTwo.compareTo(thatRankTwo) > 0) {
                    return WIN;
                } else {
                    /**
                     * Situation 1.3 Compare rest rank result
                     * if first and second rank is equal
                     */
                    this.distinctRank.remove(thisRankOne);
                    this.distinctRank.remove(thisRankTwo);
                    that.distinctRank.remove(thatRankOne);
                    that.distinctRank.remove(thatRankTwo);
                    for (Rank thisRank : this.distinctRank) {
                        for (Rank thatRank : that.distinctRank) {
                            if (thisRank.compareTo(thatRank) < 0) {
                                return LOSE;
                            } else if (thisRank.compareTo(thatRank) > 0) {
                                return WIN;
                            }
                        }
                    }
                    return DRAW;
                }
            }
        } else {
            /**
             * Situation 2 Compare those rest situation
             */
            Rank thisRankOne
                    = Rank.valueOf(Rank.getName(this.resultRank1.getRank()));
            Rank thatRankOne
                    = Rank.valueOf(Rank.getName(that.resultRank1.getRank()));
            /**
             * Situation 2.1 Compare first rank result
             */
            if (thisRankOne.compareTo(thatRankOne) < 0) {
                return LOSE;
            } else if (thisRankOne.compareTo(thatRankOne) > 0) {
                return WIN;
            } else {
                /**
                 * Situation 2.2 Compare rest rank result
                 */
                int rankLength = this.sortedDistinctRank.length;
                for (int i = rankLength - 1; i >= 0; i--) {
                    if (this.sortedDistinctRank[i]
                            .compareTo(that.sortedDistinctRank[i]) < 0
                            && !this.sortedDistinctRank[i].equals(thisRankOne)){
                        return LOSE;
                    } else if (this.sortedDistinctRank[i]
                            .compareTo(that.sortedDistinctRank[i]) > 0
                            && !this.sortedDistinctRank[i].equals(thisRankOne)){
                        return WIN;
                    }
                }
                return DRAW;
            }
        }
    }

    /**
     * This method just override with return the description of the player's
     * hand card
     *
     * @return   return the player's hand card
     */
    @Override
    public String toString() {
        return description();
    }
}
