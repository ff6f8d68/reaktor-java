package maven.hexagon.reaktorjava;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.gson.Gson;

public class Reaktor {
    private ReactionEngine engine = new ReactionEngine();

    /**
     * Creates a new atom and returns it as an Object.
     */
    public Object createAtom(int protons, int neutrons, int electrons, String elementName,
                             boolean explosive, double explosionStrength,
                             boolean flammable, double fireStrength,
                             String state, String color) {
        return new Atom(protons, neutrons, electrons, elementName,
                explosive, explosionStrength, flammable, fireStrength, state, color);
    }

    /**
     * Reacts a list of atoms and returns a list of reaction products as Objects.
     */
    public List<Object> react(List<Object> atoms) {
        List<Atom> atomList = new ArrayList<>();
        for (Object o : atoms) {
            if (o instanceof Atom a) {
                atomList.add(a);
            } else {
                throw new IllegalArgumentException("Invalid object type: " + o.getClass());
            }
        }

        List<Atom> products = engine.react(atomList);
        return new ArrayList<>(products);
    }

    /**
     * Serializes a list of atoms to JSON.
     */
    public String toJson(List<Object> atoms) {
        return new Gson().toJson(atoms);
    }
}
