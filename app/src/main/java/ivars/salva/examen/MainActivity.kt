package ivars.salva.examen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ivars.salva.examen.ui.theme.ExamenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var nVista by remember { mutableStateOf(0) }
            var usuario by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            //var listMovies = mutableListOf<Pair<String, Int>>(Pair("Black Adam", 6), Pair("Barbarian", 8), Pair("Doctor Who", 9))
            var listMovies by remember{ mutableStateOf(mutableListOf<Pair<String, Int>>())}
            listMovies.add(Pair("Black Adam", 6))
            listMovies.add(Pair("Barbarian", 8))
            ExamenTheme {
                MyScaffold(email, {email = it},usuario, {usuario = it}, password, {password = it},  nVista, listMovies, { nVista = 0 }, { nVista = 1 }, { nVista = 2 }, {nVista = 3} )
            }
        }
    }
}

@Composable
fun MyTextField(name: String, texto: String, onValueChanged: (String) -> Unit){
    TextField(
        value = name,
        onValueChange = {onValueChanged(it)},
        label = { Text(text = texto)},
        modifier = Modifier
            .background(Color.White)
    )
    Spacer(modifier = Modifier.padding(top=2.dp))
}

@Composable
fun MyTextFieldPassword(name: String, texto: String, onValueChanged: (String) -> Unit){
    TextField(
        value = name,
        onValueChange = {onValueChanged(it)},
        label = { Text(text = texto)},
        modifier = Modifier
            .background(Color.White),

    visualTransformation = PasswordVisualTransformation()

    )
    Spacer(modifier = Modifier.padding(top=2.dp))
}
//
@Composable
fun MyScaffold(email: String, changeEmail: (String) -> Unit, usuario: String, changeUsuario: (String) -> Unit, password: String, changePassword: (String) -> Unit, nVista: Int, listMovies: List<Pair<String, Int>>, cambiarVista0: () -> Unit, cambiarVista1: () -> Unit, cambiarVista2: () -> Unit, cambiarVista3: () -> Unit){
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        topBar = { MyTopAppBar()},
        scaffoldState = scaffoldState,
        bottomBar = { MyBottomNavigation(cambiarVista0, cambiarVista1, cambiarVista2)},
        content = { MyContent(nVista, email, changeEmail ,usuario, changeUsuario, password, changePassword, listMovies, cambiarVista0,cambiarVista1, cambiarVista2, cambiarVista3)}
    )
}

@Composable
fun MyContent(nVista: Int, email: String, changeEmail: (String) -> Unit, usuario: String, changeUsuario: (String) -> Unit, password: String, changePassword: (String) -> Unit, listMovies: List<Pair<String, Int>>, cambiarVista0: () -> Unit, cambiarVista1: () -> Unit, cambiarVista2: () -> Unit, cambiarVista3: () -> Unit){
    if(nVista == 0){
        MyUserLogin(usuario,changeUsuario, password, changePassword, nVista,  cambiarVista1, cambiarVista3)
    }
    if (nVista == 1){
        MyFavoritas(listMovies)
    }
    if (nVista == 2){
        MyHome(listMovies)
    }

    if(nVista == 3){
        MyRegistrarse(usuario,changeUsuario, password, changePassword, email, changeEmail, nVista,  cambiarVista1)
    }
}

