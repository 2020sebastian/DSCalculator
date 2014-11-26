package edu.depaul.csc472.calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by sd on 11/22/14.
 */
public class Decomposition {

    static String tempCoverClos = "";
    private String defaultKey = "Example: BC";
    private String defaultSuperKey = "Example: ABD";
    static List<String> realKey = new ArrayList<String>();
    static List<String> potentialKey = new ArrayList<String>();
    static List<String> potentialSuperKey = new ArrayList<String>();
    static List<String> checkPermutation = new ArrayList<String>();

    static List<Boolean> checkRHS = new ArrayList<Boolean>();
    static List<Boolean> checkLHS = new ArrayList<Boolean>();
    static List<String> stepOneBreakDown = new ArrayList<String>();
    static List<String> superKey = new ArrayList<String>();
    static List<String> thrNF = new ArrayList<String>();
    static List<String> removeNF = new ArrayList<String>();
    static List<String> holdFdNf = new ArrayList<String>();
    static List<String> key = new ArrayList<String>();


    public static List<String> lhsFD = new ArrayList<String>();
    public static List<String> rhsFD = new ArrayList<String>();

    static String[] FD = {"A-BC", "AD-B", "CD-AB"};
    static String[] RR = {"A","B","C", "D"};

    public static List<String> permutation(String s)
    {
        // The result
        List<String> res = new ArrayList<String>();
        // If input string's length is 1, return {s}
        if (s.length() == 1)
        {
            res.add(s);
        }
        else if (s.length()> 1)
        {
            int lastIndex = s.length() - 1;
            // Find out the last character
            String last = s.substring(lastIndex);
            // Rest of the string
            String rest = s.substring(0, lastIndex);
            // Perform permutation on the rest string and
            // merge with the last character
            res = merge(permutation(rest), last);
        }
        return res;
    }

    /**
     * @param list a result of permutation, e.g. {"ab", "ba"}
     * @param c    the last character
     * @return     a merged new list, e.g. {"cab", "acb" ... }
     */
    public static List<String> merge(List<String> list, String c) {
        List<String> res = new ArrayList<String>();
        // Loop through all the string in the list
        for(String s: list) {
            // For each string, insert the last character to all possible postions
            // and add them to the new list
            for (int i = 0; i <= s.length(); ++i) {
                String ps = new StringBuilder(s).insert(i, c).toString();
                res.add(ps);
            }
        }
        return res;
    }

    public static void calculateKeys() {
        for(int i = 0; superKey.size()> i; i++){
            for(int a =0; superKey.get(i).length() > a; a++){
                lhsFD.add(0, superKey.get(i).substring(0, a + 1));
                rhsFD.add(0, "");
                String checkCover = calulateCover(0);

                if (superKey.get(i).substring(0, a + 1).length() != superKey.get(i).length()
                        && checkCover.length() == RR.length){
                    lhsFD.remove(0);
                    rhsFD.remove(0);
                    break;
                }
                if (superKey.get(i).length() <= 4)
                {
                    realKey.add(superKey.get(i));
                }
                lhsFD.remove(0);
                rhsFD.remove(0);
            }

        }
    }


    public static void getSuperKeys(){
        for (int i = 0; key.size() > i; i++){
            for (int a = 0; key.get(i).length() > a; a++){
                lhsFD.add(0, key.get(i).substring(0, a));
                rhsFD.add(0, "");
                String temp = cover(0);
                if (temp.length() == RR.length
                        && !potentialKey.contains(key.get(i).substring(0, a))){
                    potentialKey.add(key.get(i).substring(0, a) + "-" + temp);
                    superKey.add(key.get(i).substring(0, a));
                }
                lhsFD.remove(0);
                rhsFD.remove(0);
            }
        }
    }

