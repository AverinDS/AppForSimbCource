package com.example.dmitry.appforsimbcourse.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.dmitry.appforsimbcourse.R
import com.example.dmitry.appforsimbcourse.helper.ScalingImage
import com.example.dmitry.appforsimbcourse.interfaces.IMyActivity
import com.example.dmitry.appforsimbcourse.model.AppUser
import com.example.dmitry.appforsimbcourse.presenter.PresenterPersonalData
import com.makeramen.roundedimageview.RoundedImageView
import com.theartofdev.edmodo.cropper.CropImage
import java.io.IOException


class PersonalDataActivity : AppCompatActivity(), View.OnClickListener, IMyActivity {

    private val LOG_TAG = "PersonalDataActivity"
    private val GALLERY_IMAGE = 12323
    private val CAMERA_CODE = 9
    private lateinit var name: EditText
    private lateinit var phone: EditText
    private lateinit var email: EditText
    private lateinit var btnSave: Button
    private lateinit var nickname: TextView
    private lateinit var changePhoto: TextView
    private lateinit var avatar: RoundedImageView
    private lateinit var uriImageCamera: Uri
    private lateinit var urlImage: Uri

    private val presenterPersonalData: PresenterPersonalData = PresenterPersonalData(this)

    private var cameraIsPermist = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)

        name = findViewById(R.id.actPersonalDataName)
        email = findViewById(R.id.actPersonalDataEmail)
        phone = findViewById(R.id.actPersonalDataPhone)
        avatar = findViewById(R.id.actPersonalDataAvatar)
        btnSave = findViewById(R.id.actPersonalDataBtnSave)
        changePhoto = findViewById(R.id.actPersonalDataChangePhoto)
        nickname = findViewById(R.id.actPersonalDataFI)

        presenterPersonalData.getUsersInfo()

        btnSave.setOnClickListener(this)
        changePhoto.setOnClickListener { v -> changePhoto(v) }

        presenterPersonalData.setMaskPhone(phone)
        presenterPersonalData.downloadPhoto()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val selectedImageGallery: Uri

        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            GALLERY_IMAGE -> {
                if (resultCode == Activity.RESULT_OK) {
                    selectedImageGallery = data!!.data
                    presenterPersonalData.cropImage(this, selectedImageGallery)
                }
            }
            CAMERA_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    presenterPersonalData.cropImage(this, uriImageCamera)
                }

            }
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    setAvatar(result.uri)
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    //нужно ли обрабатывать?
                }
            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {

        when (requestCode) {
            CAMERA_CODE -> {
                cameraIsPermist = (grantResults.isNotEmpty() && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED)

                if (cameraIsPermist) {
                    startCamera()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }

    override fun onClick(p0: View?) {
        if (name.text.toString().isNotEmpty() &&
                presenterPersonalData.emailIsValid(email.text.toString())) {
            presenterPersonalData.updateDataServer(email.text.toString(), phone.text.toString(),
                    name.text.toString())
            presenterPersonalData.startProfileActivity(this)
        } else {
            Toast.makeText(this, "Неправильно введенные данные", Toast.LENGTH_SHORT)
                    .show()
        }
    }

    override fun updateUI(appUser: AppUser) {
        nickname.text = appUser.name
        name.text.insert(0, appUser.name)
        email.text.insert(0, appUser.email)
        phone.text.insert(0, appUser.phone)
    }

    private fun changePhoto(view: View) {
        val popUpMenu = PopupMenu(this, view)
        val menuInf: MenuInflater = popUpMenu.menuInflater

        popUpMenu.setOnMenuItemClickListener { v -> onClickMenuListener(v) }

        menuInf.inflate(R.menu.actions, popUpMenu.menu)
        popUpMenu.show()
    }

    private fun onClickMenuListener(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.menuCamera -> {
                presenterPersonalData.getCameraPermission(this, CAMERA_CODE)
                //непосредственная загрузка фото вызывается в обработчике
                // разрешений onRequestPermissionsResult
                true
            }
            R.id.menuGallery -> {
                startActivityForResult(presenterPersonalData.getIntentOnImageFromGallery(),
                        GALLERY_IMAGE)
                //в таких местах лучше через packagemanager проверять есть ли у пользователя приложение которое сможет открыть этот интент
                true
            }
            else -> {
                true
            }
        }
    }

    private fun startCamera() {
        val intentUriPair: Pair<Intent, Uri> = presenterPersonalData.getIntentUriForCamera(this)
        uriImageCamera = intentUriPair.second

        startActivityForResult(intentUriPair.first, CAMERA_CODE)
    }

    private fun setAvatar(selectedImage: Uri) {
        val bitmap: Bitmap
        val scale = ScalingImage()

        try {
            bitmap = scale.scaleImage(this, selectedImage,
                    resources.getDimension(R.dimen.width_of_image_profile).toInt(),
                    resources.getDimension(R.dimen.height_of_image_profile).toInt())
            avatar.setImageBitmap(bitmap)
            sendToServer()
        } catch (ex: IOException) {
            ex.stackTrace
        }
    }

    private fun setAvatar(bitmap: Bitmap) {
        avatar.setImageBitmap(bitmap)
    }

    override fun onDownloadPhoto(bitmap: Bitmap) {
        setAvatar(bitmap)
    }

    private fun sendToServer() {
        presenterPersonalData.uploadPhoto(avatar)
    }

    override fun onFailureDownloadPhoto(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


}
