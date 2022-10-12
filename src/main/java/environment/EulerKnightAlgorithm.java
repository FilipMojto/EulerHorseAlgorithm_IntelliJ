package environment;

import java.util.HashMap;
import java.util.Optional;

/**
 * Trieda, v ktorej je implementovany algoritmus prehladavania do hlbky. Dokaze monitorovat cas vykonavania.
 * @author Filip Mojto
 */
public final class EulerKnightAlgorithm {
    private long startingTime;
    private long timeElapsed;

    private final int chessBoardSize;
    private final HashMap<String, String> unvisitedSquares = new HashMap<>();
    private ChessBoardSquare currentSquare;

    public int getChessBoardSize() {
        return chessBoardSize;
    }

    public float getTimeElapsed(){
        return (float)timeElapsed/1000;
    }

    public ChessBoardSquare getCurrentSquare(){
        return currentSquare;
    }

    /**
     * Vykona Eulerovsky pohyb, takze sa pohne len vtedy, ked je to v medziach sachovnice, vtedy, ked je nove policko nenavstivene a nie je zakazane
     * nanho skakat. Pracuje s operatormi reprezentovane stringami.
     * @return
     */
    private boolean makeEulerMove(){
        operator:
        for(var knightOperator : Knight.getKnightMoveOperators()){
            int curDimensionX = currentSquare.getDimensionX(), curDimensionY = currentSquare.getDimensionY(),
                operatorXVal, operatorYVal;

            try {
                operatorXVal = Integer.parseInt(Character.toString(knightOperator.charAt(0)) + knightOperator.charAt(1));
            }
            catch (NumberFormatException e){
                operatorXVal = knightOperator.charAt(1);
            }
            try {
                operatorYVal = Integer.parseInt(Character.toString(knightOperator.charAt(2)) + knightOperator.charAt(3));
            }
            catch (NumberFormatException e){
                operatorYVal = knightOperator.charAt(3);
            }

            if(curDimensionX + operatorXVal > 0 && curDimensionX + operatorXVal <= this.chessBoardSize && curDimensionY +
               operatorYVal > 0 && curDimensionY + operatorYVal <= this.chessBoardSize){
                if(unvisitedSquares.containsKey(Integer.toString(curDimensionX + operatorXVal) + (
                   curDimensionY + operatorYVal))){

                    for(var bannedSquareDimensions : currentSquare.getBannedSquareDimensions()){
                        if(Integer.toString(curDimensionX + operatorXVal).concat(String.valueOf(curDimensionY +
                          operatorYVal)).equals(bannedSquareDimensions)){
                            continue operator;
                        }
                    }

                    var squareToMoveOn = new ChessBoardSquare(curDimensionX + operatorXVal, curDimensionY
                    + operatorYVal);

                    squareToMoveOn.setPreviousSquare(currentSquare);
                    unvisitedSquares.remove(Integer.toString(squareToMoveOn.getDimensionX()) + squareToMoveOn
                    .getDimensionY());
                    currentSquare = squareToMoveOn;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Vykonava Eulerovske pohyby dovtedy, dokym sa to da. Rovnako kontroluje, ci uz nie je prekroceny casovy limit.
     * @param timeLimit - limit, po prekroceni ktoreho algoritmus konci a signalizuje neuspesne hladanie
     * @return je zoznam nenavstivenych policok prazdny ?
     * @throws InterruptedException limit bol prekroceny
     */
    private boolean performDepthSearch(final long timeLimit) throws InterruptedException {
        while(this.makeEulerMove()){
            if(System.currentTimeMillis() - this.startingTime > timeLimit){
                throw new InterruptedException();
            }
        }
        return this.unvisitedSquares.isEmpty();
    }

    /**
     * Vykona spatny pohyb kona vtedy, ked existuje predchadzajuci uzol, nemozno vykonat Eulerovsky pohyb a este su nejake nenavstivene policka.
     * @return true - pohyb bol vykonany, jestvuje predchazajuci uzol, inac false
     */
    private boolean makeReverseMove(){
        if(Optional.ofNullable(currentSquare.getPreviousSquare()).isPresent()){
            currentSquare.getPreviousSquare().addBannedSquareDimensions(Integer.toString(currentSquare.getDimensionX())
            + currentSquare.getDimensionY());
            this.unvisitedSquares.put(Integer.toString(currentSquare.getDimensionX()) + currentSquare.getDimensionY(), null);

            currentSquare = currentSquare.getPreviousSquare();
            return true;
        }

        return false;
    }

    /**
     * Metoda, ktora spusta test algoritmu.
     * @param timeLimit - Limit, pocas ktoreho musi algoritmus najst riesenie
     * @param startingDimensionX - Pociatocna x-ova pozicia
     * @param startingDimensionY - Pociatocny y-ova pozicia
     * @return true, ak najde riesenie, inac false
     */
    public boolean startTest(final long timeLimit, final int startingDimensionX, final int startingDimensionY){
        this.startingTime = System.currentTimeMillis();
        this.unvisitedSquares.clear();

        for(var l = 1; l <= this.chessBoardSize; l++){
            for(var c = 1; c <= this.chessBoardSize; c++){
                unvisitedSquares.put(c + Integer.toString(l), null);
            }
        }

        if(startingDimensionX < 1 || startingDimensionX > chessBoardSize || startingDimensionY < 1 || startingDimensionY
        > chessBoardSize){
            throw new IllegalArgumentException("Invalid starting configuration!");
        }

        this.currentSquare = new ChessBoardSquare(startingDimensionX, startingDimensionY);
        unvisitedSquares.remove(Integer.toString(startingDimensionX) + startingDimensionY);

        try {
            while (!this.performDepthSearch(timeLimit)) {
                if (!this.makeReverseMove()) {
                    this.timeElapsed = (System.currentTimeMillis() - startingTime);
                    return false;
                }
            }
        }
        catch (InterruptedException e){
            this.timeElapsed = (System.currentTimeMillis() - startingTime);
            return false;
        }

        this.timeElapsed = (System.currentTimeMillis() - startingTime);
        return true;
    }

    public EulerKnightAlgorithm(final int chessBoardSize){
        if(chessBoardSize < 3){
            throw new IllegalArgumentException("Invalid starting configuration!");
        }

        this.chessBoardSize = chessBoardSize;
    }
}
