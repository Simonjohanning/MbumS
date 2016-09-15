/**
 * Created by Simon Johanning for the course work "Odefying Kirkham"
 */
public class Main {
    //Constant to indicate whether the boolean junctions are conjunctions (AND) or disjunctions (OR)
    private static final String BOOLMODE = "OR";
    //number of maximal iterations to detect oscillating models (shouldn't arise though)
    private static final int MAXITERATIONS = 100000;

    private static int numIndexMaxIt = 0;
    private static int numFittingIndices = 0;

    public static void main(String[] args){
        //iterate through all BNs, encoded as elaborated in the code documentation
        for(int index=0;index<243;index++){
            if(testIndexInMedia(index)){
                printMatrix(fillMatrix(index));
                numFittingIndices++;
            //print BN if all matrices should be printed
            }else if(PRINTALLMATRICES){
                printMatrix(fillMatrix(index));
            }
        }
        System.out.println("Number of matrices with too many iterations: "+numIndexMaxIt);
        System.out.println("Number of fitting matrices: "+numFittingIndices);
    }

    /**
     * Test whether BN fits experimental data in all relevant media (BMP2, TGFb1 or both)
     *
     * @param index the integer encoding the BN under consideration
     * @return boolean value indicating whether BN fits experimental data
     */
    private static boolean testIndexInMedia(int index) {
        int BMP2, TGFb1;
        HashMap<String, Boolean> stableExpressionData;
        //test index for medium BMP2
        if(DEBUGFLAG) System.out.println("\n-----------------BMP2 only medium------------------\n");
        BMP2 = 1;
        TGFb1 = 0;
        //get a stable expression profile (steady-state)
        stableExpressionData = stableExpressionProfile(BMP2, TGFb1, fillMatrix(index));
        //if expression profile doesn't fit data, reject it
        if(!stableExpressionProfileDataFit(intToBool(BMP2), intToBool(TGFb1), stableExpressionData)) return false;
        //text index for medium TGFb1
        if(DEBUGFLAG) System.out.println("\n-----------------TGFb1 only medium------------------\n");
        BMP2 = 0;
        TGFb1 = 1;
        //get a stable expression profile (steady-state)
        stableExpressionData = stableExpressionProfile(BMP2, TGFb1, fillMatrix(index));
        //if expression profile doesn't fit data, reject it
        if(!stableExpressionProfileDataFit(intToBool(BMP2), intToBool(TGFb1), stableExpressionData)) return false;
        //test index for both media
        if(DEBUGFLAG) System.out.println("\n-----------------both GFs medium------------------\n");
        BMP2 = 1;
        TGFb1 = 1;
        //get a stable expression profile (steady-state)
        stableExpressionData = stableExpressionProfile(BMP2, TGFb1, fillMatrix(index));
        //if expression profile doesn't fit data, reject it
        if(!stableExpressionProfileDataFit(intToBool(BMP2), intToBool(TGFb1), stableExpressionData)) return false;

        //since expression data has not been rejected, it fits the data in all three cases, and can be accepted
        return true;
    }

