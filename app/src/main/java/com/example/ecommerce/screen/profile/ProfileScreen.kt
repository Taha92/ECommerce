package com.example.ecommerce.screen.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ecommerce.component.ShoppingAppBar
import com.example.ecommerce.navigation.ShoppingScreens
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale

@Preview
@Composable
fun ProfileScreen(navController: NavController = NavController(LocalContext.current)) {
    Scaffold(topBar = {
        ShoppingAppBar(title = "Profile",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            isMainScreen = false,
            navController = navController
        ) {
            navController.popBackStack()
        }
    }) {
        //content
        Surface(modifier = Modifier
            .padding(top = it.calculateTopPadding())
            .fillMaxSize()
        ) {
            //profile content
            ProfileContent(navController)
        }
    }
}

@Preview
@Composable
fun ProfileContent(navController: NavController = NavController(LocalContext.current)) {
    val currentUser = FirebaseAuth.getInstance().currentUser

    Column {
        Card(
            shape = RoundedCornerShape(2.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Row(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    shape = RoundedCornerShape(2.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(2.dp, color = Color.LightGray)
                    ) {
                    Icon(
                        imageVector = Icons.Sharp.Person,
                        contentDescription = "Person icon",
                        modifier = Modifier
                            .size(42.dp)
                    )
                }
                Text(
                    text = "Hi, ${
                        currentUser?.email.toString()
                            .split("@")[0].uppercase(Locale.getDefault())}",
                    modifier = Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Divider(modifier = Modifier.padding(start = 8.dp, end = 8.dp))

            Row(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    shape = RoundedCornerShape(2.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(2.dp, color = Color.LightGray)
                ) {
                    Icon(
                        imageVector = Icons.Default.MailOutline,
                        contentDescription = "Email icon",
                        modifier = Modifier
                            .size(42.dp)
                    )
                }
                Text(
                    text = currentUser?.email.toString(),
                    modifier = Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Divider(modifier = Modifier.padding(start = 8.dp, end = 8.dp))

            Row(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clickable {
                           navController.navigate(ShoppingScreens.OrderHistoryScreen.name)
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    shape = RoundedCornerShape(2.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(2.dp, color = Color.LightGray)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Order history icon",
                        modifier = Modifier
                            .size(42.dp)
                    )
                }
                Text(
                    text = "Previous Orders",
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(0.8f),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Order history icon",
                )
            }
        }
    }
}
