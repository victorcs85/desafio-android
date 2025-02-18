package com.picpay.desafio.android.presentation.ui.users

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.picpay.desafio.android.R
import com.picpay.desafio.android.core.extensions.gone
import com.picpay.desafio.android.core.extensions.onFailure
import com.picpay.desafio.android.core.extensions.onSuccess
import com.picpay.desafio.android.core.extensions.showError
import com.picpay.desafio.android.core.extensions.visible
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.domain.model.Response
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.presentation.ui.users.adapter.UsersAdapter
import com.picpay.desafio.android.presentation.ui.users.viewmodel.UsersViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModel<UsersViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()
        initViewModel()
    }

    private fun setUpViews() {
        binding.rvUsers.adapter = UsersAdapter()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchUsers()
    }

    private fun initViewModel() {
        viewModel.users.observe(this) { response ->
            when (response) {
                is Response.Loading -> showLoading()
                is Response.Idle -> Unit
                else -> hideLoading()
            }

            response
                .onSuccess { users: List<User> ->
                    (binding.rvUsers.adapter as? UsersAdapter)?.submitList(users)
                }
                .onFailure { errorMessage: String ->
                    showError(message = errorMessage)
                }
        }
    }

    private fun showLoading() = binding.pbUsers.visible()

    private fun hideLoading() = binding.pbUsers.gone()
}
