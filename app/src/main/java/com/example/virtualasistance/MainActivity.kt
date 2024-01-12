package com.example.virtualasistance

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import pl.droidsonroids.gif.GifImageView
import java.text.Normalizer
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var textToSpeech: TextToSpeech
    private lateinit var speechRecognizer: SpeechRecognizer
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200

    private lateinit var textoRecibido: String
    private lateinit var textoAReproducir: String

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar TextToSpeech
        textToSpeech = TextToSpeech(this, this)

        // Inicializar SpeechRecognizer
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onError(error: Int) {}
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                textoRecibido = matches?.get(0) ?: "No se ha reconocido ningún texto."
                procesarTextoReconocido()
            }

            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        // Obtener la referencia al botón
        val botonEscuchar = findViewById<GifImageView>(R.id.listener)

        botonEscuchar.setOnClickListener {
            textToSpeech.stop()
            startSpeechRecognition()
        }
    }

    private fun procesarTextoReconocido() {
        val normalizado = Normalizer.normalize(textoRecibido, Normalizer.Form.NFD)
        val cadenaSinAcentos = Regex("[^\\p{ASCII}]").replace(normalizado, "")

        val patronIdentifier = PatronIdentifier()

        var tipo = patronIdentifier.identificarPatron(cadenaSinAcentos)

        val almacenR = AlmacenRespuestas(tipo)
        var respuesta = almacenR.getRespuesta()
        when (tipo) {
            "saludo", "nombre", "estado" -> {
                textoAReproducir = respuesta
            }
            "hora" -> {
                val date = Date()
                val sdf = SimpleDateFormat("HH:mm")
                val formattedTime: String = sdf.format(date)
                textoAReproducir = "Son las $formattedTime."
            }
            "fecha" -> {
                val date = Date()
                val sdf = SimpleDateFormat("dd-MM-yyyy")
                val formattedTime: String = sdf.format(date)
                textoAReproducir = "Hoy es $formattedTime."
            }
            else -> {
                almacenR.setTipo("sin patron")
                textoAReproducir = almacenR.getRespuesta()
            }
        }
        reproducirTexto()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun startSpeechRecognition() {
        // Solicitar permisos de grabación de audio
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.RECORD_AUDIO),
                REQUEST_RECORD_AUDIO_PERMISSION
            )
            return
        }

        // Configurar el reconocimiento de voz
        val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        recognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)

        // Iniciar la escucha
        speechRecognizer.startListening(recognizerIntent)
    }

    private fun reproducirTexto() {
        val labelRespuesta = findViewById<TextView>(R.id.respuestaLabel)
        labelRespuesta.text = textoAReproducir
        textToSpeech.speak(textoAReproducir, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Configuración del idioma, por ejemplo, español
            val result = textToSpeech.setLanguage(Locale("es", "ES"))

            // Verificar si la configuración del idioma fue exitosa
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Si el idioma no es compatible, mostrar un mensaje de error o realizar otra acción
            }
        } else {
            // Si hay un error en la inicialización de TextToSpeech, mostrar un mensaje de error o realizar otra acción
        }
    }

    override fun onDestroy() {
        // Liberar recursos de TextToSpeech al destruir la actividad
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }

        // Liberar recursos de SpeechRecognizer
        if (::speechRecognizer.isInitialized) {
            speechRecognizer.destroy()
        }

        super.onDestroy()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            // Permiso concedido, puedes iniciar la escucha
            startSpeechRecognition()
        }
    }
}
