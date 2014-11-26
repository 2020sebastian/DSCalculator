package edu.depaul.csc472.calculator;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sd on 10/25/14.
 */
public class Singleton extends Activity {

    private static String fdInput = "";
    private static String relationsInput = "";
    private static List<String> relations;
    private static List<String> dependencies;
    private static String step1result;
    private static String step2result;
    private static String step3result;
    private static String step4result;
    private static String step5result;
    private static String step6result;
    private static String step7result;
    private static String SuperKeysResult = "";
    private static String AllKeysResult = "";

    private Singleton(){};

    public static void calculate() {

        relations = Arrays.asList(relationsInput.split(","));
        dependencies = Arrays.asList(fdInput.split(","));

        //commented out means leave default values inside Decomposition class
            Decomposition.RR = relations.toArray(new String[relations.size()]);
            Decomposition.FD = dependencies.toArray(new String[dependencies.size()]);

    }

    public static void clearAllInput(){
        fdInput = "";
        relationsInput = "";
        relations = null;
        dependencies = null;
        step1result = "";
        step2result = "";
        step3result = "";
        step4result = "";
        step5result = "";
        step6result = "";
        step7result = "";
        SuperKeysResult = "";
        AllKeysResult = "";

    }


    public static String getStep1result() {
        return step1result;
    }

    public static void setStep1result(StringBuilder input) {
        Singleton.step1result = input.toString();
    }

    public static String getStep2result() {
        return step2result;
    }
    public static void setStep2result(StringBuilder input) {
        Singleton.step2result = input.toString();
    }

    public static List<String> getRelations() {
        return relations;
    }
    public static void setRelations(List<String> relations) {
        Singleton.relations = relations;
    }


    public static String getStep3result() {
        return step3result;
    }

    public static void setStep3result(StringBuilder input) {
        Singleton.step3result = input.toString();
    }

    public static String getStep4result() {
        return step4result;
    }

    public static void setStep4result(StringBuilder input) {
        Singleton.step4result = input.toString();
    }

    public static String getStep5result() {
        return step5result;
    }

    public static void setStep5result(StringBuilder input) {
        Singleton.step5result = input.toString();
    }

    public static String getStep6result() {
        return step6result;
    }

    public static void setStep6result(StringBuilder input) {
        Singleton.step6result = input.toString();
    }

    public static String getStep7result() {
        return step7result;
    }

    public static void setStep7result(StringBuilder input) {
        Singleton.step7result = input.toString();
    }

    public static String getAllKeysResult() {
        return AllKeysResult;
    }

    public static void setAllKeysResult(StringBuilder input) {
        Singleton.AllKeysResult = input.toString();
    }

    public static String getSuperKeysResult() {
        return SuperKeysResult;
    }

    public static void setSuperKeysResult(StringBuilder input) {
        Singleton.SuperKeysResult = input.toString();
    }

    public static List<String> getDependencies() {
        return dependencies;
    }

    public static void setDependencies(List<String> dependencies) {
        Singleton.dependencies = dependencies;
    }



    //Getters and Setters

    public static void setRelations() {
    }
    public static void setDependencies() {
    }
    public static String getFdInput() {
        return fdInput;
    }
    public static void setFdInput(String fdInput) {
        Singleton.fdInput = fdInput;
    }
    public static String getRelationsInput() {
        return relationsInput;
    }
    public static void setRelationsInput(String relationsInput) {
        Singleton.relationsInput = relationsInput;
    }
    public static String getHelp() {
        return help;
    }


    //Text for help page

    private static String help = "Step 1: Calculating Cover\n"
            + "Before we proceed with 3NF decomposition it is very\n"
            + "useful to calculate the cover for each element on\n"
            + "the right hand side from functional dependencies\n"
            + "At the last step of 3NF decomposition we will\n"
            + "need to determine whether or not the resulting\n"
            + "relations contain a super key. By calculating\n"
            + "cover early we save some time in future and have\n"
            + "a key ready if we need it.\n\n"

            + "Step 2: Breaking Down\n"
            + "In this step we are focusing on breaking down FD's\n"
            + "Note that you can only break down left hand side.\n"
            + "For Example:\n"
            + "    Break down of Functional Dependency XY->Z \n"
            + "         Result: XY-Z\n"
            + "    Break down of Functional Dependency XY->ZW \n"
            + "         Result: XY->Z\n"
            + "         Result: XY->W\n"
            + "Notice that left hand side remains the same while\n"
            + "right hand side gets split up.\n\n"

