package jp.co.arsaga.extensions.view.baseLayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import jp.co.arsaga.extensions.view.R

abstract class BaseWebViewFragment : Fragment() {

    protected fun getWebView(): WebView? = view?.findViewById(R.id.webView)

    protected fun getSwipeView(): SwipeRefreshLayout? = view?.findViewById(R.id.reloadWebView)

    open class Client(private val swipeViewQuery: () -> SwipeRefreshLayout?) : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            swipeViewQuery()?.isRefreshing = false
        }
    }

    open val webViewClient: Client by lazy {
        Client(::getSwipeView)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.base_web_view, container, false)

    override fun onStart() {
        super.onStart()
        getWebView()?.let { webView ->
            initWebView(webView)
            webView.webViewClient = webViewClient
        }
        getSwipeView()?.setOnRefreshListener {
            getWebView()?.reload()
        }
    }

    abstract fun initWebView(webView: WebView)

}