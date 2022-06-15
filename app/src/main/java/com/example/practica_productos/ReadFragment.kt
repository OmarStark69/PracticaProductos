package com.example.practica_productos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica_productos.databinding.FragmentReadBinding
import com.google.firebase.database.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ReadFragment : Fragment() {

    private var _binding: FragmentReadBinding? = null
    private lateinit var database: DatabaseReference
    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var itemArrayList: ArrayList<Product>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentReadBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemRecyclerView = binding.itemList
        itemRecyclerView.layoutManager = LinearLayoutManager(getContext())
        itemRecyclerView.setHasFixedSize(true)

        itemArrayList = arrayListOf<Product>()
        getItemData()

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_ReadFragment_to_UploadFragment)
        }
    }

    private fun getItemData() {
        database = FirebaseDatabase.getInstance().getReference("productos")

        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (itemSnapshot in snapshot.children){

                        val item = itemSnapshot.getValue(Product::class.java)
                        itemArrayList.add(item!!)
                    }
                    itemRecyclerView.adapter = MyAdapter(itemArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}