package com.example.ecommerce.screen.orderSuccess

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ecommerce.R
import com.example.ecommerce.component.RoundedSubmitButton
import com.example.ecommerce.navigation.ShoppingScreens
import kotlinx.coroutines.launch


@Composable
fun OrderPlacedScreen(navController: NavController) {
    val bikeOffsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            while (true) {
                bikeOffsetX.animateTo(
                    targetValue = 500f,
                    animationSpec = tween(
                        durationMillis = 3000, // Increase duration to slow down the speed
                        easing = LinearEasing
                    )
                )

                // Reset the bike position to the start after reaching the end
                bikeOffsetX.snapTo(0f)

            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(modifier = Modifier
            .weight(0.8f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = " Thank you for ordering. \n Your order is on its way!",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = Color.Red.copy(alpha = 0.5f),
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Surface(modifier = Modifier
                .padding(15.dp)
                .size(350.dp),
                shape = CircleShape,
                color = Color.White,
                border = BorderStroke(2.dp, color = Color.LightGray)
            ) {
                Image(
                    painter = painterResource(R.drawable.bicyle),
                    contentDescription = null,
                    modifier = Modifier
                        .height(90.dp)
                        .absoluteOffset(x = bikeOffsetX.value.dp)
                )
            }
        }

        Box(modifier = Modifier.weight(0.1f)) {
            RoundedSubmitButton("Go To Home") {
                navController.navigate(ShoppingScreens.ShoppingHomeScreen.name)
            }
        }
    }
}
