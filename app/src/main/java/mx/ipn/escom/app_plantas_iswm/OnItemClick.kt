package mx.ipn.escom.app_plantas_iswm

import mx.ipn.escom.app_plantas_iswm.dto.DtoAreas
import mx.ipn.escom.app_plantas_iswm.dto.DtoPlanta

interface OnItemClick {
    fun seeMore(dtoPlanta: DtoPlanta)
    fun seeMore(dtoAreas: DtoAreas)
}
