package qualityautomacao.com.nfceexample.dao

import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.util.Log
import java.util.Arrays
import kotlin.experimental.and


class NdefDao : INfcDao {
    override fun readTag(tag: Tag): String {
        var contentString = ""
        val records: Array<NdefRecord>
        val ndef = Ndef.get(tag)
        val ndefMessage = ndef.cachedNdefMessage

        if (ndefMessage == null) {
            contentString = "No messages found."
        } else {
            records = ndefMessage.records
            for (record in records) {
                if (record.tnf == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(record.type, NdefRecord.RTD_TEXT)) {
                    val contentpayload = record.payload
                    val encoding = if (contentpayload[0] and 128.toByte() == 0.toByte()) "UTF-8" else "UTF-16"
                    val languageCodeLength = contentpayload[0] and 51.toByte()
                    try {
                        contentString = String(contentpayload, languageCodeLength + 1, contentpayload.size - languageCodeLength - 1, charset(encoding))
                    }catch (e:Exception){
                        Log.d("NdefReadTagErro",e.message)
                    }
                }
            }
        }


        return contentString
    }

    override fun writeTag(tag: Tag, textString: String) {
        val language = "en"
        val stringBytes = textString.toByteArray()
        val languageBytes = language.toByteArray(charset("US-ASCII"))
        val languageLength = languageBytes.size
        val stringLength = stringBytes.size
        val payload = ByteArray(1 + languageLength + stringLength)
        payload[0] = languageLength.toByte()

        val ndefRecords = arrayOf(NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, ByteArray(0), payload))
        val message = NdefMessage(ndefRecords)

        val ndef = Ndef.get(tag)
        ndef.connect()
        ndef.writeNdefMessage(message)
        ndef.close()
    }
}
