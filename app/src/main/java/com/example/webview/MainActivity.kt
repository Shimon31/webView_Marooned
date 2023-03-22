package com.example.webview

import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.webview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isOnline()){
            binding.webView.loadUrl("https://maroonedbd.com/")
            binding.webView.settings.javaScriptEnabled = true

            binding.webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    if (url != null) {
                        view?.loadUrl(url)
                    }
                    return true
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)

                    binding.webView.visibility = View.GONE
                    binding.loadingBar.visibility = View.VISIBLE

                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                    binding.webView.visibility = View.VISIBLE
                    binding.loadingBar.visibility = View.GONE

                }
            }
        }

        else{
            binding.networkTV.visibility = View.VISIBLE
            binding.webView.visibility = View.GONE
            binding.loadingBar.visibility = View.GONE
        }



    }

    override fun onBackPressed() {

        if (binding.webView.canGoBack()){
            binding.webView.goBack()
        }else{
            super.onBackPressed()
        }


    }

    fun isOnline(): Boolean{
    var sysService =getSystemService(CONNECTIVITY_SERVICE)  as ConnectivityManager
        var networkInfo : NetworkInfo? = sysService.activeNetworkInfo
        return networkInfo?.isConnected ?: false
    }

}