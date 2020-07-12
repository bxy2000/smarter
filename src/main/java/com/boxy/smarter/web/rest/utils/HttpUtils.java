package com.boxy.smarter.web.rest.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    private final static Logger log = LoggerFactory.getLogger(HttpUtils.class);

    public static JSONObject request(String url, String method, String output) {
        JSONObject json = null;
        StringBuffer buffer = new StringBuffer();

        HttpURLConnection con = null;
        try {
            URL uri = new URL(url);
            con = (HttpURLConnection) uri.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestMethod(method);

            con.setReadTimeout(60000);
            con.setConnectTimeout(60000);

            if (output != null) {
                OutputStream os = con.getOutputStream();
                try {
                    os.write(output.getBytes("UTF-8"));
                } catch (Exception e) {
                    log.info("HttpInvoker exec error: {}", e);
                } finally {
                    if (os != null) {
                        try {
                            os.close();
                        } catch (IOException e) {
                            log.info("HttpInvoker exec error: {}", e);
                        }
                    }
                }
            }

            InputStream is = null;
            InputStreamReader inputReader = null;
            BufferedReader reader = null;
            try {
                is = con.getInputStream();
                inputReader = new InputStreamReader(is, "UTF-8");
                reader = new BufferedReader(inputReader);
                String temp;
                while ((temp = reader.readLine()) != null) {
                    buffer.append(temp);
                }
            } catch (Exception e) {
                log.info("HttpInvoker exec error: {}", e);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        log.info("HttpInvoker exec error: {}", e);
                    }
                }
                if (inputReader != null) {
                    try {
                        inputReader.close();
                    } catch (IOException e) {
                        log.info("HttpInvoker exec error: {}", e);
                    }
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        log.info("HttpInvoker exec error: {}", e);
                    }
                }
            }

            // con.disconnect();
            json = JSONObject.parseObject(buffer.toString());

            if (json != null) {
                log.debug("OK, http连接Url: {}, 返回数据,json: {}", url, json);
            } else {
                log.debug("return json is null, http连接Url: {}, 返回数据,json: {}", url, json);
            }
        } catch (IOException e) {
            log.error("HttpInvoker exec error: {}", e);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        return json;
    }
}
