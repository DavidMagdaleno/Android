package Auxiliar

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object NombreFoto {
    @RequiresApi(Build.VERSION_CODES.O)
    private var contador: LocalDateTime? = LocalDateTime.now()

    @RequiresApi(Build.VERSION_CODES.O)
    fun getContador(): String {
        return contador.toString()
    }
    var log:String="Bitacora"
}