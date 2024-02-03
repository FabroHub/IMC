package edu.jorgefabro.myimcv4.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.jorgefabro.myimcv4.FileUtils.DataFiles
import com.google.android.material.snackbar.Snackbar
import edu.jorgefabro.myimcv4.R
import kotlinx.android.synthetic.main.data_model.view.*

class RecycleAdapter(val context: Context,  val listD: List<DataFiles>) :
    RecyclerView.Adapter<RecycleAdapter.ViewHolder>() {
    var items = ArrayList<DataFiles>()

    init {
        items = listD as ArrayList<DataFiles>
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val item = items[position]

            lateinit var mes: String

            if (item.month == 1) {
                mes = "Enero"
            } else if (item.month == 2) {
                mes = "Febrero"
            } else if (item.month == 3) {
                mes = "Marzo"
            } else if (item.month == 4) {
                mes = "Abril"
            } else if (item.month == 5) {
                mes = "Mayo"
            } else if (item.month == 6) {
                mes = "Junio"
            } else if (item.month == 7) {
                mes = "Julio"
            } else if (item.month == 8) {
                mes = "Agosto"
            } else if (item.month == 9) {
                mes = "Septiembre"
            } else if (item.month == 10) {
                mes = "Octubre"
            } else if (item.month == 11) {
                mes = "Nomviembre"
            } else {
                mes = "Diciembre"
            }

            holder.dayView.text = item.day.toString()
            holder.monthView.text = mes
            holder.ageView.text = item.age.toString()
            holder.indexView.text = item.index
            holder.genreView.text = item.genre
            holder.weightView.text = item.weight
            holder.heightView.text = item.height
            holder.imcView.text = item.imc
        } catch (e: Exception) {
        }
    }

    fun deleteItem(pos:Int){
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.data_model, parent, false)

        view.rLayout.setOnClickListener() {
            Snackbar.make(parent, "${items[0].index} (${items[0].imc})", Snackbar.LENGTH_SHORT)
                .show()
        }
        return ViewHolder(view)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayView: TextView = view.twDay
        val monthView: TextView = view.twMonth
        val ageView: TextView = view.twAge
        val indexView: TextView = view.twIndex
        val genreView: TextView = view.twGenre
        val weightView: TextView = view.twPesoRv
        val heightView: TextView = view.twAlturaRv
        val imcView: TextView = view.twCalc
    }

    fun setData(listData: List<DataFiles>) {
        items = listData as ArrayList<DataFiles>
        notifyDataSetChanged()
    }

}


