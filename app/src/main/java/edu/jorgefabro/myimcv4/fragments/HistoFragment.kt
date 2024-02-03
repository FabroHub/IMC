package edu.jorgefabro.myimcv4.fragments

import android.app.AlertDialog
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import edu.jorgefabro.myimcv4.DbHelpertAplication
import edu.jorgefabro.myimcv4.FileUtils.DataFiles
import edu.jorgefabro.myimcv4.R
import edu.jorgefabro.myimcv4.adapters.RecycleAdapter
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.fragment_histo.*


class HistoFragment : Fragment() {
    var listData: MutableList<DataFiles> = ArrayList()
    lateinit var adapter: RecycleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_histo, container, false)

        initRV(view)

        return view
    }

    private fun initRV(view: View) {

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = RecycleAdapter(this.requireContext(), listData)
        recyclerView.adapter = adapter

        val itemSwipe = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showDialog(viewHolder)

            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red
                        )
                    )
                    .addActionIcon(R.drawable.ic_baseline_delete_outline_24)
                    .create()
                    .decorate()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }

        val swap = ItemTouchHelper(itemSwipe)
        swap.attachToRecyclerView(recyclerView)
    }

    fun showDialog(viewHolder: RecyclerView.ViewHolder) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Eliminar")
        builder.setMessage("¿Está seguro que quiere eliminar este dato?")
        builder.setPositiveButton("Aceptar") { dialog, which ->
            val position = viewHolder.adapterPosition

            DbHelpertAplication.dataSource.delDato(listData[position].id)
            listData.removeAt(position)
            adapter.notifyItemRemoved(position)

            Snackbar.make(recyclerView, "Se han borrados los datos", Snackbar.LENGTH_SHORT).apply {
                setAction("ACEPTAR") {
                    //adapter.notifyItemChanged(/*deleteItem.toInt(),*/ deleteIndex)
                }

            }.show()
        }
        builder.setNegativeButton("Cancelar") { dialog, wich ->
            val position = viewHolder.adapterPosition
            adapter.notifyItemChanged(position)
            Toast.makeText(
                this.requireContext(),
                "No se ha borrado ningún dato",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.show()
    }

    override fun onResume() {
        super.onResume()
        listData = DbHelpertAplication.dataSource.getDatosTodo() as MutableList<DataFiles>
        adapter.setData(listData)
    }
}
