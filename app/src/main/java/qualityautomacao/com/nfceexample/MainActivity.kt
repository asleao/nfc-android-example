package qualityautomacao.com.nfceexample

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.txt_messagem
import qualityautomacao.com.nfceexample.services.NfcService


class MainActivity : AppCompatActivity() {
    val TAG = "NfcDemo"

    private var mNfcAdapter: NfcAdapter? = null
    private var mPendingIntent: PendingIntent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)

        if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;

        }

        if (!checkNotNull(mNfcAdapter?.isEnabled())) {
            txt_messagem.setText("NFC is disabled.")
        } else {
            txt_messagem.setText("NFC habilitado")
        }

        mPendingIntent = PendingIntent.getActivity(
                this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)

//        handleIntent(getIntent())
    }

    fun handleIntent(intent: Intent) {
        // TODO: handle Intent
    }

    override fun onResume() {
        super.onResume()
        mNfcAdapter?.enableForegroundDispatch(this, mPendingIntent, null, null)
    }

    override fun onPause() {
        mNfcAdapter?.disableForegroundDispatch(this)
        super.onPause()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val action = intent.getAction()
        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        val techList = tag?.getTechList()
        val mNfcService = NfcService(tag)

//        mNfcService.writeTag(tag,"webpostoPay")

        val tagContent = mNfcService.readTag(tag)

        txt_messagem.setText(tagContent)
    }


}
