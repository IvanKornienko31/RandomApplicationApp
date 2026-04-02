package com.github.ivankornienko31.stepikclientapplication

import android.app.Application
import androidx.datastore.core.DataStore
import com.github.ivankornienko31.stepikclientapplication.datastore.OnboardingPreferences
import com.github.ivankornienko31.stepikclientapplication.datastore.createDataStore
import com.github.ivankornienko31.stepikclientapplication.di.initKoin
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.dsl.module
import ru.ivk1800.riflesso.Riflesso
import androidx.datastore.preferences.core.Preferences

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
        val platformModule = module {
            val dataStore = createDataStore(applicationContext)
            single<DataStore<Preferences>> { dataStore }
        }

        initKoin(platformDependencies = platformModule) {
            androidContext(this@MainApp)
            androidLogger()
        }

        // Инициализация плагина Riflesso
        Riflesso.initialize()

        // Инициализация логов Napier
        Napier.base(DebugAntilog())
    }
}