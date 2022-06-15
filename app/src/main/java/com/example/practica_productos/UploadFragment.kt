package com.example.practica_productos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.practica_productos.databinding.FragmentUploadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class UploadFragment : Fragment() {

    private var _binding: FragmentUploadBinding? = null
    private lateinit var database: DatabaseReference

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUploadBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonEnviar.setOnClickListener {

            saveProduct()

        }

        binding.buttonVerProductos.setOnClickListener {
            findNavController().navigate(R.id.action_UploadFragment_to_ReadFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveProduct(){
        val nombre = binding.editNombre.text.toString()
        val talla = binding.editTalla.text.toString()
        val color = binding.editColor.text.toString()
        val precio = binding.editPrecio.text.toString()

        if (nombre.isEmpty()){
            binding.editNombre.error = "Ingrese nombre"
        }
        if (talla.isEmpty()){
            binding.editTalla.error = "Ingrese talla"
        }
        if (color.isEmpty()){
            binding.editColor.error = "Ingrese color"
        }
        if (precio.isEmpty()){
            binding.editPrecio.error = "Ingrese precio"
        }
        else {

            database = FirebaseDatabase.getInstance().getReference("productos")
            val prodId = database.push().key!!
            val producto = Product(nombre, talla, color, precio.toFloat())
            database.child(prodId).setValue(producto).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, "Registro adecuado", Toast.LENGTH_LONG).show()
                    binding.editNombre.text.clear()
                    binding.editTalla.text.clear()
                    binding.editColor.text.clear()
                    binding.editPrecio.text.clear()

                } else {
                    Toast.makeText(
                        context,
                        "No se pudo registrar el producto",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    }
}