package com.example.cameraactivity

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.example.cameraactivity.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {


    val cameraContract=registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if (it){
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
        }else{
            showCameraPermissionDailog()
        }
    }

    private fun showCameraPermissionDailog() {
        AlertDialog.Builder(this).apply {
            setTitle("Permission Required")
            setMessage("This app needs access to your camera so you can take awesome pictures.")
            setPositiveButton("Settings",DialogInterface.OnClickListener { dialog, which ->
                val intent=Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri:Uri= Uri.fromParts("package",packageName,null)
                intent.data=uri
                startActivity(intent)
            })
            setNegativeButton("Cancel",DialogInterface.OnClickListener { dialog, which ->

            })
        }.show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_camera_Permission).setOnClickListener {
            cameraContract.launch(android.Manifest.permission.CAMERA)
        }
        findViewById<Button>(R.id.btn_storage_Permission).setOnClickListener {

        }


    }
}

