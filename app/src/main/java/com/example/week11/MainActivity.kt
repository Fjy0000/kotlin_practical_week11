package com.example.week11

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    private lateinit var myDataBase:FirebaseDatabase
    private lateinit var myRef:DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnget = findViewById<Button>(R.id.btnGet)
        val btnsave = findViewById<Button>(R.id.btnSave)
        val btnSignIn = findViewById<Button>(R.id.btnSignIn)
        val btnSignOut = findViewById<Button>(R.id.btnLogout)
        val id = findViewById<TextView>(R.id.tv_id)
        val name = findViewById<TextView>(R.id.tv_name)
        val programme = findViewById<TextView>(R.id.tv_programme)


        // Write a message to the database
        // Write a message to the database
        val myDataBase = FirebaseDatabase.getInstance()
        val myRef = myDataBase.getReference("Student")
        auth = FirebaseAuth.getInstance()
        

        btnSignOut.setOnClickListener(){
            register("123@gmail.com","123456")
        }


        btnget.setOnClickListener(){
            myRef.child("123").get()
                .addOnSuccessListener { record->

                    id.text = record.child("studentID").value.toString()
                    name.text = record.child("studentName").value.toString()
                    programme.text = record.child("studentProgramme").value.toString()

                }.addOnFailureListener {
                    Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_LONG).show()
                }
        }
        btnsave.setOnClickListener(){
            val studentId = id.text.toString()
            val studentName = name.text.toString()
            val studentProgramme = programme.text.toString()
            val student = Student(studentId,studentName,studentProgramme)
            //myRef.setValue("Hello, World!")
            //myRef.child("test").setValue("Hello World")
            myRef.child(student.studentID).setValue(student)
                .addOnSuccessListener { Toast.makeText(applicationContext, "record added", Toast.LENGTH_LONG).show() }
                .addOnFailureListener { Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_LONG).show() }

        }
    }

    fun register(email: String, psw: String) {

        auth.createUserWithEmailAndPassword(email, psw)
            .addOnSuccessListener {
                val user = auth.currentUser

            }
            .addOnFailureListener { e ->
                Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
            }
    }

    fun login(email: String, psw: String) {
        auth.signInWithEmailAndPassword(email, psw)
            .addOnSuccessListener {
                val user = auth.currentUser

            }
            .addOnFailureListener { e->
                Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
            }
    }

    fun signOut() {
        auth.signOut()

    }

}