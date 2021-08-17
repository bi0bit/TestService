package com.ilagoproject.myapplication.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.textfield.TextInputEditText
import com.ilagoproject.myapplication.App
import com.ilagoproject.myapplication.R
import com.ilagoproject.myapplication.apiservice.data.AuthorizationRequest
import com.ilagoproject.myapplication.databinding.FragmentAuthorizationBinding
import com.ilagoproject.myapplication.utils.isOnline
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import com.ilagoproject.myapplication.viewmodel.base.AppViewModelFragment
import es.dmoral.toasty.Toasty
import io.reactivex.rxjava3.core.Completable
import kotlinx.android.synthetic.main.fragment_authorization.*

class AuthorizationFragment : AppViewModelFragment() {
    init {
        App.modelComponent.inject(this)
    }

    lateinit var preference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preference = context?.getSharedPreferences(App.APP_PREFERENCE, Context.MODE_PRIVATE)!!
    }

    fun signIn(){
        if(login.text?.isEmpty() == true){
            login.markAsRequired()
            return
        }
        if(password.text?.isEmpty() == true){
            password.markAsRequired()
            return
        }

        if(context!!.isOnline().not()){
            Toasty.error(context!!, R.string.no_internet_access, Toasty.LENGTH_LONG).show()
            return
        }

        startSignIn()

        val login: Long = login.text.toString().toLong()
        val password: String = password.text.toString()

        model.setAuthData(login, password)

        signIn(login, password)
    }

    private fun signIn(login: Long, password: String){
        val authRequest = AuthorizationRequest(login, password)

        if(context!!.isOnline())
            model.service.signIn(authRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model::updateTokenFromResponse, this::onSignInError, this::onSignInSuccess)
        else {
            Toasty.warning(context!!, getString(R.string.no_internet_access), Toasty.LENGTH_LONG)
                .show()
        }
    }

    fun onCheckedRememberMe(){
        model.setIsRemember(rememberMe.isChecked, preference)
    }

    private fun startSignIn(){
        progressIndicatorAuthorization.show()
        rememberMe.visibility = View.GONE
        signInBtn.visibility = View.GONE
    }

    private fun endSignIn(){
        progressIndicatorAuthorization.hide()
        rememberMe.visibility = View.VISIBLE
        signInBtn.visibility = View.VISIBLE
    }

    private fun onSignInSuccess(){
        if(model.getIsRemember())
            model.saveAuthData(preference)
        goToPage(R.id.action_authorizationFragment_to_dataFragment)
    }

    private fun onSignInError(throwable: Throwable?){
        endSignIn()
        Toasty.error(context!!, getString(R.string.wrong_auth_data), Toast.LENGTH_LONG).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(model.isAuthDataLoaded){
            signIn(model.authDataPeanut.login, model.authDataPeanut.password)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAuthorizationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_authorization, container, false)
        binding.viewModel = this
        return binding.root
    }

    private fun TextInputEditText.markAsRequired(){
        setHintTextColor(ColorStateList.valueOf(Color.RED))
    }

}