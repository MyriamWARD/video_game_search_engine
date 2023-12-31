package fr.lernejo.fileinjector;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;
class LauncherTest {
    private final String path = "src/test/resources";
    @Test
    void main_terminates_before_5_sec() {
        assertTimeoutPreemptively(
            Duration.ofSeconds(5L),
            () -> Launcher.main(new String[]{}));
    }

    @Test
    void game_throwIOException_when_File_dont_Exist() {
        File gameFile = new File(this.path);
        String jsonGameFilePath = gameFile.getAbsolutePath() + "/game.json";
        assertThrows(IOException.class, ()->Launcher.main(new String[]{jsonGameFilePath}));
    }
}
