package com.example.ecommerce.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerce.navigation.ShoppingScreens
import com.example.ecommerce.screen.home.HomeScreenViewModel


@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    /*Image(
        modifier = modifier

            .padding(bottom = 16.dp),
        painter = painterResource(id = R.mipmap.login_logo),
        contentDescription = "App logo"
    )*/
    Text(
        text = "Online Shopping",
        modifier = modifier.padding(bottom = 16.dp),
        style = MaterialTheme.typography.displayMedium,
        color = Color.Red.copy(alpha = 0.5f)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController = NavController(LocalContext.current),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val showDialog = remember {
        mutableStateOf(false)
    }

    val showIt = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    TopAppBar(
        modifier = Modifier
            .shadow(elevation = elevation),
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            )
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = {
                    onAddActionClicked.invoke()
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search icon"
                    )
                }
                IconButton(onClick = {
                    showDialog.value = true
                }) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "more icon"
                    )
                }
            } else {
                Box {}
            }
        },
        navigationIcon = {
            if (icon != null) {
                Icon(imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .clickable {
                            onButtonClicked.invoke()
                        })
            }

        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingAppBar(
    title: String,
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onBackArrowClicked: () -> Unit = {}
) {
    val openDialog = remember { mutableStateOf(false) }

   /* if (openDialog.value) {
        ShowAlertDialog(title = stringResource(id = R.string.logout_title), message = stringResource(id = R.string.logout_description)
            , openDialog) {
            FirebaseAuth.getInstance().signOut().run {
                navController.navigate(ReaderScreens.LoginScreen.name)
            }
        }
    }*/


    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                /*if (showProfile) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_profile),
                            contentDescription = "Profile icon",
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(ReaderScreens.ReaderStatsScreen.name)
                                }
                                .size(45.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }*/

                if (icon != null){
                    Icon(imageVector = icon, contentDescription = "arrow back",
                        tint = Color.Red.copy(alpha = 0.7f),
                        modifier = Modifier.clickable { onBackArrowClicked.invoke() })
                }

                Spacer(modifier = Modifier.width(40.dp))

                Text(
                    text = title,
                    color = Color.Red.copy(alpha = 0.7f),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
            }
        },
        actions = {
            IconButton(onClick = {
                //openDialog.value = true
                /*FirebaseAuth.getInstance().signOut().run {
                    navController.navigate(ReaderScreens.LoginScreen.name)
                }*/
                navController.navigate(ShoppingScreens.ShoppingCartScreen.name)
            }) {
                if (isMainScreen && viewModel.list.isNotEmpty()) Row {
                    Icon(
                        imageVector = Icons.Rounded.ShoppingCart,
                        contentDescription = "Logout icon",
                        tint = Color.Red.copy(alpha = 0.7f),
                    )
                } else {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = "Empty cart icon",
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF92CBDF)
        ),
    )
}


@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    labelId: String = "Email",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(
        modifier = modifier,
        valueState = emailState,
        labelId = labelId,
        enabled = enabled,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    readOnly: Boolean = false,
    maxChar: Int? = 50
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            if (it.length <= maxChar!!) valueState.value = it
            //valueState.value = it
        },
        label = { Text(text = labelId) },
        singleLine = isSingleLine,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        ),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        readOnly = readOnly,
        maxLines = 1
    )
}


@Composable
fun PasswordInput(
    modifier: Modifier,
    passwordState: MutableState<String>,
    labelId: String, enabled: Boolean,
    passwordVisibility: MutableState<Boolean>,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    val visualTransformation = if (passwordVisibility.value) VisualTransformation.None
    else PasswordVisualTransformation()

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = {
            passwordState.value = it
        },
        label = { Text(text = labelId) },
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        ),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        visualTransformation = visualTransformation,
        trailingIcon = {PasswordVisibility(passwordVisibility = passwordVisibility)},
        keyboardActions = onAction
    )
}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value

    IconButton(onClick = { passwordVisibility.value = !visible }) {
        Icons.Default.Close
    }
}

@Preview
@Composable
fun RoundedSubmitButton(
    label: String = "Continue",
    radius: Int = 29,
    loading: Boolean = true,
    validInputs: Boolean = true,
    onClick: () -> Unit = {}
) {
    Box(modifier = Modifier.padding(16.dp)) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .heightIn(60.dp)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        bottomEndPercent = radius,
                        bottomStartPercent = radius,
                        topStartPercent = radius,
                        topEndPercent = radius
                    )
                ),
            enabled = validInputs,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF92CBDF)),
            shape = RectangleShape
        ) {
            /*if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
            else */Text(text = label, modifier = Modifier.padding(5.dp))
        }
    }
}

@Composable
fun RoundedButton(
    label: String = "Continue",
    radius: Int = 29,
    enabled: Boolean = false,
    onPress: () -> Unit = {}
) {
    Surface(modifier = Modifier
        .padding(16.dp)
        .clip(
            RoundedCornerShape(
                bottomEndPercent = radius,
                bottomStartPercent = radius,
                topStartPercent = radius,
                topEndPercent = radius
            )
        ),
        color = Color(0xFF92CBDF)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .heightIn(60.dp)
            .clickable { onPress.invoke() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 15.sp
                )
            )
        }
    }
}

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String, Boolean) -> Unit,
    onComplete: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }

    BasicTextField(
        modifier = modifier.padding(16.dp),
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length < otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            } else {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
                onComplete.invoke()
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> "0"
        index > text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = Modifier
            .width(46.dp)
            .border(
                2.dp, when {
                    isFocused -> Color(0xFF92CBDF)
                    else -> Color.LightGray
                }, RoundedCornerShape(8.dp)
            )
            .padding(22.dp),
        text = char,
        style = MaterialTheme.typography.titleLarge,
        color = if (isFocused) {
            Color.LightGray
        } else {
            Color.Red.copy(alpha = 0.5f)
        },
        textAlign = TextAlign.Center
    )
}
