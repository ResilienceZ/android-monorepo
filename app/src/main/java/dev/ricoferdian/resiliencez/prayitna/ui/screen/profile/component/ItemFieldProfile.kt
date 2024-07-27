package dev.ricoferdian.resiliencez.prayitna.ui.screen.profile.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.ricoferdian.resiliencez.prayitna.ui.theme.CustomColor
import dev.ricoferdian.resiliencez.prayitna.ui.theme.PrayitnaTheme

@Composable
fun ItemFieldProfile(
    title: String,
    textPlaceHolder: String,
    modifier: Modifier = Modifier,
    isEditable: Boolean = true,
    textInputed: String? = null,
    onChangeText: (String) -> Unit,
    ) {
    var inputedText by remember { mutableStateOf(TextFieldValue(textInputed ?: "")) }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = CustomColor.Black,
            fontWeight = FontWeight.SemiBold
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(28.dp)
        ) {
            if (inputedText.text.isEmpty()) {
                Text(
                    text = textPlaceHolder,
                    fontSize = 14.sp,
                    color = CustomColor.CadetGray,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            BasicTextField(
                value = inputedText,
                enabled = isEditable,
                onValueChange = {
                    inputedText = it
                    onChangeText(it.text)
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    color = CustomColor.Black,
                    fontWeight = FontWeight.Normal,
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemFieldProfilePreview() {
    PrayitnaTheme {
        ItemFieldProfile(
            textPlaceHolder = "Input your email",
            title = "Email",
            onChangeText = {}
        )
    }
}