    public static void threeNF() {
        int size = holdFdNf.size();
        for(int i = 0; size > i; i++ ){
            String temp = holdFdNf.get(i);
            for (int n = 0; temp.length() > n; n++){
                combo(Character.toString(temp.charAt(n)), 0, temp, temp.length());}
            for(int s = 0; size > s; s++){
                for(int a = 0; thrNF.size() > a; a++){
                    if(holdFdNf.get(s).contains(thrNF.get(a)) && !holdFdNf.get(s).equals(thrNF.get(a))){
                        removeNF.add(thrNF.get(a));
                    }
                }
            }
            thrNF.clear();
        }

        for(int a = 0; removeNF.size() > a; a++)
        {
            String temp = removeNF.get(a);
            for (int n = 0; temp.length() > n; n++)
            {
                combo(Character.toString(temp.charAt(n)), 0, temp, temp.length());
            }
            for(int s = 0; thrNF.size() > s; s++)
            {
                holdFdNf.remove(thrNF.get(s));
            }
        }
    }


    public static String combo(String a, int b, String value, int size)
    {
        if (b == value.length())
        {
            return a;
        }
        else if (!a.contains(Character.toString(value.charAt(b))))
        {
            String temp = a;
            a = a + Character.toString(value.charAt(b));
            if(a.length() == size)
            {
                thrNF.add(a);
            }
            if(a.length() >= 2 && a.length() < 3)
            {
                com(a, 0, value, size);
            }
            a = temp;
            b++;
            return combo(a, b, value, size);
        }
        else
        {
            b++;
            return combo(a, b, value, size);
        }
    }

    /**
     * String Permutationt Stage 2
     * Takes care of Strings of size > 2
     *
     */
    public static String com(String a, int b, String value, int size){
        if (a.length() == value.length())
        {
            return a;
        }
        else if (!a.contains(Character.toString(value.charAt(b))))
        {
            a = a + Character.toString(value.charAt(b));
            if(!key.contains(a) && a.length()==size)
            {

                thrNF.add(a);
            }
            else
            {
                if(!key.contains(a))
                {
                    key.add(a);
                }
            }
            b++;
            return com(a, b, value, size);
        }
        else
        {
            b++;
            return com(a, b, value, size);
        }
    }

    public static void clearChekingTool()
    {
        checkRHS.clear();
    }
    /**
     * Check if right or left hand side does not contain characters that are in the cover
     * @param cover
     * @param rightHand
     * @return
     */
    public static Boolean check(String cover, String rightHand)
    {
        initChekingTool(rightHand.length());
        char[] letterRhs = rightHand.toCharArray();

        for (int i = 0; letterRhs.length > i; i++)
        {
            if (!cover.contains(Character.toString(letterRhs[i])))
            {
                checkRHS.remove(i);
                checkRHS.add(i, true);
            }
            else
            {
                checkRHS.remove(i);
                checkRHS.add(i, false);
            }
        }
        if (checkRHS.contains(true))
        {
            clearChekingTool();
            return true;
        }
        clearChekingTool();
        return false;
    }
    public static String cover(int i){


        String tempLhs = lhsFD.get(i);
        String tempRhs = rhsFD.get(i);

        lhsFD.remove(i);
        rhsFD.remove(i);
        String tempCover = tempLhs + tempRhs;

        int position = 0;
        while (position != lhsFD.size())
        {

            if (tempCover.contains(lhsFD.get(position).toString()) && lhsFD.get(position).length() == 1)
            {


                if (check(tempCover, rhsFD.get(position)) == true)
                {
                    char[] letterRhs = rhsFD.get(position).toCharArray();

                    for (int n = 0; letterRhs.length > n; n++)
                    {
                        if (!tempCover.contains(Character.toString(letterRhs[n])))
                        {
                            tempCover = tempCover + Character.toString(letterRhs[n]);
                            position = 0;
                        }
                    }
                }
                else
                {
                    position++;
                }
            }
            else if (lhsFD.get(position).length() > 1)
            {
                if ((check(tempCover, lhsFD.get(position)) == false) && (check(tempCover, rhsFD.get(position)) == true))
                {
                    char[] letterRhs = rhsFD.get(position).toCharArray();
                    for (int n = 0; letterRhs.length > n; n++)
                    {
                        if (!tempCover.contains(Character.toString(letterRhs[n])))
                        {
                            tempCover = tempCover + Character.toString(letterRhs[n]);
                            position = 0;
                        }
                    }
                }
                else
                {
                    position++;
                }
            }
            else if (!tempCover.contains(lhsFD.get(position)))
            {
                position++;
            }
        }
        lhsFD.add(i, tempLhs);
        rhsFD.add(i, tempRhs);
        return tempCover;
    }

