package org.AnsySan.operation;

public interface OperationCommand {
    void execute();

    ConsoleOperationType getOperationType();
}
