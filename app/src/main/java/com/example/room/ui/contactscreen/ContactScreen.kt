package com.example.room.ui.contactscreen

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.room.domain.Contact
import com.example.room.ui.contactscreen.components.AddContactDialog
import com.example.room.ui.contactscreen.components.ContactEvent
import com.example.room.ui.contactscreen.components.ContactState
import com.example.room.ui.contactscreen.components.SortType
import com.example.room.ui.theme.*

@Composable
fun ContactScreen(
    modifier: Modifier = Modifier,
    state: ContactState,
    onEvent: (ContactEvent) -> Unit
) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Black0D, Blue1A, Blue16)
    )

    ContactScreenContent(
        modifier = modifier,
        state = state,
        onEvent = onEvent,
        gradientBrush = gradientBrush
    )
}

@Composable
fun ContactScreenContent(
    modifier: Modifier = Modifier,
    state: ContactState,
    onEvent: (ContactEvent) -> Unit,
    gradientBrush: Brush
) {
    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        topBar = {
            TopBar(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Blue8B,
                        shape = CircleShape
                    ),
                state = state
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(ContactEvent.ShowDialog) },
                containerColor = Blue8B,
                contentColor = Black0D,
                shape = CircleShape,
                modifier = Modifier
                    .size(56.dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add contact",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush)
        ) {
            if (state.isAddingContact) {
                AddContactDialog(state = state, onEvent = onEvent)
            }
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize(),
                contentPadding = padding,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item { Spacer(modifier = Modifier.height(4.dp)) }

                item {
                    SortByCard(
                        modifier = Modifier.fillMaxWidth(),
                        state = state,
                        onEvent = onEvent
                    )
                }

                item { Spacer(modifier = Modifier.height(4.dp)) }

                itemsIndexed(
                    items = state.contacts,
                    key = { _, contact -> contact.id ?: 0 }
                ) { index, contact ->
                    ContactCard(
                        contact = contact,
                        index = index,
                        onDelete = { onEvent(ContactEvent.DeleteContact(contact)) }
                    )
                }

                if (state.contacts.isEmpty()) {
                    item {
                        NoContacts(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 60.dp),
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    state: ContactState
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = modifier,
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = PureWhite,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Contacts",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = PureWhite
                    )
                    Text(
                        text = "${state.contacts.size} contacts",
                        fontSize = 12.sp,
                        color = GrayB0
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Black0D
        )
    )
}

@Composable
private fun SortByCard(
    modifier: Modifier = Modifier,
    state: ContactState,
    onEvent: (ContactEvent) -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Blue1F.copy(alpha = 0.7f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "Sort by",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = GrayB0,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SortType.entries.forEach { sortType ->
                    val isSelected = state.sortType == sortType
                    FilterChip(
                        selected = isSelected,
                        onClick = { onEvent(ContactEvent.SortContact(sortType)) },
                        label = {
                            Text(
                                text = sortType.name.replace("_", " ").lowercase(),
                                fontSize = 14.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Blue8B,
                            selectedLabelColor = Black0D,
                            containerColor = Color.Transparent,
                            labelColor = GrayB0
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = Gray6B,
                            selectedBorderColor = Blue8B,
                            enabled = true,
                            selected = isSelected
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun NoContacts(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = Blue8B.copy(alpha = 0.5f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    tint = GrayB0,
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No contacts yet",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = PureWhite
            )
            Text(
                text = "Tap + to add your first contact",
                fontSize = 14.sp,
                color = GrayB0
            )
        }
    }
}

@Composable
private fun ContactCard(
    contact: Contact,
    index: Int,
    onDelete: () -> Unit
) {
    val avatarColors = listOf(
        PurpleAB,
        Blue7E,
        Purple91,
        Purple76,
        Blue45
    )
    val color = avatarColors[index % avatarColors.size]

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = Blue1F
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        shape = CircleShape,
                        color = color
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${
                        contact.firstName.firstOrNull()?.uppercase() ?: ""
                    }${contact.lastName?.firstOrNull()?.uppercase() ?: ""}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (contact.lastName.isNullOrBlank()) contact.firstName else "${contact.firstName} ${contact.lastName}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PureWhite,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = null,
                        tint = GrayB0,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = contact.phoneNumber,
                        fontSize = 14.sp,
                        color = GrayB0,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = RedCE,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
