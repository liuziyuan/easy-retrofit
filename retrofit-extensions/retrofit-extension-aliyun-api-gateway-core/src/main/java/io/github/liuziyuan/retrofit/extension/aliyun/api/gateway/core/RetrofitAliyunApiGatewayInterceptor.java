package io.github.liuziyuan.retrofit.extension.aliyun.api.gateway.core;

import com.alibaba.cloudapi.sdk.client.OkHttp3Client;
import com.alibaba.cloudapi.sdk.constant.HttpConstant;
import com.alibaba.cloudapi.sdk.enums.HttpConnectionModel;
import com.alibaba.cloudapi.sdk.model.*;
import com.alibaba.cloudapi.sdk.util.ApiRequestMaker;
import com.alibaba.cloudapi.sdk.util.HttpCommonUtil;
import io.github.liuziyuan.retrofit.core.extension.BaseInterceptor;
import okhttp3.*;
import okio.Buffer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RetrofitAliyunApiGatewayInterceptor extends BaseInterceptor {

    private final BaseClientInitialParam builderParam;

    public RetrofitAliyunApiGatewayInterceptor(BaseClientInitialParam builderParam) {
        this.builderParam = builderParam;
    }

    @Override
    protected Response executeIntercept(Chain chain) throws IOException {
        Request request = chain.request();
        String requestContentType = request.header(HttpConstant.CLOUDAPI_HTTP_HEADER_CONTENT_TYPE) + ";  charset=utf-8";
        try (Buffer buffer = new Buffer()) {
            if (request.body() != null) {
                request.body().writeTo(buffer);
            }
            byte[] bytes = buffer.readByteArray();
            HttpMethodModel httpMethodModel = new HttpMethodModel(request.method(), requestContentType, HttpConstant.CLOUDAPI_CONTENT_TYPE_JSON);
            ApiRequest apiRequest = new ApiRequest(httpMethodModel, request.url().uri().getPath(), bytes);
            return chain.proceed(buildRequest(apiRequest));
        }
    }

    private Request buildRequest(ApiRequest request) {
        if (request.getHttpConnectionMode() == HttpConnectionModel.SINGER_CONNECTION) {
            request.setHost(builderParam.getHost());
            request.setScheme(builderParam.getScheme());
        }

        ApiRequestMaker.make(request, builderParam.getAppKey(), builderParam.getAppSecret());
        RequestBody requestBody = null;
        if (null != request.getFormParams() && request.getFormParams().size() > 0) {
            requestBody = RequestBody.create(MediaType.parse(request.getFirstHeaderValue(HttpConstant.CLOUDAPI_HTTP_HEADER_CONTENT_TYPE)), HttpCommonUtil.buildParamString(request.getFormParams()));
        }
        /**
         *  如果类型为byte数组的body不为空
         *  将body中的内容MD5算法加密后再采用BASE64方法Encode成字符串，放入HTTP头中
         *  做内容校验，避免内容在网络中被篡改
         */
        else if (null != request.getBody() && request.getBody().length > 0) {
            requestBody = RequestBody.create(MediaType.parse(request.getFirstHeaderValue(HttpConstant.CLOUDAPI_HTTP_HEADER_CONTENT_TYPE)), request.getBody());
        }
        return new Request.Builder()
                .method(request.getMethod().getValue(), requestBody)
                .url(request.getUrl())
                .headers(getHeadersFromMap(request.getHeaders()))
                .build();
    }

    private Headers getHeadersFromMap(Map<String, List<String>> map) {
        List<String> nameAndValues = new ArrayList<String>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            for (String value : entry.getValue()) {
                nameAndValues.add(entry.getKey());
                nameAndValues.add(value);
            }
        }

        return Headers.of(nameAndValues.toArray(new String[nameAndValues.size()]));

    }

    private ApiResponse getApiResponse(ApiRequest request, Response response) throws IOException {
        ApiResponse apiResponse = new ApiResponse(response.code());
        apiResponse.setHeaders(response.headers().toMultimap());
        apiResponse.setBody(response.body().bytes());
        apiResponse.setContentType(response.header(HttpConstant.CLOUDAPI_HTTP_HEADER_CONTENT_TYPE, ""));

        return apiResponse;
    }

    /**
     * 暂时不支持同名header
     *
     * @param headers
     * @return
     */
    private Map<String, String> getSimpleMapFromRequest(Headers headers) {
        Map<String, List<String>> complexMap = headers.toMultimap();
        Map<String, String> simpleMap = new HashMap<String, String>();
        for (Map.Entry<String, List<String>> entry : complexMap.entrySet()) {
            simpleMap.put(entry.getKey(), entry.getValue().get(0));
        }

        return simpleMap;
    }
}
