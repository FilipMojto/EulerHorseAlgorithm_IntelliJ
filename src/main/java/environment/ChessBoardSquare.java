package environment;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Trieda reprezentujuca policko na sachovnici. Obsahuje dolezite udaje o policku: suradnice, agregaciu na prechazajuci uzol a zoznam suradnic
 * zakazanych policok.
 * @author Filip Mojto
 *
 */
public final class ChessBoardSquare {
    private final int dimensionX;
    private final int dimensionY;
    private ChessBoardSquare previousSquare;
    private final ArrayList<String> bannedSquareDimensions = new ArrayList<>(8);

    public int getDimensionY() {
        return dimensionY;
    }

    public int getDimensionX() {
        return dimensionX;
    }

    public ChessBoardSquare getPreviousSquare() {
        return previousSquare;
    }

    void addBannedSquareDimensions(final String  bannedSquareDimensions){
        Objects.requireNonNull(bannedSquareDimensions);
        this.bannedSquareDimensions.add(0, bannedSquareDimensions);
    }

    ArrayList<String> getBannedSquareDimensions(){
        return this.bannedSquareDimensions;
    }

    void setPreviousSquare(final ChessBoardSquare previousSquare){
        this.previousSquare = previousSquare;
    }

    ChessBoardSquare(final int dimensionX, final int dimensionY){
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
    }
}
