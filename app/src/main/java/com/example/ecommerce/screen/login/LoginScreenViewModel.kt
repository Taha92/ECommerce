package com.example.ecommerce.screen.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.model.User
import com.example.ecommerce.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginScreenViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        context: Context,
        home: () -> Unit
    )
            = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("FB", "signInWithEmailAndPassword: Yayyy ${task.result}")
                        home()
                    } else {
                        Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                        Log.d("FB", "signInWithEmailAndPassword: Credentials invalid")
                    }
                }
        } catch (ex: Exception) {
            Log.d("FB", "signInWithEmailAndPassword: ${ex.message}")
        }
    }

    fun createUserWithEmailAndPassword(email: String, password: String, home: () -> Unit) {
        if (_loading.value == false) {
            _loading.value = true

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val displayName = task.result.user?.email?.split('@')?.get(0)
                        createUser(displayName)
                        home()
                    } else {
                        Log.d("FB", "createUserWithEmailAndPassword: ${task.result}")
                    }

                    _loading.value = false
                }
        }
    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = User(
            id = null,
            userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = ""
        ).toMap()


        FirebaseFirestore.getInstance().collection(Constants.USERS)
            .add(user)
    }
}