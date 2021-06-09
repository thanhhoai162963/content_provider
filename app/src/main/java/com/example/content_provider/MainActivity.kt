package com.example.content_provider

import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.TextureView
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE = 1
    lateinit var text: TextView
    lateinit var button: Button
    var name:String? = null
    private val arrayContact = mutableListOf<String>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button_get_list_contact)
        text = findViewById(R.id.text)

        button.setOnClickListener {
            readContact()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun readContact() {
        // neu cái quyền chưa có đồng ý
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf<String>(android.Manifest.permission.READ_CONTACTS), REQUEST_CODE)
        }else {
            val uri = Uri.parse("content://contacts/people")
            val cursor = contentResolver.query(uri,null,null,null)
            if (cursor?.count!! > 0){
                cursor.moveToFirst()
                while(! cursor.isAfterLast){
                    var id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    cursor.moveToNext()
                }
                cursor.close()
                text.text = name
            }
        }

    }
}