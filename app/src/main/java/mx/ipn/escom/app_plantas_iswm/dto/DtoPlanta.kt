package mx.ipn.escom.app_plantas_iswm.dto

import java.io.Serializable

data class DtoPlanta(var idDocument: String = "",
                     var nombrePlanta: String = "",
                     var especie:String = "",
                     var subespecie:String = "",
                     var fechaPlantacion :String = "",
                     var fechaRegistro :String = "",
    /*
    var alto :String = "",
    var ancho :String = "",
    var largo :String = "",
    var lugarPlantacion:String = "",
    var temporadaPlantacion:String = "",
    var exposicionLuz:String = "",
    var tiempoExposicion:String = "",
    */
                     var usuario:String =""
) : Serializable