    public static void simplify(int a)
    {
        String tempRhs = rhsFD.get(a);
        String tempCover = calulateCover(a);

        if(tempCover.contains(tempRhs)){
            lhsFD.remove(a);
            rhsFD.remove(a);
        }
    }
    public static void simplifyLHS(int a)
    {
        String tempLhs = lhsFD.get(a);
        String tempRhs = rhsFD.get(a);
        if(tempLhs.length() > 2)
        {
            for(int i=0;tempLhs.length() >i; i++)
            {
                String tempChar = tempLhs.substring(0 ,tempLhs.length());
                lhsFD.remove(a);
                lhsFD.add(a, tempChar);
                String cover = calulateCover(a);
                if(cover.contains(tempRhs))
                {
                    if(tempChar.length() == 2)
                    {
                        int b = tempChar.length();
                        while(b != 0)
                        {
                            char aChar = tempLhs.charAt(b);
                            String temp = Character.toString(aChar);
                            lhsFD.remove(a);
                            lhsFD.add(a, temp);
                            String covera = calulateCover(a);
                            if(covera.contains(tempRhs))
                            {
                                break;
                            }
                        }
                    }
                    break;
                }
            }
            calulateCover(a);
        }
        else if( tempLhs.length() == 2 )
        {
            char[] temp  = tempLhs.toCharArray();
            for(int i=0;tempLhs.length() >i; i++){
                String tempChar = Character.toString(temp[i]);
                lhsFD.remove(a);
                lhsFD.add(a, tempChar);
                String cover = calulateCover(a);
                if(cover.contains(tempRhs))
                {
                    break;
                }
                else
                {
                    lhsFD.remove(a);
                    lhsFD.add(a, tempLhs);
                }
            }
        }
        calulateCover(a);
    }


    public static String calulateCover(int a)
    {
        String tempCover;
        String tempLhs = lhsFD.get(a);
        String tempRhs = rhsFD.get(a);
        lhsFD.remove(a);
        rhsFD.remove(a);
        tempCover = tempLhs;
        Boolean checkStatus = true;
        int position = 0;
        while (checkStatus == true && position != lhsFD.size())
        {
            while (position != lhsFD.size())
            {
                if (tempCover.contains(lhsFD.get(position)) && !tempCover.contains(rhsFD.get(position)))
                {
                    tempCover = tempCover + rhsFD.get(position);
                    position = 0;
                }
                else if (lhsFD.get(position).length() > 1)
                {
                    if ((check(tempCover, lhsFD.get(position)) == false) && (check(tempCover, rhsFD.get(position)) == true))
                    {
                        char[] letterRhs = rhsFD.get(position).toCharArray();
                        for (int n = 0; letterRhs.length > n; n++)
                        {
                            if (!tempCover.contains(Character.toString(letterRhs[n])))
                            {
                                tempCover = tempCover + Character.toString(letterRhs[n]);
                                position = 0;
                            }
                        }
                    }
                    else
                    {
                        position++;
                    }

                }
                else
                {
                    checkStatus = false;
                    position++;
                }
            }
            lhsFD.add(a, tempLhs);
            rhsFD.add(a, tempRhs);
        }
        return tempCover;
    }
    public static void breakLHSandRHS(String[] b)
    {
        for (int i = 0; b.length > i; i++)
        {
            String[] temp = b[i].split("-");
            String LHS = temp[0];
            String RHS = temp[1];
            lhsFD.add(LHS);
            rhsFD.add(RHS);
        }
    }

    public static void breakDown(String[] array)
    {
        for (int i = 0; array.length > i; i++)
        {
            String[] temp = array[i].split("-");
            String LHS = temp[0];
            String RHS = temp[1];
            char[] characterArray = RHS.toCharArray();
            for (int n = 0; n < characterArray.length; n++)
            {
                char brokenRHS = characterArray[n];
                lhsFD.add(LHS);
                rhsFD.add(Character.toString(brokenRHS));
                stepOneBreakDown.add(LHS + "-" + brokenRHS);
            }
        }
    }

