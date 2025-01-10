package com.example.firebasecontacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasecontacts.databinding.FragmentMainBinding


class MainFragment : Fragment(), Removable {

    private var adapter: CustomAdapter? = null
    private var userList = User.users

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.recyclerViewRV.layoutManager = LinearLayoutManager(context)

        refreshData()

        binding.backBTN.setOnClickListener{
            val navController = findNavController()
            navController.navigate(R.id.action_mainFragment_to_loginFragment)
        }

        binding.saveBTN.setOnClickListener{
            val name = binding.nameET.text.toString()
            val phoneNumber = binding.phoneNumberTV.text.toString()

            userList.add(User(name, phoneNumber))

            binding.nameET.text.clear()
            binding.phoneNumberTV.text.clear()
            onResume()
        }




        return binding.root
    }

    override fun onResume() {
        super.onResume()
        refreshData()
        adapter?.setOnItemClickListener(object :
            CustomAdapter.OnItemClickListener{
            override fun onItemClick(user: User, position: Int) {
                val dialog = MyDialog()
                val args = Bundle()
                args.putSerializable("user", user)
                dialog.arguments = args
                dialog.setRemovableListener(this@MainFragment)
                dialog.show(childFragmentManager, "custom")
            }
        })

    }

    private fun refreshData() {
        adapter = CustomAdapter(userList)
        binding.recyclerViewRV.adapter = adapter
        binding.recyclerViewRV.setHasFixedSize(true)
    }

    override fun remove(user: User?) {
        userList.remove(user)
        onResume()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}