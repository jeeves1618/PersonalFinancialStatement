package IngestionEngine;

import CommonModules.bsheetElements;

public class IngestH2dbTest {
    public static void main(String args[]){
        bsheetElements[] bsheetElementsList;
        IngestH2db ingestH2db = new IngestH2db();
        bsheetElementsList = ingestH2db.transferData();
        for (int loopIterator = 0; loopIterator < bsheetElements.numofElements; loopIterator++){
            System.out.println(bsheetElementsList[loopIterator].typeAssetOrLiability);
            System.out.println(bsheetElementsList[loopIterator].subType);
            System.out.println(bsheetElementsList[loopIterator].itemDescription);
            System.out.println(bsheetElementsList[loopIterator].cashValue);
            System.out.println(bsheetElementsList[loopIterator].cashValueFmtd);
        }
    }
}
