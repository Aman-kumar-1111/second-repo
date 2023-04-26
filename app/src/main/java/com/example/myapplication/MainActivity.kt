package com.example.myapplication

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.UserData
import com.example.myapplication.view.UserAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var addsBtn: FloatingActionButton
    private  var recv: RecyclerView?=null
    private lateinit var userList: ArrayList<UserData>
    private   var userAdapter: UserAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userList= ArrayList()
        addsBtn=findViewById(R.id.addButton)
        recv=findViewById(R.id.mRecycler)
        userAdapter= UserAdapter(this,userList)
        recv?.layoutManager=LinearLayoutManager(this)
        recv?.adapter=userAdapter
        addsBtn.setOnClickListener {addInfo()}
    }
    private  fun addInfo(){
        val inflater= LayoutInflater.from(this)
        val v=inflater.inflate(R.layout.add_item,null)
        val userName=v.findViewById<EditText>(R.id.userName)
        val userNo=v.findViewById<EditText>(R.id.userNo)
        val addDialog=AlertDialog.Builder(this)
        addDialog.setView(v)
        addDialog.setPositiveButton("ok") { dialog, which ->
            val names=userName.text.toString()
            val number=userNo.text.toString()
            userList.add(UserData("Name: $names","Mobile: $number"))
            userAdapter?.notifyDataSetChanged()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("cancel"){
            dialog,which ->
           dialog.dismiss()
            Toast.makeText(this,"cancel",Toast.LENGTH_LONG).show()
        }

        addDialog.create()
        addDialog.show()


    }

}