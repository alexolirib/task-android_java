package com.alexolirib.tasks.repository.api;

import android.content.Context;

import com.alexolirib.tasks.constants.TaskConstants;
import com.alexolirib.tasks.entities.APIResponse;
import com.alexolirib.tasks.entities.FullParameters;
import com.alexolirib.tasks.infra.InternetNotAvailableException;
import com.alexolirib.tasks.utils.NetworkUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;

public class ExternalRepository {

    private Context mContext;

    public ExternalRepository(Context context){
        this.mContext = context;
    }

    public APIResponse execute(FullParameters parameters) throws InternetNotAvailableException{

        if(!NetworkUtils.isConnectionAvailable(this.mContext)){
            throw new InternetNotAvailableException();
        }

        APIResponse response;
        InputStream inputStream;
        //meio de se conectar com API
        HttpURLConnection conn;

        try{
            //obtem url
            URL url;
            if(parameters.getMethod() == TaskConstants.OPERATION_METHOD.GET){
                url = new URL(parameters.getUrl() + getQuery(parameters.getParameters(), parameters.getMethod()));
            } else{
                url = new URL(parameters.getUrl());
            }

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(100000);
            conn.setConnectTimeout(150000);
            conn.setRequestMethod(parameters.getMethod());
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);

            if(parameters.getHeaderParameters() != null){
                Iterator it = parameters.getHeaderParameters().entrySet().iterator();
                while (it.hasNext()){
                    Map.Entry pair = (Map.Entry) it.next();
                    conn.setRequestProperty(pair.getKey().toString(), pair.getValue().toString());
                    it.remove();
                }
            }

            if(parameters.getMethod() !=  TaskConstants.OPERATION_METHOD.GET){
                String query = getQuery(parameters.getParameters(), parameters.getMethod());
                byte[] postDataBytes = query.getBytes("UTF-8");
                int postDataBytesLength = postDataBytes.length;

                conn.setRequestProperty("Content-Length", Integer.toString(postDataBytesLength));
                //está indo corpo na request
                conn.setDoOutput(true);
                conn.getOutputStream().write(postDataBytes);
            }

            conn.connect();
            //lendo a resposta da api
            if(conn.getResponseCode() == TaskConstants.STATUS_CODE.SUCCESS){
                inputStream = conn.getInputStream();
                response = new APIResponse(getStringFromInputStream(inputStream), conn.getResponseCode());
            }else {
                inputStream = conn.getErrorStream();
                response = new APIResponse(getStringFromInputStream(inputStream), conn.getResponseCode());
            }
            inputStream.close();
            conn.disconnect();

        } catch (Exception e){
            response = new APIResponse("", TaskConstants.STATUS_CODE.NOT_FOUND);
        }

        return response;
    }

    private String getStringFromInputStream(InputStream inputStream) {
        if(inputStream == null){
            return "";
        }

        BufferedReader br;
        StringBuilder builder = new StringBuilder();

        String line;
        try {
            //preciso ler o inputStream(que é preciso do buffered
            br = new BufferedReader(new InputStreamReader(inputStream));
            while((line = br.readLine())!= null ){
                builder.append(line);
            }

            br.close();
        }catch (Exception e){
            return "";
        }
        return builder.toString();
    }

    private String getQuery(AbstractMap<String, String> params, String method) throws UnsupportedEncodingException {
        //Task/Overdue?pagesize=50&page=3(query string) - depois do "?" vem com chave e valor
        if(params == null){
            return "";
        }
        StringBuilder builder = new StringBuilder();
        //pois começa com "?"
        boolean first = true;

        for(Map.Entry<String, String> e: params.entrySet()){
            if(first){
                if(method == TaskConstants.OPERATION_METHOD.GET){
                    builder.append("?");
                }
                first = false;
            } else{
                builder.append("&");
            }
            //pode ser que tenha caractere especial, tratando aqui
            builder.append(URLEncoder.encode(e.getKey(), "UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(e.getValue(), "UTF-8"));
        }
        return builder.toString();
    }
}
