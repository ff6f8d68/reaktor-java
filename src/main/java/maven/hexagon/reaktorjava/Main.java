package maven.hexagon.reaktorjava;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Step 1 — create atoms
        List<Atom> atoms = new ArrayList<>();
        atoms.add(new Atom(1, 0, 1, "Hydrogen", false, 0, true, 1, "gas", "#FFFFFF"));
        atoms.add(new Atom(8, 8, 8, "Oxygen", false, 0, true, 2, "gas", "#FF0000"));
        atoms.add(new Atom(6, 6, 6, "Carbon", false, 0, true, 1.5, "solid", "#000000"));

        // Step 2 — create Reaction Engine
        ReactionEngine engine = new ReactionEngine();

        // Step 3 — call react
        List<Atom> products = engine.react(atoms);

        // Step 4 — convert predicted products to JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(products);

        // Step 5 — print JSON
        System.out.println(jsonOutput);
    }
}
