package fr.epf.min2.projetfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class QRCodeScannerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val integrator = IntentIntegrator(this)
        integrator.setPrompt("Scannez un QR Code")
        integrator.setOrientationLocked(true)
        integrator.setBeepEnabled(true)
        integrator.captureActivity = CaptureActivityPortrait::class.java
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null && result.contents != null) {
            val id = result.contents.toIntOrNull()
            if (id != null) {
                val intent = Intent(this, ProductDetailActivity::class.java)
                intent.putExtra("product_id", id)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "QR Code invalide", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            Toast.makeText(this, "Scan annulé", Toast.LENGTH_SHORT).show()
            finish()
        }

        super.onActivityResult(requestCode, resultCode, data)
    }


    fun testSimulateScanResult(qrContent: String) {
        val id = qrContent.toIntOrNull()
        if (id != null) {
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("product_id", id)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
