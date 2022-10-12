package environment;

/**
 * Trieda obsahujuca operatory typicke pre kona ako figurku v sachu.
 * @author Filip Mojto
 */
public final class Knight {
    private final static String[] knightMoveOperators = {"+1+2", "+1-2", "-1+2", "-1-2", "+2+1", "+2-1", "-2+1", "-2-1"};
    public static String[] getKnightMoveOperators() {
        return knightMoveOperators;
    }

    private Knight(){}
}
