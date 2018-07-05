package qualityautomacao.com.nfceexample.dao

import android.nfc.Tag

class IsoDepDao : INfcDao {
    override fun readTag(tag: Tag): String {
        return ""
    }

    override fun writeTag() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
