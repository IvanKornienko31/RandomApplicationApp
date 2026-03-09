package com.github.ivankornienko31.stepikclientapplication

import android.app.Application
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import ru.ivk1800.riflesso.Riflesso

/**
 * Этот класс необходим для корректной работы Napier и Riflesso.
 *
 * Из-за короткого ЖЦ Activity и постоянной её перерисовки после смены конфигураций, необходимо всё
 * логирование выносить в отдельный класс, характеризующий приложение.
 *
 * Чтобы плагин из Android Studio мог считывать рекомпозиции внутри приложения, дополнительно прокидывается [Riflesso.initialize]
 *
 * Соответствующий класс приложения добавлен в AndroidManifest в тег `<application>`
 *
 * *Примечание:* данная функция пока что работает только для Android. В iOS будет внедрено позднее
 *
 * @author Иван Корниенко
 */

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Инициализация плагина Riflesso
        Riflesso.initialize()

        // Инициализация логов Napier
        Napier.base(DebugAntilog())
    }
}