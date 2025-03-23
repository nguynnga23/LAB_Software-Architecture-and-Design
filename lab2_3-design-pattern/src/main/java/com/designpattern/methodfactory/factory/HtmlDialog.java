package com.designpattern.methodfactory.factory;

import com.designpattern.methodfactory.buttons.Button;
import com.designpattern.methodfactory.buttons.HtmlButton;

public class HtmlDialog extends Dialog{
    @Override
    protected Button createButton() {
        return new HtmlButton();
    }
}
