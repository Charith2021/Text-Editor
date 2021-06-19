package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditorFormController {
    public TextArea txtEditor;
    public AnchorPane pneFind;
    public TextField txtSearch;

    private int findOffSet;

    public  void  initialize(){
        pneFind.setVisible(false);
    }




    public void mnuItemNew_OnAction(ActionEvent actionEvent) {
        txtEditor.clear();
        txtEditor.requestFocus();
    }

    public void mnuItemExit_OnAction(ActionEvent actionEvent) {
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
        }
    }

    public void btnFindPrevious_OnAction(ActionEvent actionEvent) {
    }
}