            + "Step 3: Simplifying LHS\n"
            + "In this step we are focusing only on functional\n"
            + "dependencies that have two or more attributes on\n"
            + "their left hand side. For Example:\n"
            + "    XY->A \n"
            + "    Y->A \n"
            + "    X->B \n"
            + "    B->F \n"
            + "To simplify left hand side we need to calculate\n"
            + "cover for each attribute on the left hand side. If\n"
            + "during the calculation we will get the element from\n"
            + "the right hand side, we will remove the neighboring\n"
            + "element of the right hand side. For Example:\n"
            + "    Let's take XY->A. To simplify the LHS\n"
            + "    we start calculating the cover for X\n"
            + "            Cover for X: X->XBF\n"
            + "    If during the cover calculation we get the right\n"
            + "    hand side of XY which is A, we will remove Y.\n"
            + "    Else, we will proceed with calculating the cover\n"
            + "    for our next element which is Y\n"
            + "            Cover for Y: Y->YA\n"
            + "    In this case we came across the right hand side\n"
            + "    of XY which is A, so we will remove X. And obtain\n"
            + "    a final result of: Y->A.\n"
            + "                       Y->A \n"
            + "                       X->B \n"
            + "                       B->F \n"
            + "Notice, during the calculation of the cover for this\n"
            + "part, we DO NOT include the right hand side of the\n"
            + "element during cover calculation\n\n"

            + "Step 4: Simplifying RHS\n"
            +"In this step we are focusing on functional\n"
            +"dependencies that were obtained as a result\n"
            +"from RHS simplification:\n"
            + "                Y->A.\n"
            + "                Y->A \n"
            + "                X->B \n"
            + "                B->F \n"
            + "Simplification of the left hand side follows the\n"
            + "same principal as the RHS simplification. During \n"
            + "the calculation of the cover we do not include\n"
            + "the right hand side of the functional dependency\n"
            + "For Example:\n"
            + "                Y->A Cover (Y->YA)\n"
            + "    In this case we came across the right hand side\n"
            + "    of Y which is A, so we will remove Y. And obtain\n"
            + "    result of:   Y->A.\n"
            + "                X->B \n"
            + "                B->F \n"
            + "    and proceed with simplifying:\n"
            + "                Y->A Cover (Y->A)\n"
            + "                X->B Cover (X->BF)\n"
            + "                B->F Cover (B->B)\n"
            + "    During the simplification process no removals\n"
            + "    occurred so our final result is:\n"
            + "                Y->A\n"
            + "                X->B\n"
            + "                B->F\n\n"

            +"Step 5: Minimal Cover\n"
            + "In this step we simply provide the result\n"
            + "obtained from simplification of the Right\n"
            + "hand side, which is:\n"
            + "                Y->A \n"
            + "                X->B \n"
            + "                B->F \n\n"

            +"Step 6: Canonical Cover\n"
            +"In this step we are focusing on combining\n"
            +"functional dependencies that have the same\n"
            +"left hand side. For Example:\n"
            +"                Y->A.\n"
            +"                Y->B \n"
            + "    Because both of the dependencies have same\n"
            + "    left hand sides we can combine them into one.\n"
            + "    This combination will result in:\n"
            + "                Y->AB \n"
            + "Note, you can only combine functional dependencies\n"
            + "if the left hand sides of those dependencies\n"
            + "are alike.\n\n"

            +"Step 7: 3NF\n"
            +"In this step we are focusing on calculating\n"
            +"3NF decomposition of the functional dependencies\n"
            + "that were obtained as a result of Canonical Cover\n"
            + "calculation. For Example:\n"
            + "    Suppose result that was obtained is:\n"
            + "            X->A\n"
            + "            Y->B\n"
            + "            B->W\n"
            + "    To calculate 3NF we take the first FD from the list\n"
            + "    and make it our R1:\n"
            + "            R1(X->A)\n"
            + "    Then we take our next FD Y->B, and check if R1\n"
            + "    does NOT contain XA. If it does not we make it our\n"
            + "    R2, and if it does we simply move down to the next FD\n"
            + "    B->W and remove Y->B out of the list. But for this\n"
            + "    example R1 does not contain Y->B. So we make it our R2:\n"
            + "            R1(XA)\n"
            + "            R2(YB)\n"
            + "    After addition we proceed by the same set of rules.\n"
            + "    And as a result obtain a potential 3NF decomposition\n"
            + "            R1(XA)\n"
            + "            R2(YB)\n"
            + "            R3(BW)\n"
            + "Notice, that I have said that the decomposition that is\n"
            + "potential 3NF decomposition. For it to be 3NF it needs\n"
            + "to contain at least one key. If it does then we are done\n"
            + "and the decomposition that was calculated is 3NF. If it\n"
            + "does not, you need to come up with a key and simply insert\n"
            + "it as your Ri(). This way decomposition will be 3NF.\n\n"

