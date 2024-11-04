package com.farmiot.smartagriculture.state

data class LoginFormState(
    val userNameError: Int? = null,
    val userEmailError: Int? = null,
    val phoneError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)