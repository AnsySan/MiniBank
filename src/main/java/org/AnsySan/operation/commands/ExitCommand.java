package org.AnsySan.operation.commands;

import org.AnsySan.operation.ConsoleOperationType;
import org.AnsySan.operation.OperationCommand;
import org.springframework.stereotype.Component;

@Component
public class ExitCommand implements OperationCommand {
    public ExitCommand() {
    }

    public void execute() {
        throw new IllegalArgumentException();
    }

    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.EXIT;
    }
}