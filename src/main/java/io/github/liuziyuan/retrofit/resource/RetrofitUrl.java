package io.github.liuziyuan.retrofit.resource;

import io.github.liuziyuan.retrofit.util.RetrofitUrlUtils;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

/**
 * RetrofitUrl object
 *
 * @author liuziyuan
 */
@Getter
public class RetrofitUrl {

    private final Environment environment;
    private final String inputBaseUrl;
    private final String inputRetrofitDynamicBaseUrl;
    private final BaseUrl defaultUrl;
    private final BaseUrl dynamicUrl;
    private final String retrofitUrlPrefix;
    private boolean isDynamicUrl;


    public RetrofitUrl(String baseUrl, String inputRetrofitDynamicBaseUrl, String retrofitUrlPrefix, Environment environment) {
            this.environment = environment;
            this.inputBaseUrl = baseUrl;
            this.inputRetrofitDynamicBaseUrl = inputRetrofitDynamicBaseUrl;
            this.retrofitUrlPrefix = getUrlByConvertBaseUrl(retrofitUrlPrefix, environment, false);
            String url;
            if (StringUtils.isNotEmpty(inputRetrofitDynamicBaseUrl)) {
                this.isDynamicUrl = true;
                url = RetrofitUrlUtils.convertBaseUrl(inputRetrofitDynamicBaseUrl, environment, true);
                dynamicUrl = new BaseUrl(url);
            } else {
                dynamicUrl = new BaseUrl();
            }
            url = RetrofitUrlUtils.convertBaseUrl(baseUrl, environment, true);
            defaultUrl = new BaseUrl(url);
    }

    private String getUrlByConvertBaseUrl(String url, Environment environment, boolean checkUrl) {
        if (url != null) {
            return RetrofitUrlUtils.convertBaseUrl(url, environment, checkUrl);
        } else {
            return StringUtils.EMPTY;
        }
    }


}
