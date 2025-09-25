package maven.hexagon.reaktorjava;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.gson.Gson;
public class Reaktor {
    private ReactionEngine engine = new ReactionEngine();

    public Atom createAtom(int protons, int neutrons, int electrons, String elementName,
                           boolean explosive, double explosionStrength,
                           boolean flammable, double fireStrength,
                           String state, String color) {
        return new Atom(protons, neutrons, electrons, elementName,
                explosive, explosionStrength, flammable, fireStrength, state, color);
    }

    public List<Atom> react(List<Atom> atoms) {
        return engine.react(atoms);
    }

    public String toJson(List<Atom> atoms) {
        return new Gson().toJson(atoms);
    }
}