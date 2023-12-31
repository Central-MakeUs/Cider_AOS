package com.cider.cider.presentation.challenge.create

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
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeCreateDetailBinding
import com.cider.cider.domain.model.ImageCardModel
import com.cider.cider.domain.type.BottomSheetType
import com.cider.cider.domain.type.ProfileEdit
import com.cider.cider.domain.type.challenge.ImageType
import com.cider.cider.presentation.adapter.ImageListAdapter
import com.cider.cider.presentation.dialog.CertifyImageBottomSheetDialog
import com.cider.cider.presentation.dialog.NumPickerBottomSheetDialog
import com.cider.cider.presentation.viewmodel.ChallengeCreateViewModel
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class ChallengeCreateDetailFragment: BindingFragment<FragmentChallengeCreateDetailBinding>(R.layout.fragment_challenge_create_detail) {

    private val viewModel: ChallengeCreateViewModel by hiltNavGraphViewModels(R.id.nav_challenge_create)

    private lateinit var imageType: ImageType

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.challenge = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setButton()
        setEditText()
        setRecyclerView()
        setObserve()
    }

    private fun setEditText() {
        binding.etChallengeTitle.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.tvChallengeTitleAlert.visibility =
                    if ((viewModel.challengeTitle.value?.length ?: 0) < 5) View.VISIBLE else View.INVISIBLE
            }
        }

        binding.etChallengeIntroduction.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.tvChallengeIntroductionAlert.visibility =
                    if ((viewModel.challengeIntroduction.value?.length ?: 0) < 30) View.VISIBLE else View.INVISIBLE
            }
        }

        binding.etChallengeAuthentication.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.tvChallengeAuthentication.visibility =
                    if ((viewModel.challengeAuthentication.value?.length ?: 0) < 5) View.VISIBLE else View.INVISIBLE
            }
        }

        binding.etChallengeAuthentication.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.etChallengeAuthentication.clearFocus()
                hideKeyBoard()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun setObserve() {
        viewModel.challengeTitle.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                viewModel.checkButtonState()
            }
        }
        viewModel.challengeAuthentication.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                viewModel.checkButtonState()
            }
        }
        viewModel.challengeIntroduction.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                viewModel.checkButtonState()
            }
        }
    }

    private fun setButton() {
        binding.btnChallengeCreate.setOnClickListener {
            findNavController().navigate(
                R.id.action_challengeCreateDetailFragment_to_challengeCreateCheckFragment
            )
        }

        binding.btnChallengeCapacityPeople.setOnClickListener {
            hideKeyBoard()
            showCapacityBottomSheetDialog(BottomSheetType.CAPACITY,30,3,viewModel.capacity.value?:3)
        }

        binding.btnChallengeRecruitmentPeriodDown.setOnClickListener {
            hideKeyBoard()
            showCapacityBottomSheetDialog(BottomSheetType.RECRUITMENT_PERIOD,7,1,viewModel.recruitmentPeriod.value?:1)
        }

        binding.btnChallengeChallengePeriodDown.setOnClickListener {
            hideKeyBoard()
            showCapacityBottomSheetDialog(BottomSheetType.CHALLENGE_PERIOD,8,1,viewModel.challengePeriod.value?:1)
        }

        binding.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnImageAddSuccess.setOnClickListener {
            imageType = ImageType.SUCCESS
            showBottomSheetDialog()
        }

        binding.btnImageAddFail.setOnClickListener {
            imageType = ImageType.FAIL
            showBottomSheetDialog()
        }
    }

    private fun showPermissionDeniedDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("권한이 필요합니다.")
        builder.setMessage("이 앱은 미디어 이미지에 접근하는 권한이 필요합니다. 권한을 부여하시겠습니까?")
        builder.setPositiveButton("네") { _, _ ->
            // Open app settings to allow the user to grant the permission
            openAppSettings()
        }
        builder.setNegativeButton("아니오") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireContext().packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    private fun showCapacityBottomSheetDialog(type: BottomSheetType?,max: Int, min: Int, value: Int) {
        val dialog = NumPickerBottomSheetDialog(type)
        val bundle = Bundle()
        bundle.putInt("maxValue",max)
        bundle.putInt("minValue",min)
        bundle.putInt("value",value)
        dialog.arguments = bundle

        dialog.setOnValueChangedListener(object : NumPickerBottomSheetDialog.OnValueChangedListener {
            override fun onValueUpdated(newValue: Int, type: BottomSheetType?) {
                Log.d("TEST Button","$newValue")
                when (type) {
                    BottomSheetType.CAPACITY -> viewModel.changeCapacity(newValue)
                    BottomSheetType.CHALLENGE_PERIOD -> viewModel.changeChallengePeriod(newValue)
                    BottomSheetType.RECRUITMENT_PERIOD -> viewModel.changeRecruitmentPeriod(newValue)
                    else -> { }
                }
            }

        })
        dialog.show(parentFragmentManager, "Capacity")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }

    private fun hideKeyBoard() {
        binding.etChallengeIntroduction.clearFocus()
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etChallengeAuthentication.windowToken, 0)
    }

    private fun setRecyclerView() {
        val imageListAdapter1 = ImageListAdapter()
        binding.rvSuccessImage.apply {
            adapter = imageListAdapter1
            layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
        }

        imageListAdapter1.setOnItemClickListener {
            imageType = ImageType.SUCCESS
            requestPermission()
        }

        viewModel.successImageList.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                if (it.isNotEmpty()) binding.btnImageAddSuccess.visibility = View.GONE
                else binding.btnImageAddSuccess.visibility = View.VISIBLE
                imageListAdapter1.submitList(it)
                Log.d("TEST image","asd")
            }
        }
        val imageListAdapter = ImageListAdapter()
        binding.rvFailImage.apply {
            adapter = imageListAdapter
            layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
        }

        imageListAdapter.setOnItemClickListener {
            imageType = ImageType.FAIL
            requestPermission()
        }

        viewModel.failImageList.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                if (it.isNotEmpty()) binding.btnImageAddFail.visibility = View.GONE
                else binding.btnImageAddFail.visibility = View.VISIBLE
                imageListAdapter.submitList(it)
            }
        }
    }

    private fun showBottomSheetDialog() {
        val dialog = CertifyImageBottomSheetDialog()

        dialog.setOnValueChangedListener(object : CertifyImageBottomSheetDialog.OnValueChangedListener {

            override fun onValueUpdated(type: ProfileEdit) {
                when (type) {
                    ProfileEdit.CAMERA -> {
                        requestPermissionCamera()
                    }
                    ProfileEdit.GALLERY -> {
                        requestPermission()
                    }
                    ProfileEdit.RANDOM -> TODO()
                }
            }

        })
        dialog.show(parentFragmentManager, "Capacity")
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openGallery()
            } else {
                showPermissionDeniedDialog()
            }
        }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                openGallery()
            }
        } else {
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
    }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.data
                // Call a function to set the image from the URI to the ImageView
                setImageFromUri(selectedImageUri)
            }
        }

    private val requestPermissionCameraLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openCamera()
            } else {
                showPermissionDeniedDialog()
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

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.extras
                setImageFromUri(getImageUri(requireContext(),selectedImageUri?.get("data") as Bitmap))
            }
        }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    private fun setImageFromUri(imageUri: Uri?) {
        try {
            if (imageUri != null) {
                if (imageType == ImageType.SUCCESS) {
                    viewModel.addImageSuccess(ImageCardModel(imageUri))
                    Log.d("TEST image", "add Image success")
                }
                else {
                    viewModel.addImageFail(ImageCardModel(imageUri))
                    Log.d("TEST image", "add Image fail")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getImageUri(context: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(context.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }
}