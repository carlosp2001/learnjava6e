package ch08.examples.game;

import javax.swing.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Our apple tossing game. This class extends JFrame to create our
 * main application window. We can now setup several trees for
 * target practice. (The ability for the player to aim and throw
 * will be covered in Chapter 10.)
 */
public class AppleToss extends JFrame {

    public static final int FIELD_WIDTH = 800;
    public static final int FIELD_HEIGHT = 600;

    Field field = new Field();
    Physicist player1 = new Physicist();

    // Helper class
    Random random = new Random();

    public AppleToss() {
        // Create our frame
        super("Apple Toss Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FIELD_WIDTH, FIELD_HEIGHT);
        setResizable(false);

        // Build the field with our player and some trees
        setupFieldForOnePlayer();
    }

    /**
     * Helper method to return a good x value for a tree so it's not off the left or right edge.
     *
     * @return x value within the bounds of the playing field width
     */
    private int goodX() {
        // at least half the width of the tree plus a few pixels
        int leftMargin = Field.TREE_WIDTH_IN_PIXELS / 2 + 5;
        // now find a random number between a left and right margin
        int rightMargin = FIELD_WIDTH - leftMargin;

        // And return a random number starting at the left margin
        return leftMargin + random.nextInt(rightMargin - leftMargin);
    }

    /**
     * Helper method to return a good y value for a tree so it's
     * not off the top or bottom of the screen.
     *
     * @return y value within the bounds of the playing field height
     */
    private int goodY() {
        // at least half the height of the "leaves" plus a few pixels
        int topMargin = Field.TREE_WIDTH_IN_PIXELS / 2 + 5;
        // a little higher off the bottom
        int bottomMargin = FIELD_HEIGHT - Field.TREE_HEIGHT_IN_PIXELS;

        // And return a random number starting at the top margin but not past the bottom
        return topMargin + random.nextInt(bottomMargin - topMargin);
    }

    /**
     * A helper method to populate a one player field with target trees.
     */
    private void setupFieldForOnePlayer() {
        // Place our (new) physicist in the lower left corner and connect them to the field
        player1.setPosition(100, 500);
        field.setPlayer(player1);
        player1.setField(field);

        // And now make a few trees for target practice
        for (int i = field.trees.size(); i < Field.MAX_TREES; i++) {
            Tree t = new Tree();
            t.setPosition(goodX(), goodY());
            // Trees can be close to each other and overlap,
            // but they shouldn't intersect our physicist
            while (player1.isTouching(t)) {
                // We do intersect this tree, so let's try again
                t.setPosition(goodX(), goodY());
                System.err.println("Repositioning an intersecting tree...");
            }
            field.addTree(t);
        }
        add(field);
    }

    public static void main(String args[]) {
//        AppleToss game = new AppleToss();
//        game.setVisible(true);

        // Manipular fechas agregando dias o semanas
        LocalDate reminder = LocalDate.now();
        reminder = reminder.plusWeeks(1);

        LocalDateTime betterReminder = reminder.atTime(LocalTime.of(9, 0));

        System.out.println(betterReminder);

        // Utilizando zonas horarias con fechas
        LocalDateTime piLocal = LocalDateTime.parse("2023-03-14T02:00");

        ZonedDateTime piCentral = piLocal.atZone(ZoneId.of("America/Tegucigalpa"));
        System.out.println("piCentral " + piCentral);

        ZonedDateTime piAlaMode = piCentral.withZoneSameInstant(ZoneId.of("Europe/Paris"));
        System.out.println("piAlaMode" + piAlaMode);

        // Convertimos la fecha a la nueva zona horaria
        System.out.println(piCentral.withZoneSameInstant(ZoneId.systemDefault()));

        // Utilizando el formateador de texto
        DateTimeFormatter shortUs = DateTimeFormatter.ofPattern("M-d-y H:m z");

        System.out.println(ZonedDateTime.parse("12-10-2021 12:30 EST", shortUs));
    }
}