    /**
     * Function to test whether the expression data of the BN fits the experimental data in the specified medium.
     * Expression data fit is determined by table 1 in Kirkham et al. 2012
     *
     * @param BMP2 boolean to indicate whether BMP2 is present in the medium
     * @param TGFb1 boolean to indicate whether TGFb1 is present in the medium
     * @param stableExpressionData expression data of the steady state of the BN under consideration
     * @return boolean indicating the expression data of the BN fits the experimental data in the specified medium
     * @throws IllegalArgumentException gets thrown when the combination of GFs in the medium is not allowed / not of interested (i.e. neither BMP2 nor TGFb1 is present in the medium)
     */
    private static boolean stableExpressionProfileDataFit(boolean BMP2, boolean TGFb1, HashMap<String, Boolean> stableExpressionData) throws IllegalArgumentException{
        //if the medium under consideration contains both BMP2 and TFGb1
        if(BMP2 && TGFb1){
            //model fits the data in the BMP2/TGFb1 environment if Dlx5 and Msx2 are expressed, but Runx2 is not
            if(!DEBUGFLAG) return ((stableExpressionData.get("Dlx5")) && (stableExpressionData.get("Msx2")) && !(stableExpressionData.get("Runx2")));
            else{
                System.out.println("\nstableExpressionData of Dlx5 is "+(stableExpressionData.get("Dlx5"))+" and should be true in order to fit");
                System.out.println("\nstableExpressionData of Msx2 is "+(stableExpressionData.get("Msx2"))+" and should be true in order to fit");
                System.out.println("\nstableExpressionData of Runx2 is "+(stableExpressionData.get("Runx2"))+" and should be false in order to fit");
                return ((stableExpressionData.get("Dlx5")) && (stableExpressionData.get("Msx2")) && !(stableExpressionData.get("Runx2")));
            }
        }
        //if the medium under consideration contains only BMP2
        else if(BMP2){
            //model fits the data in the BMP2 environment if Dlx5, Runx2 and Msx2 are expressed
            if(!DEBUGFLAG) return ((stableExpressionData.get("Dlx5")) && (stableExpressionData.get("Msx2")) && (stableExpressionData.get("Runx2")));
            else{
                System.out.println("\nstableExpressionData of Dlx5 is "+(stableExpressionData.get("Dlx5"))+" and should be true in order to fit");
                System.out.println("\nstableExpressionData of Msx2 is "+(stableExpressionData.get("Msx2"))+" and should be true in order to fit");
                System.out.println("\nstableExpressionData of Runx2 is "+(stableExpressionData.get("Runx2"))+" and should be true in order to fit");
                return ((stableExpressionData.get("Dlx5")) && (stableExpressionData.get("Msx2")) && (stableExpressionData.get("Runx2")));
            }
        }
        //if the medium under consideration contains only TFGb1
        else if(TGFb1){
            //model fits the data in the TGFb1 environment if Runx2 is expressed and both Dlx5 and Msx2 are not
            if(!DEBUGFLAG) return (!(stableExpressionData.get("Dlx5")) && !(stableExpressionData.get("Msx2")) && (stableExpressionData.get("Runx2")));
            else{
                System.out.println("\nstableExpressionData of Dlx5 is "+(stableExpressionData.get("Dlx5"))+" and should be false in order to fit");
                System.out.println("\nstableExpressionData of Msx2 is "+(stableExpressionData.get("Msx2"))+" and should be false in order to fit");
                System.out.println("\nstableExpressionData of Runx2 is "+(stableExpressionData.get("Runx2"))+" and should be true in order to fit");
                return (!(stableExpressionData.get("Dlx5")) && !(stableExpressionData.get("Msx2")) && (stableExpressionData.get("Runx2")));
            }
        }
        //This case should not arise (since no media is not considered in Kirkham), and thus an error is thrown
        else throw new IllegalArgumentException("Error!! The case of no GF in the media is not covered and should not arise!!");
    }

    /**
     * function to fill the matrix describing the BM encoded by index (see documentation for index encoding).
     *
     * @param index The index of the BM, as encoded as described in the documentation
     * @return A HashMap with the TFs as keys and an array of influence of the relevant species on the TF (-1: inhibitory influence, 0: no influence, 1: activatory influence). See function body or documentation for semantics of the data.
     */
    private static HashMap<String, int[]> fillMatrix(int index){
        /**
         * Matrix to keep the influence of species for the three species of investigation (key)
         * Structure of entries is:
         * 0. entry: Influence of BMP2
         * 1. entry: Influence of TGFb1
         * 2. entry: Influence of Dlx5
         * 3. entry: Influence of Msx2
         * 4. entry: Influence of Runx2
         */
        HashMap<String, int[]> matrix = new HashMap<>(3);

        //integers to describe the (yet unclear) influence of the species unto one another as encoded in the index
        int Msx2toDlx5 = ((((int) Math.floor(index / 3)) % 3) - 1);
        int Runx2toDlx5 = ((int) Math.floor(index % 3) - 1);
        int Dlx5toMsx2 = (((int) Math.floor(index / 81) % 3) - 1);
        int Msx2toRunx2 = (((int) Math.floor(index / 27) % 3) - 1);
        int TGFb1toRunx2 = (((int) Math.floor(index / 9) % 3) - 1);

        //entry for the influence of species on Dlx5.
        //BMP2 has an activatory influence on Dlx5, TGFb1 has none, and the influence of Msx2 and Runx2 on Dlx5 is unclear
        int[] Dlx5Array =  {1, 0, 0, Msx2toDlx5, Runx2toDlx5};
        matrix.put("Dlx5", Dlx5Array);

        //entry for the influence of species on Msx2.
        //BMP2 and TGFb1 have an activatory influence on Msx2, Runx2 has none, and the influence of Dlx5 on Msx2 is unclear
        int[] Msx2Array =  {1, 1, Dlx5toMsx2, 0, 0};
        matrix.put("Msx2", Msx2Array);

        //entry for the influence of species on Runx2.
        //Dlx5 has an activatory influence on Runx2, BMP2 has none, and the influence of Msx2 and TGFb1 on Runx2 is unclear
        int[] Runx2Array =  {0, TGFb1toRunx2, 1, Msx2toRunx2, 0};
        matrix.put("Runx2", Runx2Array);

        return matrix;
    }

