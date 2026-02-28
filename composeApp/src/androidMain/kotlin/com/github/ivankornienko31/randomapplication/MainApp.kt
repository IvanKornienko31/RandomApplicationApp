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
 *
 * *Примечание:* данная функция пока что работает только для Android. В iOS будет внедрено позднее
 *
 * @author Иван Корниенко
 */

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Инициализация логов Napier
        Napier.base(DebugAntilog())
    }
}