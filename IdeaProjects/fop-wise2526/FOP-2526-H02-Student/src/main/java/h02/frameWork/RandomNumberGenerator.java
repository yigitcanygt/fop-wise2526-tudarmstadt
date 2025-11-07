package h02.frameWork;

final class RandomNumberGenerator {

    private RandomNumberGenerator(){
        // prevents instantiation
    }

     /**
     * returns a number between 0 (inclusive) and max (exclusive)
     *
     * @param max upper bound
     * @return random number n (0 <= n < max)
     */
    static int getRandomNumber(int max) {
        return (int) (Math.random() * (max));
    }
}
