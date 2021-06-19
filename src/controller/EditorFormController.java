package controller;

import com.sun.java.util.jar.pack.ConstantPool;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class EditorFormController {
    public TextArea txtEditor;
    public AnchorPane pneFind;
    public TextField txtSearch;

    private int findOffSet;

    public  void  initialize(){
        pneFind.setVisible(false);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {

            FXUtil.highlightOnTextArea(txtEditor,newValue, Color.web("yellow", 0.8));

            try {
                Pattern regExp = Pattern.compile(newValue);
                Matcher matcher = regExp.matcher(txtEditor.getText());

                searchList.clear();

                while (matcher.find()) {
                    searchList.add(new ConstantPool.Index(matcher.start(), matcher.end()));
                }
            } catch (PatternSyntaxException e) {

            }
        });
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
        }else {
            findOffSet=0;
        }
    }

    public void btnFindPrevious_OnAction(ActionEvent actionEvent) {
        String pattern=txtSearch.getText();
        String text=txtEditor.getText();

        Pattern regEx=Pattern.compile(pattern);
        Matcher matcher=regEx.matcher(text);

        boolean exists = matcher.find(findOffSet);
        if (exists){
            findOffSet=matcher.start() - 1;
            txtEditor.selectRange(matcher.start(), matcher.end());
        }else {
            findOffSet=0;
        }

    }
}
