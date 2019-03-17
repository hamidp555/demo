package com.message.demo.test.component;

import com.message.demo.test.component.common.MessageService;
import org.junit.BeforeClass;

public class AbstractTests {

    private static Boolean ready = false;

    @BeforeClass
    public static void beforeClass() {
        synchronized (ready) {
            if (ready) {
                return;
            }
            try {
                MessageService.startService();
            } catch (Exception e) {
                System.exit(1);
            }
            ready = true;
        }
    }
}
