package qualityautomacao.com.nfceexample.dao

import android.nfc.NdefRecord
import android.nfc.Tag
import android.nfc.tech.Ndef
import java.util.Arrays
import kotlin.experimental.and

class NdefDao : INfcDao {
    override fun readTag(tag: Tag): String {
        var contentString = ""
        val records: Array<NdefRecord>

        val ndef = Ndef.get(tag)

        val ndefMessage = ndef.cachedNdefMessage

        records = ndefMessage.records

        if (records.size == 0) {

        } else {
            for (Record in records) {
                if (Record.tnf == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(Record.type, NdefRecord.RTD_TEXT)) {
                    val contentpayload = Record.payload
                    val encoding = if (contentpayload[0] and 128.toByte() == 0.toByte()) "UTF-8" else "UTF-16"
                    val languageCodeLength = contentpayload[0] and 51
                    contentString = String(contentpayload, languageCodeLength + 1, contentpayload.size - languageCodeLength - 1, charset(encoding))
                }
            }
        }


        return contentString
    }

    override fun writeTag() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
