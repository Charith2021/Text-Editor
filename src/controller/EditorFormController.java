package controller;


import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class EditorFormController {
    public TextArea txtEditor;
    public AnchorPane pneFind;
    public TextField txtSearch;
    public MenuItem mnuItem;
    private int findOffset = -1;

    private int findOffSet;

    public  void  initialize(){
        pneFind.setVisible(false);
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

    }

    public void mnuItemSelectAll_OnAction(ActionEvent actionEvent) {
        txtEditor.selectAll();
    }


    public void btnFindNext_OnAction(ActionEvent actionEvent) {
        String pattern=txtSearch.getText();
        String text=txtEditor.getText();

        Pattern regEx=Pattern.compile(pattern);
        Matcher matcher=regEx.matcher(text);

        boolean exists = matcher.find(findOffSet);
        if (exists){
            findOffSet=matcher.start() + 1;
            txtEditor.selectRange(matcher.start(), matcher.end());
        }else {
            findOffSet=0;
        }
    }

    public void btnFindPrevious_OnAction(ActionEvent actionEvent) {

        String pattern = new StringBuilder(txtSearch.getText()).reverse().toString();
        String text = new StringBuilder(txtEditor.getText()).reverse().toString();

        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(text);

        if (matcher.find(++findOffset)) {
            findOffset = matcher.start();
            txtEditor.selectRange(text.length() - matcher.end(), text.length() - matcher.start());

            if (!matcher.find(findOffset +1)){
                findOffset = -1;
            }
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
}
