package h06;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {

        System.out.println("=== H6.6 Spielwiese ===\n");

        System.out.println("1) FuelPoweredCar mit Benzin und leerem Tank erstellt");
        FuelPoweredCar car = new FuelPoweredCar(FuelType.GASOLINE, 0);

        car.fillUp(50);
        System.out.println("2) 50L getankt. Füllstand: " + car.getFillingLevel() + "L\n");

        car.turnOn();
        double consumedCar = car.letMeMove(100000);
        System.out.println("3) Motor gestartet, 100km gefahren");
        System.out.println("   Verbrauchter Kraftstoff: " + consumedCar + "L");
        System.out.println("   Füllstand nach Fahrt: " + car.getFillingLevel() + "L\n");

        System.out.println("4) DrivableLawnMower mit Diesel und 5L Tank erstellt\n");
        DrivableLawnMower mower = new DrivableLawnMower(FuelType.DIESEL, 5);

        mower.spinBlade();
        System.out.println("5) Versuch Klinge zu starten (Motor aus)");
        System.out.println("   Klinge dreht: " + mower.isBladeSpinning() + "\n");

        double mowerMove1 = mower.letMeMove(500);
        System.out.println("6) Versuch 500m zu fahren (Motor aus)");
        System.out.println("   Verbrauch: " + mowerMove1 + "L\n");

        System.out.println("7) Motor des Rasenmähers angeschaltet\n");
        mower.turnOn();

        double mowerMove2 = mower.letMeMove(20000);
        System.out.println("8) Rasenmäher fährt 20km");
        System.out.println("   Verbrauch: " + mowerMove2 + "L");
        System.out.println("   Füllstand: " + mower.getFillingLevel() + "L\n");

        double carTax = TaxCalculator.calculateTax(car, 20);
        System.out.println("9) Steuer für Auto (20L Benzin): " + carTax + " Euro\n");

        double mowerTax = TaxCalculator.calculateTax(mower, 3);
        System.out.println("10) Steuer für Rasenmäher (3L Diesel): " + mowerTax + " Euro\n");

        System.out.println("11) HybridLawnMower mit Diesel und 6L Tank erstellt\n");
        mower = new HybridLawnMower(FuelType.DIESEL, 6);

        double hybridTax = TaxCalculator.calculateTax(mower, 3);
        System.out.println("12) Steuer für Hybrid-Rasenmäher (3L Diesel): " + hybridTax + " Euro\n");

        System.out.println("13) Kettensäge wird gestartet...");
        Chainsaw saw = new Chainsaw();
        int attempts = 0;
        while (!saw.isMotorRunning()) {
            saw.attemptStart();
            attempts++;
        }
        System.out.println("    Kettensäge nach " + attempts + " Versuchen gestartet!\n");

        System.out.println("14) 2 Holzstücke hinzugefügt (strength=2.0, cuttingDepth=5.0)");
        saw.addWood(new Wood(2.0, 5.0));
        saw.addWood(new Wood(2.0, 5.0));
        System.out.println("    Holzstücke vor dem Sägen: " + saw.getRemainingWood() + "\n");

        saw.sawWood(10);
        System.out.println("15) 10 Sekunden gesägt");
        System.out.println("    Verbleibende Holzstücke: " + saw.getRemainingWood() + "\n");

        System.out.println("16) Smartphone erstellt\n");
        Smartphone phone = new Smartphone(exampleTokenDirectory(), exampleMessageReceiver());

        System.out.println("17) Smartphone für 6 Sekunden benutzt:");
        System.out.println("    Nachrichten:");
        phone.use(6);

        System.out.println("\n=== Alle Tests abgeschlossen! ===");
    }

    /**
     * @return an example TokenDirectory that maps the placeholders used in {@link Main#exampleMessageReceiver}
     * to example values
     */
    @DoNotTouch
    private static TokenDictionary exampleTokenDirectory() {
        TokenDictionary dictionary = new TokenDictionary();
        dictionary.putToken("name", "Max Mustermann");
        dictionary.putToken("date", "26/06/2025");
        dictionary.putToken("animal", "Hund");
        dictionary.putToken("university", "TU Darmstadt");

        return dictionary;
    }

    /**
     * @return an example MessageReceiver that provides messages containing common placeholders such as name and date
     */
    @DoNotTouch
    private static MessageReceiver exampleMessageReceiver() {
        MessageReceiver receiver = new MessageReceiver();
        receiver.addMessage("Hello <name>, how are you doing");
        receiver.addMessage("<date> was a wild day");
        receiver.addMessage("Ich habe das an der <university> gelernt");

        return receiver;
    }
}
