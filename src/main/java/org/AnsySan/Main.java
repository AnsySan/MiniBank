package org.AnsySan;

import org.AnsySan.config.ApplicationConfiguration;
import org.AnsySan.console.OperationConsoleListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public Main() {
    }

    static void main() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(new Class[]{ApplicationConfiguration.class})) {
            OperationConsoleListener listener = (OperationConsoleListener)context.getBean(OperationConsoleListener.class);
            listener.start();
        }
    }
}