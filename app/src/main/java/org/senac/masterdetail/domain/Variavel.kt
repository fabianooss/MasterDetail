package org.senac.masterdetail.domain

import java.io.Serializable
import java.util.*

data class Variavel (var nome: String, var tipo: TipoVariavel, var valores: MutableList<Valores>) : Serializable {


    fun addOrUpdate(valor : Valores?, txt : String) {
        valor.apply {
            this?.valor = txt
        }
        if (valor == null) {
            this.valores.add(
                Valores(
                    UUID.randomUUID().toString(),
                    txt
                )
            )
        }
    }

    fun remover(valor : Valores?) {
        this.valores.remove(valor)
    }

}

enum class TipoVariavel {
    STRING, INTEIRO, NUMERICO
}

data class Valores (val id: String, var valor: String) : Serializable {

}