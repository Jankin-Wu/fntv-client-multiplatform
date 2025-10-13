package com.jankinwu.fntv.client.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jankinwu.fntv.client.LocalStore
import com.jankinwu.fntv.client.data.store.PreferencesManager
import com.jankinwu.fntv.client.data.store.SystemAccountData
import com.jankinwu.fntv.client.ui.component.rememberToastManager
import com.jankinwu.fntv.client.viewmodel.LoginViewModel
import com.jankinwu.fntv.client.viewmodel.UiState
import fntv_client_multiplatform.composeapp.generated.resources.Res
import fntv_client_multiplatform.composeapp.generated.resources.login_background
import fntv_client_multiplatform.composeapp.generated.resources.login_fn_logo
import io.github.composefluent.component.Switcher
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

// 自定义颜色以匹配图片风格
val DarkBackgroundColor = Color(0xFF1E1E2D)
val CardBackgroundColor = Color(0xFF1A1D26)
val PrimaryBlue = Color(0xFF3A7BFF)
val TextColor = Color.White
val HintColor = Color.Gray

@Suppress("RememberReturnType")
@Composable
fun LoginScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var host by remember { mutableStateOf("") }
    var isHttps by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    val loginViewModel: LoginViewModel = koinViewModel()
    val loginUiState by loginViewModel.uiState.collectAsState()
    val preferencesManager = remember { PreferencesManager.getInstance() }
    val store = LocalStore.current
    val toastManager = rememberToastManager()
    // 初始化时加载保存的账号信息
    remember {
        if (preferencesManager.hasSavedCredentials()) {
            username = preferencesManager.getSavedUsername()
            password = preferencesManager.getSavedPassword()
            isHttps = preferencesManager.isHttps()
            rememberMe = true
        }
    }

    // 处理登录结果
    LaunchedEffect(loginUiState) {
        when (val state = loginUiState) {
            is UiState.Success -> {
                // 登录成功，更新Store中的登录状态
                store.isLoggedIn = true
            }

            is UiState.Error -> {
                // 登录失败，可以显示错误信息
                toastManager.showToast("登录失败，${state.message}", false)
                println("登录失败: ${state.message}")
            }

            else -> {
                // 其他状态，如Initial或Loading，可以不做处理
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(Res.drawable.login_background),
            contentDescription = "登录背景图",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        Surface(
            color = CardBackgroundColor,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.width(400.dp)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 40.dp, vertical = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 1. Logo
                Image(
                    painterResource(Res.drawable.login_fn_logo),
                    contentDescription = "飞牛logo",
                    modifier = Modifier
                )
                Text("FN_Media", color = HintColor, fontSize = 16.sp)

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = host,
                    onValueChange = { host = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("ip地址、域名或 FN ID") },
                    singleLine = true,
                    placeholder = { Text("请输入ip地址、域名或 FN ID") },
                    colors = getTextFieldColors()
                )

                // 2. 用户名输入框
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("用户名或邮箱") },
                    singleLine = true,
                    colors = getTextFieldColors()
                )

                // 3. 密码输入框
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("密码") },
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image =
                            if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val description = if (passwordVisible) "隐藏密码" else "显示密码"
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, description, tint = HintColor)
                        }
                    },
                    colors = getTextFieldColors()
                )

                // 4. 记住账号 和 忘记密码
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = rememberMe,
                            onCheckedChange = { rememberMe = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = PrimaryBlue,
                                checkmarkColor = TextColor,
                                uncheckedColor = HintColor
                            )
                        )
                        Text("记住账号", color = TextColor, fontSize = 14.sp)
                    }
                    TextButton(onClick = { /* TODO: 忘记密码逻辑 */ }) {
                        Text("忘记密码?", color = HintColor, fontSize = 14.sp)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("HTTPS 安全访问", color = TextColor, fontSize = 16.sp)
                    Switcher(isHttps, { isHttps = it })
                }

                // 5. 登录按钮
                Button(
                    onClick = {
                        if (host.isBlank() || username.isBlank() || password.isBlank()) {
                            toastManager.showToast("请填写完整的登录信息", false)
                            return@Button
                        }
                        if (isHttps) {
                            SystemAccountData.isHttps = true
                        }
                        // 执行登录逻辑
                        loginViewModel.login(username, password, rememberMe = rememberMe, isHttps = isHttps)
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                ) {
                    Text("登录", fontSize = 16.sp)
                }

                // 6. NAS 登录按钮
                Button(
                    onClick = { /* TODO: NAS 登录逻辑 */ },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C3C4D))
                ) {
                    Text("使用 NAS 登录", fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
private fun getTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = PrimaryBlue,
    unfocusedBorderColor = Color.Gray,
    focusedLabelColor = PrimaryBlue,
    unfocusedLabelColor = HintColor,
    cursorColor = PrimaryBlue,
    focusedTextColor = TextColor,
    unfocusedTextColor = TextColor
)
