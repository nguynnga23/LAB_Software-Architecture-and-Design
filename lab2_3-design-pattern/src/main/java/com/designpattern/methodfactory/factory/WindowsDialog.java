package com.designpattern.methodfactory.factory;

import com.designpattern.methodfactory.buttons.Button;
import com.designpattern.methodfactory.buttons.WindowsButton;

public class WindowsDialog extends Dialog{
    @Override
    protected Button createButton() {
        return new WindowsButton();
    }
}
