package com.phz.dev.feature.practice.mlkit.scan.ct

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.Image
import android.os.Bundle
import android.os.Process
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.gyf.immersionbar.ktx.immersionBar
import com.jraska.console.Console
import com.phz.common.page.activity.BaseVmDbPureActivity
import com.phz.common.state.BaseViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivityMlkitScanCtBinding
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors

/**
 * @author phz on 2021/8/24
 * @description
 */
class MlKitScanWithConsoleActivity :
    BaseVmDbPureActivity<BaseViewModel, ActivityMlkitScanCtBinding>() {
    private val scanner = BarcodeScanning.getClient(options)

    private var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>? = null

    private val cameraExecutor by lazy {
        Executors.newSingleThreadExecutor()
    }

    override fun layoutId(): Int = R.layout.activity_mlkit_scan_ct

    override fun initView(savedInstanceState: Bundle?) {

        val mToolbar = mViewDataBinding.mToolbar
        //初始化沉浸式
        setSupportActionBar(mViewDataBinding.mToolbar)
        supportActionBar?.title = ""
        immersionBar {
            titleBar(mToolbar)
            statusBarColor(R.color.colorTransparent)
            autoStatusBarDarkModeEnable(true)
        }
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun initData() {
        if (checkPermission(
                cameraPermission[0],
                Process.myPid(),
                Process.myUid()
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, cameraPermission, REQUEST_CAMERA)
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun startCamera() {
        val previewView = mViewDataBinding.previewFrame
        val scanOverlay = mViewDataBinding.overlay

        cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture?.addListener({
            try {
                val cameraProvider = cameraProviderFuture?.get()
                //绑定预览
                val preview = Preview.Builder()
                    .build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                //使用后置相机
                val cameraSelector =
                    CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()

                //构建图片扫描
                val imageAnalysis = ImageAnalysis.Builder()
                    .setTargetRotation(preview.targetRotation)
                    .setTargetResolution(Size(scanOverlay.width, scanOverlay.height))//设置预期目标的分辨率
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                //先解绑所有
                cameraProvider?.unbindAll()
                //将相机绑定到当前页面的生命周期，再也不用担心开关相机了
                cameraProvider?.bindToLifecycle(
                    this,
                    cameraSelector,
                    imageAnalysis,
                    preview
                )
                //设置分析器
                imageAnalysis.setAnalyzer(
                    cameraExecutor, {
                        val mediaImage: Image? = it.image
                        if (mediaImage != null) {
                            try {
                                //要识别图像中的条形码，请InputImage从Bitmap、media.Image、ByteBuffer、 字节数组或设备上的文件创建对象。然后，将InputImage对象传递给 BarcodeScanner的process方法。
                                val image =
                                    InputImage.fromMediaImage(
                                        mediaImage,
                                        it.imageInfo.rotationDegrees
                                    )
                                //从摄像头获取media.Image类型数据后调用process方法
                                scanner.process(image)
                                    .addOnSuccessListener { barCodes ->
                                        when {
                                            barCodes.size > 0 -> {
                                                var qrCode = if (barCodes.size == 1) {
                                                    var consoleResult = barCodes[0].displayValue
                                                    consoleResult
                                                } else {
                                                    var sb = StringBuilder()
                                                    for (i in barCodes.indices) {
                                                        sb.append("$i:" + barCodes[i].displayValue + "\t")
                                                    }
                                                    sb.toString()
                                                }
                                                Console.writeLine("QRCode content: $qrCode")
                                                Thread.sleep(500)
                                            }
                                            else -> {
                                                //没扫出东西
                                            }
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        e.printStackTrace()
                                    }
                                    .addOnCompleteListener { q ->
                                        mediaImage.close()
                                        it.close()
                                    }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                )
            } catch (e: InterruptedException) {
                // Currently no exceptions thrown. cameraProviderFuture.get()
                // shouldn't block since the listener is being called, so no need to
                // handle InterruptedException.
                e.printStackTrace()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(this))

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //开始扫描吧
            startCamera()
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        const val REQUEST_CAMERA = 0x99
        val cameraPermission = arrayOf(Manifest.permission.CAMERA)
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS, Barcode.FORMAT_UNKNOWN).build()
    }
}