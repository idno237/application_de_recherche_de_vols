//DatabaseHelper.kt
import android.content.Context
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object DatabaseHelper {
    fun copyDatabaseFromAssets(context: Context, dbName: String) {
        val dbFile = context.getDatabasePath(dbName)
        if (!dbFile.exists()) {
            context.assets.open(dbName).use { input ->
                FileOutputStream(dbFile).use { output ->
                    input.copyTo(output)
                }
            }
        }
    }
}

