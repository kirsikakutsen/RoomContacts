package com.example.room.ui.contactscreen.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.room.ui.theme.Black0D
import com.example.room.ui.theme.Blue1A
import com.example.room.ui.theme.Blue1F
import com.example.room.ui.theme.Blue8B
import com.example.room.ui.theme.Gray6B
import com.example.room.ui.theme.GrayB0
import com.example.room.ui.theme.PureWhite
import com.example.room.ui.theme.RedCE

@Composable
fun AddContactDialog(
    state: ContactState,
    onEvent: (ContactEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = { onEvent(ContactEvent.HideDialog) },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(24.dp)
                .animateContentSize(),
            colors = CardDefaults.cardColors(
                containerColor = Blue1A
            ),
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .background(
                            color = Blue8B,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = PureWhite,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "New Contact",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                )

                Text(
                    text = "Add someone to your contacts",
                    fontSize = 14.sp,
                    color = GrayB0
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.firstName,
                    onValueChange = { onEvent(ContactEvent.SetFirstName(it)) },
                    placeholder = {
                        Text("First name", color = Gray6B)
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Person,
                            contentDescription = null,
                            tint = Blue8B
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Blue1F,
                        unfocusedContainerColor = Blue1F,
                        focusedBorderColor = Blue8B,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = PureWhite,
                        unfocusedTextColor = PureWhite,
                        cursorColor = Blue8B,
                        errorLeadingIconColor = RedCE,
                        errorBorderColor = RedCE
                    ),
                    singleLine = true,
                    isError = state.isFirstNameMissing
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.lastName ?: "",
                    onValueChange = { onEvent(ContactEvent.SetLastName(it)) },
                    placeholder = {
                        Text("Last name", color = Gray6B)
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Person,
                            contentDescription = null,
                            tint = Blue8B
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Blue1F,
                        unfocusedContainerColor = Blue1F,
                        focusedBorderColor = Blue8B,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = PureWhite,
                        unfocusedTextColor = PureWhite,
                        cursorColor = Blue8B
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.phoneNumber,
                    onValueChange = { newValue ->
                        val allowed = Regex("^\\+?[0-9]*$")
                        if (newValue.length <= 15 && newValue.matches(allowed)) {
                            onEvent(ContactEvent.SetPhoneNumber(newValue))
                        }
                    },
                    placeholder = {
                        Text("Phone number", color = Gray6B)
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Phone,
                            contentDescription = null,
                            tint = Blue8B
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Blue1F,
                        unfocusedContainerColor = Blue1F,
                        focusedBorderColor = Blue8B,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = PureWhite,
                        unfocusedTextColor = PureWhite,
                        cursorColor = Blue8B,
                        errorBorderColor = RedCE
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    isError = state.isPhoneNumberMissing
                )

                Spacer(modifier = Modifier.height(28.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { onEvent(ContactEvent.HideDialog) },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = GrayB0
                        ),
                        border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(
                            brush = Brush.linearGradient(listOf(Gray6B, Gray6B))
                        )
                    ) {
                        Text(
                            "Cancel",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Button(
                        onClick = { onEvent(ContactEvent.SaveContact) },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    color = Blue8B,
                                    shape = RoundedCornerShape(14.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Save Contact",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Black0D
                            )
                        }
                    }
                }
            }
        }
    }
}
