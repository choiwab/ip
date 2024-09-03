package blacknut.ui;

import blacknut.ui.BlacknutExceptions.IncorrectFormatException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    public static Task fromFileFormat(String line) throws IncorrectFormatException {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new IncorrectFormatException("File format incorrect: " + line);
        }
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (parts.length != 4) throw new IncorrectFormatException("File format incorrect: " + line);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime by = LocalDateTime.parse(parts[3], formatter);
                task = new Deadline(description, by.format(formatter));
                break;
            case "E":
                if (parts.length != 5) throw new IncorrectFormatException("File format incorrect: " + line);
                task = new Event(description, parts[3], parts[4]);
                break;
            default:
                throw new IncorrectFormatException("Unknown task type: " + type);
        }

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }

    public String toFileFormat() {
        return this.getClass().getSimpleName().substring(0, 1) + " | " + (isDone ? "1" : "0") + " | " + description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
