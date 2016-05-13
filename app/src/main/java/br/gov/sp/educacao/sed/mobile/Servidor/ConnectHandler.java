package br.gov.sp.educacao.sed.mobile.Servidor;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import br.gov.sp.educacao.sed.mobile.Enuns.TipoServicoEnum;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.Util.Queries;

/**
 * Created by techresult on 26/08/2014.
 */
public class ConnectHandler extends AsyncTask<ParametroJson, Integer, RetornoJson> {

    @Override
    protected RetornoJson doInBackground(ParametroJson... params) {
        Log.i("Json dados Conect", params[0].getTipoServicoEnum().toString());
        RetornoJson retornoJson;
        publishProgress(1);
        try {
            HttpResponse response = null;

            if(TipoServicoEnum.DADOS_OFF_LINE.equals(params[0].getTipoServicoEnum())){
                //response = connectionGet(params[0]);
                InputStream is = params[0].getContext().getAssets().open("responseoffline.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                final String resultString = new String(buffer, "UTF-8");
                retornoJson = new RetornoJson(204, resultString);

            }else{
                //response = connectionPost(params[0]);
                publishProgress(4);

                InputStream is = params[0].getContext().getAssets().open("responselogin.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                final String resultString = new String(buffer, "UTF-8");
                retornoJson = new RetornoJson(200, resultString);
            }


        }catch (Exception e){
            retornoJson = new RetornoJson(999, e.getMessage());
            e.printStackTrace();
        }

        publishProgress(10);

        return retornoJson;
    }

    private HttpResponse connectionPost(ParametroJson parametroJson) throws Exception{
        HttpContext localContext = new BasicHttpContext();
        HttpClient client = getNewHttpClient();
        HttpPost post = new HttpPost(parametroJson.getTipoServicoEnum().toString());
        UsuarioTO usuario = Queries.getUsuarioAtivo(parametroJson.getContext());
        publishProgress(2);

        if (usuario != null && usuario.getToken() != null) {
            post.setHeader("Authorization", usuario.getToken());
        }

        post.setHeader("Content-type", "application/json");
        post.setHeader("Accept-Encoding", "gzip,deflate,sdch");

        post.setEntity(new StringEntity(parametroJson.getJsonObject(), "UTF-8"));

        publishProgress(3);
        HttpResponse response = client.execute(post, localContext);

        return response;
    }

    private HttpResponse connectionGet(ParametroJson parametroJson) throws Exception{
        HttpContext localContext = new BasicHttpContext();
        HttpClient client = getNewHttpClient();


        HttpGet httpGet = new HttpGet(parametroJson.getTipoServicoEnum().toString());
        UsuarioTO usuario = Queries.getUsuarioAtivo(parametroJson.getContext());
        publishProgress(2);

        httpGet.setHeader("Authorization", usuario.getToken());
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("Accept-Encoding", "gzip,deflate,sdch");

        publishProgress(3);
        HttpResponse response = client.execute(httpGet, localContext);

        return response;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(RetornoJson retornoJson) {
        super.onPostExecute(retornoJson);
    }

    private class CustomX509TrustManager implements X509TrustManager{

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    private class MySSLSocketFactory1 extends SSLSocketFactory{
        SSLContext sslContext = SSLContext.getInstance("TLS");

        public MySSLSocketFactory1(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
            super(truststore);

            TrustManager tm = new CustomX509TrustManager();
            sslContext.init(null, new TrustManager[]{tm}, null);
        }

        public MySSLSocketFactory1(SSLContext context) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
            super(null);
            sslContext = context;
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException{
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }

    }

    private HttpClient getNewHttpClient() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[]{new CustomX509TrustManager()},
                    new SecureRandom());

            HttpClient client = new DefaultHttpClient();

            SSLSocketFactory ssf = new MySSLSocketFactory1(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setSoTimeout(params, 50000000);
            HttpConnectionParams.setConnectionTimeout(params, 50000000);
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            ClientConnectionManager ccm = client.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            sr.register(new Scheme("https", ssf, 443));
            return new DefaultHttpClient(ccm, params);

        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

    //TODO melhorar essa rotina para convert Stream to Classe mapeada utilizar o GSon
    private String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {

                e.printStackTrace();

            }
        }
        return sb.toString();
    }
}
