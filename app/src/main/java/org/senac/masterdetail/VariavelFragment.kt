package org.senac.masterdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.senac.masterdetail.adapter.ValorAdapter
import org.senac.masterdetail.domain.TipoVariavel
import org.senac.masterdetail.domain.Valores
import org.senac.masterdetail.domain.Variavel
import java.util.*

class VariavelFragment : Fragment() {

    private lateinit var txtVariavel: TextView

    private lateinit var rgTipo : RadioGroup

    private lateinit var rvValores : RecyclerView

    private lateinit var variavel: Variavel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_variavel, container, false)

        bindComponents(view)

        variavel = getVariavel()

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            findNavController(it).navigate(
                VariavelFragmentDirections.actionVariavelFragmentToValorFragment(variavel))
        }

        setupValores(variavel)

        return view
    }

    fun bindComponents(view : View) {
        txtVariavel = view.findViewById<TextView>(R.id.et_variavel)
        rvValores = view.findViewById<RecyclerView>(R.id.rv_valores)
        rgTipo = view.findViewById<RadioGroup>(R.id.radioGroup)
    }

    fun setupValores(variavel: Variavel) {
        val layout = LinearLayoutManager(context)
        val adapter =
            ValorAdapter(variavel.valores)
        rvValores.adapter = adapter
        rvValores.layoutManager = layout
        adapter.onItemClick = { valor ->
            findNavController(rvValores).navigate(
                VariavelFragmentDirections.actionVariavelFragmentToValorFragment(variavel, valor))
        }

    }

    fun getVariavel() : Variavel {

        val variavel= VariavelFragmentArgs.fromBundle(arguments!!).variavel ?: load()

        txtVariavel.text = variavel.nome
        when (variavel.tipo) {
            TipoVariavel.STRING ->      rgTipo.check(R.id.rb_string)
            TipoVariavel.INTEIRO ->     rgTipo.check(R.id.rb_inteiro)
            TipoVariavel.NUMERICO ->    rgTipo.check(R.id.rb_numerica)
        }
        rgTipo.setOnCheckedChangeListener { _, checkedId ->
            variavel.tipo =
                when (checkedId) {
                    R.id.rb_string -> TipoVariavel.STRING
                    R.id.rb_inteiro -> TipoVariavel.INTEIRO
                    else -> TipoVariavel.NUMERICO
                }
        }
        return variavel
    }

    fun load(): Variavel {
        // se possuir uma api ou banco de dados, deve modificar para carregar essas informações
        val valores = mutableListOf<Valores>(
            Valores(UUID.randomUUID().toString(),"valor 1"),
            Valores(UUID.randomUUID().toString(),"valor 2")
        )
        return Variavel("Minha variável", TipoVariavel.STRING,  valores)
    }



}
