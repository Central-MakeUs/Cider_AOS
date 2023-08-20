package com.cider.cider.presentation.mypage

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentProfileEditBinding
import com.cider.cider.domain.type.ProfileEdit
import com.cider.cider.presentation.dialog.ProfileEditBottomSheetDialog
import com.cider.cider.presentation.viewmodel.MyPageViewModel
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream


@AndroidEntryPoint
class ProfileEditFragment: BindingFragment<FragmentProfileEditBinding>(R.layout.fragment_profile_edit) {

    private val viewModel: MyPageViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setToolbar()
        setBtn()
    }

    private fun setToolbar() {
        binding.toolbar.tvToolbarTitle.text = "프로필 수정"
        binding.toolbar.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setBtn() {
        binding.btnNameEdit.setOnClickListener {
            binding.etName.requestFocus()
        }
        binding.btnRegister.setOnClickListener {
            lifecycleScope.launch {
                if (viewModel.profileName.value != viewModel.myPageModel.value?.name) {
                    if (viewModel.setProfile()) {
                        onBackPressed()
                    } else {
                        Toast.makeText(requireContext(), "프로필 수정에 실패했습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                if (viewModel.profileUri.value != viewModel.myPageModel.value?.profileUri) {
                    if (viewModel.setProfileImage(requireContext())) {
                        onBackPressed()
                    } else {
                        Toast.makeText(requireContext(), "프로필 수정에 실패했습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
        binding.ivCamera.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog() {
        val dialog = ProfileEditBottomSheetDialog()

        dialog.setOnValueChangedListener(object : ProfileEditBottomSheetDialog.OnValueChangedListener {

            override fun onValueUpdated(type: ProfileEdit) {
                when (type) {
                    ProfileEdit.CAMERA -> {
                        requestPermissionCamera()
                    }
                    ProfileEdit.GALLERY -> {
                        requestPermission()
                    }
                    ProfileEdit.RANDOM -> {
                        getRandomImageResource()
                    }
                }
            }

        })
        dialog.show(parentFragmentManager, "Capacity")
    }

    private fun getRandomImageResource() {
        val imageResourceIds = arrayOf(
            R.drawable.profile_bear,
            R.drawable.profile_chick,
            R.drawable.profile_chicken,
            R.drawable.profile_pig,
            R.drawable.profile_rabbit,
            R.drawable.profile_dog
        )
        val randomIndex = (imageResourceIds.indices).random()
        setImageFromUri(Uri.parse("android.resource://${context?.packageName}/${imageResourceIds[randomIndex]}"))
    }


    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openGallery()
            } else {
                requestPermission()
            }
        }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_MEDIA_IMAGES
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            openGallery()
        }
    }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.data
                // Call a function to set the image from the URI to the ImageView
                setImageFromUri(selectedImageUri)
            }
        }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    private fun setImageFromUri(imageUri: Uri?) {
        try {
            if (imageUri != null) {
                viewModel.profileUri.value = imageUri
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun requestPermissionCamera() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionCameraLauncher.launch(Manifest.permission.CAMERA)
        } else {
            openCamera()
        }
    }

    private val requestPermissionCameraLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openCamera()
            } else {
                requestPermissionCamera()
            }
        }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.extras
                Log.d("TEST Camera","$selectedImageUri")

                setImageFromUri(getImageUri(requireContext(),selectedImageUri?.get("data") as Bitmap))
            }
        }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }

    private fun getImageUri(context: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(context.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }
}