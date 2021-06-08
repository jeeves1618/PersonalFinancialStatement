package CommonModules;
import java.util.Scanner;

public class RupeeFormatter{
    public String formattedRupee(String fmtdAmount){
        int stringLength = fmtdAmount.length();
        char[] inptAmtChar = fmtdAmount.toCharArray();
        char[] outpAmtChar = new char[stringLength*2];
        int outIterator = stringLength*2, inIterator;
        boolean swapFlag = true;
        String rupeeFormatted;
        for(inIterator = 1; inIterator < stringLength && inptAmtChar[stringLength - inIterator] != ' '; inIterator++){
            if (inIterator >= 10 && inptAmtChar[stringLength - inIterator] != ',' && swapFlag == true) {
                outpAmtChar[--outIterator] = ',';
                outpAmtChar[--outIterator] = inptAmtChar[stringLength - inIterator];
                swapFlag = false;
            } else if (inIterator >= 10 && inptAmtChar[stringLength - inIterator] != ',' && swapFlag == false){
                outpAmtChar[--outIterator] = inptAmtChar[stringLength - inIterator];
                swapFlag = true;
            } else if (inIterator < 10){
                outpAmtChar[--outIterator] = inptAmtChar[stringLength - inIterator];
                swapFlag = true;
            }
        }
        if((stringLength - inIterator) == 2)
            rupeeFormatted = "Rs." + new String(outpAmtChar).replace("\u0000", "");
        else
            rupeeFormatted = "-Rs." + new String(outpAmtChar).replace("\u0000", "");
        return rupeeFormatted;
    }
    public static void main(String args[]){
        RupeeFormatter r1 = new RupeeFormatter();
        Scanner scan = new Scanner(System.in);

        System.out.println("Please Enter your Annual Salary in String format :");
        String fmtdAmt = scan.nextLine();
        System.out.println(r1.formattedRupee(fmtdAmt));
    }
}
