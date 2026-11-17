package org.AnsySan.console;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.AnsySan.operation.ConsoleOperationType;
import org.springframework.stereotype.Component;

@Component
public class ConsoleInput {
    private final Scanner scanner;

    public ConsoleInput() {
        this.scanner = new Scanner(System.in);
    }

    public void printAvailableCommands() {
        String availableCommands = (String)Arrays.stream(ConsoleOperationType.values()).map(Enum::name).collect(Collectors.joining(", "));
        System.out.println("Available commands: " + availableCommands);
    }

    public String readRequiredString(String prompt) {
        System.out.println(prompt);
        String value = this.scanner.nextLine().trim();
        if (value.isBlank()) {
            throw new IllegalArgumentException("Input must not be empty");
        } else {
            return value;
        }
    }

    public Integer readRequiredInt(String prompt) {
        System.out.println(prompt);
        String value = this.scanner.nextLine().trim();
        if (value.isBlank()) {
            throw new IllegalArgumentException("Expected an integer, but got empty input");
        } else {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException var4) {
                throw new IllegalArgumentException("Invalid integer format: '" + value + "'");
            }
        }
    }
}