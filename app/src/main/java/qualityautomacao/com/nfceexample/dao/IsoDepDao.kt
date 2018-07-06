package qualityautomacao.com.nfceexample.dao

import android.nfc.Tag
import android.nfc.tech.IsoDep
import java.io.IOException


class IsoDepDao : INfcDao {
    override fun readTag(tag: Tag): String {
        val SELECT = byteArrayOf(0x00.toByte(), // CLA Class
                0xA4.toByte(), // INS Instruction
                0x04.toByte(), // P1  Parameter 1
                0x00.toByte(), // P2  Parameter 2
                0x0A.toByte(), // Length
                0x63, 0x64, 0x63, 0x00, 0x00, 0x00, 0x00, 0x32, 0x32, 0x31 // AID
        )
        val tagIso = IsoDep.get(tag)

        tagIso.connect()

        var result = tagIso.transceive(SELECT)
        if (!(result[0] == 0x90.toByte() && result[1] == 0x00.toByte()))
            throw IOException("could not select applet")

        val GET_STRING = byteArrayOf(0x80.toByte(), // CLA Class
                0x04, // INS Instruction
                0x00, // P1  Parameter 1
                0x00, // P2  Parameter 2
                0x10  // LE  maximal number of bytes expected in result
        )

        result = tagIso.transceive(GET_STRING)
        val len = result.size
        if (!(result[len - 2] === 0x90.toByte() && result[len - 1] === 0x00.toByte()))
            throw RuntimeException("could not retrieve msisdn")

        val data = ByteArray(len - 2)
        System.arraycopy(result, 0, data, 0, len - 2)
        val str = String(data).trim { it <= ' ' }

        tagIso.close()
        return str
    }

    override fun writeTag(tag: Tag, textString: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
