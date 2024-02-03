package edu.jorgefabro.myimcv4.fragments

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.jorgefabro.myimcv4.FileUtils.DataFiles
import com.google.android.material.snackbar.Snackbar
import edu.jorgefabro.myimcv4.DbHelpertAplication
import edu.jorgefabro.myimcv4.R
import kotlinx.android.synthetic.main.fragment_histo.*
import kotlinx.android.synthetic.main.fragment_imc.*
import kotlinx.android.synthetic.main.fragment_imc.view.*
import java.text.DecimalFormat
import java.util.*

class ImcFragment : Fragment() {
    var listData: ArrayList<DataFiles> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_imc, container, false)

        setDate()
        view.btnCalc.setOnClickListener() {
            setListener()
        }

        return view
    }

    //función del botón, hace el cálculo y cuando poner si estás en un buen peso o no.
    private fun setListener() {
        try {

            val peso = etPeso.text.toString().toDouble()
            val altura = etAltura.text.toString().toDouble()

            val cambio = altura / 100
            twNumeros.text = (peso / (cambio * cambio)).toString()
            twInfo.text = "Concrete su género para mayor precisión"
        } catch (e: java.lang.Exception) {
            //Si no se ha rellenado algún campo aparecería un Toast con el mensaje de abajo.
            Snackbar.make(
                llTodo,
                "Para calcular debe rellenar todos los datos.",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        var info: String = ""
        var genero: String = ""
        var peso: String = ""
        var altura: String = ""

        if (etAltura.text != null || etPeso.text != null) {
            var imc: Double? = null

            try {
                imc = twNumeros.text.toString().toDouble()
                if (imc < 18.5 && rbHombre.isChecked) {
                    twInfo.text = "Peso inferior al normal"
                    info = "Peso inferior al normal"
                    genero = "Hombre"
                    twInfo.setBackgroundColor(Color.rgb(77, 210, 255))
                    peso = etPeso.text.toString()
                    altura = etAltura.text.toString()
                } else if (imc > 18.6 && twNumeros.text.toString()
                        .toDouble() < 24.9 && rbHombre.isChecked
                ) {
                    twInfo.text = "Normal"
                    info = "Normal"
                    genero = "Hombre"
                    twInfo.setBackgroundColor(Color.rgb(153, 255, 153))
                    peso = etPeso.text.toString()
                    altura = etAltura.text.toString()
                } else if (imc > 25 && twNumeros.text.toString()
                        .toDouble() < 29.9 && rbHombre.isChecked
                ) {
                    twInfo.text = "Sobrepeso"
                    info = "Sobrepeso"
                    genero = "Hombre"
                    twInfo.setBackgroundColor(Color.rgb(255, 153, 102))
                    peso = etPeso.text.toString()
                    altura = etAltura.text.toString()
                } else if (imc > 30 && rbHombre.isChecked) {
                    twInfo.text = "Obesidad"
                    info = "Obesidad"
                    genero = "Hombre"
                    twInfo.setBackgroundColor(Color.RED)
                    twInfo.setTextColor(Color.WHITE)
                    peso = etPeso.text.toString()
                    altura = etAltura.text.toString()
                } else if (imc < 18.5 && rbMujer.isChecked) {
                    twInfo.text = "Peso inferior al normal"
                    info = "Peso inferior al normal"
                    genero = "Mujer"
                    twInfo.setBackgroundColor(Color.rgb(77, 210, 255))
                    peso = etPeso.text.toString()
                    altura = etAltura.text.toString()
                } else if (imc > 18.6 && twNumeros.text.toString()
                        .toDouble() < 23.9 && rbMujer.isChecked
                ) {
                    twInfo.text = "Normal"
                    info = "Normal"
                    genero = "Mujer"
                    twInfo.setBackgroundColor(Color.rgb(153, 255, 153))
                    peso = etPeso.text.toString()
                    altura = etAltura.text.toString()
                } else if (imc > 24 && twNumeros.text.toString()
                        .toDouble() < 28.9 && rbMujer.isChecked
                ) {
                    twInfo.text = "Sobrepeso"
                    info = "Sobrepeso"
                    genero = "Mujer"
                    twInfo.setBackgroundColor(Color.rgb(255, 153, 102))
                    peso = etPeso.text.toString()
                    altura = etAltura.text.toString()
                } else if (imc > 29 && rbMujer.isChecked) {
                    twInfo.text = "Obesidad"
                    info = "Obesidad"
                    genero = "Mujer"
                    twInfo.setBackgroundColor(Color.RED)
                    twInfo.setTextColor(Color.WHITE)
                    peso = etPeso.text.toString()
                    altura = etAltura.text.toString()
                }
                val decimal = DecimalFormat("#.00")
                var imcreg = decimal.format(imc)

                /*val dpeso = DecimalFormat("##")
                var pesod = dpeso.format(peso)

                val daltura = DecimalFormat("##")
                var alturad = daltura.format(altura)*/

                //registro(info, genero, imcreg, peso, altura)

                val alertDialog = AlertDialog.Builder(requireContext())

                alertDialog.apply {
                    setTitle("Atención")
                    setMessage("¿Desea guardar el cálculo?")

                    setPositiveButton("Guardar") { _, _ ->

                        listData.add(DataFiles(dia, mes, anyo, info, genero, peso, altura, imcreg,id))
                        DbHelpertAplication.dataSource.addDatos(dia, mes, anyo, info, genero, peso, altura, imcreg)

                        Snackbar.make(
                            llTodo,
                            "Se ha guardado el registro en el historial",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    setNegativeButton("Descartar") { _, _ ->
                        Snackbar.make(
                            llTodo,
                            "No se ha guardado el registro en el historial",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
                alertDialog.show()
            } catch (e: java.lang.Exception) {
            }
        } else {
            Snackbar.make(
                llTodo,
                "Concrete su género para mayor precisión",
                Snackbar.LENGTH_SHORT
            )
                .show()
        }

    }

    private fun initRecyclerView(){
       /* recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = RecycleAdapter()
        recycleAdapter.adapter*/
    }

    var dia: Int = 0
    var mes: Int = 0
    var anyo: Int = 0
    private fun setDate() {
        val hoy = Calendar.getInstance()
        dia = hoy.get(Calendar.DAY_OF_MONTH)
        mes = hoy.get(Calendar.MONTH) + 1
        anyo = hoy.get(Calendar.YEAR)
    }

}