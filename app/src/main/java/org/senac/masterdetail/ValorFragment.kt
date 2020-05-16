package org.senac.masterdetail

import android.os.Bundle
import android.transition.Visibility
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import org.senac.masterdetail.domain.Valores
import org.senac.masterdetail.domain.Variavel
import java.util.*

class ValorFragment : Fragment() {

    private lateinit var variavel: Variavel

    private var valor : Valores? = null

    private lateinit var etValor : TextView

    private lateinit var btAdicionar : Button

    private lateinit var btExcluir : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_valor, container, false)

        variavel = ValorFragmentArgs.fromBundle(arguments!!).variavel
        valor = ValorFragmentArgs.fromBundle(arguments!!).valor

        initComponents(view)

        return view
    }

    fun initComponents(view : View) {
        etValor = view.findViewById<TextView>(R.id.et_valor)
        btAdicionar = view.findViewById<Button>(R.id.bt_adicionar_valor)
        btExcluir = view.findViewById<Button>(R.id.bt_excluir_valor)

        btAdicionar.setOnClickListener {
           adicionar(it)
        }

        view.findViewById<Button>(R.id.bt_cancelar_valor).setOnClickListener {
            cancelar(it)
        }

        btExcluir.setOnClickListener {
            excluir(it)
        }

        etValor.text = valor?.valor ?: ""
        btAdicionar.text = if (valor == null) "Adicionar" else "Editar"
        btExcluir.visibility = if (valor == null) View.GONE else View.VISIBLE

    }

    fun cancelar(view : View) {
        Navigation.findNavController(view)
            .navigate(ValorFragmentDirections.actionValorFragmentToVariavelFragment(variavel))

    }


    fun excluir(view : View) {
        this.variavel.remover(valor)
        Navigation.findNavController(view)
            .navigate(ValorFragmentDirections.actionValorFragmentToVariavelFragment(variavel))

    }

    fun adicionar(view : View) {
        if (etValor.text.trim().length < 1) {
            etValor.setError("Campo obrigatório")

        } else {
            etValor.setError(null)

            variavel.addOrUpdate(valor, etValor.text.toString())

            Navigation.findNavController(view)
                .navigate(ValorFragmentDirections.actionValorFragmentToVariavelFragment(variavel))
        }
    }

}
