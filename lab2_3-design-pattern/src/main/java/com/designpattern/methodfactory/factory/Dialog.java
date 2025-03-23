package com.designpattern.methodfactory.factory;

import com.designpattern.methodfactory.buttons.Button;

public abstract class Dialog {
    public void renderWindow(){
        Button okButton = createButton();
        okButton.render();
    }

    protected abstract Button createButton();
}

