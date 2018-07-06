package qualityautomacao.com.nfceexample.services

import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.nfc.tech.MifareClassic
import qualityautomacao.com.nfceexample.dao.INfcDao
import qualityautomacao.com.nfceexample.dao.IsoDepDao
import qualityautomacao.com.nfceexample.dao.MifareClassicDao
import qualityautomacao.com.nfceexample.dao.NdefDao

class NfcService(tag: Tag) : INfcService {
    val mNfcDao: INfcDao

    init {
        mNfcDao = when {
            IsoDep.get(tag) != null -> IsoDepDao()
            MifareClassic.get(tag) != null -> MifareClassicDao()
            else -> NdefDao()
        }
    }

    override fun readTag(tag: Tag): String {
        return mNfcDao.readTag(tag)
    }

    override fun writeTag(tag: Tag, textString: String) {
        mNfcDao.writeTag(tag, textString)
    }
}