    public static Boolean closure(String a, String b)
    {
        Boolean check = true;
        lhsFD.add(0, a);
        rhsFD.add(0, "");
        String temp = cover(0);

        for (int i = 0; b.length() > i; i++)
        {
            if (!temp.contains(Character.toString(b.charAt(i))))
            {
                check = false;
                break;
            }
        }

        if (check == false)
        {
            tempCoverClos = temp;
            return false;
        }
        lhsFD.remove(0);
        rhsFD.remove(0);
        tempCoverClos = temp;
        return true;
    }

    public static Boolean inferance(String a, String b)
    {
        Boolean check = true;
        lhsFD.add(0, a);
        rhsFD.add(0, "");
        String temp = cover(0);

        for (int i = 0; b.length() > i; i++)
        {
            if (!temp.contains(Character.toString(b.charAt(i))))
            {
                check = false;
                break;
            }
        }
        if (check == false)
        {
            tempCoverClos = temp;
            return false;
        }
        lhsFD.remove(0);
        rhsFD.remove(0);
        tempCoverClos = temp;
        return true;
    }
    public static void canonicalCover(int a)
    {
        String tempLhs = lhsFD.get(a);
        String tempRhs = rhsFD.get(a);

        lhsFD.remove(a);
        rhsFD.remove(a);
        for (int i = 0; lhsFD.size() > i; i++)
        {
            if (lhsFD.get(i).equals(tempLhs))
            {

                tempRhs = tempRhs + rhsFD.get(i);
                lhsFD.remove(i);
                rhsFD.remove(i);
            }
        }
        lhsFD.add(a, tempLhs);
        rhsFD.add(a, tempRhs);
    }



    public static void initChekingTool(int sizeRight)
    {
        for (int i = 0; sizeRight > i; i++)
        {
            checkRHS.add(false);
        }
    }

//    public static void main(String[]args){
//
//
//        stepOne();
//        stepTwo();
//        stepThree();
//        stepFour();
//        stepFive();
//        stepSix();
//        stepSeven();
//
//        //NEW ONES
//
//
//        getAllSuperKeys();
//        getAllKeys();


//        checkIfKey();
//        checkIfSuperKey();

//        determines();
//        inference();
//
//    }

    public static void stepOne(){
        StringBuilder result = new StringBuilder();

        result.append("Step 1: Calculating Cover\n\n");
        result.append("Before we proceed with 3NF decomposition it is very useful to calculate the cover for each element on the right hand side from functional dependencies\n"
                +"At the last step of 3NF decomposition we will need to determine whether or not the resulting\n"
                +"relations contain a super key. \n\nBy calculating cover early we save some time in future and have\n"
                +"a key ready if we need it.\n\n\n");
        result.append("Result:\n");



        breakLHSandRHS(FD);
        for (int a = 0; lhsFD.size() > a; a++)
        {
            String temp = cover(a);
            String str = lhsFD.get(a) + "->" + temp;
            result.append(str+"\n");
        }


        Singleton.setStep1result(result);

        ClearAll();
    }

    public static void stepTwo(){
        StringBuilder result = new StringBuilder();
        result.append("Step 2: Breaking Down\n\n");
        result.append("In this step we are focusing on breaking down FD's\n"
                + "Note that you can only break down left hand side. For Example:\n"
                + "    Break down of Functional Dependency XY->Z \n"
                + "         Result: XY-Z\n"
                + "    Break down of Functional Dependency XY->ZW \n"
                + "         Result: XY->Z\n"
                + "         Result: XY->W\n"
                + "Notice that left hand side remains the same while right hand side gets split up.\n\n\n");
        result.append("Result:\n");

        breakDown(FD);

        for(int a = 0; stepOneBreakDown.size() > a; a++ ){

            String str = stepOneBreakDown.get(a);
            result.append(str + "\n");
        }
        Singleton.setStep2result(result);
        ClearAll();
    }

