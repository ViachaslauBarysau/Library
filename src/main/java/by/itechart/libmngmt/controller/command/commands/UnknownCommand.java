package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Processes requests if user was redirected to the error page.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UnknownCommand extends LibraryCommand {
    private static volatile UnknownCommand instance;

    public static synchronized UnknownCommand getInstance() {
        UnknownCommand localInstance = instance;
        if (localInstance == null) {
            synchronized (UnknownCommand.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new UnknownCommand();
                }
            }
        }
        return localInstance;
    }

    /**
     * Forwards user to the error page.
     *
     * @throws ServletException in case of servlet failure
     * @throws IOException      in case of IO failure
     */
    @Override
    public void process() throws ServletException, IOException {
        forward("unknownpage");
    }
}
