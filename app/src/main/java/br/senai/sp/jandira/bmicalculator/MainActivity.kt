package br.senai.sp.jandira.bmicalculator

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.bmicalculator.calculate.calculate
import br.senai.sp.jandira.bmicalculator.calculate.getBmiClassification
import br.senai.sp.jandira.bmicalculator.model.Client
import br.senai.sp.jandira.bmicalculator.model.Product
import br.senai.sp.jandira.bmicalculator.ui.theme.BMICalculatorTheme
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Product p1 = new Product();
        val p1 = Product()
        p1.id = 100
        p1.productName = "Mouse sem fio"

        val c1 = Client(
            id = 100,
            name = "Pedro",
            salary = 1500.0
        )

        setContent {
            BMICalculatorTheme {
                CalculatorScreen()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CalculatorScreen() {

//classe peso
    var weightState by rememberSaveable {
        mutableStateOf("")
    }
// classe altura
    var heightState by rememberSaveable { // salvar o numero quando rotar a tela
        mutableStateOf("")
    }

    var bmiState by rememberSaveable {
        mutableStateOf("0.0")
    }

    var bmiClassificationState by rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current.applicationContext
    val context2 = LocalContext.current



    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // HEADER
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bmi),
                    contentDescription = "",
                    modifier = Modifier.size(100.dp)
                )
                Text(
                    text = stringResource(id = R.string.title),
                    fontSize = 32.sp,
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 4.sp
                )
            }

            // FORM
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.weight_label),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = weightState, //variavel de estado
                    onValueChange = {
                        Log.i("ds2m", it)
                        weightState = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Text(
                    text = stringResource(id = R.string.height_label),
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                )
                OutlinedTextField(
                    value = heightState, //variavel de estado
                    onValueChange = {
                        heightState = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(48.dp))
                Button(

                    onClick = {
                        var w = weightState.toDouble()
                        var h = heightState.toDouble()
                        var bmi = calculate(weight = w, height =  h)
                        bmiState = String.format("%.2f", bmi)
                        bmiClassificationState = getBmiClassification(bmi, context)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.button_calculate),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            // FOOTER
            Column() {
                Card(
                    modifier = Modifier
                        .fillMaxSize(),
                    shape = RoundedCornerShape(
                        topStart = 48.dp, topEnd = 48.dp
                    ),
                    backgroundColor = Color(
                        red = 79,
                        green = 54,
                        blue = 232
                    ),
                    //border = BorderStroke(2.dp, Color.Black)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Text(
                            text = stringResource(id = R.string.your_score),
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = bmiState,
                            color = Color.White,
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = bmiClassificationState,
                            color = Color.White,
                            fontSize = 20.sp
                        )
                        Row() {
                            Button(onClick =  {
                                weightState = ""
                                heightState = ""
                                bmiClassificationState = ""
                                bmiState = ""

                                }) {
                                Text(text = stringResource(id = R.string.reset))
                            }
                            Spacer(modifier = Modifier.width(24.dp))
                            Button(onClick = {
                                val openOther = Intent(context2, SignUpActivity::class.java)
                                context2.startActivity(openOther)
                            }) {
                                Text(text = stringResource(id = R.string.share))
                            }
                        }
                    }
                }
            }
        }
    }
}
