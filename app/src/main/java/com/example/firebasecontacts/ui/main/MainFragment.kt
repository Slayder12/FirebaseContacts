package com.example.firebasecontacts.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasecontacts.R
import com.example.firebasecontacts.utils.Removable
import com.example.firebasecontacts.models.UserModel
import com.example.firebasecontacts.utils.Util
import com.example.firebasecontacts.utils.Validator
import com.example.firebasecontacts.databinding.FragmentMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class MainFragment : Fragment(), Removable {

    private var adapter: CustomAdapter? = null
    private var userList: MutableList<UserModel> = mutableListOf()
    private var user: UserModel? = null

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
            if (!Validator(requireContext()).userValidate(name, phoneNumber)) return@setOnClickListener
            user = UserModel(name = name, phoneNumber = phoneNumber)
            addUser(user!!)
            with(binding){
                nameET.text.clear()
                phoneNumberTV.text.clear()
            }
            refreshData()
        }

        return binding.root
    }

    private fun addUser(userAdd: UserModel) {
        val id = FirebaseAuth.getInstance().currentUser!!.uid
        val database = Firebase.database(Util.firebaseUrl)

        val reference = database.reference
            .child("users")
            .child(id)
            .child("userList") // Добавляем новый узел для списка пользователей

        // Генерируем уникальный ключ для нового пользователя
        val newUserKey = reference.push().key

        if (newUserKey != null) {
            // Создаем Map для данных пользователя
            val userMap = mapOf(
                "name" to userAdd.name,
                "phoneNumber" to userAdd.phoneNumber
            )

            // Записываем данные в Firebase под уникальным ключом
            reference.child(newUserKey).setValue(userMap)
                .addOnSuccessListener {
                    toast("User added successfully")
                    onResume()
                }
                .addOnFailureListener { e ->
                    toast("Firebase, Error adding user $e")
                }
        } else {
            toast("Failed to generate a unique key for the user")
        }
    }

    override fun onResume() {
        super.onResume()
        refreshAdapter()
        adapter?.setOnItemClickListener(object :
            CustomAdapter.OnItemClickListener {
            override fun onItemClick(user: UserModel, position: Int) {
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
        // Очищаем список перед обновлением
        userList.clear()

        val id = FirebaseAuth.getInstance().currentUser!!.uid
        val database = Firebase.database(Util.firebaseUrl)
        val reference = database.reference
            .child("users")
            .child(id)
            .child("userList") // Читаем данные из узла userList

        // Чтение данных из Firebase
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val key = userSnapshot.key // Получаем уникальный ключ пользователя
                        val name = userSnapshot.child("name").getValue(String::class.java)
                        val phoneNumber = userSnapshot.child("phoneNumber").getValue(String::class.java)

                        if (key != null && name != null && phoneNumber != null) {
                            val user = UserModel(key, name, phoneNumber) // Сохраняем ключ в объекте User
                            userList.add(user) // Добавляем пользователя в список
                        }
                    }
                }

                // Обновляем адаптер
                onResume()
            }

            override fun onCancelled(error: DatabaseError) {
                toast("Failed to read data from Firebase: ${error.message}")
            }
        })
    }

    private fun refreshAdapter(){
        adapter = CustomAdapter(userList)
        binding.recyclerViewRV.adapter = adapter
        binding.recyclerViewRV.setHasFixedSize(true)
    }

    override fun remove(user: UserModel?) {
        if (user?.key == null) {
            toast("User or user key is null")
            return
        }

        val id = FirebaseAuth.getInstance().currentUser!!.uid
        val database = Firebase.database(Util.firebaseUrl)
        val reference = database.reference
            .child("users")
            .child(id)
            .child("userList")
            .child(user.key) // Указываем ключ пользователя для удаления

        // Удаляем пользователя из Firebase
        reference.removeValue()
            .addOnSuccessListener {
                userList.remove(user) // Удаляем пользователя из локального списка
                onResume() // Обновляем адаптер
                toast("${user.name} удалён(а)")
            }
            .addOnFailureListener { e ->
                toast("Failed to delete user: ${e.message}")
            }
    }

    private fun toast(text: String) {
        Toast.makeText(
            requireContext(),
            text,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}