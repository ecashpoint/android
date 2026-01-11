package de.ecashpoint.ui.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import de.ecashpoint.ui.navigation.Screen
import de.ecashpoint.ui.theme.Green97C
import kotlin.math.log

@Composable
fun LoginScreen(navController: NavController , loginViewModel: LoginViewModel){

    val authUiState = loginViewModel.authUiState
    LaunchedEffect(authUiState.isLoggedIn) {
        if(authUiState.isLoggedIn){
            navController.navigate(Screen.Main.route){
                popUpTo(Screen.Login.route){inclusive = true}
            }
        }
    }
    BackGroundLogin {
        Login(loginViewModel = loginViewModel)
    }
}

@Composable
fun Login(loginViewModel: LoginViewModel){

    val authUiState = loginViewModel.authUiState

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        shape = RoundedCornerShape((16.dp)),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp))
    {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Icon(
                imageVector = Icons.Default.CreditCard,
                contentDescription = "E-SmartPoints",
                tint = Color.Blue,
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = "E-SMARTPOINTS",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Anmelden",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
//email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "E-Mail") },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = Color.Blue
                    )
                },
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    focusedPlaceholderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    cursorColor = Color.Gray
                )
            )
            //password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Passwort") },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = Color.Blue
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    focusedPlaceholderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    cursorColor = Color.Gray,

                )
            )

            //button
            Row(modifier = Modifier.fillMaxWidth()) {
                ElevatedButton(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        loginViewModel.login(email , password)

                    },
                    enabled = !email.isBlank() && !password.isBlank(),

                    colors = ButtonDefaults.buttonColors(Color.Blue)
                ) {
                if (authUiState.isLoading){
                    CircularProgressIndicator(modifier = Modifier.size(20.dp))
                }else{
                    Text(
                        text = "Einloggen" ,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
                }
            }

            //recuperacion contraseña
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                TextButton(
                    onClick = {}
                ) {
                    Text(text = "Hast du kein Konto? Registriere dich", color = Color.Blue)
                }
            }

        }


    }
}