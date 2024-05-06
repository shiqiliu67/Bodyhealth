package com.cs411cmp003.bodywatchfrontend.ui.menuUserInfo

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.cs411cmp003.bodywatchfrontend.data.response.UserResponse
import com.cs411cmp003.bodywatchfrontend.databinding.FragmentUsersProfileBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UsersProfileFragment : Fragment() {
    private var _binding: FragmentUsersProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUsersProfileBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        getUserId()?.let {
            if (it != -1){
                checkUser(it)
            }
            else{
                displaySignUp()
            }
        }
    }

    private fun displaySignUp() {
        binding.layoutUserLogin.visibility = View.VISIBLE
        binding.layoutUserInfo.visibility = View.GONE
        val id = binding.etUserIdLogin.text
        binding.btnLogin.setOnClickListener {
            val userId = binding.etUserIdLogin.text.toString().toInt()
            checkUser(userId)
        }
        binding.btnUserInfoSignup.setOnClickListener {
            //show info page
            displayUserInfo(null)
        }
    }

    private fun displayUserInfo(user: UserResponse?) {
        binding.layoutUserLogin.visibility = View.GONE
        binding.layoutUserInfo.visibility = View.VISIBLE
        //input userinfo
        binding.etUserId.text = user?.userId.toString()
        binding.etEmail.setText(user?.email.toString())
        binding.etHeight.setText(user?.height.toString())
        binding.etWeight.setText(user?.weight.toString())
        binding.etFirstName.setText(user?.firstName)
        binding.etLastName.setText(user?.lastName)
        binding.etPhoneNumber.setText(user?.phoneNumber)
        //click event
        if (user?.userId != null) {
            //update user
            binding.etUserIdCreate.visibility = View.GONE
            binding.etUserId.visibility = View.VISIBLE
            binding.btnUserInfoSave.setOnClickListener {
                updateUser(user = getUserInfo("update"), userId = user.userId)
            }
        } else {
            binding.btnUserInfoSave.text = "Create"
            binding.etUserIdCreate.visibility = View.VISIBLE
            binding.etUserId.visibility = View.GONE
            binding.btnUserInfoSave.setOnClickListener {
               if ( binding.etUserIdCreate.text.toString().isNotEmpty()
                ) {
                   createUser(user = getUserInfo("create"))
                } else {
                    Toast.makeText(requireContext(), "UserId is empty", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.btnUserInfoDelete.setOnClickListener {
            if (user?.userId != null) {
                deleteUser(user.userId)
            } else if (binding.etUserIdCreate.text != null && binding.etUserIdCreate.text.toString()
                    .isNotEmpty()
            ) {
                deleteUser(binding.etUserIdCreate.text.toString().toInt())
            } else {
                Toast.makeText(requireContext(), "UserId is empty", Toast.LENGTH_SHORT).show()
            }
        }

    }

    /**
     * get user by id
     */
    private fun checkUser(userId: Int) {
        try {
            viewModel.successGetUserResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    Toast.makeText(requireContext(), "Success log in", Toast.LENGTH_SHORT).show()
                    saveUser(it)
                    displayUserInfo(it)
                }
            }
            viewModel.failedGetUserResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    Toast.makeText(requireContext(), "User doesn't exist", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            viewModel.getUserById(userId)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    /**
     * save user in app
     */
    private fun saveUser(user: UserResponse){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putInt("user", user.userId)
            apply()
        }
    }

    private fun getUserId(): Int? {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        return sharedPref?.getInt("user",-1)
    }

    /**
     * create user
     */
    private fun createUser(user: UserResponse) {
        try {
            viewModel.successCreateUserResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    Toast.makeText(requireContext(), "Create successful", Toast.LENGTH_SHORT)
                        .show()
                }
                saveUser(user)
            }
            viewModel.failedCreateUserResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            viewModel.createUser(user = user)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * update user
     */
    private fun updateUser(user: UserResponse, userId: Int) {
        try {
            viewModel.successUpdateUserResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    Toast.makeText(requireContext(), "Update successful", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            viewModel.failedUpdateUserResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            viewModel.updateUser(user = user, userId = userId)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * delete user
     */

    private fun deleteUser(userId: Int) {
        try {
            viewModel.successDeleteUserResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    Toast.makeText(requireContext(), "Delete successful", Toast.LENGTH_SHORT).show()
                    clearData()
                    displaySignUp()
                }
            }
            viewModel.failedDeleteUserResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            viewModel.deleteUser(userId)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * get user from input
     */
    private fun getUserInfo(method: String): UserResponse {
        var height = 0
        var weight = 0
        try {
            height = binding.etHeight.text.toString().toInt()
            weight = binding.etWeight.text.toString().toInt()
        }
        catch (e: NumberFormatException){
            println("not a number")

        }

            when (method) {
                "update" -> {
                    return UserResponse(
                        userId = binding.etUserId.text.toString().toInt(),
                        email = binding.etEmail.text.toString(),
                        height = binding.etHeight.text.toString().toInt(),
                        weight = binding.etWeight.text.toString().toInt(),
                        firstName = binding.etFirstName.text.toString(),
                        lastName = binding.etLastName.text.toString(),
                        phoneNumber = binding.etPhoneNumber.text.toString()
                    )
                }
                "create" -> {
                    return UserResponse(
                        userId = binding.etUserIdCreate.text.toString().toInt(),
                        email = binding.etEmail.text.toString() ?: "",
                        height = height,
                        weight = weight,
                        firstName = binding.etFirstName.text.toString(),
                        lastName = binding.etLastName.text.toString(),
                        phoneNumber = binding.etPhoneNumber.text.toString()
                    )
                }
                else -> {
                    return UserResponse(
                        userId = binding.etUserId.text.toString().toInt(),
                        email = binding.etEmail.text.toString(),
                        height = binding.etHeight.text.toString().toInt(),
                        weight = binding.etWeight.text.toString().toInt(),
                        firstName = binding.etFirstName.text.toString(),
                        lastName = binding.etLastName.text.toString(),
                        phoneNumber = binding.etPhoneNumber.text.toString()
                    )
                }
            }

    }

    private fun clearData() {
        binding.etUserIdLogin.setText("")
        binding.etUserId.text = ""
        binding.etEmail.setText("")
        binding.etHeight.setText("")
        binding.etWeight.setText("")
        binding.etFirstName.setText("")
        binding.etLastName.setText("")
        binding.etPhoneNumber.setText("")
        //userid
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putInt("user", -1)
            apply()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "UserInfo"
    }
}