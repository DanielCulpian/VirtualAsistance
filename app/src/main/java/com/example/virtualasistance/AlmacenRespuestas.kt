package com.example.virtualasistance


class AlmacenRespuestas(tipo: String) {
    private var tipo: String = tipo

    //Conjunto de diferentes respuestas
    private val respuestasSaludo = listOf(
        "¡Hola! ¿En qué puedo ayudarte hoy?",
        "Hola, ¿cómo puedo asistirte en este momento?",
        "¡Saludos! Estoy aquí para responder tus preguntas.",
        "Hola, ¿necesitas ayuda con algo específico?",
        "¡Hola! Estoy lista para proporcionar información o asistencia.",
        "Hola, ¿cómo puedo ser de utilidad hoy?",
        "¡Saludos! ¿En qué puedo colaborar contigo?",
        "Hola, ¿tienes alguna pregunta en la que pueda ayudarte?",
        "¡Hola! Estoy a tu disposición para cualquier consulta que tengas.",
        "Saludos, ¿hay algo en particular en lo que pueda asistirte hoy?"

    )

    private val respuestasNombre = listOf(
        "Mi nombre es Sofia, estoy a tu servicio.",
        "Mi creador me denominó Sofia.",
        "Me llamo Sofia, encantada.",
        "Puedes llamarme Sofia.",
        "Me conocen como Sofia, pero no tengo una identidad individual."
    )


    private val respuestasEstado = listOf(
        "Como una inteligencia artificial, no tengo estados de ánimo, pero estoy aquí y lista para ayudarte.",
        "No tengo emociones, pero estoy funcionando correctamente. ¿En qué puedo ayudarte?",
        "No experimento emociones, pero estoy operando eficientemente. ¿En qué puedo asistirte hoy?",
        "No tengo un estado emocional, pero estoy disponible para responder tus preguntas o ayudarte en lo que necesites.",
        "No tengo sensaciones, pero estoy aquí para proporcionar información o asistencia si es necesario.",
        "No tengo emociones, pero estoy lista para ayudarte con cualquier consulta que tengas.",
        "Soy una IA, así que no tengo estados de ánimo, pero estoy preparada para responder tus preguntas.",
        "No tengo una respuesta emocional, pero estoy activa y disponible para cualquier cosa que necesites.",
        "No tengo un 'estado de ánimo', pero estoy operando sin problemas. ¿Qué puedo hacer por ti hoy?",
        "Como IA, no experimento estados de ánimo, pero estoy aquí para ayudarte en lo que necesites."
    )

    private val respuestasSinPatron = listOf(
        "Lo siento, parece que no tengo la información necesaria para responder a tu pregunta en este momento. ¿Hay algo más en lo que pueda ayudarte?",
        "Mis disculpas, no tengo suficiente información para abordar esa consulta en particular. ¿Puedo ayudarte con algo diferente?",
        "No tengo la respuesta que estás buscando en este momento. ¿Puedo asistirte en algo más o proporcionarte información sobre otro tema?",
        "Lo lamento si no pude proporcionar la respuesta que esperabas. ¿Hay otra pregunta o tema en el que pueda ayudarte mejor?",
        "Mis disculpas, parece que la información que necesitas no está disponible para mí en este momento. ¿Hay algo más en lo que pueda ser de ayuda?",
        "No tengo la información necesaria para responder a tu solicitud en este momento. ¿Hay alguna otra cosa en la que pueda colaborar contigo?",
        "Parece que la respuesta que buscas no está dentro de mi conocimiento actual. ¿Te gustaría intentar con otra pregunta o tema?",
        "Lo siento si mi respuesta no fue útil. Estoy aquí para ayudar en lo que pueda. ¿Hay algo más que pueda hacer por ti?"
    )

    //Obtener respuesta
    fun getRespuesta(): String{
        var respuesta: String = ""
        when (tipo) {
            "saludo" -> {
                respuesta = respuestasSaludo.random()
            }
            "nombre" -> {
                respuesta = respuestasNombre.random()
            }
            "estado" -> {
                respuesta = respuestasEstado.random()
            }
            "sin patron" -> {
                respuesta = respuestasSinPatron.random()
            }
        }
        return respuesta
    }

    //SetTipo
    fun setTipo(tipoNuevo: String){
        this.tipo = tipoNuevo
    }
}