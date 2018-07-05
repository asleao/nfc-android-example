package qualityautomacao.com.nfceexample.dao

import android.nfc.FormatException
import android.nfc.Tag
import java.io.IOException
import java.io.UnsupportedEncodingException

interface INfcDao {
    @Throws(UnsupportedEncodingException::class)
    fun readTag(tag: Tag): String

    @Throws(IOException::class, FormatException::class)
    fun writeTag(tag: Tag, textString: String)
}
