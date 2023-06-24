package com.example.cameraactivity

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.example.cameraactivity.databinding.ActivityImagePickerBinding
import com.example.cameraactivity.databinding.ActivityMainBinding
import java.io.File

class ImagePickerActivity : AppCompatActivity() {

   private val REQ_GALLERY=100
    private val REQ_CAMERA=200


    lateinit var binding: ActivityImagePickerBinding

    private  var galleryPermissionContract=registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if (it){
            pickImageFromGallery()
        }
    }

    private fun pickImageFromGallery() {
        var intent=Intent()
        intent.action=Intent.ACTION_GET_CONTENT
        intent.type="image/*"
        startActivityForResult(intent,REQ_GALLERY)
    }

    private val galleryResult=registerForActivityResult(ActivityResultContracts.GetContent()){
        if (it!=null){
            binding.ivImage.setImageURI(it)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagePickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivImage.setOnClickListener {
            showOptionDialog()
        }

    }

    private fun showOptionDialog() {
        AlertDialog.Builder( this).apply {
            setTitle("Select Image")
            setItems(arrayOf("From Gallery","From Camera"),DialogInterface.OnClickListener { dialogInterface, i ->
                if (i==0){
                    //GALLERY
                    galleryResult.launch("image/*")
                }else{
                    //CAMERA
                    var intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent,REQ_CAMERA)
                }
            })
            show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==REQ_GALLERY){
            if (resultCode== RESULT_OK && data != null){
                var imageUri=data.data
                binding.ivImage.setImageURI(imageUri)
            }
        }else if(requestCode == REQ_CAMERA){
            if (resultCode == RESULT_OK && data !=null){
               // var imageUri =data.data
               // binding.ivImage.setImageURI(imageUri)
             val imageBitmap = data?.extras?.get("data")as Bitmap
               binding.ivImage.setImageBitmap(imageBitmap)
            }

        }
    }
}