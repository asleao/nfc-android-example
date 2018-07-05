package qualityautomacao.com.nfceexample.services

import android.nfc.Tag
import java.io.UnsupportedEncodingException

interface INfcService {
    @Throws(UnsupportedEncodingException::class)
    fun readTag(tag: Tag): String

    fun writeTag()
}
