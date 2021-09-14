package mx.ipn.escom.app_plantas_iswm

interface OnItemClick {
    fun editarPlanta(dtoPlanta: ConsultarPlantas.DtoPlanta)
    fun eliminarPlanta(dtoPlanta: ConsultarPlantas.DtoPlanta)
    fun seeMore(dtoPlanta: ConsultarPlantas.DtoPlanta)
}
