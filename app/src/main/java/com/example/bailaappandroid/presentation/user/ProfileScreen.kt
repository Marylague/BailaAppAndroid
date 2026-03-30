package com.example.bailaappandroid.presentation.user

import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bailaappandroid.R


@Composable
fun ProfileXmlScreen(viewModel: ProfileViewModel = viewModel()) {
    val profile = viewModel.profile

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            LayoutInflater.from(context)
                .inflate(R.layout.fragment_profile, null, false)
        },
        update = { view ->

            val etName = view.findViewById<EditText>(R.id.etName)
            val etEmail = view.findViewById<EditText>(R.id.etEmail)
            val btnEdit = view.findViewById<Button>(R.id.btnEdit)

            profile?.let {
                etName.setText(it.name)
                etEmail.setText(it.email)
            }

            var isEditing = false

            btnEdit.setOnClickListener {
                isEditing = !isEditing
                etName.isEnabled = isEditing
                etEmail.isEnabled = isEditing
                btnEdit.text = if (isEditing) "Сохранить" else "Редактировать"
            }
        }
    )
}