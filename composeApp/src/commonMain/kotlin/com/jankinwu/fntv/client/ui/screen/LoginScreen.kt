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
import androidx.compose.runtime.mutableIntStateOf
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
import com.jankinwu.fntv.client.ui.component.NumberInput
import com.jankinwu.fntv.client.ui.component.ToastManager
import com.jankinwu.fntv.client.ui.component.rememberToastManager
import com.jankinwu.fntv.client.ui.selectedCheckBoxColors
import com.jankinwu.fntv.client.ui.selectedSwitcherStyle
import com.jankinwu.fntv.client.utils.DomainIpValidator
import com.jankinwu.fntv.client.viewmodel.LoginViewModel
import com.jankinwu.fntv.client.viewmodel.UiState
import fntv_client_multiplatform.composeapp.generated.resources.Res
import fntv_client_multiplatform.composeapp.generated.resources.login_background
import fntv_client_multiplatform.composeapp.generated.resources.login_fn_logo
import io.github.composefluent.component.CheckBox
import io.github.composefluent.component.CheckBoxDefaults
import io.github.composefluent.component.Switcher
import io.github.composefluent.component.SwitcherDefaults
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

// 自定义颜色以匹配图片风格
val DarkBackgroundColor = Color(0xFF1E1E2D)
val CardBackgroundColor = Color(0xFF1A1D26).copy(alpha = 0.8f)
val PrimaryBlue = Color(0xFF3A7BFF)
val TextColor = Color.White
val HintColor = Color.Gray

@Suppress("RememberReturnType")
@Composable
fun LoginScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var host by remember { mutableStateOf("") }
    var port by remember { mutableIntStateOf(0) }
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
            host = preferencesManager.getSavedHost()
            port = preferencesManager.getSavedPort()
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
                modifier = Modifier.padding(horizontal = 40.dp, vertical = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 1. Logo
                Image(
                    painterResource(Res.drawable.login_fn_logo),
                    contentDescription = "飞牛logo",
                    modifier = Modifier
                        .width(174.dp)
                )
                Text("FN_Media", color = HintColor, fontSize = 16.sp)

//                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = host,
                        onValueChange = { host = it },
                        modifier = Modifier
                            .weight(2.0f),
                        label = { Text("ip 或域名") },
                        singleLine = true,
                        placeholder = { Text("请输入ip或域名") },
                        colors = getTextFieldColors()
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    NumberInput(
                        onValueChange = { port = it },
                        value = port,
                        modifier = Modifier.weight(1.0f),
                        placeholder = "请输入端口",
                        minValue = 0,
                        label = "端口（0为默认）"
                    )
                }

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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        CheckBox(rememberMe,
                            "记住账号",
                            onCheckStateChange = { rememberMe = it },
                            colors = if(rememberMe) {
                                selectedCheckBoxColors()
                            } else {
                                CheckBoxDefaults.defaultCheckBoxColors()
                            }
                        )
//                        Checkbox(
//                            checked = rememberMe,
//                            onCheckedChange = { rememberMe = it },
//                            colors = CheckboxDefaults.colors(
//                                checkedColor = PrimaryBlue,
//                                checkmarkColor = TextColor,
//                                uncheckedColor = HintColor
//                            ),
//                        )
//                        Text("记住账号", color = TextColor, fontSize = 14.sp)
                    }
                    TextButton(onClick = { /* TODO: 忘记密码逻辑 */ }) {
                        Text("忘记密码?", color = HintColor, fontSize = 14.sp)
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("HTTPS 安全访问", color = TextColor, fontSize = 16.sp)
                    Switcher(
                        isHttps,
                        { isHttps = it },
                        styles =  if (isHttps) {
                            selectedSwitcherStyle()
                        } else {
                            SwitcherDefaults.defaultSwitcherStyle()
                        },
                    )
                }

                // 5. 登录按钮
                Button(
                    onClick = {
                        handleLogin(
                            host = host,
                            port = port,
                            username = username,
                            password = password,
                            isHttps = isHttps,
                            rememberMe = rememberMe,
                            toastManager = toastManager,
                            loginViewModel = loginViewModel
                        )
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

private fun handleLogin(
    host: String,
    port: Int,
    username: String,
    password: String,
    isHttps: Boolean,
    rememberMe: Boolean,
    toastManager: ToastManager,
    loginViewModel: LoginViewModel
) {
    if (host.isBlank() || username.isBlank() || password.isBlank()) {
        toastManager.showToast("请填写完整的登录信息", false)
        return
    }
    if (isHttps) {
        SystemAccountData.isHttps = true
    }
    val isValidDomainOrIP = DomainIpValidator.isValidDomainOrIP(host)
    if (!isValidDomainOrIP) {
        toastManager.showToast("请填写正确的ip地址或域名", false)
        return
    }
    SystemAccountData.host = host
    if (port != 0) {
        SystemAccountData.port = port
    }
    // 执行登录逻辑
    loginViewModel.login(username, password, rememberMe = rememberMe, isHttps = isHttps)
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