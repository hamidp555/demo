package com.message.demo.test.component.common;

import cucumber.runtime.java.picocontainer.PicoFactory;

public class ObjectFactory extends PicoFactory {
    public ObjectFactory() {
        addClass(Context.class);
    }
}

