package h10.animals.birds;

public class KingPenguin extends Penguin {
    public KingPenguin(String name, int age) {
        super(name, age);
    }

    /**
     * Prints the name of the king penguin and that it is king.
     */
    public void king() {
        System.out.println(this.name + " is King!");
    }
}