    /**
     * function to find the steady-state expression profile of the BM described by the matrix in a medium potentially containing BMP2 and TGFb1.
     * Function will iterate until it finds a stable expression profile or iterates too much (more than MAXITERATION times), indicating an error
     *
     * @param BMP2 integer indicating whether BMP2 is present in the medium (value 1: present, value 0: not present)
     * @param TGFb1 integer indicating whether TGFb1 is present in the medium (value 1: present, value 0: not present)
     * @param matrix Matrix describing the BM of interest
     * @return A stable expression profile indicating whether a TF is expressed in the steady-state
     */
    private static HashMap<String, Boolean> stableExpressionProfile(int BMP2, int TGFb1, HashMap<String, int[]> matrix){
        //counter to prevent too long oscillations
        int iterationCounter = 0;
        //The initial expression matrix, where none of the TFs are expressed
        HashMap<String, Boolean> TFexpressionOld = setupExpressionMatrix();
        //The first iteration of the BM described by the matrix
        HashMap<String, Boolean> TFexpressionNew = updateTFExpression(TFexpressionOld, BMP2, TGFb1, matrix);
        //As long as the TF expression differs from the previous one (no steady-state reached)...
        while(TFExpressionDifferent(TFexpressionOld,TFexpressionNew) && (iterationCounter < MAXITERATIONS)){
            TFexpressionOld = TFexpressionNew;
            //...iterate the discrete BM
            TFexpressionNew = updateTFExpression(TFexpressionOld, BMP2, TGFb1, matrix);
            iterationCounter++;
            if(DEBUGFLAG){
                System.out.println("\n-----------------------\nTFExpression old is ");
                printTFExpression(TFexpressionOld);
                System.out.println("\n\nTFExpression new is ");
                printTFExpression(TFexpressionNew);
                System.out.println("Difference between them? "+TFExpressionDifferent(TFexpressionOld,TFexpressionNew));
            }
        }
        //indicate that no steady-state has been reached
        if(iterationCounter == MAXITERATIONS){
            System.out.println("\n----------------------------------\n");
            System.out.println("Maxiterations reached with matrix ");
            printMatrix(matrix);
            System.out.println(" with BMP2 "+BMP2+" and TGFb1 "+TGFb1);
            numIndexMaxIt++;
        }
        //expression profile corresponding to a steady state
        return TFexpressionNew;
    }

    private static void printTFExpression(HashMap<String, Boolean> tFexpression) {
        for(String tf : tFexpression.keySet()){
            System.out.println("\nExpression for "+tf+"\t is "+tFexpression.get(tf));
        }
    }

    /**
     * function to update the TF expression (i.e. to calculate the next time step of the BM under interest)
     *
     * @param tFexpressionOld the expression of the TFs in the previous step
     * @param BMP2 integer indicating whether BMP2 is present in the environment (1) or not (0)
     * @param TGFb1 integer indicating whether TGFb1 is present in the environment (1) or not (0)
     * @param matrix the matrix describing the influence of the relevant species onto the TF
     * @return a HashMap of the expression values of the TFs
     */
    private static HashMap<String,Boolean> updateTFExpression(HashMap<String, Boolean> tFexpressionOld, int BMP2, int TGFb1, HashMap<String, int[]> matrix) {
        HashMap<String, Boolean> tFexpressionNew = new HashMap<>(3);
        //differentiate whether the influence of the species in the BM are connected via conjunctions or disjunctions
        switch(BOOLMODE){
            case "AND":
                //in the case of conjunctions, calculate the new value of the expression of the TFs via calculateNewExpressionValueAnd with the respective entry in the corresponding matrix
                tFexpressionNew.put("Dlx5", calculateNewExpressionValueAnd(tFexpressionOld, BMP2, TGFb1, matrix.get("Dlx5")));
                tFexpressionNew.put("Msx2", calculateNewExpressionValueAnd(tFexpressionOld, BMP2, TGFb1, matrix.get("Msx2")));
                tFexpressionNew.put("Runx2", calculateNewExpressionValueAnd(tFexpressionOld, BMP2, TGFb1, matrix.get("Runx2")));
                break;
            case "OR":
                //in the case of conjunctions, calculate the new value of the expression of the TFs via calculateNewExpressionValueOr with the respective entry in the corresponding matrix
                tFexpressionNew.put("Dlx5", calculateNewExpressionValueOr(tFexpressionOld, BMP2, TGFb1, matrix.get("Dlx5")));
                tFexpressionNew.put("Msx2", calculateNewExpressionValueOr(tFexpressionOld, BMP2, TGFb1, matrix.get("Msx2")));
                tFexpressionNew.put("Runx2", calculateNewExpressionValueOr(tFexpressionOld, BMP2, TGFb1, matrix.get("Runx2")));
                break;
            default:
                System.out.println("ERROR!!! BOOLMODE SET INCORRECTLY TO "+BOOLMODE);
        }
        return tFexpressionNew;
    }

