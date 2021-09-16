package com.abdalla.altarifiappfinal.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdalla.altarifiappfinal.R

@SuppressLint("ViewConstructor")
class MakeImage(
    ctx: Context,
    onBitmapCreated: (bitmap: Bitmap) -> Unit,
    quote: String,
    title: String
) :
    LinearLayoutCompat(ctx) {
    fun getView(ctx: Context, quote: String, title: String): View {
        val width = 900
        val height = 900

        val view = ComposeView(ctx)
        view.visibility = View.GONE
        view.layoutParams = LayoutParams(width, height)
        this.addView(view)

        view.setContent {
            ImageContent(quote, title)
        }
        return view
    }

    init {
        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val bitmap =
                    createBitmapFromView(
                        view = getView(ctx, quote, title),
                        width = 900,
                        height = 900
                    )
                onBitmapCreated(bitmap)
                viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }
}

fun createBitmapFromView(view: View, width: Int, height: Int): Bitmap {
    view.layoutParams = LinearLayoutCompat.LayoutParams(
        LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
        LinearLayoutCompat.LayoutParams.WRAP_CONTENT
    )

    view.measure(
        View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
        View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
    )

    view.layout(0, 0, width, height)

    val canvas = Canvas()
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    canvas.setBitmap(bitmap)
    view.draw(canvas)
    return bitmap

}


@Composable
fun ImageContent(quote: String, title: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
        Modifier.size(370.dp)
    ) {

        Image(
            painterResource(
                id =
                R.drawable.img_post
            ), contentDescription = null
        ,Modifier.fillMaxSize()
        )

        var multiplier by remember { mutableStateOf(1f) }

        Text(
            quote,
            color = Color(0xFF6F4C5B),
            textAlign = TextAlign.Center,
            lineHeight = 33.sp,
            maxLines = 6, // modify to fit your need
            overflow = TextOverflow.Visible,
            fontFamily = FontFamily(Font(resId = R.font.asmaa)),
            modifier = Modifier
                .padding(8.dp, 80.dp, 8.dp, 20.dp)
            ,
            style = TextStyle(
                fontSize = 24.sp * multiplier
            ),
            onTextLayout = {
                if (it.hasVisualOverflow) {
                    multiplier *= 0.70f // you can tune this constant
                }
            }
        )
    }

}

@Preview
@Composable
fun Dd() {
    ImageContent(
        quote = "إذا وُجد (الكفر) أُلغي إطلاق الفتنة إلا عليه ، لأنه فتنة أعظم من كل فتنة {وَالۡفِتۡنَةُ أَكۡبَرُ مِنَ الۡقَتۡلِ} البقرة إذا وُجد (الكفر) أُلغي إطلاق الفتنة إلا عليه ، لأنه فتنة أعظم من كل فتنة {وَالۡفِتۡنَةُ أَكۡبَرُ مِنَ الۡقَتۡلِ} البقرة 217217إذا وُجد (الكفر) أُلغي إطلاق الفتنة إلا عليه ، لأنه فتنة أعظم من كل فتنة {وَالۡفِتۡنَةُ أَكۡبَرُ مِنَ الۡقَتۡلِ} البقرة 217",
        title = "ddddddddd"
    )


}



