package com.example.myapplication.view

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.UserData

class UserAdapter(val c: Context,val userList: ArrayList<UserData>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class UserViewHolder(val v: View):RecyclerView.ViewHolder(v){
   var name:TextView?=null
   var mbNum:TextView?=null
        var mMenus:ImageView?=null
        init {
             name=v.findViewById<TextView>(R.id.mTitle)
             mbNum=v.findViewById<TextView>(R.id.mSubTitle)
            mMenus=v.findViewById(R.id.mMenu)
            mMenus?.setOnClickListener{popupMenu(it)
            }
        }

        private fun popupMenu(v:View) {
            val position=userList[adapterPosition]
            val popupMenu= PopupMenu(c,v)
            popupMenu.inflate(R.menu.show_menu)
             popupMenu.setOnMenuItemClickListener {
                 when(it.itemId)
                 {
                     R.id.editText->
                     {
                         val v=LayoutInflater.from(c).inflate(R.layout.add_item,null)
                         val name=v.findViewById<EditText>(R.id.userName)
                         val number=v.findViewById<EditText>(R.id.userNo)
                         AlertDialog.Builder(c)
                             .setView(v)
                             .setPositiveButton("ok"){ _,_->
                                 position.userName=name.text.toString()
                                 position.userMo=number.text.toString()
                                 Toast.makeText(c,"User info is Updated",Toast.LENGTH_SHORT).show()
                             }
                             .setNegativeButton("cancel")
                             {
                                     dialog,_->
                                 dialog.dismiss()
                             }
                             .create()
                             .show()

                        true
                     }
                     R.id.deleteText->
                     {
                         AlertDialog.Builder(c)
                             .setTitle("delete")
                             .setIcon(R.drawable.ic_warning)
                             .setMessage("Are you sure Delete information")
                             .setPositiveButton("yes")
                             {
                                dialog,_->
                                 userList.removeAt(adapterPosition)
                                 notifyDataSetChanged()
                                 Toast.makeText(c,"Delete Element",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                             }
                             .setNegativeButton("no")
                             {
                                 dialog,_->
                                 dialog.dismiss()
                             }
                             .create()
                             .show()
                         true
                     }
                             else ->true
                 }

             }
            popupMenu.show()
            val popup=PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible=true
            val menu=popup.get(popupMenu)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       val inflater=LayoutInflater.from(parent.context)
        val v=inflater.inflate(R.layout.item_list,parent,false)
        return UserViewHolder(v)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder:  RecyclerView.ViewHolder, position: Int) {
        val newList=userList[position]
        if (holder is UserViewHolder) {
            holder.name?.text = newList.userName
            holder.mbNum?.text = newList.userMo
        }
    }
}