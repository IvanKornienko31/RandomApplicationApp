package com.github.ivankornienko31.randomapplication

import android.app.Application
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

/**
 * Этот класс необходим для корректной работы Napier.
 *
 * Из-за короткого ЖЦ Activity и постоянной её перерисовки после смены конфигураций, необходимо всё
 * логирование выносить в отдельный класс, характеризующий приложение.
 *
 * Соответствующий класс добавлен в AndroidManifest в тег `<application>`
 */

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()

//      Инициализация логов Napier
        Napier.base(DebugAntilog())
    }
}