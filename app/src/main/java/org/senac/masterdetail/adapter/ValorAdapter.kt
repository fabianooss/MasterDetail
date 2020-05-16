package org.senac.masterdetail.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.senac.masterdetail.R
import org.senac.masterdetail.domain.Valores


class ValorAdapter(val valores: List<Valores>)  : RecyclerView.Adapter<ValorAdapter.ValorViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    var onItemClick: ((Valores) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValorViewHolder {
       val view = LayoutInflater.from(parent.context)
           .inflate(R.layout.valor_item, parent, false)
       return ValorViewHolder((view))

    }

    override fun getItemCount(): Int {
        return valores.size
    }

    override fun onBindViewHolder(holder: ValorViewHolder, position: Int) {
        if (holder is ValorViewHolder) {
            holder.bind(valores.get(position))
        }
    }

    inner class ValorViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        private lateinit var tvItem : TextView

        init {
            tvItem = view.findViewById<TextView>(R.id.tv_item)
            tvItem.setOnClickListener {
                onItemClick?.invoke(valores.get(adapterPosition))
            }
        }

        fun bind(valor : Valores) {
            tvItem.text = valor.valor
        }
    }

}

