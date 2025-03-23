package com.designpattern.methodfactory;

import com.designpattern.methodfactory.factory.Dialog;
import com.designpattern.methodfactory.factory.HtmlDialog;
import com.designpattern.methodfactory.factory.WindowsDialog;

public class Demo {
    private static Dialog dialog;

    public static void main(String[] args) {
        configure();
        runBusinessLogic();
    }
    private static void configure() {
        if (System.getProperty("os.name").equals("Windows 10")) {
            dialog = new WindowsDialog();
        } else {
            dialog = new HtmlDialog();
        }
    }
    private static void runBusinessLogic() {
        dialog = new HtmlDialog();
    }


}
