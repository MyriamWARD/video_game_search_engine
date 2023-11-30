package fr.lernejo.fileinjector;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class LauncherTest {

    @Test
    void main_terminates_before_10_sec() {
        assertTimeoutPreemptively(
            Duration.ofSeconds(10L),
            () -> Launcher.main(new String[]{}));
    }
}