    public static void stepThree(){
        StringBuilder result = new StringBuilder();
        result.append("Step 3: Simplifying LHS\n\n");

        result.append("In this step we are focusing only on functional\n"
                + "dependencies that have two or more attributes on their left hand side. For Example:\n"
                +"    XY->A \n"
                +"    Y->A \n"
                +"    X->B \n"
                +"    B->F \n"
                +"To simplify left hand side we need to calculate cover for each attribute on the left hand side. If\n"
                + "during the calculation we will get the element from the right hand side, we will remove the neighboring\n"
                +"element of the right hand side. For Example:\n"
                +"    Let's take XY->A. To simplify the LHS we start calculating the cover for X\n"
                +"            Cover for X: X->XBF\n"
                +"    If during the cover calculation we get the right hand side of XY which is A, we will remove Y.\n"
                +"    Else, we will proceed with calculating the cover for our next element which is Y\n"
                +"            Cover for Y: Y->YA\n"
                +"    In this case we came across the right hand side of XY which is A, so we will remove X. And obtain\n"
                +"    a final result of: Y->A.\n"
                +"                       Y->A \n"
                +"                       X->B \n"
                +"                       B->F \n"
                +"Notice, during the calculation of the cover for this part, we DO NOT include the right hand side of the\n"
                +"element during cover calculation\n\n\n");
        result.append("Result:\n\n");
        breakDown(FD);
        for (int a = 0; stepOneBreakDown.size() > a; a++)
        {
            simplifyLHS(a);
        }

        for (int a = 0; stepOneBreakDown.size() > a; a++)
        {
            String str = lhsFD.get(a) + "-" + rhsFD.get(a);

            result.append(str + "\n");
        }
        Singleton.setStep3result(result);
        ClearAll();
    }

    public static void stepFour(){
        StringBuilder result = new StringBuilder();
        result.append("Step 4: Simplifying RHS\n\n");

        result.append("In this step we are focusing on functional dependencies that were obtained as a result\n"
                +"from RHS simplification:\n"
                + "                Y->A.\n"
                + "                Y->A \n"
                + "                X->B \n"
                + "                B->F \n"
                + "Simplification of the left hand side follows the same principal as the RHS simplification. During \n"
                + "the calculation of the cover we do not include the right hand side of the functional dependency\n"
                + "For Example:\n"
                + "                Y->A Cover (Y->YA)\n"
                + "    In this case we came across the right hand side\n of Y which is A, so we will remove Y. And obtain\n"
                + "    result of:   Y->A.\n"
                + "                X->B \n"
                + "                B->F \n"
                + "    and proceed with simplifying:\n"
                + "                Y->A Cover (Y->A)\n"
                + "                X->B Cover (X->BF)\n"
                + "                B->F Cover (B->B)\n"
                + "    During the simplification process no removals occurred so our final result is:\n"
                + "                Y->A\n"
                + "                X->B\n"
                + "                B->F\n\n\n");
        result.append("Result:\n\n");
        breakDown(FD);
        for (int a = 0; stepOneBreakDown.size() > a; a++)
        {
            simplifyLHS(a);
        }

        for (int a = 0; lhsFD.size() > a; a++)
        {
            simplify(a);
        }

        for (int a = 0; lhsFD.size() > a; a++)
        {
            String str = lhsFD.get(a) + "-" + rhsFD.get(a);

            result.append(str + "\n");
        }
        Singleton.setStep4result(result);
        ClearAll();
    }

    public static void stepFive(){
        StringBuilder result = new StringBuilder();
        result.append("Step 5: Minimal Cover\n\n");


        result.append("In this step we simply provide the result\n"
                + "obtained from simplification of the Right hand side, which is:\n"
                + "                Y->A \n"
                + "                X->B \n"
                + "                B->F \n\n\n");
        result.append("Result:\n\n");
        breakDown(FD);
        for (int a = 0; stepOneBreakDown.size() > a; a++)
        {
            simplifyLHS(a);
        }

        for (int a = 0; lhsFD.size() > a; a++)
        {
            simplify(a);
        }

        for (int a = 0; lhsFD.size() > a; a++)
        {
            String str = lhsFD.get(a) + "-" + rhsFD.get(a);

            result.append(str + "\n");
        }
        Singleton.setStep5result(result);
        ClearAll();
    }

