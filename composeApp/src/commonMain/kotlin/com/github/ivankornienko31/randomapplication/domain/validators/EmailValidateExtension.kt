package com.github.ivankornienko31.randomapplication.domain.validators

/**
 * Данный Extension от класса String необходим для того, чтобы не приводить логику проверки email явно в коде экрана (частичное соблюдение CleanArchitecture)
 * @return Значение `true`, если email соответствует регулярному выражению. Иначе `false`
 *
 * @author Иван Корниенко*/

fun String.isValidEmail(): Boolean {
    if (isEmpty()) return false

    return Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$").matches(this)
}