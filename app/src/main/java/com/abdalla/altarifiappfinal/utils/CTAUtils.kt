package com.abdalla.altarifiappfinal.utils

import android.content.*
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.abdalla.altarifiappfinal.screen.detail.DetailViewModel
import com.abdalla.altarifiappfinal.screen.favourites.FavouritesViewModel
import com.abdalla.altarifiappfinal.screen.quoteslist.QuotesListViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

// used to copy quote to clipboard
fun Context.copyToClipboard(text: CharSequence) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    clipboard.setPrimaryClip(clip)
}

// used to share quote to other application
fun Context.shareToOthersText(quote: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, quote)
    startActivity(Intent.createChooser(intent, "Share via"))

}

// used to share quote to other application
fun Context.shareToOthersImage(quotesListViewModel: QuotesListViewModel) {
    val imageUri = saveImage(quotesListViewModel.onBitmapGenerated.value!!)
    val shareIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, imageUri)
        type = "image/*"
    }
    startActivity(Intent.createChooser(shareIntent, "Share via"))

}

// used to share quote to other application
fun Context.shareToOthersImage(favouritesViewModel: FavouritesViewModel) {
    val imageUri = saveImage(favouritesViewModel.onBitmapGenerated.value!!)
    val shareIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, imageUri)
        type = "image/*"
    }
    startActivity(Intent.createChooser(shareIntent, "Share via"))

}

private fun Context.saveImage(bitmap: Bitmap): Uri {
    val fos: OutputStream
    val imageUri: Uri

    val timestamp = SimpleDateFormat("yyyyMMdd_hhMMss").format(Date())

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val resolver = applicationContext.contentResolver
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timestamp)
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" + "KutipanAlkitab")
        imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
        fos = resolver.openOutputStream(imageUri)!!
    } else {
        val imagesDir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DCIM
        ).toString() + File.separator + "KutipanAlkitab"

        val file = File(imagesDir)
        if (!file.exists()) file.mkdir()
        val image = File(imagesDir, "$timestamp.png")
        imageUri = Uri.fromFile(image)
        fos = FileOutputStream(image)
    }

    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
    fos.flush()
    fos.close()
    return imageUri
}