    public static void stepSix(){

        StringBuilder result = new StringBuilder();
        result.append("Step 6: Canonical Cover\n\n");


        result.append("In this step we are focusing on combining functional dependencies that have the same\n"
                +"left hand side. For Example:\n"
                +"                Y->A.\n"
                +"                Y->B \n"
                + "    Because both of the dependencies have same left hand sides we can combine them into one.\n"
                + "    This combination will result in:\n"
                + "                Y->AB \n"
                + "Note, you can only combine functional dependencies if the left hand sides of those dependencies\n"
                + "are alike.\n\n\n");
        result.append("Result:\n\n");
        breakDown(FD);
        for (int a = 0; stepOneBreakDown.size() > a; a++)
        {
            simplifyLHS(a);
        }
        for (int a = 0; lhsFD.size() > a; a++)
        {
            simplify(a);
        }
        for (int a = 0; lhsFD.size() > a; a++)
        {
            canonicalCover(a);
        }

        for (int a = 0; lhsFD.size() > a; a++)
        {
            String str = lhsFD.get(a) + "-" + rhsFD.get(a);

            result.append(str + "\n");
        }
        Singleton.setStep6result(result);
        ClearAll();
    }

    public static void stepSeven(){

        StringBuilder result = new StringBuilder();
        result.append("Step 7: 3NF\n\n");


        result.append("In this step we are focusing on calculating 3NF decomposition of the functional dependencies\n"
                + "that were obtained as a result of Canonical Cover calculation. For Example:\n"
                + "    Suppose result that was obtained is:\n"
                + "            X->A\n"
                + "            Y->B\n"
                + "            B->W\n"
                + "    To calculate 3NF we take the first FD from the list and make it our R1:\n"
                + "            R1(X->A)\n"
                + "    Then we take our next FD Y->B, and check if R1 does NOT contain XA. If it does not we make it our\n"
                + "    R2, and if it does we simply move down to the next FD B->W and remove Y->B out of the list. But for this\n"
                + "    example R1 does not contain Y->B. So we make it our R2:\n"
                + "            R1(XA)\n"
                + "            R2(YB)\n"
                + "    After addition we proceed by the same set of rules. And as a result obtain a potential 3NF decomposition\n"
                + "            R1(XA)\n"
                + "            R2(YB)\n"
                + "            R3(BW)\n"
                + "Notice, that I have said that the decomposition that is potential 3NF decomposition. For it to be 3NF it needs\n"
                + "to contain at least one key. If it does then we are done and the decomposition that was calculated is 3NF. If it\n"
                + "does not, you need to come up with a key and simply insert it as your Ri(). This way decomposition will be 3NF.\n\n\n");
        result.append("Result:\n\n");
        breakDown(FD);
        for (int a = 0; stepOneBreakDown.size() > a; a++)
        {
            simplifyLHS(a);
        }
        for (int a = 0; lhsFD.size() > a; a++)
        {
            simplify(a);
        }
        for (int a = 0; lhsFD.size() > a; a++)
        {
            canonicalCover(a);
        }
        for(int i = 0; lhsFD.size() > i; i++ ){

            holdFdNf.add(lhsFD.get(i) + rhsFD.get(i));
        }
        threeNF();

        for (int a = 0; holdFdNf.size() > a; a++)
        {
            String str = "RR" + (a + 1) + "(" + holdFdNf.get(a) + ")";

            result.append(str + "\n");
        }
        Singleton.setStep7result(result);
        ClearAll();
    }

    private static void inference() {
        Scanner user_input = new Scanner( System.in );
        System.out.print("Inference element: ");
        String inferenceA  =  user_input.next( );

        System.out.print("Element to infere from: ");
        String inferenceB  =  user_input.next( );



        breakLHSandRHS(FD);


        if (inferance(inferenceA, inferenceB) == true)
        {
            System.out.println(inferenceA + " can infer " + inferenceB);
            System.out.print("From ");

            for(int i = 0; RR.length > i; i++){
                System.out.print(RR[i]);
            }
            System.out.println("");
            System.out.print("Cover:");
            System.out.print("  " + inferenceB + "-" + tempCoverClos);
        }
        else
        {
            System.out.println(inferenceA + " can NOT infer " + inferenceB);
            System.out.print("From " + RR);
            for(int i = 0; RR.length > i; i++){
                System.out.print(RR[i]);
            }
            System.out.println("");
            System.out.print("Cover:");
            System.out.print("  " + inferenceB + "-" + tempCoverClos);
        }


        ClearAll();

    }


