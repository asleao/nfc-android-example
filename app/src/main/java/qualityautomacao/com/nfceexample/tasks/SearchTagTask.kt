package qualityautomacao.com.nfceexample.tasks

import android.app.Activity
import android.nfc.Tag
import android.os.AsyncTask
import qualityautomacao.com.nfceexample.MainActivity
import qualityautomacao.com.nfceexample.services.NfcService
import java.lang.ref.WeakReference

class SearchTagTask(activity: Activity) : AsyncTask<Tag, Any, String>() {
    var activityRef: WeakReference<Activity>

    init {
        activityRef = WeakReference(activity)
    }

    override fun doInBackground(vararg params: Tag): String {
        var tagContent = "Tag is empty."
        try {
            val tag = params[0]
            val mNfcService = NfcService(tag)
            tagContent = mNfcService.readTag(tag)
            return tagContent
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return tagContent
    }

    override fun onPostExecute(result: String) {
        val activity = activityRef.get() as? MainActivity
        if (activity != null) {
            activity.setMessage(result)
        }
    }
}
