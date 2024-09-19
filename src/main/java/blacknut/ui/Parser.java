package blacknut.ui;

import blacknut.ui.BlacknutExceptions.*;

/**
 * Parses user input into commands and extracts necessary details for executing those commands.
 */
class Parser {

    /**
     * Extracts the command keyword from the user input.
     *
     * @param input The user input.
     * @return The command keyword.
     */
    public String parseCommand(String input) {
        assert input != null && !input.trim().isEmpty() : "Input should not be null or empty";
        return input.split(" ")[0].toLowerCase();
    }
    /**
     * Parses the task index from the user input.
     *
     * @param input The user input.
     * @return The task index (0-based).
     * @throws InvalidTaskNumberException If the task number is invalid.
     */
    public int parseIndex(String input) throws BlacknutExceptions.InvalidTaskNumberException {
        assert input != null && input.split(" ").length > 1 : "Input should have a task number";
        try {
            return Integer.parseInt(input.split(" ")[1]) - 1;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new BlacknutExceptions.InvalidTaskNumberException("Invalid task number. Please provide a valid number.");
        }
    }

    /**
     * Parses the description of a task from the user input.
     *
     * @param input The user input.
     * @param command The command keyword (e.g., "todo").
     * @return The task description.
     * @throws EmptyDescriptionException If the description is empty.
     */
    public String parseDescription(String input, String command) throws BlacknutExceptions.EmptyDescriptionException {
        assert command != null && !command.isEmpty() : "Command should not be null or empty";
        assert input.length() > command.length() : "Input should have a description after the command";
        String description = input.substring(command.length()).trim();
        if (description.isEmpty()) {
            throw new BlacknutExceptions.EmptyDescriptionException("The description cannot be empty.");
        }
        return description;
    }

    /**
     * Parses the deadline details from the user input.
     *
     * @param input The user input.
     * @return An array containing the task description and deadline date/time.
     * @throws IncorrectFormatException If the input format is incorrect.
     */
    public String[] parseDeadline(String input) throws BlacknutExceptions.IncorrectFormatException {
        assert input.contains(" /by ") : "Input should contain a /by separator";
        assert input.length() > 9 : "Input should be long enough to contain a description and deadline";
        String[] parts = input.substring(9).split(" /by ");
        if (parts.length != 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new BlacknutExceptions.IncorrectFormatException(
                    "The format for a deadline should be: deadline <description> /by <yyyy-MM-dd HH:mm>");
        }
        return new String[]{parts[0].trim(), parts[1].trim()};
    }

    /**
     * Parses the event details from the user input.
     *
     * @param input The user input.
     * @return An array containing the task description, start time, and end time.
     * @throws IncorrectFormatException If the input format is incorrect.
     */
    public String[] parseEvent(String input) throws BlacknutExceptions.IncorrectFormatException {
        assert input.contains(" /from ") && input.contains(" /to ") : "Input should contain /from and /to separators";
        assert input.length() > 6 : "Input should be long enough to contain a description, start, and end times";
        String[] parts = input.substring(6).split(" /from | /to ");
        if (parts.length != 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new BlacknutExceptions.IncorrectFormatException("The format for an event should be: event <description> /from <yyyy-mm-dd> /to <yyyy-mm-dd>");
        }
        return new String[]{parts[0].trim(), parts[1].trim(), parts[2].trim()};
    }

    /**
     * Parses the keyword for finding tasks from the user input.
     *
     * @param input The user input.
     * @return The keyword for finding tasks.
     * @throws EmptyDescriptionException If the keyword is empty.
     */
    public String parseKeyword(String input) {
        return input.substring(5).trim();
    }
}