    /**
     * calculates the new expression value of the species of interest via a disjunction of the factors involved
     *
     * @param tFexpressionOld the expression data of the current time step (the new time step is based upon)
     * @param BMP2 integer to indicate whether BMP2 is present in the medium (1) or not (0)
     * @param TGFb1 integer to indicate whether TGFb1 is present in the medium (1) or not (0)
     * @param matrixRow the influences of other species on the TF under consideration as in the BM under consideration
     * @return true if the TF is set to 'expressed' in the next time step or not
     */
    private static Boolean calculateNewExpressionValueOr(HashMap<String, Boolean> tFexpressionOld, int BMP2, int TGFb1,int[] matrixRow) {
        //if BMP2 is present in the medium and it upregulates the TF, the expression value is 1
        if((BMP2 == 1 ) && (matrixRow[0] == 1)) return true;
        //if TGFb1 is present in the medium and it upregulates the TF, the expression value is 1
        else if((TGFb1 == 1 ) && (matrixRow[1] == 1)) return true;
        //if Dlx5 is expressed and it upregulates the TF, the expression value is 1
        else if(tFexpressionOld.get("Dlx5") && (matrixRow[2] == 1)) return true;
        //if Dlx5 is not expressed and it inhibits the TF, the expression value is 1
        else if(!tFexpressionOld.get("Dlx5") && (matrixRow[2] == -1)) return true;
        //if Msx2 is expressed and it upregulates the TF, the expression value is 1
        else if(tFexpressionOld.get("Msx2") && (matrixRow[3] == 1)) return true;
        //if Msx2 is not expressed and it inhibits the TF, the expression value is 1
        else if(!tFexpressionOld.get("Msx2") && (matrixRow[3] == -1)) return true;
        //if Runx2 is expressed and it upregulates the TF, the expression value is 1
        else if(tFexpressionOld.get("Runx2") && (matrixRow[4] == 1)) return true;
        //if Runx2 is not expressed and it inhibits the TF, the expression value is 1
        else if(!tFexpressionOld.get("Runx2") && (matrixRow[4] == -1)) return true;
        else return false;
    }

    /**
     * calculates the new expression value of the species of interest via a conjunction of the factors involved
     *
     * @param tFexpressionOld the expression data of the current time step (the new time step is based upon)
     * @param BMP2 integer to indicate whether BMP2 is present in the medium (1) or not (0)
     * @param TGFb1 integer to indicate whether TGFb1 is present in the medium (1) or not (0)
     * @param matrixRow the influences of other species on the TF under consideration as in the BM under consideration
     * @return true if the TF is set to 'expressed' in the next time step or not
     */
    private static Boolean calculateNewExpressionValueAnd(HashMap<String, Boolean> tFexpressionOld, int BMP2, int TGFb1,int[] matrixRow) {
        //if BMP2 upregulates the TF and is not present in the medium, the expression value is 0
        if((BMP2 == 0 ) && (matrixRow[0] == 1)) return false;
        //if TGFb1 upregulates the TF and is not present in the medium, the expression value is 0
        else if((TGFb1 == 0 ) && (matrixRow[1] == 1)) return false;
        //if Dlx5 upregulates the TF and is not expressed, the expression value is 0
        else if(!tFexpressionOld.get("Dlx5") && (matrixRow[2] == 1)) return false;
        //if Dlx5 inhibits the TF and is expressed expressed, the expression value is 0
        else if(tFexpressionOld.get("Dlx5") && (matrixRow[2] == -1)) return false;
        //if Msx2 upregulates the TF and is not expressed, the expression value is 0
        else if(!tFexpressionOld.get("Msx2") && (matrixRow[3] == 1)) return false;
        //if Msx2 inhibits the TF and is expressed, the expression value is 0
        else if(tFexpressionOld.get("Msx2") && (matrixRow[3] == -1)) return false;
        //if Runx2 upregulates the TF and is not expressed, the expression value is 0
        else if(!tFexpressionOld.get("Runx2") && (matrixRow[4] == 1)) return false;
        //if Runx2 inhibits the TF and is expressed, the expression value is 0
        else if(tFexpressionOld.get("Runx2") && (matrixRow[4] == -1)) return false;
        else return true;
    }


}