            +"Generating Superkeys\n"
            +"In this step we are focusing on calculating\n"
            + "superkeys. To calculate a superkey you need to\n"
            + "follow two simple steps:\n"
            + "        Step 1: calculate the cover of the element\n"
            + "        Step 2: Check if your cover fits the rule\n"
            + "                for superkeys.\n"
            + "Rule: For an element to be considered as superkey\n"
            + "its cover needs to contain every element from relation.\n"
            + "For Example:\n"
            + "                RR(A, B, C, D)\n"
            + "                FD(A->B, B->CD) \n"
            + "                Cover for A-> ABCD\n"
            + "        Cover for A contains all elements in RR so\n"
            + "        A is a super key.\n\n"

            +"Generating Keys\n"
            +"In this step we are focusing on calculating\n"
            + "keys. To calculate a key you need to\n"
            + "follow two simple steps:\n"
            + "        Step 1: calculate the cover of the element\n"
            + "        Step 2: Check if your cover fits the rule\n"
            + "                for superkeys.\n"
            + "Rule: For an element to be considered a key\n"
            + "its cover needs to contain every element from relation.\n"
            + "and no proper subset of that key can be a super key.\n"
            + "For Example:\n"
            + "                RR(A, B, C, D)\n"
            + "                FD(A->B, B->CD) \n"
            + "                Cover for A-> ABCD\n"
            + "        Cover for A contains all elements in RR so\n"
            + "        A is a super key. That satisfies first condition\n"
            + "        And because A is a minimal key, A has no proper\n"
            + "        subsets so A is also a key.\n"
            + "For Example:\n"
            + "                RR(A, B, C, D, F)\n"
            + "                FD(A->D, C->D, B->C AB->F) \n"
            + "                Cover for A->AD\n"
            + "                Cover for AB->ADBCF\n"
            + "        AB contains all elements in RR so that satisfies\n"
            + "        the first condition. Lets check subsets of AB\n"
            + "                A->AD\n"
            + "                B->BCD\n"
            + "        No proper subset of AB is a super key so.\n"
            + "        we could safely say that AB is a key.\n\n"

            +"Checking if Key\n"
            +"In this step we are focusing on calculating\n"
            + "keys. To calculate a key you need to\n"
            + "follow two simple steps:\n"
            + "        Step 1: calculate the cover of the element\n"
            + "        Step 2: Check if your cover fits the rule\n"
            + "                for superkeys.\n"
            + "Rule: For an element to be considered a key\n"
            + "its cover needs to contain every element from relation.\n"
            + "and no proper subset of that key can be a super key.\n"
            + "For Example:\n"
            + "                RR(A, B, C, D)\n"
            + "                FD(A->B, B->CD) \n"
            + "                Cover for A-> ABCD\n"
            + "        Cover for A contains all elements in RR so\n"
            + "        A is a super key. That satisfies first condition\n"
            + "        And because A is a minimal key, A has no proper\n"
            + "        subsets so A is also a key.\n"
            + "For Example:\n"
            + "                RR(A, B, C, D, F)\n"
            + "                FD(A->D, C->D, B->C AB->F) \n"
            + "                Cover for A->AD\n"
            + "                Cover for AB->ADBCF\n"
            + "        AB contains all elements in RR so that satisfies\n"
            + "        the first condition. Lets check subsets of AB\n"
            + "                A->AD\n"
            + "                B->BCD\n"
            + "        No proper subset of AB is a super key so.\n"
            + "        we could safely say that AB is a key.\n\n"

            +"Checking if SuperKey\n"
            +"In this step we are focusing on calculating\n"
            + "superkeys. To calculate a superkey you need to\n"
            + "follow two simple steps:\n"
            + "        Step 1: calculate the cover of the element\n"
            + "        Step 2: Check if your cover fits the rule\n"
            + "                for superkeys.\n"
            + "Rule: For an element to be considered as superkey\n"
            + "its cover needs to contain every element from relation.\n"
            + "For Example:\n"
            + "                RR(A, B, C, D)\n"
            + "                FD(A->B, B->CD) \n"
            + "                Cover for A-> ABCD\n"
            + "        Cover for A contains all elements in RR so\n"
            + "        A is a super key.\n\n"

            + "Checking Closure\n"
            +"To check if X determines Y simply calculate\n"
            + "the cover for X. And if cover for X contains Y\n"
            + "then you can say that X determines Y. Otherwise,\n"
            + "X does not determine Y.\n\n"

            +"Checking Inference\n"
            +"To check if we can infer X->Y from relation RR\n"
            + "simply calculate the cover for X. And if cover\n"
            + "for X contains Y then you can say that we can infer \n"
            + "X->Y from RR. And if it does not then we can not.\n\n"
            ;
}
