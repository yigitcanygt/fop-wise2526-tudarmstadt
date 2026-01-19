package h10;

import h10.animals.birds.Penguin;
import h10.animals.mammals.JugglingLion;
import h10.animals.mammals.Lion;
import org.junit.jupiter.api.Test;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * An example JUnit test class.
 */
public class EnclosureTest {

    @Test
    @StudentImplementationRequired("H10.5.1")
    public void testAnyMatch() {
        Lion aslan1 = new Lion("Simba", 5);
        Lion aslan2 = new Lion("Mufasa", 8);
        JugglingLion jongleAslan = new JugglingLion("Alex", 4);
        GroundEnclosure<Lion> gehege = new GroundEnclosure<>();
        gehege.getAnimals().push(aslan1);
        gehege.getAnimals().push(aslan2);
        assertFalse(gehege.anyMatch(Enclosure.HAS_2_LEGS),
            "Gehege'de 2 bacakli hayvan olmamali");
        gehege.getAnimals().push(jongleAslan);
        assertTrue(gehege.anyMatch(Enclosure.HAS_2_LEGS),
            "Gehege'de 2 bacakli hayvan olmali");
    }


    @Test
    @StudentImplementationRequired("H10.5.2")
    public void testMapFunctional() {
        Penguin penguen1 = new Penguin("Pingu", 1);
        Penguin penguen2 = new Penguin("Poncho", 2);
        Penguin penguen3 = new Penguin("Pepper", 3);
        WaterEnclosure<Penguin> suGehege = new WaterEnclosure<>();
        suGehege.getAnimals().push(penguen1);
        suGehege.getAnimals().push(penguen2);
        suGehege.getAnimals().push(penguen3);
        String tabelaMetni = suGehege
            .mapFunctional(
                () -> new GroundEnclosure<>(),
                p -> new Penguin("Sir " + p.getName(), p.getAge() + 1)
            )
            .mapFunctional(
                () -> new WaterEnclosure<>(),
                p -> p
            )
            .reduce(
                "Adult Penguin Pool: ",
                Enclosure.SIGN_WITH_ANIMAL_NAME
            );
        String beklenen = "Adult Penguin Pool: \n" +
            "Sir Pingu (2 years old)\n" +
            "Sir Poncho (3 years old)\n" +
            "Sir Pepper (4 years old)";
        assertEquals(beklenen, tabelaMetni, "Tabela metni yanlis!");
    }
}
