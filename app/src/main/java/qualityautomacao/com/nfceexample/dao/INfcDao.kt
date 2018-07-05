package qualityautomacao.com.nfceexample.dao

import android.nfc.Tag
import java.io.UnsupportedEncodingException

interface INfcDao {
    @Throws(UnsupportedEncodingException::class)
    fun readTag(tag: Tag): String

    fun writeTag()
}
