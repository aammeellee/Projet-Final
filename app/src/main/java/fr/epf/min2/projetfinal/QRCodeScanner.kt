package fr.epf.min2.projetfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView

class QRCodeScannerActivity : AppCompatActivity() {

    private lateinit var barcodeView: DecoratedBarcodeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_scanner)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Scan de QR Code"


        barcodeView = findViewById(R.id.barcode_scanner)
        barcodeView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                result?.text?.let { content ->
                    barcodeView.pause()
                    val id = content.toIntOrNull()
                    if (id != null) {
                        val intent = Intent(this@QRCodeScannerActivity, ProductDetailActivity::class.java)
                        intent.putExtra("product_id", id)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@QRCodeScannerActivity, "QR Code invalide", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }

            override fun possibleResultPoints(resultPoints: List<ResultPoint>?) {}
        })
    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