@Composable
fun MyRegistrarse(usuario: String, changeUsuario: (String) -> Unit, password: String, changePassword: (String) -> Unit, email: String, changeEmail: (String) -> kotlin.Unit, nVista: Int, cambiarVista1: () -> Unit){
    var repitePassword by remember { mutableStateOf("") }
    var deporte by remember { mutableStateOf(false) }
    var romance by remember { mutableStateOf(false) }
    var accion by remember { mutableStateOf(false) }
    var historicas by remember { mutableStateOf(false) }
    var scifi by remember { mutableStateOf(false) }
    var documentales by remember { mutableStateOf(false) }
    Column() {
        MyTextField(usuario, "Nombre", changeUsuario)
        Spacer(modifier = Modifier.padding(top = 10.dp))
        MyTextField(email, "Email", changeEmail)
        Spacer(modifier = Modifier.padding(top = 10.dp))
        MyTextField(name = password, texto = "Contraseña", onValueChanged = changePassword )
        Spacer(modifier = Modifier.padding(top = 10.dp))
        MyTextField(name = repitePassword, texto ="Repite la contraseña", onValueChanged = {repitePassword = it})
        Spacer(modifier = Modifier.padding(top = 20.dp))
        Text("Intereses", Modifier.padding(start = 10.dp))
        Spacer(modifier = Modifier.padding(top = 20.dp))
        MyCheckBoxes(
            deporte = deporte,
            romance = romance,
            accion = accion,
            historicas = historicas ,
            scifi = scifi,
            documentales = documentales ,
            onChangeDeporte = {deporte = it},
            onChangeRomance = {romance = it},
            onChangeAccion = {accion = it},
            onChangeHistoricas = {historicas = it},
            onChangeScifi = {scifi = it},
            onChangeDocumentales = {documentales = it}
        )
        Button(onClick = {
            if(password == repitePassword && email.isNotEmpty() && usuario.isNotEmpty()){
                cambiarVista1()
            }
        }) {
            Text("Enviar")
        }
    }
}

@Composable
fun MyCheckBoxes(
    deporte:Boolean,
    romance: Boolean,
    accion: Boolean,
    historicas: Boolean,
    scifi: Boolean,
    documentales: Boolean,
    onChangeDeporte: (Boolean) -> Unit,
    onChangeRomance: (Boolean) -> Unit,
    onChangeAccion: (Boolean) -> Unit,
    onChangeHistoricas: (Boolean) -> Unit,
    onChangeScifi: (Boolean) -> Unit,
    onChangeDocumentales: (Boolean) -> Unit
){
    Row{
        Column() {
            Row {
                Checkbox(checked = deporte, onCheckedChange = { onChangeDeporte(!deporte) })
                Spacer(modifier = Modifier.padding(start = 10.dp))
                Text(text = "Deporte",modifier = Modifier.padding(vertical = 12.dp))
            }

            Row {
                Checkbox(checked = romance, onCheckedChange = { onChangeRomance(!romance) })
                Spacer(modifier = Modifier.padding(start = 10.dp))
                Text(text = "Cine",modifier = Modifier.padding(vertical = 12.dp))
            }

            Row {
                Checkbox(checked = accion, onCheckedChange = { onChangeAccion(!accion) })
                Spacer(modifier = Modifier.padding(start = 10.dp))
                Text(text = "Viajes",modifier = Modifier.padding(vertical = 12.dp))
            }
        }
        Column() {
            Row {
                Checkbox(checked = historicas, onCheckedChange = { onChangeHistoricas(!historicas) })
                Spacer(modifier = Modifier.padding(start = 10.dp))
                Text(text = "Museos",modifier = Modifier.padding(vertical = 12.dp))
            }

            Row {
                Checkbox(
                    checked = scifi,
                    onCheckedChange = { onChangeScifi(!scifi) })
                Spacer(modifier = Modifier.padding(start = 10.dp))
                Text(text = "Videojuegos",modifier = Modifier.padding(vertical = 12.dp))
            }

            Row {
                Checkbox(checked = documentales, onCheckedChange = { onChangeDocumentales(!documentales) })
                Spacer(modifier = Modifier.padding(start = 10.dp))
                Text(text = "Anime",modifier = Modifier.padding(vertical = 12.dp))
            }
        }
    }
}

@Composable
fun MyHome(listMovies: List<Pair<String, Int>>){
    Column(Modifier.padding(start = 2.dp)) {
        listMovies.forEach{ item ->
            MyCardDelete(item.first, item.second.toString())
            Spacer(modifier = Modifier.padding(bottom = 2.dp))
        }
    }
}