    private static void determines() {
        Scanner user_input = new Scanner( System.in );
        System.out.print("Enter element to check: ");
        String closureA =  user_input.next( );

        System.out.print("Enter element to check against: ");
        String closureB  =  user_input.next( );

        breakLHSandRHS(FD);


        if (closure(closureA, closureB) == true)
        {
            System.out.println(closureA + "->(TRUE)->" + closureB);
            System.out.println("");
            System.out.print("Cover:");
            System.out.print(closureA + "->" + tempCoverClos);
        }
        else
        {
            System.out.println(closureA + "->(FALSE)->" + closureB);
            System.out.println("");
            System.out.print("Cover:");
            System.out.print(closureA + "->" + tempCoverClos);
        }
        ClearAll();

    }


    private static void checkIfSuperKey() {
        Scanner user_input = new Scanner( System.in );
        System.out.print("Enter the superkey for check: ");
        String chekingKey =  user_input.next( );
        breakLHSandRHS(FD);

        String temp = "";
        for (int i = 0; RR.length > i; i++)
        {
            temp = temp + RR[i];
        }


        key.addAll(key.size(), permutation(temp));


        getSuperKeys();
        calculateKeys();

        Collections.sort(realKey);


        if (superKey.contains(chekingKey))
        {
            System.out.println(chekingKey + " IS a Superkey");
        }
        else
        {
            System.out.println(chekingKey + " is NOT a Superkey");
        }
        ClearAll();
    }


    private static void checkIfKey() {
        Scanner user_input = new Scanner( System.in );
        System.out.print("Enter the key for check: ");
        String chekingKey =  user_input.next( );

        breakLHSandRHS(FD);

        String temp = "";
        for (int i = 0; RR.length > i; i++)
        {
            temp = temp + RR[i];
        }


        key.addAll(key.size(), permutation(temp));


        getSuperKeys();
        calculateKeys();

        Collections.sort(realKey);


        if (realKey.contains(chekingKey))
        {
            System.out.println(chekingKey + " IS a Key");
        }
        else
        {
            System.out.println(chekingKey + " is NOT a Key");
        }
        ClearAll();
    }


    public static void getAllKeys() {
        StringBuilder result = new StringBuilder();

        breakLHSandRHS(FD);

        String temp = "";
        for (int i = 0; RR.length > i; i++)
        {
            temp = temp + RR[i];
        }


        key.addAll(key.size(), permutation(temp));


        getSuperKeys();
        calculateKeys();

        Collections.sort(realKey);
        Set<String> s = new HashSet<String>();
        for (int a = 0; realKey.size() > a; a++)
        {
            String str = realKey.get(a);

            s.add(str);
        }

        for (String item : s) {
            result.append(item + "\n");
        }


        Singleton.setAllKeysResult(result);
        ClearAll();
    }


    public static void getAllSuperKeys() {
        StringBuilder result = new StringBuilder();
        breakLHSandRHS(FD);

        String temp = "";
        for (int i = 0; RR.length > i; i++)
        {
            temp = temp + RR[i];
        }


        key.addAll(key.size(), permutation(temp));


        getSuperKeys();
        Collections.sort(superKey);
        Set<String> s = new HashSet<String>();
        for (int a = 0; superKey.size() > a; a++)
        {
            String str = superKey.get(a);
            s.add(str.trim());

        }
        for (String item : s) {
            result.append(item.trim() + "\n");
        }

        Singleton.setSuperKeysResult(result);
        ClearAll();
    }


    public static void ClearAll()
    {
        tempCoverClos = "";
        checkRHS.clear();
        checkLHS.clear();
        stepOneBreakDown.clear();
        superKey.clear();
        thrNF.clear();
        removeNF.clear();
        holdFdNf.clear();
        key.clear();
        lhsFD.clear();
        rhsFD.clear();
    }


}
