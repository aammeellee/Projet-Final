package fr.epf.min2.projetfinal

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QRCodeScannerTest {

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testQRCodeResultNavigatesToProductDetail() {
        Intents.intending(IntentMatchers.hasComponent(ProductDetailActivity::class.java.name))
            .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, Intent()))

        ActivityScenario.launch(QRCodeScannerActivity::class.java).onActivity {
            it.testSimulateScanResult("3")
        }

        Intents.intended(IntentMatchers.hasComponent(ProductDetailActivity::class.java.name))
    }
}
