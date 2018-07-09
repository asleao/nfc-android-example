package qualityautomacao.com.nfceexample

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.content.IntentFilter.MalformedMimeTypeException
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.txt_messagem
import qualityautomacao.com.nfceexample.services.NfcService
import qualityautomacao.com.nfceexample.tasks.SearchTagTask
import java.lang.ref.WeakReference


class MainActivity : AppCompatActivity() {

    private var mNfcAdapter: NfcAdapter? = null
    private var mPendingIntent: PendingIntent? = null
    private var task: SearchTagTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)

        if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show()
            finish()
        }

        if (!checkNotNull(mNfcAdapter?.isEnabled())) {
            txt_messagem.setText("NFC is disabled.")
        } else {
            txt_messagem.setText("NFC habilitado")
        }

        val ndef = IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)

        try {
            ndef.addDataType("*/*")
        } catch (e: MalformedMimeTypeException) {
            throw RuntimeException("fail", e)
        }

        mPendingIntent = PendingIntent.getActivity(
                this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
    }

    override fun onResume() {
        super.onResume()
        mNfcAdapter?.enableForegroundDispatch(this, mPendingIntent, null, null)
    }

    override fun onPause() {
        mNfcAdapter?.disableForegroundDispatch(this)
        if (task != null) {
            task?.cancel(true)
            task = null
        }
        super.onPause()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val action = intent.getAction()
        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        val techList = tag?.getTechList()
        readTag(tag)
//        mNfcService.writeTag(tag,"webpostoPay")
    }

    private fun readTag(tag: Tag) {
        val searchTagTask = SearchTagTask(this)
        searchTagTask.execute(tag)
    }

    fun setMessage(result: String) {
        txt_messagem.setText(result)
    }

}
