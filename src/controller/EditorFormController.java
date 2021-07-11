package controller;


import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.FXUtil;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class EditorFormController {
    private final List<Index> searchList = new ArrayList<>();
    public TextArea txtEditor;
    public AnchorPane pneFind;
    public TextField txtSearch;
    public MenuItem mnuItem;
    public AnchorPane pneReplace;
    public TextField txtSearch1;
    public TextField txtSearch11;
    public TextField txtReplace;
    private int findOffset = -1;
    private PrinterJob printerJob;

   // private int findOffSet;

    public  void  initialize(){
        pneFind.setVisible(false);
        pneReplace.setVisible(false);
        this.printerJob = PrinterJob.createPrinterJob();

        ChangeListener textListener = (ChangeListener<String>) (observable, oldValue, newValue) -> {
            searchMatches(newValue);
        };

        txtSearch.textProperty().addListener(textListener);
        txtSearch1.textProperty().addListener(textListener);
    }

    private void searchMatches(String query) {
        FXUtil.highlightOnTextArea(txtEditor,query, Color.web("yellow", 0.8));

        try {
            Pattern regExp = Pattern.compile(query);
            Matcher matcher = regExp.matcher(txtEditor.getText());

            searchList.clear();

            while (matcher.find()) {
                searchList.add(new Index(matcher.start(), matcher.end()));
            }

            if (searchList.isEmpty()){
                findOffset = -1;
            }
        } catch (PatternSyntaxException e) {

        }
    }


    public void mnuItemNew_OnAction(ActionEvent actionEvent) {
        txtEditor.clear();
        txtEditor.requestFocus();
    }

    public void mnuItemExit_OnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) txtSearch.getScene().getWindow();
        stage.close();
    }

    public void mnuItemFind_OnAction(ActionEvent actionEvent) {
        pneFind.setVisible(true);
        txtSearch.requestFocus();
    }

    public void mnuItemReplace_OnAction(ActionEvent actionEvent) {
        findOffset = -1;
        if (pneFind.isVisible()){
            pneFind.setVisible(false);
        }
        pneReplace.setVisible(true);
        txtSearch1.requestFocus();
    }

    public void mnuItemSelectAll_OnAction(ActionEvent actionEvent) {
        txtEditor.selectAll();
    }


    public void btnFindNext_OnAction(ActionEvent actionEvent) {
      /*  String pattern=txtSearch.getText();
        String text=txtEditor.getText();

        Pattern regEx=Pattern.compile(pattern);
        Matcher matcher=regEx.matcher(text);

        boolean exists = matcher.find(findOffSet);
        if (exists){
            findOffSet=matcher.start() + 1;
            txtEditor.selectRange(matcher.start(), matcher.end());
        }else {
            findOffSet=0;
        } */

        if (!searchList.isEmpty()) {
            findOffset++;
            if (findOffset >= searchList.size()) {
                findOffset = 0;
            }
            txtEditor.selectRange(searchList.get(findOffset).startingIndex, searchList.get(findOffset).endIndex);
        }
    }

    public void btnFindPrevious_OnAction(ActionEvent actionEvent) {

  /*      String pattern = new StringBuilder(txtSearch.getText()).reverse().toString();
        String text = new StringBuilder(txtEditor.getText()).reverse().toString();

        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(text);

        if (matcher.find(++findOffset)) {
            findOffset = matcher.start();
            txtEditor.selectRange(text.length() - matcher.end(), text.length() - matcher.start());

            if (!matcher.find(findOffset +1)){
                findOffset = -1;
            }
        }    */

        if (!searchList.isEmpty()) {
            findOffset--;
            if (findOffset < 0) {
                findOffset = searchList.size() - 1;
            }
            txtEditor.selectRange(searchList.get(findOffset).startingIndex, searchList.get(findOffset).endIndex);
        }


    }

    public void btnReplace_OnAction(ActionEvent actionEvent) {
        //        try {
//            String replacedText = Pattern.compile(txtSearch1.getText()).matcher(txtEditor.getText()).replaceAll(txtReplace.getText());
//            txtEditor.setText(replacedText);
//        }catch (PatternSyntaxException e){
//            // There is nothing to do here
//        }

//        for (int i = 0; i< searchList.size(); i++) {
//            txtEditor.replaceText(searchList.get(i).startingIndex, searchList.get(i).endIndex, txtReplace.getText());
//            searchMatches(txtSearch1.getText());
//            i=-1;
//        }

        while (!searchList.isEmpty()) {
            txtEditor.replaceText(searchList.get(0).startingIndex, searchList.get(0).endIndex, txtReplace.getText());
            searchMatches(txtSearch1.getText());
        }

    }

    public void btnReplaceAll_OnAction(ActionEvent actionEvent) {
        if (findOffset == -1) return;
        txtEditor.replaceText(searchList.get(findOffset).startingIndex, searchList.get(findOffset).endIndex, txtReplace.getText());
        searchMatches(txtSearch1.getText());
    }

    public void mnuFile_OpenOnAction(ActionEvent actionEvent) {     //to open another text file
        FileChooser fileChooser = new FileChooser();   //1st step
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add
                (new FileChooser.ExtensionFilter("All Text Files", "*.txt", "*.html"));
        fileChooser.getExtensionFilters().add
                (new FileChooser.ExtensionFilter("All Files", "*"));
        File file = fileChooser.showOpenDialog(txtEditor.getScene().getWindow());   //secon step

        if (file == null) return;

        txtEditor.clear();
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                txtEditor.appendText(line + '\n');
            }

        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

    public void mnuSaveAs_OnAction(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        File file = fileChooser.showSaveDialog(txtEditor.getScene().getWindow());  // api save as giyama file eka save karanna tanak select karanawane eg-desktop. Me method eken wenne a tana point wena eka.onanam sout ekak dala blnna save as gihin tanak torala

        if (file == null) return;   //save dialog box eka enawa. eka cancel karoth tama file eka null wenne

        try(FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw)){
            bw.write(txtEditor.getText());
        }catch (IOException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void mnuPrint_OnAction(ActionEvent actionEvent) {
        boolean printDialog = printerJob.showPrintDialog(txtEditor.getScene().getWindow());
        if(printDialog){              //meka damme nathnam print dialog box eka ahama print nokara cancel karoth exit code eka 0 wen na
            printerJob.printPage(txtEditor.lookup("Text"));
       }
    }

    public void mnuPageSetup_OnAction(ActionEvent actionEvent) {
     printerJob.showPageSetupDialog(txtEditor.getScene().getWindow());
    }
}


    class Index {
        int startingIndex;
        int endIndex;

        public Index(int startingIndex, int endIndex) {
            this.startingIndex = startingIndex;
            this.endIndex = endIndex;
        }
    }

