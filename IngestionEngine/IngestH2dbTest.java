package IngestionEngine;

import CommonModules.ChartOfAccounts;

public class IngestH2dbTest {
    public static void main(String args[]){
        ChartOfAccounts[] chartOfAccountsList;
        IngestH2db ingestH2db = new IngestH2db();
        chartOfAccountsList = ingestH2db.transferData();
        for (int loopIterator = 0; loopIterator < ChartOfAccounts.numofElements; loopIterator++){
            System.out.println(chartOfAccountsList[loopIterator].typeAssetOrLiability);
            System.out.println(chartOfAccountsList[loopIterator].subType);
            System.out.println(chartOfAccountsList[loopIterator].itemDescription);
            System.out.println(chartOfAccountsList[loopIterator].cashValue);
            System.out.println(chartOfAccountsList[loopIterator].cashValueFmtd);
        }
    }
}
