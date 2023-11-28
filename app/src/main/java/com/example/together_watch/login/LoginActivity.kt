package com.example.together_watch.login

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.together_watch.ui.theme.*
import com.example.together_watch.ui.theme.Together_watchTheme
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginActivity: ComponentActivity(), LoginContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Together_watchTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShowTestButton()
                }
            }
        }
    }

    @Preview
    @Composable
    fun ShowTestButton() {
        Column(
        ) {
            ShowLoginButton()
            ShowHomeButton()
        }
    }

    @Composable
    override fun ShowLoginButton() {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = KakaoYellow,
                contentColor = Black
            ),
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.size(width = 400.dp, height = 80.dp),
            onClick = {
                Log.d("kakao-sdk", "카카오 로그인")
                val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                    if (error != null) {
                        Log.e("kakao-sdk", "카카오 계정으로 로그인 실패", error)
                    } else if (token != null) {
                        LoginPresenter().callKakaoLoginFunction(token.accessToken)
                    }
                }

                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                    // 카카오톡 로그인
                    UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                        // 로그인 실패 부분
                        if (error != null) {
                            Log.e("kakao-login-sdk", "로그인 실패 $error")
                            // 사용자가 취소
                            if (error is ClientError && error.reason == ClientErrorCause.Cancelled ) {
                                return@loginWithKakaoTalk
                            }
                            // 다른 오류
                            else {
                                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                            }
                        }
                        // 로그인 성공 부분
                        else if (token != null) {
                            Log.d("kakao-login-sdk", "로그인 성공 ${token.accessToken}")
                            LoginPresenter().callKakaoLoginFunction(token.accessToken)
                        }
                    }
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                }
            }
        ) {
            Text(
                text = "카카오 로그인",
                fontSize = 30.sp,
                color = Black
            )
        }
    }

    @Composable
    override fun ShowHomeButton() {
        Button(
            onClick = {
                Log.d("jihyun", "홈 화면으로 이동")
            }
        ) {
            Text(
                text = "홈으로 바로 가기"
            )
        }
    }


}