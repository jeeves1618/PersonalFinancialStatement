package ViewServices;

import CommonModules.ChartOfAccounts;
import CommonModules.NaturalLanguageProcessor;
import IngestionEngine.IngestNLPExcel;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewNaturalLanguageProcessor {
    String fileWithPathname;
    public ViewNaturalLanguageProcessor(){
        ResourceBundle properties  = ResourceBundle.getBundle("Properties");
        this.fileWithPathname = properties.getString("NLPtokenizer");
    }

    public ArrayList<NaturalLanguageProcessor> getNlpEntries(){
        IngestNLPExcel nlpEntries = new IngestNLPExcel(this.fileWithPathname);
        return nlpEntries.transferData();
    }
}
