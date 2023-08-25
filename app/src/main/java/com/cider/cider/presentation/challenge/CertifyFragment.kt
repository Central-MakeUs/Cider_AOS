package com.cider.cider.presentation.challenge

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeCertifyBinding
import com.cider.cider.domain.model.ChallengeListModel
import com.cider.cider.domain.model.ImageCardModel
import com.cider.cider.domain.type.challenge.ImageType
import com.cider.cider.presentation.viewmodel.CertifyViewModel
import com.cider.cider.presentation.viewmodel.ChallengeCertifyViewModel
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.logging.Logger

@AndroidEntryPoint
class CertifyFragment: BindingFragment<FragmentChallengeCertifyBinding>(R.layout.fragment_challenge_certify) {

    private val certify : ChallengeCertifyViewModel by viewModels()
    private var itemArray:List<ChallengeListModel>? = listOf<ChallengeListModel>()
    private var data: Int? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.challenge = certify
        binding.executePendingBindings()
        binding.lifecycleOwner = viewLifecycleOwner

        val bundle = arguments

        data = bundle?.getInt("id")

        super.onViewCreated(view, savedInstanceState)
        setButton()
        setCamera()
        setObserve()
        setSpinner()
    }

    private fun setCamera() {
        binding.btnImage.setOnClickListener {
            requestPermission()
        }
    }

    private fun setObserve() {
        certify.title.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                certify.checkButtonState()
            }
        }
        certify.content.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                certify.checkButtonState()
            }
        }
    }

    private fun setButton() {
        binding.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnBottom.setOnClickListener {
            lifecycleScope.launch {
                binding.btnBottom.isEnabled = false
                if (itemArray?.get(binding.spinnerChallenge.selectedItemPosition)
                        ?.let { it1 -> certify.setCertify(requireContext(), it1.id) } == true) {
                    Toast.makeText(requireContext(),"인증에 성공했습니다",Toast.LENGTH_SHORT).show()
                    binding.btnBottom.isEnabled = true
                    onBackPressed()
                } else {
                    Toast.makeText(requireContext(),"인증에 실패했습니다",Toast.LENGTH_SHORT).show()
                    binding.btnBottom.isEnabled = true
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }

    private fun setSpinner() {
        lifecycleScope.launch {
            itemArray = certify.getChallengeList()
            val spinnerAdapter = ArrayAdapter(
                requireContext(),
                R.layout.item_spinner_dropdown,
                itemArray?.map {it.challengeName} ?: mutableListOf()
            )

            spinnerAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
            binding.spinnerChallenge.adapter = spinnerAdapter
            binding.spinnerChallenge.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                    }
                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }
            if (itemArray != null && data != null) {
                for ((position, item) in itemArray!!.withIndex()) {
                    if (item.id == data ) {
                        binding.spinnerChallenge.setSelection(position)
                    }
                }
            }
        }
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
                certify.addImage(ImageCardModel(uri = imageUri))
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