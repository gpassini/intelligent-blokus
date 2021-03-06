package com.intelligentblokus.intelligentblokus

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.shell.jline.InteractiveShellApplicationRunner
import org.springframework.shell.jline.ScriptShellApplicationRunner
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(properties = [
    InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
    ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"])
internal class IntelligentblokusApplicationTests {

    @Test
    fun contextLoads() {
    }

}
