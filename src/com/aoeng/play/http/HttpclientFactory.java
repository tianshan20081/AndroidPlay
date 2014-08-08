package com.aoeng.play.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

public class HttpclientFactory {
	private static final int MAX_CONNECTIONS = 10;
	private static final int TIMEOUT = 10 * 1000;
	private static final int SOCKET_BUFFER_SIZE = 8 * 1024;
	private static final int MAX_RETRIES = 5;
	private static final String HEADER_ACCEPT_ENCODING = "accept-Encoding";
	private static final String ENCODING_GZIP = "gzip";

	public static DefaultHttpClient create(boolean isHttps) {
		// TODO Auto-generated method stub
		HttpParams params = createHttpParams();
		DefaultHttpClient httpClient = null;
		if (isHttps) {
			SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			schemeRegistry.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 443));
			ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(
					params, schemeRegistry);
			httpClient = new DefaultHttpClient(cm, params);
		} else {
			httpClient = new DefaultHttpClient();
		}
		return null;
	}

	private static HttpParams createHttpParams() {
		// TODO Auto-generated method stub
		final HttpParams params = new BasicHttpParams();
		// 设置是否启用旧连接检查，默认是开启的。关闭这个旧连接检查可以提高一点点性能，但是增加了I/O错误的风险（当服务端关闭连接时）。
		// 开启这个选项则在每次使用老的连接之前都会检查连接是否可用，这个耗时大概在15-30ms之间
		HttpConnectionParams.setStaleCheckingEnabled(params, false);
		HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);// 设置链接超时时间
		HttpConnectionParams.setSoTimeout(params, TIMEOUT);// 设置socket超时时间
		HttpConnectionParams.setSocketBufferSize(params, SOCKET_BUFFER_SIZE);// 设置缓存大小
		HttpConnectionParams.setTcpNoDelay(params, true);// 是否不使用延迟发送(true为不延迟)
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1); // 设置协议版本
		HttpProtocolParams.setUseExpectContinue(params, true);// 设置异常处理机制
		HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);// 设置编码
		HttpClientParams.setRedirecting(params, false);// 设置是否采用重定向

		ConnManagerParams.setTimeout(params, TIMEOUT);// 设置超时
		ConnManagerParams.setMaxConnectionsPerRoute(params,
				new ConnPerRouteBean(MAX_CONNECTIONS));// 多线程最大连接数
		ConnManagerParams.setMaxTotalConnections(params, 10); // 多线程总连接数
		return params;
	}

	private static void createHttpClient(DefaultHttpClient httpClient) {
		httpClient.addRequestInterceptor(new HttpRequestInterceptor() {

			@Override
			public void process(HttpRequest request, HttpContext context)
					throws HttpException, IOException {
				// TODO Auto-generated method stub
				if (!request.containsHeader(HEADER_ACCEPT_ENCODING)) {
					request.addHeader(HEADER_ACCEPT_ENCODING, ENCODING_GZIP);
				}
			}
		});
		httpClient.addResponseInterceptor(new HttpResponseInterceptor() {

			@Override
			public void process(HttpResponse response, HttpContext context)
					throws HttpException, IOException {
				// TODO Auto-generated method stub
				final HttpEntity entity = response.getEntity();
				if (null == entity) {
					return;
				}
				final Header encoding = entity.getContentEncoding();
				if (encoding != null) {
					for (HeaderElement element : encoding.getElements()) {
						if (element.getName().equalsIgnoreCase(ENCODING_GZIP)) {
							response.setEntity(new InflatingEntity(response
									.getEntity()));
							break;
						}
					}
				}

			}
		});
		httpClient.setHttpRequestRetryHandler(new HttpRetry(MAX_RETRIES));
	}

	public static class InflatingEntity extends HttpEntityWrapper {

		public InflatingEntity(HttpEntity wrapped) {
			super(wrapped);
			// TODO Auto-generated constructor stub
		}
		@Override
		public InputStream getContent() throws IOException {
			// TODO Auto-generated method stub
			return new GZIPInputStream(wrappedEntity.getContent());
		}
		@Override
		public long getContentLength() {
			// TODO Auto-generated method stub
			return -1;
		}

	}
}
