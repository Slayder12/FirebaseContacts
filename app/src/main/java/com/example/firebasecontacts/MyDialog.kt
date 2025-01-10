package com.example.firebasecontacts

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class MyDialog: DialogFragment() {

    private var removable: Removable? = null

    fun setRemovableListener(listener: Removable) {
        this.removable = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val user = arguments?.getSerializable("user") as? User
        val builder = AlertDialog.Builder(
            requireActivity()
        )

        return builder.setTitle("Внимание!")
            .setMessage("Хотите удалить ${user?.name}?")
            .setIcon(R.drawable.ic_delete)
            .setCancelable(true)
            .setPositiveButton("Да") { _, _ ->
                removable?.remove(user)
                Toast.makeText(context, "Cотрудник, ${user?.name} удалён", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Нет") { _, _ -> }
            .create()

    }
}