@Composable
fun MyCardDelete(name:String, nStars: String){
    Card(elevation = 10.dp,modifier = Modifier
        .fillMaxWidth()
        .height(70.dp))
    {
        Row(Modifier.padding(start = 1.dp, top = 4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Imagen de perfil",
                modifier = Modifier
                    .size(60.dp)
                    .clip(
                        CircleShape
                    )
            )
            Column(Modifier.padding(start = 40.dp, top = 10.dp)) {
                Text(name)
                Row() {
                    Icon(
                        imageVector = Icons.Filled.Star, contentDescription = "star",
                        Modifier
                            .size(20.dp)
                            .padding(top = 1.dp)
                    )
                    Text(text = nStars, fontSize = 12.sp, modifier = Modifier.padding(top = 3.dp))
                }
            }
            Row(horizontalArrangement = Arrangement.End) {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .weight(1f)
                ) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "delete")
                }
            }
        }
    }
}

@Composable
fun MyCard(name:String, nStars:String){
    Card(elevation = 10.dp,modifier = Modifier
        .fillMaxWidth()
        .height(70.dp))
    {
        Row(Modifier.padding(start = 1.dp, top = 4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Imagen de perfil",
                modifier = Modifier
                    .size(60.dp)
                    .clip(
                        CircleShape
                    )
            )
            Column(Modifier.padding(start = 40.dp, top = 10.dp)) {
                Text(name)
                Row() {
                    Icon(
                        imageVector = Icons.Filled.Star, contentDescription = "star",
                        Modifier
                            .size(20.dp)
                            .padding(top = 1.dp)
                    )
                    Text(text = nStars, fontSize = 12.sp, modifier = Modifier.padding(top = 3.dp))
                }
            }
            Row(horizontalArrangement = Arrangement.End) {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .weight(1f)
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
                }
            }
        }
    }
}
@Composable
fun MyFavoritas( listMovies: List<Pair<String, Int>>){
    Column(Modifier.padding(start = 2.dp)) {
        listMovies.forEach{ item ->
            MyCard(item.first, item.second.toString())
            Spacer(modifier = Modifier.padding(bottom = 2.dp))
        }
    }
}

@Composable
fun MyUserLogin(usuario: String,changeUsuario: (String) -> Unit, password: String, changePassword: (String) -> Unit, nVista: Int, nVistaCambiar: () -> Unit, cambiarVista3: () -> Unit){
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
    ) {
        Column(
            Modifier
                .background(Color.White)
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Spacer(Modifier.padding(top = 20.dp))
            MyTextField(usuario, "Nombre", changeUsuario)
            Spacer(modifier = Modifier.padding(top = 10.dp))
            MyTextFieldPassword(password, "Contraseña", changePassword)
            Spacer(Modifier.padding(top = 20.dp))
            Button(onClick = {
                if(usuario.isNotEmpty() && password.isNotEmpty())
                    nVistaCambiar()

            }) {
                Text("Entrar")
            }
            Button(onClick = { cambiarVista3() }) {
                Text("¿Aún no estas registrado?")
            }
        }
    }
}



@Composable
fun MyTopAppBar(){
    TopAppBar(
        title = {
            Text(text = "MonkeyFilms")
        },
        backgroundColor = Color.Red,
        contentColor = Color.White,
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Filled.List, contentDescription = "back")
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
            }
        }
    )
}

@Composable
fun MyBottomNavigation(cambiarVista0: () -> Unit,cambiarVista1: () -> Unit,cambiarVista2: () -> Unit){
    BottomNavigation(
        backgroundColor = Color.Red,
        contentColor = Color.White,
    ) {
        BottomNavigationItem(selected = false, onClick = cambiarVista1, icon = {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "home"
            )
        })
        BottomNavigationItem(selected = false, onClick = cambiarVista2, icon = {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "favorite"
            )
        })

        BottomNavigationItem(selected = false, onClick = {}  , icon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "person"
            )
        })
    }
}