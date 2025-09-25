package apps.hexagon.modified.platform;

import java.util.*;
import com.google.gson.Gson;

class Atom {
    int protons;
    int neutrons;
    int electrons;

    boolean explosive;
    double explosionStrength;
    boolean flammable;
    double fireStrength;
    String state; // "solid", "liquid", "gas", "crystal"
    String color;
    String elementName;

    boolean lightsOnFire;
    boolean explodes;

    public Atom(int protons, int neutrons, int electrons, String elementName,
                boolean explosive, double explosionStrength,
                boolean flammable, double fireStrength,
                String state, String color) {
        this.protons = protons;
        this.neutrons = neutrons;
        this.electrons = electrons;
        this.elementName = elementName;

        this.explosive = explosive;
        this.explosionStrength = explosionStrength;
        this.flammable = flammable;
        this.fireStrength = fireStrength;
        this.state = state;
        this.color = color;

        this.lightsOnFire = false;
        this.explodes = false;
    }

    public int valence() {
        return Math.abs(electrons - protons);
    }
}

class ReactionEngine {

    public List<Atom> react(List<Atom> atoms) {
        List<Atom> products = new ArrayList<>();

        for (int size = 2; size <= atoms.size(); size++) {
            List<List<Atom>> groups = generateCombinations(atoms, size);
            for (List<Atom> group : groups) {
                Atom product = tryReaction(group);
                if (product != null) {
                    products.add(product);
                }
            }
        }

        return products;
    }

    private List<List<Atom>> generateCombinations(List<Atom> atoms, int size) {
        List<List<Atom>> combinations = new ArrayList<>();
        combine(atoms, combinations, new ArrayList<>(), 0, size);
        return combinations;
    }

    private void combine(List<Atom> atoms, List<List<Atom>> result, List<Atom> temp, int start, int size) {
        if (temp.size() == size) {
            result.add(new ArrayList<>(temp));
            return;
        }
        for (int i = start; i < atoms.size(); i++) {
            temp.add(atoms.get(i));
            combine(atoms, result, temp, i + 1, size);
            temp.remove(temp.size() - 1);
        }
    }

    private Atom tryReaction(List<Atom> group) {
        int totalValence = 0;
        for (Atom a : group) totalValence += a.valence();

        Random rand = new Random();
        double reactionChance = 0.3;
        if (totalValence % 2 != 0 || rand.nextDouble() > reactionChance) {
            return null;
        }

        int totalProtons = 0, totalNeutrons = 0, totalElectrons = 0;
        boolean explosive = false;
        double explosionStrength = 0;
        boolean flammable = false;
        double fireStrength = 0;
        boolean lightsOnFire = false;
        boolean explodes = false;

        Set<String> states = new HashSet<>();
        List<String> colors = new ArrayList<>();

        for (Atom a : group) {
            totalProtons += a.protons;
            totalNeutrons += a.neutrons;
            totalElectrons += a.electrons;
            explosive |= a.explosive;
            explosionStrength = Math.max(explosionStrength, a.explosionStrength);
            flammable |= a.flammable;
            fireStrength = Math.max(fireStrength, a.fireStrength);
            states.add(a.state);
            colors.add(a.color);

            // Determine effects
            if (a.flammable && rand.nextDouble() < 0.7) lightsOnFire = true;
            if (a.explosive && rand.nextDouble() < 0.5) explodes = true;
        }

        String name = "";
        for (Atom a : group) name += a.elementName + "-";

        Atom product = new Atom(totalProtons, totalNeutrons, totalElectrons, name,
                explosive, explosionStrength * 1.1,
                flammable, fireStrength * 1.05,
                combineState(states), blendColors(colors));

        product.lightsOnFire = lightsOnFire;
        product.explodes = explodes;

        return product;
    }

    private String combineState(Set<String> states) {
        if (states.contains("gas")) return "gas";
        if (states.contains("liquid")) return "liquid";
        if (states.contains("crystal")) return "crystal";
        return "solid";
    }

    private String blendColors(List<String> colors) {
        int r = 0, g = 0, b = 0;
        for (String c : colors) {
            int colorInt = Integer.parseInt(c.substring(1), 16);
            r += (colorInt >> 16) & 0xFF;
            g += (colorInt >> 8) & 0xFF;
            b += colorInt & 0xFF;
        }
        int n = colors.size();
        r /= n; g /= n; b /= n;
        return String.format("#%02X%02X%02X", r, g, b);
    }
}

public class LiveReactionSimulator {
    public static void main(String[] args) {
        List<Atom> atoms = new ArrayList<>();
        atoms.add(new Atom(1, 0, 1, "Hydrogen", false, 0, true, 1, "gas", "#FFFFFF"));
        atoms.add(new Atom(8, 8, 8, "Oxygen", false, 0, true, 2, "gas", "#FF0000"));
        atoms.add(new Atom(6, 6, 6, "Carbon", false, 0, true, 1.5, "solid", "#000000"));

        ReactionEngine engine = new ReactionEngine();
        List<Atom> products = engine.react(atoms);

        Gson gson = new Gson();
        System.out.println(gson.toJson(products));
    }
}
