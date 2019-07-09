package main

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Condition.be
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.sleep
import com.codeborne.selenide.logevents.SelenideLogger
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.leadium.click
import org.leadium.extensions.Selenoid
import org.leadium.should
import org.leadium.*
import org.leadium.utils.LogEventPrinter
import org.openqa.selenium.TimeoutException
import utils.FileFinder
import java.io.File
import java.lang.StringBuilder


class Main {

    @BeforeEach
    fun beforeEach() {
        try {
            Selenide.open("")
        } catch (e: TimeoutException) {
            println("Check your connection!")
        }
    }

    @AfterEach
    fun afterEach() {
        Selenide.close()
    }

    companion object {
        init {
            SelenideLogger.addListener("LogEventPrinter", LogEventPrinter)
            Configuration.baseUrl = "https://web.whatsapp.com/"
            Configuration.browser = "chrome"
            Configuration.driverManagerEnabled = true
            Configuration.driverManagerEnabled = true
            Configuration.headless = false
            Configuration.startMaximized = true
            Configuration.holdBrowserOpen = false
            Configuration.savePageSource = false
            Configuration.screenshots = false
            Configuration.timeout = 60_000L
        }
    }

    @Test
    fun begin() {
        val customers = ArrayList<String>()
        val customersFile = FileFinder() byEndOfPath "customers"
        customersFile.bufferedReader().use { customers.addAll(it.readLines()) }

        val message = StringBuilder()
        val messageFile = FileFinder() byEndOfPath "message"
        messageFile.bufferedReader().use { message.append(it.readText()) }

        "Поиск или новый чат" should be(visible)
        customers.forEach {
            "Поиск"["//input[@type='text']"] set it
            sleep(1_000L)
            if ("Чаты, контакты и сообщения не найдены" has visible) {
                println("Контакт $it не найден")
            } else {
                "Клиент"["//div[@class='X7YrQ']/div[@tabindex='-1']"].click()
                sleep(1_000L)
                "Введите сообщение" should be(visible)
                ("Введите сообщение"["//div[@contenteditable='true']"] set message).pressEnter()
                sleep(main.Configuration.interval)
            }
        }
        println("Done")
    }
}