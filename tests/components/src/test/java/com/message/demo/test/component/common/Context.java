package com.message.demo.test.component.common;


import com.message.demo.test.component.domain.Client;


public class Context {

    public Client getClient(){
        return new Client();
    }
}
