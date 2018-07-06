package qualityautomacao.com.nfceexample.dao

import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.util.Log
import java.io.IOException

class MifareClassicDao : INfcDao {
    override fun readTag(tag: Tag): String {
        val mifare: MifareClassic = MifareClassic.get(tag)

        try {
            if (!mifare.isConnected) {
                mifare.connect()
                val sectorCount = mifare.sectorCount
                var blockSectorCount = 0
                for (sector in 0..sectorCount) {
                    var auth = mifare.authenticateSectorWithKeyA(sector, MifareClassic.KEY_DEFAULT)

                    if (!auth) {
                        auth = mifare.authenticateSectorWithKeyB(sector, MifareClassic.KEY_DEFAULT)
                    }

                    if (auth) {
                        blockSectorCount = mifare.getBlockCountInSector(sector)
                        for (blockId in 0..blockSectorCount) {
                            val blockIndex = mifare.sectorToBlock(sector)
//                            val data = String(mifare.readBlock(blockIndex), charset("UTF-8"))
                            return mifare.readBlock(blockIndex).toString()
                        }
                    }
                }
//                return String(payload, Charset.forName("US-ASCII"))
            }
        } catch (e: IOException) {
            Log.e("MifareClassic", "IOException while writing MifareClassic message...", e);

        } finally {
            if (mifare != null) {
                try {
                    mifare.close();
                } catch (e: IOException) {
                    Log.e("MifareClassic", "Error closing tag...", e)
                }
            }
        }
        return ""
    }

    override fun writeTag(tag: Tag, textString: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
