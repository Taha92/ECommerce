package com.example.ecommerce.screen.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ecommerce.R
import com.example.ecommerce.component.InputField
import com.example.ecommerce.component.RoundedSubmitButton
import com.example.ecommerce.component.ShoppingAppBar
import com.example.ecommerce.model.CardInfo
import com.example.ecommerce.navigation.ShoppingScreens
import com.google.gson.Gson


@Composable
fun CardDetailScreen(
    navController: NavController,
    totalPrice: String
) {

    Scaffold(topBar = {
        ShoppingAppBar(
            title = "Card Details",
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
            .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
            .fillMaxSize()
        ) {
            //card content
            CardDetailsContent(totalPrice = totalPrice, navController = navController)
        }
    }
}

@Composable
fun CardHolderNameInput(
    modifier: Modifier = Modifier,
    nameState: MutableState<String>,
    labelId: String = "Cardholder Name",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(
        modifier = modifier,
        valueState = nameState,
        labelId = labelId,
        enabled = enabled,
        keyboardType = KeyboardType.Text,
        imeAction = imeAction,
        maxChar = 25
    )
}

@Composable
fun CardNumberInput(
    modifier: Modifier = Modifier,
    numberState: MutableState<String>,
    labelId: String = "Card Number",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(
        modifier = modifier,
        valueState = numberState,
        labelId = labelId,
        enabled = enabled,
        keyboardType = KeyboardType.Number,
        imeAction = imeAction,
        maxChar = 16
    )
}

@Composable
fun CardMonthInput(
    modifier: Modifier = Modifier,
    monthState: MutableState<String>,
    labelId: String = "Month",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(
        modifier = modifier,
        valueState = monthState,
        labelId = labelId,
        enabled = enabled,
        imeAction = imeAction,
        readOnly = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthDropDown(
    monthState: MutableState<String>
) {

    val list = listOf("01", "02", "03", "04", "05",
        "06", "07", "08", "09", "10", "11", "12")

    val isExpanded = remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(4.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded.value,
            onExpandedChange = { isExpanded.value = !isExpanded.value }
        ) {
            CardMonthInput(
                modifier = Modifier.menuAnchor(),
                monthState = monthState,
                enabled = true,
                onAction = KeyboardActions {
                    //passwordFocusRequest.requestFocus()
                })
            
            ExposedDropdownMenu(expanded = isExpanded.value, onDismissRequest = { isExpanded.value = false }) {
                list.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        text = { Text(text) },
                        onClick = {
                            monthState.value = list[index]
                            isExpanded.value = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

@Composable
fun CardYearInput(
    modifier: Modifier = Modifier,
    yearState: MutableState<String>,
    labelId: String = "Year",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(
        modifier = modifier,
        valueState = yearState,
        labelId = labelId,
        enabled = enabled,
        imeAction = imeAction,
        readOnly = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YearDropDown(
    yearState: MutableState<String>,
    valid: Boolean
) {

    val list = listOf("2024", "2025", "2026", "2027", "2028",
        "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036",
        "2037", "2038", "2039", "2040")

    val isExpanded = remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(4.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded.value,
            onExpandedChange = { isExpanded.value = !isExpanded.value }
        ) {
            CardYearInput(
                modifier = Modifier.menuAnchor(),
                yearState = yearState,
                enabled = true,
                onAction = KeyboardActions {
                    if (!valid) return@KeyboardActions
                })
            

            ExposedDropdownMenu(expanded = isExpanded.value, onDismissRequest = { isExpanded.value = false }) {
                list.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        text = { Text(text) },
                        onClick = {
                            yearState.value = list[index]
                            isExpanded.value = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

@Composable
fun CardCVVInput(
    modifier: Modifier = Modifier,
    numberState: MutableState<String>,
    labelId: String = "CVV",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(
        modifier = modifier,
        valueState = numberState,
        labelId = labelId,
        enabled = enabled,
        keyboardType = KeyboardType.Number,
        imeAction = imeAction,
        maxChar = 3
    )
}

@Composable
fun CardDetailsContent(
    totalPrice: String,
    loading: Boolean = false,
    navController: NavController
) {
    val cardHolderName = rememberSaveable { mutableStateOf("") }
    val cardNumber = rememberSaveable { mutableStateOf("") }
    val cardMonthExpiry = rememberSaveable { mutableStateOf("") }
    val cardYearExpiry = rememberSaveable { mutableStateOf("") }
    val cardCvv = rememberSaveable { mutableStateOf("") }
    val termsAndCondition = rememberSaveable { mutableStateOf(false) }
    val cardNumberFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(cardHolderName.value, cardNumber.value,
        cardMonthExpiry.value, cardYearExpiry.value, termsAndCondition.value, cardCvv.value
    ) {
        (cardHolderName.value.trim().isNotEmpty() && cardNumber.value.trim().isNotEmpty()
                && cardMonthExpiry.value.trim().isNotEmpty() && cardYearExpiry.value.trim().isNotEmpty() &&
                cardCvv.value.trim().isNotEmpty()) && termsAndCondition.value
    }


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(4.dp)
        .verticalScroll(rememberScrollState())
    ) {
        Column(modifier = Modifier.weight(0.8f)) {
            HeaderDescription()

            CardHolderNameInput(
                nameState = cardHolderName,
                enabled = !loading,
                onAction = KeyboardActions {
                    cardNumberFocusRequest.requestFocus()
                })

            CardNumberInput(
                numberState = cardNumber,
                enabled = !loading,
                onAction = KeyboardActions {
                    //passwordFocusRequest.requestFocus()
                })

            //CardExpiryRow()
            Row(modifier = Modifier.height(80.dp)) {
                Box(modifier = Modifier.weight(0.5f)) {
                    MonthDropDown(cardMonthExpiry)
                }
                Box(modifier = Modifier.weight(0.5f)) {
                    YearDropDown(cardYearExpiry, valid)
                }
            }

            CardCVVInput(
                numberState = cardCvv,
                enabled = !loading,
                onAction = KeyboardActions {
                    //passwordFocusRequest.requestFocus()
                })

            TermsAndCondition(termsAndCondition)

            Row(modifier = Modifier
                .padding(16.dp)
            ){
                Image(
                    painter = painterResource(id = R.mipmap.master_card),
                    contentDescription = "Master card icon",
                    modifier = Modifier
                        .size(42.dp)
                )
                Image(
                    painter = painterResource(id = R.mipmap.visa_card),
                    contentDescription = "Visa card icon",
                    modifier = Modifier
                        .size(42.dp)
                        .padding(start = 6.dp)
                )
                Image(
                    painter = painterResource(id = R.mipmap.american_express_card),
                    contentDescription = "American express card icon",
                    modifier = Modifier
                        .size(42.dp)
                        .padding(start = 6.dp)
                )
            }
        }

        val cardInfo = CardInfo(
            holderName = cardHolderName.value,
            holderNumber = cardNumber.value,
            expiryMonth = cardMonthExpiry.value,
            expiryYear = cardYearExpiry.value,
            cardCvv = cardCvv.value
        )
        val cardInfoJson = Gson().toJson(cardInfo)

        Box(modifier = Modifier.weight(0.1f)) {
            SubmitButton(
                loading = loading,
                validInputs = valid,
                totalPrice = totalPrice,
                navController = navController,
                cardInfoJson = cardInfoJson
            ) {
                //onDone(email.value.trim(), password.value.trim())
                keyboardController?.hide()
            }
        }
    }
}

@Composable
fun SubmitButton(
    loading: Boolean,
    validInputs: Boolean,
    totalPrice: String,
    navController: NavController,
    cardInfoJson: String,
    onClick: () -> Unit,
) {
    RoundedSubmitButton(validInputs = validInputs) {
        navController.navigate(ShoppingScreens.CheckoutScreen.name + "?cardInfo=${cardInfoJson}?totalBill=${totalPrice}")
    }
}


@Composable
fun TermsAndCondition(
    checked: MutableState<Boolean>
) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checked.value,
            modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp),
            onCheckedChange = { checked.value = it },
        )
        Text(
            text = "I have read and accept the terms!",
            color = Color.Red.copy(alpha = 0.5f),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(end = 10.dp, top = 10.dp, bottom = 10.dp)
        )
    }
}

/*@Composable
fun CardExpiryRow() {
    Row(modifier = Modifier.height(80.dp)) {
        Box(modifier = Modifier.weight(0.5f)) {
            MonthDropDown()
        }
        Box(modifier = Modifier.weight(0.5f)) {
            YearDropDown()
        }
    }
}*/

@Composable
fun HeaderDescription() {
    Text(
        text = "Security",
        style = MaterialTheme.typography.titleLarge,
        color = Color.Red.copy(alpha = 0.5f),
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp, top = 10.dp)
    )
    Text(
        text = "Our payment infrastructure is provided by MasterPass, a MasterCard application, and transaction security is guaranteed by Mastercard.",
        modifier = Modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
        style = MaterialTheme.typography.titleMedium
    )
}
