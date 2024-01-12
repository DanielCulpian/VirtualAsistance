package com.example.virtualasistance

class PatronIdentifier {
    private val patronesSaludo = listOf(
        "hola",
        "hi",
        "buenas",
        "saludos",
        "encantado",
        "encantada",
        "holis"
    )

    private val patronesNombre = listOf(
        "nombre",
        "llamas",
        "como te llamas",
        "dime tu nombre",
        "tu nombre",
        "como te denominas",
        "quien eres"
    )

    private val patronesEstado = listOf(
        "que tal",
        "como se encuentra usted",
        "que tal te encuentras",
        "que tal estas",
        "como estas",
        "estas bien",
        "como te encuentras",
        "estado"
    )

    private val patronesHora = listOf(
        "hora",
        "que hora es",
        "me puedes decir la hora",
        "hora actual"
    )

    private val patronesFecha = listOf(
        "dia",
        "que dia es hoy",
        "me puedes decir en que dia estamos",
        "dia actual",
        "me podrias decir la fecha",
        "fecha"

    )

    fun identificarPatron(texto: String): String{
        var resultado = "sin patron"
        var patron = "\\b(${patronesSaludo.joinToString("|")})\\b".toRegex(RegexOption.IGNORE_CASE)

        if(patron.containsMatchIn(texto)){
            resultado = "saludo"
        }else{
            patron = "\\b(${patronesNombre.joinToString("|")})\\b".toRegex(RegexOption.IGNORE_CASE)
            if(patron.containsMatchIn(texto)){
                resultado = "nombre"
            }else{
                patron = "\\b(${patronesEstado.joinToString("|")})\\b".toRegex(RegexOption.IGNORE_CASE)
                if(patron.containsMatchIn(texto)){
                    resultado = "estado"
                }else{
                    patron = "\\b(${patronesHora.joinToString("|")})\\b".toRegex(RegexOption.IGNORE_CASE)
                    if (patron.containsMatchIn(texto)){
                        resultado = "hora"
                    }else{
                        patron ="\\b(${patronesFecha.joinToString("|")})\\b".toRegex(RegexOption.IGNORE_CASE)
                        if (patron.containsMatchIn(texto)){
                            resultado = "fecha"
                        }
                    }
                }
            }
        }

        return resultado

    }
}